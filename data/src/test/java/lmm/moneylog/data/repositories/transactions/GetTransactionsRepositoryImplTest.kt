package lmm.moneylog.data.repositories.transactions

import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import lmm.moneylog.data.transaction.database.TransactionDao
import lmm.moneylog.data.transaction.database.TransactionEntity
import lmm.moneylog.data.transaction.repositories.impls.GetTransactionsRepositoryImpl
import org.junit.Test

class GetTransactionsRepositoryImplTest {

    @Test
    fun `should get transactions`() {
        val dao: TransactionDao = mockk()
        val repository = GetTransactionsRepositoryImpl(dao)

        every { dao.selectAllTransactions() } returns listOf(
            listOf(
                TransactionEntity(
                    value = 0.5,
                    description = "",
                    year = 1,
                    month = 2,
                    day = 3
                ).also { it.id = 10 }
            )
        ).asFlow()

        runBlocking {
            val transaction = repository.getAllTransactions().first()[0]
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
}