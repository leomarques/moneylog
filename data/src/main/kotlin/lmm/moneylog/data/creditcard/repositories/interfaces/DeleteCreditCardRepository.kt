package lmm.moneylog.data.creditcard.repositories.interfaces

interface DeleteCreditCardRepository {
    suspend fun delete(id: Int)
}
