package lmm.moneylog.data.balance.interactors

import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import lmm.moneylog.data.balance.repositories.GetBalanceRepository
import lmm.moneylog.data.transaction.model.TransactionBalance
import org.junit.Before
import org.junit.Test

class GetBalanceInteractorTest {
    private val getBalanceRepository = mockk<GetBalanceRepository>()
    private val interactor = GetBalanceInteractor(getBalanceRepository)

    @Before
    fun setUp() {
        every { getBalanceRepository.getAllTransactionsValues() } returns flow {
            listOf(
                TransactionBalance(1.0, 5, 2021),
                TransactionBalance(9.0, 5, 2021),
                TransactionBalance(-5.0, 5, 2021)
            )
        }
    }

    @Test
    fun execute() {
        runBlocking {
            interactor.execute(5, 2021).collect {
                assert(it.total == 5.0)
                assert(it.credit == 9.0)
                assert(it.debt == -5.0)
            }
        }
    }
}
