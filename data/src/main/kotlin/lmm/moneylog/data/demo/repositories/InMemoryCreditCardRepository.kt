package lmm.moneylog.data.demo.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import lmm.moneylog.data.creditcard.model.CreditCard
import lmm.moneylog.data.creditcard.repositories.interfaces.AddCreditCardRepository
import lmm.moneylog.data.creditcard.repositories.interfaces.DeleteCreditCardRepository
import lmm.moneylog.data.creditcard.repositories.interfaces.GetCreditCardsRepository
import lmm.moneylog.data.creditcard.repositories.interfaces.UpdateCreditCardRepository

class InMemoryCreditCardRepository :
    GetCreditCardsRepository,
    AddCreditCardRepository,
    UpdateCreditCardRepository,
    DeleteCreditCardRepository {
    private val creditCards = MutableStateFlow<List<CreditCard>>(emptyList())
    private var nextId = 1

    override fun getCreditCards(): Flow<List<CreditCard>> = creditCards

    override suspend fun getCreditCardsSuspend(): List<CreditCard> = creditCards.value

    override suspend fun getCreditCardById(id: Int): CreditCard? =
        creditCards.value.firstOrNull { it.id == id }

    override suspend fun save(creditCard: CreditCard) {
        val newCreditCard = creditCard.copy(id = nextId++)
        creditCards.value = creditCards.value + newCreditCard
    }

    override suspend fun update(creditCard: CreditCard) {
        creditCards.value =
            creditCards.value.map {
                if (it.id == creditCard.id) creditCard else it
            }
    }

    override suspend fun delete(id: Int) {
        creditCards.value = creditCards.value.filterNot { it.id == id }
    }

    internal fun clear() {
        creditCards.value = emptyList()
        nextId = 1
    }

    internal fun setInitialData(data: List<CreditCard>) {
        creditCards.value = data
        nextId = (data.maxOfOrNull { it.id } ?: 0) + 1
    }
}
