package lmm.moneylog.data.backup.utils

import lmm.moneylog.data.account.model.Account
import lmm.moneylog.data.accounttransfer.model.AccountTransfer
import lmm.moneylog.data.backup.model.BackupData
import lmm.moneylog.data.category.model.Category
import lmm.moneylog.data.categorypredictor.model.CategoryKeyword
import lmm.moneylog.data.creditcard.model.CreditCard
import lmm.moneylog.data.time.model.DomainTime
import lmm.moneylog.data.transaction.model.Transaction

@Suppress(
    "MagicNumber",
    "LoopWithTooManyJumpStatements",
    "ComplexCondition",
    "CyclomaticComplexMethod",
    "TooManyFunctions",
    "ReturnCount"
)
class CsvBackupUtil {
    companion object {
        const val ACCOUNTS_HEADER = "ACCOUNTS"
        const val CATEGORIES_HEADER = "CATEGORIES"
        const val CREDIT_CARDS_HEADER = "CREDIT_CARDS"
        const val TRANSACTIONS_HEADER = "TRANSACTIONS"
        const val ACCOUNT_TRANSFERS_HEADER = "ACCOUNT_TRANSFERS"
        const val CATEGORY_KEYWORDS_HEADER = "CATEGORY_KEYWORDS"
        const val SECTION_SEPARATOR = "---"

        private const val ACCOUNTS_MIN_PARTS = 4
        private const val CATEGORIES_MIN_PARTS = 4
        private const val CREDIT_CARDS_MIN_PARTS = 6
        private const val TRANSACTIONS_MIN_PARTS = 6
        private const val TRANSFERS_MIN_PARTS = 7
        private const val KEYWORDS_MIN_PARTS = 3
    }

    fun exportToCsv(data: BackupData): String {
        val sb = StringBuilder()

        appendAccountsSection(sb, data.accounts)
        appendCategoriesSection(sb, data.categories)
        appendCreditCardsSection(sb, data.creditCards)
        appendTransactionsSection(sb, data.transactions)
        appendTransfersSection(sb, data.accountTransfers)
        appendCategoryKeywordsSection(sb, data.categoryKeywords)

        return sb.toString()
    }

    fun importFromCsv(csvContent: String): BackupData {
        val accounts = mutableListOf<Account>()
        val categories = mutableListOf<Category>()
        val creditCards = mutableListOf<CreditCard>()
        val transactions = mutableListOf<Transaction>()
        val accountTransfers = mutableListOf<AccountTransfer>()
        val categoryKeywords = mutableListOf<CategoryKeyword>()

        var currentSection: String? = null
        var skipHeaderLine = false

        csvContent.lines().forEach { rawLine ->
            val line = rawLine.trim()
            if (line.isEmpty()) return@forEach

            val section = parseSectionHeader(line)
            if (section != null) {
                currentSection = section
                skipHeaderLine = true
                return@forEach
            }

            if (skipHeaderLine) {
                skipHeaderLine = false
                return@forEach
            }

            when (currentSection) {
                ACCOUNTS_HEADER -> parseAccountLine(line)?.let(accounts::add)
                CATEGORIES_HEADER -> parseCategoryLine(line)?.let(categories::add)
                CREDIT_CARDS_HEADER -> parseCreditCardLine(line)?.let(creditCards::add)
                TRANSACTIONS_HEADER -> parseTransactionLine(line)?.let(transactions::add)
                ACCOUNT_TRANSFERS_HEADER -> parseAccountTransferLine(line)?.let(accountTransfers::add)
                CATEGORY_KEYWORDS_HEADER -> parseCategoryKeywordLine(line)?.let(categoryKeywords::add)
            }
        }

        return BackupData(
            accounts = accounts,
            categories = categories,
            creditCards = creditCards,
            transactions = transactions,
            accountTransfers = accountTransfers,
            categoryKeywords = categoryKeywords
        )
    }

