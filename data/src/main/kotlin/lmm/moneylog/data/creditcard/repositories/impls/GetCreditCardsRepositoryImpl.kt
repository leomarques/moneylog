package lmm.moneylog.data.creditcard.repositories.impls

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import lmm.moneylog.data.creditcard.database.CreditCardDao
import lmm.moneylog.data.creditcard.model.CreditCard
import lmm.moneylog.data.creditcard.repositories.interfaces.GetCreditCardsRepository

class GetCreditCardsRepositoryImpl(
    private val creditCardDao: CreditCardDao
) : GetCreditCardsRepository {
    override suspend fun getCreditCardById(id: Int): CreditCard? {
        val creditCard = creditCardDao.selectCreditCardById(id)
        return if (creditCard != null) {
            with(creditCard) {
                CreditCard(
                    id = id,
                    name = name,
                    color = color,
                    closingDay = closingDay,
                    dueDay = dueDay,
                    limit = limit,
                )
            }
        } else {
            null
        }
    }

    override fun getCreditCards(): Flow<List<CreditCard>> =
        creditCardDao.selectCreditCards().map { creditCards ->
            creditCards.map { creditCard ->
                with(creditCard) {
                    CreditCard(
                        id = id,
                        name = name,
                        color = color,
                        closingDay = closingDay,
                        dueDay = dueDay,
                        limit = limit,
                    )
                }
            }
        }

    override suspend fun getCreditCardsSuspend(): List<CreditCard> =
        creditCardDao.selectCreditCardsSuspend().map { creditCards ->
            with(creditCards) {
                CreditCard(
                    id = id,
                    name = name,
                    color = color,
                    closingDay = closingDay,
                    dueDay = dueDay,
                    limit = limit,
                )
            }
        }
}
