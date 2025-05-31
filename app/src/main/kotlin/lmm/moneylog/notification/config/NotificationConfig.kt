package lmm.moneylog.notification.config

object NotificationConfig {
    const val NUBANK_PACKAGE = "com.nu.production"

    val SUPPORTED_PACKAGES =
        setOf(
            NUBANK_PACKAGE
        )

    object Channel {
        const val ID = "moneylog_channel"
        const val NAME = "MoneyLog Notifications"
    }

    object Notification {
        const val MAX_TEXT_LENGTH = 500
    }

    object Patterns {
        val NUBANK_TRANSACTION_REGEX =
            Regex(
                """Compra de (?<moeda>R\$|US\$)\s*(?<valor>(?:\d{1,3}(?:[\.,]\d{3})*|[\d]+)(?:[\.,]\d{2})??)""" +
                    """ APROVADA em\s+(?<estabelecimento>.*?)\s+para o cartão""",
                RegexOption.IGNORE_CASE
            )
    }
}
