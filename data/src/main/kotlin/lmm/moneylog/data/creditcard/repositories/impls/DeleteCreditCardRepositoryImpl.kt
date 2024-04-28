package lmm.moneylog.data.creditcard.repositories.impls

import lmm.moneylog.data.creditcard.database.CreditCardDao
import lmm.moneylog.data.creditcard.repositories.interfaces.DeleteCreditCardRepository

class DeleteCreditCardRepositoryImpl(
    private val creditCardDao: CreditCardDao
) : DeleteCreditCardRepository {
    override suspend fun delete(id: Int) {
        creditCardDao.delete(id)
    }
}
