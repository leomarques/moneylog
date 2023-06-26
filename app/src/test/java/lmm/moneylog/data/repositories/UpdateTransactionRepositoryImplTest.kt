package lmm.moneylog.data.repositories

import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import lmm.moneylog.TestCoroutineDispatcher
import lmm.moneylog.data.database.transaction.TransactionDao
import lmm.moneylog.data.database.transaction.TransactionEntity
import lmm.moneylog.domain.models.Transaction
import lmm.moneylog.domain.time.DomainTime
import org.junit.Test

class UpdateTransactionRepositoryImplTest {

    @Test
    fun `should update transactions`() {
        val transactionDaoMock: TransactionDao = mockk(relaxed = true)
        val repository = UpdateTransactionRepositoryImpl(
            transactionDao = transactionDaoMock,
            coroutineDispatcherProvider = TestCoroutineDispatcher()
        )

        val transaction = Transaction(
            value = 0.5,
            description = "desc",
            date = DomainTime(
                day = 1,
                month = 2,
                year = 2003
            )
        )

        runBlocking {
            repository.update(transaction)

            verify {
                transactionDaoMock.update(
                    TransactionEntity(
                        value = 0.5,
                        description = "desc",
                        year = 2003,
                        month = 2,
                        day = 1
                    )
                )
            }
        }
    }
}
