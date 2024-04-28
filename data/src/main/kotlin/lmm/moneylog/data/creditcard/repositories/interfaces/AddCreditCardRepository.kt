package lmm.moneylog.data.creditcard.repositories.interfaces

import lmm.moneylog.data.creditcard.model.CreditCard

interface AddCreditCardRepository {
    suspend fun save(creditCard: CreditCard)
}
