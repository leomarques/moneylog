package lmm.moneylog.data.creditcard.repositories.interfaces

import lmm.moneylog.data.creditcard.model.CreditCard

interface UpdateCreditCardRepository {
    suspend fun update(creditCard: CreditCard)
}
