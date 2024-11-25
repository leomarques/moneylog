package lmm.moneylog.data.balance.interactors

import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import lmm.moneylog.data.balance.repositories.GetBalanceRepository
import lmm.moneylog.data.balance.model.TransactionBalance
import org.junit.Before
import org.junit.Test

class GetBalanceInteractorTest {
    private val getBalanceRepository = mockk<GetBalanceRepository>()
    private val interactor = GetBalanceInteractor(getBalanceRepository)

    @Before
    fun setUp() {
        every { getBalanceRepository.getAllTransactionsValues() } returns
            flow {
                listOf(
                    TransactionBalance(1.0, 5, 2021, 0),
                    TransactionBalance(9.0, 5, 2021, 0),
                    TransactionBalance(-5.0, 5, 2021, 0),
                    TransactionBalance(-5.0, 5, 2021, null)
                )
            }
    }

    @Test
    fun `should calculate correct balance for given month and year`() {
        runBlocking {
            interactor.execute(5, 2021).collect {
                assert(it.total == 5.0)
                assert(it.credit == 9.0)
                assert(it.debt == -5.0)
            }
        }
    }

    @Test
    fun `should not consider unpaid transactions`() {
        runBlocking {
            interactor.execute(5, 2021).collect {
                assert(it.total == 5.0)
                assert(it.credit == 9.0)
                assert(it.debt == -5.0)
            }
        }
    }

    @Test
    fun `should return zero balance for month with no transactions`() {
        runBlocking {
            interactor.execute(6, 2021).collect {
                assert(it.total == 0.0)
                assert(it.credit == 0.0)
                assert(it.debt == 0.0)
            }
        }
    }
}
