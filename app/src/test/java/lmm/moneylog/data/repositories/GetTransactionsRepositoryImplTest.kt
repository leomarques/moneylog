package lmm.moneylog.data.repositories

import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import lmm.moneylog.data.database.TransactionDao
import lmm.moneylog.data.database.TransactionEntity
import org.junit.Test
import kotlin.test.assertEquals

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
                )
            )
        ).asFlow()

        runBlocking {
            val trans = repository.getAllTransactions().first()[0]
            assertEquals(0.5, trans.value)
            assertEquals("", trans.description)
            assertEquals(1, trans.date.year)
            assertEquals(2, trans.date.month)
            assertEquals(3, trans.date.day)
        }
    }

}
