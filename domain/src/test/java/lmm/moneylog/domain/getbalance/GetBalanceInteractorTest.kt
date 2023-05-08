package lmm.moneylog.domain.getbalance

import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetBalanceInteractorTest {

    private lateinit var interactor: GetBalanceInteractor

    @Before
    fun setUp() {
        interactor = GetBalanceInteractor(FakeGetBalanceRepository())
    }

    @Test
    fun `should return correct total balance`() {
        runBlocking {
            val balance = interactor.execute().first()
            assertEquals(6.0, balance.total)
        }
    }
}
