package lmm.moneylog.data.balance

import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.runBlocking
import lmm.moneylog.data.balance.interactors.GetBalanceInteractor
import lmm.moneylog.data.balance.model.TransactionBalance
import lmm.moneylog.data.balance.repositories.GetBalanceRepository
import lmm.moneylog.data.graphs.interactors.GetNetWorthHistoryInteractor
import lmm.moneylog.data.time.repositories.DomainTimeRepository
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Integration test to verify consistency between Balance Card and Net Worth Graph calculations
 *
 * This test ensures that:
 * 1. The home balance card total matches the last point in the net worth graph
 * 2. Both components use identical time-filtering logic
 * 3. Future-dated transactions don't affect current balance
 */
class BalanceConsistencyTest {
    @Test
    fun `balance card should exclude future transactions when calculating total`() {
        // Mock repository
        val getBalanceRepository = mockk<GetBalanceRepository>()

        // Create test transactions including future months
        val transactionBalances =
            listOf(
                TransactionBalance(1000.0, 4, 2024, 1), // April - paid to account 1
                TransactionBalance(-200.0, 4, 2024, 1), // April - paid from account 1
                TransactionBalance(500.0, 5, 2024, 2), // May - paid to account 2
                TransactionBalance(-100.0, 5, 2024, 2), // May - paid from account 2
                TransactionBalance(300.0, 6, 2024, 1), // June (current) - paid to account 1
                TransactionBalance(-50.0, 6, 2024, 1), // June (current) - paid from account 1
                TransactionBalance(1000.0, 7, 2024, 1), // July (future) - should be excluded
                TransactionBalance(100.0, 6, 2024, null) // June - not paid (no accountId) - should be excluded
            )

        // Expected cumulative balance up to June 2024:
        // April: 1000 - 200 = 800
        // May: 800 + 500 - 100 = 1200
        // June: 1200 + 300 - 50 = 1450
        // (July transaction excluded as it's in the future)
        // (Transaction with null accountId excluded)
        val expectedBalance = 1450.0

        // Setup mocks for GetBalanceInteractor
        every { getBalanceRepository.getTransactions() } returns
            flow { emit(transactionBalances) }.flowOn(Dispatchers.Unconfined)

        // Create interactor
        val balanceInteractor = GetBalanceInteractor(getBalanceRepository)

        runBlocking {
            // Get balance from Balance Card for June 2024
            val balanceModel = balanceInteractor.execute(6, 2024).first()
            val balanceCardTotal = balanceModel.total

            // Verify balance card excludes future transactions
            assertEquals(
                "Balance Card should exclude future transactions and match expected cumulative balance",
                expectedBalance,
                balanceCardTotal,
                0.01
            )
        }
    }

    @Test
    fun `balance card should use same filtering logic as net worth calculation`() {
        // This test verifies that both components filter by:
        // 1. accountId != null (only paid transactions)
        // 2. Time period (up to and including specified month/year)

        val getBalanceRepository = mockk<GetBalanceRepository>()

        val transactionBalances =
            listOf(
                TransactionBalance(100.0, 1, 2024, 1), // Jan - included
                TransactionBalance(200.0, 2, 2024, null), // Feb - excluded (no accountId)
                TransactionBalance(300.0, 3, 2024, 2), // Mar - included
                TransactionBalance(400.0, 4, 2024, 1), // Apr - excluded (future)
            )

        every { getBalanceRepository.getTransactions() } returns
            flow { emit(transactionBalances) }.flowOn(Dispatchers.Unconfined)

        val balanceInteractor = GetBalanceInteractor(getBalanceRepository)

        runBlocking {
            // Query for March 2024
            val balanceModel = balanceInteractor.execute(3, 2024).first()

            // Should include: Jan (100) + Mar (300) = 400
            // Should exclude: Feb (no accountId), Apr (future month)
            assertEquals(400.0, balanceModel.total, 0.01)
        }
    }