    private fun appendAccountsSection(sb: StringBuilder, accounts: List<Account>) {
        sb.appendLine("$SECTION_SEPARATOR$ACCOUNTS_HEADER$SECTION_SEPARATOR")
        sb.appendLine("id,name,color,archived")
        accounts.forEach { account ->
            sb.appendLine("${account.id},${escapeCsv(account.name)},${account.color},${account.archived}")
        }
        sb.appendLine()
    }

    private fun appendCategoriesSection(sb: StringBuilder, categories: List<Category>) {
        sb.appendLine("$SECTION_SEPARATOR$CATEGORIES_HEADER$SECTION_SEPARATOR")
        sb.appendLine("id,name,color,isIncome")
        categories.forEach { category ->
            sb.appendLine("${category.id},${escapeCsv(category.name)},${category.color},${category.isIncome}")
        }
        sb.appendLine()
    }

    private fun appendCreditCardsSection(sb: StringBuilder, cards: List<CreditCard>) {
        sb.appendLine("$SECTION_SEPARATOR$CREDIT_CARDS_HEADER$SECTION_SEPARATOR")
        sb.appendLine("id,name,closingDay,dueDay,limit,color")
        cards.forEach { card ->
            sb.appendLine(
                "${card.id},${escapeCsv(card.name)},${card.closingDay},${card.dueDay},${card.limit},${card.color}"
            )
        }
        sb.appendLine()
    }

    private fun appendTransactionsSection(sb: StringBuilder, transactions: List<Transaction>) {
        sb.appendLine("$SECTION_SEPARATOR$TRANSACTIONS_HEADER$SECTION_SEPARATOR")
        sb.appendLine(
            "id,value,description,day,month,year,accountId,categoryId,creditCardId,invoiceMonth,invoiceYear"
        )
        transactions.forEach { transaction ->
            sb.appendLine(
                "${transaction.id},${transaction.value},${escapeCsv(transaction.description)}," +
                    "${transaction.date.day},${transaction.date.month},${transaction.date.year}," +
                    "${transaction.accountId ?: ""},${transaction.categoryId ?: ""}," +
                    "${transaction.creditCardId ?: ""},${transaction.invoiceMonth ?: ""}," +
                    "${transaction.invoiceYear ?: ""}"
            )
        }
        sb.appendLine()
    }

    private fun appendTransfersSection(sb: StringBuilder, transfers: List<AccountTransfer>) {
        sb.appendLine("$SECTION_SEPARATOR$ACCOUNT_TRANSFERS_HEADER$SECTION_SEPARATOR")
        sb.appendLine("id,value,year,month,day,originAccountId,destinationAccountId")
        transfers.forEach { transfer ->
            sb.appendLine(
                "${transfer.id},${transfer.value},${transfer.year},${transfer.month},${transfer.day}," +
                    "${transfer.originAccountId},${transfer.destinationAccountId}"
            )
        }
        sb.appendLine()
    }

    private fun appendCategoryKeywordsSection(sb: StringBuilder, keywords: List<CategoryKeyword>) {
        sb.appendLine("$SECTION_SEPARATOR$CATEGORY_KEYWORDS_HEADER$SECTION_SEPARATOR")
        sb.appendLine("id,categoryId,keyword")
        keywords.forEach { keyword ->
            sb.appendLine("${keyword.id},${keyword.categoryId},${escapeCsv(keyword.keyword)}")
        }
    }

    private fun parseSectionHeader(line: String): String? {
        if (!line.startsWith(SECTION_SEPARATOR) || !line.endsWith(SECTION_SEPARATOR)) return null
        return line.removeSurrounding(SECTION_SEPARATOR, SECTION_SEPARATOR)
    }

    private fun parseAccountLine(line: String): Account? {
        val parts = parseCsvLine(line)
        if (parts.size < ACCOUNTS_MIN_PARTS) return null

        val id = parts[0].toIntOrNull() ?: return null
        val color = parts[2].toLongOrNull() ?: return null

        return Account(
            id = id,
            name = parts[1],
            color = color,
            archived = parts[3].toBooleanStrictOrNull() ?: false
        )
    }

