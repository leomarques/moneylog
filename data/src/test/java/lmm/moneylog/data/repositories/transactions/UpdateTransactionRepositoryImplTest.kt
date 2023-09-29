package lmm.moneylog.data.repositories.transactions

import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import lmm.moneylog.data.transaction.Transaction
import lmm.moneylog.data.transaction.database.TransactionDao
import lmm.moneylog.data.transaction.database.TransactionEntity
import lmm.moneylog.data.transaction.repositories.impls.UpdateTransactionRepositoryImpl
import lmm.moneylog.data.transaction.time.DomainTime
import org.junit.Test

class UpdateTransactionRepositoryImplTest {

    @Test
    fun `should update transactions`() {
        val transactionDaoMock: TransactionDao = mockk(relaxed = true)
        val repository = UpdateTransactionRepositoryImpl(
            transactionDao = transactionDaoMock
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

            coVerify {
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
