package lmm.moneylog.data.transaction.nubank.parser

import kotlinx.coroutines.runBlocking
import lmm.moneylog.data.categorypredictor.repositories.interfaces.CategoryKeywordRepository
import lmm.moneylog.data.creditcard.repositories.interfaces.GetCreditCardsRepository
import lmm.moneylog.data.notification.repositories.NotificationSettingsRepository
import lmm.moneylog.data.transaction.nubank.model.NubankTransactionInfo

class NubankTransactionParserImpl(
    private val notificationSettingsRepository: NotificationSettingsRepository,
    private val getCreditCardsRepository: GetCreditCardsRepository,
    private val categoryKeywordRepository: CategoryKeywordRepository
) : NubankTransactionParser {
    override fun parseTransactionInfo(text: String): NubankTransactionInfo? {
        val sanitizedText = sanitizeInput(text)
        if (sanitizedText.isBlank()) return null

        return NUBANK_TRANSACTION_REGEX
            .find(sanitizedText)
            ?.let { match ->
                val place = sanitizePlace(match.groups["estabelecimento"]?.value)
                val savedCreditCardId = notificationSettingsRepository.getDefaultCreditCardId()
                val creditCardClosingDay =
                    savedCreditCardId?.let { id ->
                        runBlocking {
                            getCreditCardsRepository.getCreditCardById(id)?.closingDay
                        }
                    }

                // Use keyword-based prediction
                val categoryId =
                    runBlocking {
                        categoryKeywordRepository.predictCategory(place)
                    }

                NubankTransactionInfo(
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
            .take(MAX_TEXT_LENGTH)
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

    companion object {
        private const val MAX_TEXT_LENGTH = 500

        private val NUBANK_TRANSACTION_REGEX =
            Regex(
                """Compra de (?<moeda>R\$|US\$)\s*(?<valor>(?:\d{1,3}(?:[\.,]\d{3})*|[\d]+)(?:[\.,]\d{2})??)""" +
                    """ APROVADA em\s+(?<estabelecimento>.*?)\s+para o cartão""",
                RegexOption.IGNORE_CASE
            )
    }
}
