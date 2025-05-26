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
        return getCreditCardsRepository.getCreditCards().flatMapLatest { creditCards ->
            combine(
                creditCards.map { creditCard ->
                    val invoiceCode = calculateInvoiceForCard(creditCard.closingDay)

                    getTransactionsRepository.getTransactionsByInvoice(
                        invoiceCode = invoiceCode,
                        creditCardId = creditCard.id
                    ).map { transactions ->
                        Triple(creditCard, transactions.sumOf { it.value }, invoiceCode)
                    }
                }
            ) { creditCardWithTransactions ->
                val firstInvoiceCode =
                    creditCardWithTransactions.firstOrNull()?.third
                        ?: domainTimeRepository.getCurrentInvoice()

                CreditCardHomeInfoResult(
                    cards =
                        creditCardWithTransactions.map { (creditCard, valueSum, _) ->
                            CreditCardHomeInfo(
                                id = creditCard.id,
                                name = creditCard.name,
                                value = abs(valueSum),
                                color = creditCard.color
                            )
                        },
                    invoiceCode = firstInvoiceCode
                )
            }
        }
    }

    private fun calculateInvoiceForCard(closingDay: Int): String {
        val currentTime = domainTimeRepository.getCurrentDomainTime()
        val currentDay = currentTime.day

        return if (currentDay < closingDay) {
            val month = if (currentTime.month == 1) 12 else currentTime.month - 1
            val year = if (currentTime.month == 1) currentTime.year - 1 else currentTime.year
            "$month.$year"
        } else {
            "${currentTime.month}.${currentTime.year}"
        }
    }
}
