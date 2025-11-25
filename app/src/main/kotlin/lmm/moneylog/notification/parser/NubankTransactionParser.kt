package lmm.moneylog.notification.parser

import kotlinx.coroutines.runBlocking
import lmm.moneylog.data.categorypredictor.repositories.interfaces.CategoryKeywordRepository
import lmm.moneylog.data.creditcard.repositories.interfaces.GetCreditCardsRepository
import lmm.moneylog.notification.config.NotificationConfig
import lmm.moneylog.notification.model.NotificationTransactionInfo
import lmm.moneylog.notification.predictor.CreditCardPredictor
import kotlin.text.get

class NubankTransactionParser(
    private val creditCardPredictor: CreditCardPredictor,
    private val getCreditCardsRepository: GetCreditCardsRepository,
    private val categoryKeywordRepository: CategoryKeywordRepository
) : TransactionParser {
    override fun parseTransactionInfo(text: String): NotificationTransactionInfo? {
        val sanitizedText = sanitizeInput(text)
        if (sanitizedText.isBlank()) return null

        return NotificationConfig.Patterns.NUBANK_TRANSACTION_REGEX.find(sanitizedText)
            ?.let { match ->
                val place = sanitizePlace(match.groups["estabelecimento"]?.value)
                val savedCreditCardId = creditCardPredictor.getCreditCardId()
                val creditCardClosingDay = savedCreditCardId?.let { id ->
                    runBlocking {
                        getCreditCardsRepository.getCreditCardById(id)?.closingDay
                    }
                }

                // Use keyword-based prediction
                val categoryId = runBlocking {
                    categoryKeywordRepository.predictCategory(place)
                }

                NotificationTransactionInfo(
                    value = sanitizeValue(match.groups["valor"]?.value),
                    place = place,
                    currency = match.groups["moeda"]?.value ?: "R$",
                    categoryId = categoryId,
                    creditCardId = savedCreditCardId,
                    creditCardClosingDay = creditCardClosingDay
                )
            }
    }

    private fun sanitizeInput(text: String): String {
        return text
            .trim()
            .take(NotificationConfig.Notification.MAX_TEXT_LENGTH)
            .replace(Regex("\\s+"), " ") // Replace multiple spaces with single space
    }

    private fun sanitizeValue(value: String?): String =
        value
            ?.trim()
            ?.replace(Regex("[^\\d,.]"), "") // Keep only digits, commas, and dots
            ?.takeIf { it.isNotBlank() } ?: ""

    private fun sanitizePlace(place: String?): String =
        place
            ?.trim()
            ?.replace(Regex("\\s+"), " ") // Replace multiple spaces with single space
            ?.takeIf { it.isNotBlank() } ?: ""
}
