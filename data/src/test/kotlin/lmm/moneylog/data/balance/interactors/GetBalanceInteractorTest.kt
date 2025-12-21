package lmm.moneylog.data.balance.interactors

import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.runBlocking
import lmm.moneylog.data.balance.model.TransactionBalance
import lmm.moneylog.data.balance.repositories.GetBalanceRepository
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetBalanceInteractorTest {
    private val getBalanceRepository = mockk<GetBalanceRepository>()
    private val interactor = GetBalanceInteractor(getBalanceRepository)

    @Before
    fun setUp() {
        every { getBalanceRepository.getTransactions() } returns
            flow {
                emit(
                    listOf(
                        TransactionBalance(1.0, 5, 2021, 1),
                        TransactionBalance(9.0, 5, 2021, 1),
                        TransactionBalance(-5.0, 5, 2021, 1),
                        TransactionBalance(-5.0, 6, 2021, 1)
                    )
                )
            }.flowOn(Dispatchers.Unconfined)
    }

    @Test
    fun `should calculate correct balance for given month and year`() {
        runBlocking {
            interactor
                .execute(5, 2021)
                .flowOn(Dispatchers.Unconfined)
                .collect {
                    // Total now only includes transactions up to and including month 5, 2021
                    // Excludes the -5.0 transaction from month 6
                    assertEquals(5.0, it.total, 0.0)
                    assertEquals(10.0, it.credit, 0.0)
                    assertEquals(5.0, it.debt, 0.0)
                }
        }
    }

    @Test
    fun `should include transactions from previous months when calculating total`() {
        every { getBalanceRepository.getTransactions() } returns
            flow {
                emit(
                    listOf(
                        TransactionBalance(100.0, 3, 2021, 1),
                        TransactionBalance(50.0, 4, 2021, 1),
                        TransactionBalance(-30.0, 5, 2021, 1),
                        TransactionBalance(200.0, 6, 2021, 1)
                    )
                )
            }.flowOn(Dispatchers.Unconfined)

        runBlocking {
            interactor
                .execute(5, 2021)
                .flowOn(Dispatchers.Unconfined)
                .collect {
                    // Total should include: 100 (month 3) + 50 (month 4) + (-30) (month 5)
                    // Should exclude: 200 (month 6 - future month)
                    assertEquals(120.0, it.total, 0.0)
                }
        }
    }

    @Test
    fun `should exclude transactions from future years`() {
        every { getBalanceRepository.getTransactions() } returns
            flow {
                emit(
                    listOf(
                        TransactionBalance(100.0, 12, 2020, 1),
                        TransactionBalance(50.0, 1, 2021, 1),
                        TransactionBalance(200.0, 1, 2022, 1)
                    )
                )
            }.flowOn(Dispatchers.Unconfined)

        runBlocking {
            interactor
                .execute(12, 2021)
                .flowOn(Dispatchers.Unconfined)
                .collect {
                    // Total should include: 100 (2020) + 50 (Jan 2021)
                    // Should exclude: 200 (year 2022 - future year)
                    assertEquals(150.0, it.total, 0.0)
                }
        }
    }

    @Test
    fun `should only count transactions with accountId for total balance`() {
        every { getBalanceRepository.getTransactions() } returns
            flow {
                emit(
                    listOf(
                        TransactionBalance(100.0, 5, 2021, 1),
                        TransactionBalance(50.0, 5, 2021, null), // No accountId
                        TransactionBalance(-30.0, 5, 2021, 2)
                    )
                )
            }.flowOn(Dispatchers.Unconfined)

        runBlocking {
            interactor
                .execute(5, 2021)
                .flowOn(Dispatchers.Unconfined)
                .collect {
                    // Total should only include transactions with accountId: 100 + (-30) = 70
                    // Should exclude: 50 (null accountId)
                    assertEquals(70.0, it.total, 0.0)
                }
        }
    }
}
