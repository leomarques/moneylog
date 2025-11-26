package lmm.moneylog.data.creditcard.repositories.impls

import lmm.moneylog.data.creditcard.database.CreditCardDao
import lmm.moneylog.data.creditcard.database.CreditCardEntity
import lmm.moneylog.data.creditcard.model.CreditCard
import lmm.moneylog.data.creditcard.repositories.interfaces.UpdateCreditCardRepository

class UpdateCreditCardRepositoryImpl(
    private val creditCardDao: CreditCardDao
) : UpdateCreditCardRepository {
    override suspend fun update(creditCard: CreditCard) {
        creditCardDao.update(
            with(creditCard) {
                CreditCardEntity(
                    name = name,
                    color = color,
                    closingDay = closingDay,
                    dueDay = dueDay,
                    limit = limit,
                ).also {
                    it.id = id
                }
            }
        )
    }
}
