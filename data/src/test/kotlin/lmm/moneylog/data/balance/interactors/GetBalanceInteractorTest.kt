package lmm.moneylog.data.balance.interactors

import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.runBlocking
import lmm.moneylog.data.balance.model.TransactionBalance
import lmm.moneylog.data.balance.repositories.GetBalanceRepository
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
                        TransactionBalance(1.0, 5, 2021),
                        TransactionBalance(9.0, 5, 2021),
                        TransactionBalance(-5.0, 5, 2021),
                        TransactionBalance(-5.0, 6, 2021)
                    )
                )
            }.flowOn(Dispatchers.Unconfined)
    }

    @Test
    fun `should calculate correct balance for given month and year`() {
        runBlocking {
            interactor.execute(5, 2021)
                .flowOn(Dispatchers.Unconfined).collect {
                    assert(it.total == 5.0)
                    assert(it.credit == 10.0)
                    assert(it.debt == -5.0)
                }
        }
    }
}