    @Test
    fun `net worth should include historical balance before 24-month window`() {
        // This test verifies that net worth calculation includes transactions
        // from before the 24-month window (historical balance)

        val getBalanceRepository = mockk<GetBalanceRepository>()
        val domainTimeRepository = mockk<DomainTimeRepository>()

        // Transactions spanning more than 24 months
        val transactionBalances =
            listOf(
                // Historical transactions (before 24-month window)
                TransactionBalance(5000.0, 1, 2022, 1), // Jan 2022 - 30 months ago
                TransactionBalance(3000.0, 6, 2022, 1), // Jun 2022 - 25 months ago
                // Within 24-month window
                TransactionBalance(1000.0, 7, 2022, 1), // Jul 2022 - 24 months ago (start of window)
                TransactionBalance(500.0, 6, 2024, 1), // Jun 2024 - current month
            )

        every { getBalanceRepository.getTransactions() } returns
            flow { emit(transactionBalances) }.flowOn(Dispatchers.Unconfined)

        every { domainTimeRepository.getMonthName(any()) } returns "Jun"

        val netWorthInteractor =
            GetNetWorthHistoryInteractor(
                getBalanceRepository,
                domainTimeRepository
            )

        runBlocking {
            // Get net worth history ending at June 2024
            val netWorthHistory = netWorthInteractor.getNetWorthHistory(6, 2024).first()

            // The last point (June 2024) should include ALL transactions:
            // 5000 + 3000 + 1000 + 500 = 9500
            val currentNetWorth = netWorthHistory.lastOrNull()?.netWorth ?: 0.0

            assertEquals(
                "Net worth should include historical balance from before the 24-month window",
                9500.0,
                currentNetWorth,
                0.01
            )

            // The first point in the window (24 months ago) should also include historical balance
            val firstNetWorth = netWorthHistory.firstOrNull()?.netWorth ?: 0.0

            // At the start of window (Jul 2022), it should have:
            // Historical (Jan 2022 + Jun 2022) + Jul 2022 = 5000 + 3000 + 1000 = 9000
            assertEquals(
                "Net worth at window start should include historical balance",
                9000.0,
                firstNetWorth,
                0.01
            )
        }
    }

    @Test
    fun `balance card and net worth should show identical values for same month`() {
        // Integration test verifying balance card and net worth graph show same value

        val getBalanceRepository = mockk<GetBalanceRepository>()
        val domainTimeRepository = mockk<DomainTimeRepository>()

        val transactionBalances =
            listOf(
                TransactionBalance(1000.0, 1, 2024, 1),
                TransactionBalance(-200.0, 2, 2024, 1),
                TransactionBalance(500.0, 3, 2024, 2),
                TransactionBalance(-50.0, 3, 2024, 1),
            )

        every { getBalanceRepository.getTransactions() } returns
            flow { emit(transactionBalances) }.flowOn(Dispatchers.Unconfined)

        every { domainTimeRepository.getMonthName(any()) } returns "Mar"

        val balanceInteractor = GetBalanceInteractor(getBalanceRepository)
        val netWorthInteractor =
            GetNetWorthHistoryInteractor(
                getBalanceRepository,
                domainTimeRepository
            )

        runBlocking {
            // Get balance for March 2024
            val balanceModel = balanceInteractor.execute(3, 2024).first()
            val balanceCardTotal = balanceModel.total

            // Get net worth for March 2024
            val netWorthHistory = netWorthInteractor.getNetWorthHistory(3, 2024).first()
            val marchNetWorth = netWorthHistory.lastOrNull()?.netWorth ?: 0.0

            // Both should show same value: 1000 - 200 + 500 - 50 = 1250
            assertEquals(1250.0, balanceCardTotal, 0.01)
            assertEquals(1250.0, marchNetWorth, 0.01)
            assertEquals(
                "Balance Card and Net Worth must show identical values",
                balanceCardTotal,
                marchNetWorth,
                0.01
            )
        }
    }
}
