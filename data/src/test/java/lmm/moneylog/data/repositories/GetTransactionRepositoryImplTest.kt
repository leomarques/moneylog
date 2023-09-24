package lmm.moneylog.data.repositories

import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import lmm.moneylog.data.transaction.database.TransactionDao
import lmm.moneylog.data.transaction.database.TransactionEntity
import lmm.moneylog.data.transaction.repositories.impls.GetTransactionRepositoryImpl
import org.junit.Test

class GetTransactionRepositoryImplTest {

    private val dao: TransactionDao = mockk()
    private val repository = GetTransactionRepositoryImpl(dao)

    @Test
    fun `should get transactions`() {
        every { dao.selectTransaction(any()) } returns listOf(
            TransactionEntity(
                value = 0.5,
                "",
                1,
                2,
                3
            ).also { it.id = 10 }
        ).asFlow()

        runBlocking {
            val transaction = repository.getTransactionById(10).first()!!
            with(transaction) {
                assertEquals(10, id)
                assertEquals(0.5, value)
                assertEquals("", description)
                assertEquals(1, date.year)
                assertEquals(2, date.month)
                assertEquals(3, date.day)
            }
        }
    }

    @Test
    fun `should not get transactions`() {
        every { dao.selectTransaction(any()) } returns listOf(
            null
        ).asFlow()

        runBlocking {
            val transaction = repository.getTransactionById(0).first()
            assertNull(transaction)
        }
    }
}
