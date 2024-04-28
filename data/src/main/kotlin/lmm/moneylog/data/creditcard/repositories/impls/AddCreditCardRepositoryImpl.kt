package lmm.moneylog.data.creditcard.repositories.impls

import lmm.moneylog.data.creditcard.database.CreditCardDao
import lmm.moneylog.data.creditcard.database.CreditCardEntity
import lmm.moneylog.data.creditcard.model.CreditCard
import lmm.moneylog.data.creditcard.repositories.interfaces.AddCreditCardRepository

class AddCreditCardRepositoryImpl(
    private val creditCardDao: CreditCardDao
) : AddCreditCardRepository {
    override suspend fun save(creditCard: CreditCard) {
        creditCardDao.insert(
            with(creditCard) {
                CreditCardEntity(
                    name = name,
                    color = color,
                    limit = limit,
                    closingDay = closingDay,
                    dueDay = dueDay
                )
            }
        )
    }
}
