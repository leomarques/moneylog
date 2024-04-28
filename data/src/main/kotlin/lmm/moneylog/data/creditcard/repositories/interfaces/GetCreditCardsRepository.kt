package lmm.moneylog.data.creditcard.repositories.interfaces

import kotlinx.coroutines.flow.Flow
import lmm.moneylog.data.creditcard.model.CreditCard

interface GetCreditCardsRepository {
    fun getCreditCards(): Flow<List<CreditCard>>

    suspend fun getCreditCardsSuspend(): List<CreditCard>

    suspend fun getCreditCardById(id: Int): CreditCard?
}
