package lmm.moneylog.ui.features.creditcard.homecard.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import lmm.moneylog.data.creditcard.repositories.interfaces.GetCreditCardsRepository
import lmm.moneylog.data.time.repositories.DomainTimeRepository
import lmm.moneylog.data.transaction.repositories.interfaces.GetTransactionsRepository
import lmm.moneylog.ui.features.creditcard.homecard.model.CreditCardHomeCardItem
import lmm.moneylog.ui.features.creditcard.homecard.model.CreditHomeCardUIState
import kotlin.math.abs

class CreditHomeCardViewModel(
    getCreditCardsRepository: GetCreditCardsRepository,
    getTransactionsRepository: GetTransactionsRepository,
    domainTimeRepository: DomainTimeRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(CreditHomeCardUIState())
    val uiState: StateFlow<CreditHomeCardUIState> = _uiState.asStateFlow()

    init {
        val invoiceCode = domainTimeRepository.getCurrentInvoice()

        viewModelScope.launch {
            getCreditCardsRepository.getCreditCards().flatMapLatest { creditCards ->
                combine(
                    creditCards.map { creditCard ->
                        getTransactionsRepository.getTransactionsByInvoice(
                            invoiceCode = invoiceCode,
                            creditCardId = creditCard.id
                        ).map { transactions -> creditCard to transactions.sumOf { it.value } }
                    }
                ) { creditCardWithTransactions ->
                    creditCardWithTransactions.map { (creditCard, valueSum) ->
                        CreditCardHomeCardItem(
                            id = creditCard.id,
                            name = creditCard.name,
                            value = abs(valueSum),
                            color = creditCard.color
                        )
                    }
                }
            }.collect { creditCardsWithValues ->
                _uiState.update {
                    CreditHomeCardUIState(
                        cards = creditCardsWithValues,
                        invoiceCode = invoiceCode
                    )
                }
            }
        }
    }
}