    private fun parseCategoryLine(line: String): Category? {
        val parts = parseCsvLine(line)
        if (parts.size < CATEGORIES_MIN_PARTS) return null

        val id = parts[0].toIntOrNull() ?: return null
        val color = parts[2].toLongOrNull() ?: return null

        return Category(
            id = id,
            name = parts[1],
            color = color,
            isIncome = parts[3].toBooleanStrictOrNull() ?: false
        )
    }

    private fun parseCreditCardLine(line: String): CreditCard? {
        val parts = parseCsvLine(line)
        if (parts.size < CREDIT_CARDS_MIN_PARTS) return null

        return CreditCard(
            id = parts[0].toIntOrNull() ?: return null,
            name = parts[1],
            closingDay = parts[2].toIntOrNull() ?: return null,
            dueDay = parts[3].toIntOrNull() ?: return null,
            limit = parts[4].toIntOrNull() ?: return null,
            color = parts[5].toLongOrNull() ?: return null
        )
    }

    private fun parseTransactionLine(line: String): Transaction? {
        val parts = parseCsvLine(line)
        if (parts.size < TRANSACTIONS_MIN_PARTS) return null

        return Transaction(
            id = parts[0].toLongOrNull() ?: return null,
            value = parts[1].toDoubleOrNull() ?: return null,
            description = parts[2],
            date =
                DomainTime(
                    day = parts[3].toIntOrNull() ?: return null,
                    month = parts[4].toIntOrNull() ?: return null,
                    year = parts[5].toIntOrNull() ?: return null
                ),
            accountId = parts.getOrNull(6).toNullableInt(),
            categoryId = parts.getOrNull(7).toNullableInt(),
            creditCardId = parts.getOrNull(8).toNullableInt(),
            invoiceMonth = parts.getOrNull(9).toNullableInt(),
            invoiceYear = parts.getOrNull(10).toNullableInt()
        )
    }

    private fun parseAccountTransferLine(line: String): AccountTransfer? {
        val parts = parseCsvLine(line)
        if (parts.size < TRANSFERS_MIN_PARTS) return null

        return AccountTransfer(
            id = parts[0].toIntOrNull() ?: return null,
            value = parts[1].toDoubleOrNull() ?: return null,
            year = parts[2].toIntOrNull() ?: return null,
            month = parts[3].toIntOrNull() ?: return null,
            day = parts[4].toIntOrNull() ?: return null,
            originAccountId = parts[5].toIntOrNull() ?: return null,
            destinationAccountId = parts[6].toIntOrNull() ?: return null
        )
    }

    private fun parseCategoryKeywordLine(line: String): CategoryKeyword? {
        val parts = parseCsvLine(line)
        if (parts.size < KEYWORDS_MIN_PARTS) return null

        return CategoryKeyword(
            id = parts[0].toIntOrNull() ?: return null,
            categoryId = parts[1].toIntOrNull() ?: return null,
            keyword = parts[2]
        )
    }

    private fun escapeCsv(value: String): String {
        val needsQuotes = value.contains(",") || value.contains("\"") || value.contains("\n") || value.contains("\r")
        return if (needsQuotes) "\"${value.replace("\"", "\"\"")}\"" else value
    }

    private fun parseCsvLine(line: String): List<String> {
        val result = mutableListOf<String>()
        val sb = StringBuilder()
        var inQuotes = false
        var i = 0

        while (i < line.length) {
            val char = line[i]
            when {
                char == '"' && inQuotes && i + 1 < line.length && line[i + 1] == '"' -> {
                    sb.append('"')
                    i += 2
                    continue
                }
                char == '"' -> inQuotes = !inQuotes
                char == ',' && !inQuotes -> {
                    result.add(sb.toString())
                    sb.clear()
                }
                else -> sb.append(char)
            }
            i++
        }

        result.add(sb.toString())
        return result
    }

    private fun String?.toNullableInt(): Int? = this?.takeIf { it.isNotEmpty() }?.toIntOrNull()
}
