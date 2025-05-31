package lmm.moneylog.notification.parser

import lmm.moneylog.notification.config.NotificationConfig
import lmm.moneylog.notification.model.NotificationTransactionInfo
import lmm.moneylog.notification.predictor.CategoryPredictor
import lmm.moneylog.notification.predictor.CreditCardPredictor
import kotlin.text.get

class NubankTransactionParser(
    private val creditCardPredictor: CreditCardPredictor
) : TransactionParser {
    override fun parseTransactionInfo(text: String): NotificationTransactionInfo? {
        val sanitizedText = sanitizeInput(text)
        if (sanitizedText.isBlank()) return null

        return NotificationConfig.Patterns.NUBANK_TRANSACTION_REGEX.find(sanitizedText)?.let { match ->
            val place = sanitizePlace(match.groups["estabelecimento"]?.value)

            if (place.isBlank()) return null

            val categoryId = CategoryPredictor.predictCategory(place)
            val creditCardId = creditCardPredictor.getCreditCardId()

            NotificationTransactionInfo(
                value = sanitizeValue(match.groups["valor"]?.value),
                place = place,
                currency = match.groups["moeda"]?.value ?: "R$",
                categoryId = categoryId,
                creditCardId = creditCardId
            )
        }
    }

    private fun sanitizeInput(text: String): String {
        return text.trim()
            .take(NotificationConfig.Notification.MAX_TEXT_LENGTH)
            .replace(Regex("\\s+"), " ") // Replace multiple spaces with single space
    }

    private fun sanitizeValue(value: String?): String {
        return value?.trim()
            ?.replace(Regex("[^\\d,.]"), "") // Keep only digits, commas, and dots
            ?.takeIf { it.isNotBlank() } ?: ""
    }

    private fun sanitizePlace(place: String?): String {
        return place?.trim()
            ?.replace(Regex("\\s+"), " ") // Replace multiple spaces with single space
            ?.takeIf { it.isNotBlank() } ?: ""
    }
}
