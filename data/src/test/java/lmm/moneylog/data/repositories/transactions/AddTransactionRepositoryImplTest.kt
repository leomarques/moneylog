package lmm.moneylog.data.repositories.transactions

import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import lmm.moneylog.data.transaction.Transaction
import lmm.moneylog.data.transaction.database.TransactionDao
import lmm.moneylog.data.transaction.database.TransactionEntity
import lmm.moneylog.data.transaction.repositories.impls.AddTransactionRepositoryImpl
import lmm.moneylog.data.transaction.time.DomainTime
import org.junit.Test

class AddTransactionRepositoryImplTest {

    @Test
    fun `should call insert`() {
        val transactionDaoMock: TransactionDao = mockk(relaxed = true)
        val addTransactionRepositoryImpl =
            AddTransactionRepositoryImpl(
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
            addTransactionRepositoryImpl.save(transaction)
            coVerify {
                transactionDaoMock.insert(
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
