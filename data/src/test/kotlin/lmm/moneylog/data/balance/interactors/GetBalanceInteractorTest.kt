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
            interactor.execute(5, 2021)
                .flowOn(Dispatchers.Unconfined).collect {
                    assertEquals(0.0, it.total, 0.0)
                    assertEquals(10.0, it.credit, 0.0)
                    assertEquals(5.0, it.debt, 0.0)
                }
        }
    }
}
