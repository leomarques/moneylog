package lmm.moneylog.data.creditcard.interactors

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import lmm.moneylog.data.creditcard.model.CreditCardHomeInfo
import lmm.moneylog.data.creditcard.model.CreditCardHomeInfoResult
import lmm.moneylog.data.creditcard.repositories.interfaces.GetCreditCardsRepository
import lmm.moneylog.data.time.repositories.DomainTimeRepository
import lmm.moneylog.data.transaction.repositories.interfaces.GetTransactionsRepository
import kotlin.math.abs

@OptIn(ExperimentalCoroutinesApi::class)
class GetCreditCardHomeInfoInteractor(
    private val getCreditCardsRepository: GetCreditCardsRepository,
    private val getTransactionsRepository: GetTransactionsRepository,
    private val domainTimeRepository: DomainTimeRepository
) {
    fun execute(): Flow<CreditCardHomeInfoResult> {
        val invoiceCode = domainTimeRepository.getCurrentInvoice()

        return getCreditCardsRepository.getCreditCards().flatMapLatest { creditCards ->
            combine(
                creditCards.map { creditCard ->
                    getTransactionsRepository.getTransactionsByInvoice(
                        invoiceCode = invoiceCode,
                        creditCardId = creditCard.id
                    ).map { transactions -> creditCard to transactions.sumOf { it.value } }
                }
            ) { creditCardWithTransactions ->
                CreditCardHomeInfoResult(
                    cards =
                        creditCardWithTransactions.map { (creditCard, valueSum) ->
                            CreditCardHomeInfo(
                                id = creditCard.id,
                                name = creditCard.name,
                                value = abs(valueSum),
                                color = creditCard.color
                            )
                        },
                    invoiceCode = invoiceCode
                )
            }
        }
    }
}
