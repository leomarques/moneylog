package lmm.moneylog.domain.balance.getbalance

import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import lmm.moneylog.domain.balance.getbalance.fakerepos.FakeGetBalanceRepository1
import lmm.moneylog.domain.balance.getbalance.fakerepos.FakeGetBalanceRepository2
import lmm.moneylog.domain.balance.getbalance.fakerepos.FakeGetBalanceRepository3
import lmm.moneylog.domain.balance.getbalance.fakerepos.FakeGetBalanceRepository4
import org.junit.Test

class GetBalanceInteractorTest {

    @Test
    fun `should return correct balance 1`() {
        runBlocking {
            val interactor = GetBalanceInteractor(FakeGetBalanceRepository1())

            val balance = interactor.execute().first()
            assertEquals(6.0, balance.credit)
            assertEquals(0.0, balance.debt)
            assertEquals(6.0, balance.total)
        }
    }

    @Test
    fun `should return correct balance 2`() {
        runBlocking {
            val interactor = GetBalanceInteractor(FakeGetBalanceRepository2())

            val balance = interactor.execute().first()
            assertEquals(11.0, balance.credit)
            assertEquals(20.0, balance.debt)
            assertEquals(-9.0, balance.total)
        }
    }

    @Test
    fun `should return correct balance 3`() {
        runBlocking {
            val interactor = GetBalanceInteractor(FakeGetBalanceRepository3())

            val balance = interactor.execute().first()
            assertEquals(0.0, balance.credit)
            assertEquals(30.0, balance.debt)
            assertEquals(-30.0, balance.total)
        }
    }

    @Test
    fun `should return correct balance 4`() {
        runBlocking {
            val interactor = GetBalanceInteractor(FakeGetBalanceRepository4())

            val balance = interactor.execute().first()
            assertEquals(0.0, balance.credit)
            assertEquals(0.0, balance.debt)
            assertEquals(0.0, balance.total)
        }
    }
}
