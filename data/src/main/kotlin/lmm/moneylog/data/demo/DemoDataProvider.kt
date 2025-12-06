package lmm.moneylog.data.demo

import lmm.moneylog.data.account.model.Account
import lmm.moneylog.data.category.model.Category
import lmm.moneylog.data.categorypredictor.model.CategoryKeyword
import lmm.moneylog.data.creditcard.model.CreditCard
import lmm.moneylog.data.misc.SampleDataGenerator
import lmm.moneylog.data.misc.SampleDataGenerator.ACCOUNT_ID_NUCONTA
import lmm.moneylog.data.misc.SampleDataGenerator.ACCOUNT_ID_SANTANDER
import lmm.moneylog.data.misc.SampleDataGenerator.CATEGORY_ID_FOOD
import lmm.moneylog.data.misc.SampleDataGenerator.CATEGORY_ID_GIFT
import lmm.moneylog.data.misc.SampleDataGenerator.CATEGORY_ID_SALARY
import lmm.moneylog.data.misc.SampleDataGenerator.CATEGORY_ID_TRANSPORT
import lmm.moneylog.data.misc.SampleDataGenerator.CREDIT_CARD_CLOSING_DAY
import lmm.moneylog.data.misc.SampleDataGenerator.CREDIT_CARD_DUE_DAY
import lmm.moneylog.data.misc.SampleDataGenerator.CREDIT_CARD_ID_NUBANK
import lmm.moneylog.data.misc.SampleDataGenerator.CREDIT_CARD_LIMIT
import lmm.moneylog.data.misc.SampleDataGenerator.DEFAULT_TRANSACTION_COUNT
import lmm.moneylog.data.time.model.DomainTime
import lmm.moneylog.data.transaction.model.Transaction

/**
 * Demo data provider using the same logic as PopulateDB.kt
 * Generates realistic demo data with 500 transactions over 3 years of history
 *
 * This class uses [SampleDataGenerator] to ensure consistency with database initialization.
 */
object DemoDataProvider {
    fun getAccounts(): List<Account> =
        listOf(
            Account(
                id = ACCOUNT_ID_NUCONTA,
                name = SampleDataGenerator.Accounts.NUCONTA_NAME,
                color = SampleDataGenerator.Accounts.NUCONTA_COLOR,
                archived = false
            ),
            Account(
                id = ACCOUNT_ID_SANTANDER,
                name = SampleDataGenerator.Accounts.SANTANDER_NAME,
                color = SampleDataGenerator.Accounts.SANTANDER_COLOR,
                archived = false
            )
        )

    fun getCategories(): List<Category> =
        listOf(
            // Income categories
            Category(
                id = CATEGORY_ID_SALARY,
                name = SampleDataGenerator.Categories.SALARY_NAME,
                color = SampleDataGenerator.Categories.SALARY_COLOR,
                isIncome = true
            ),
            Category(
                id = CATEGORY_ID_GIFT,
                name = SampleDataGenerator.Categories.GIFT_NAME,
                color = SampleDataGenerator.Categories.GIFT_COLOR,
                isIncome = true
            ),
            // Expense categories
            Category(
                id = CATEGORY_ID_FOOD,
                name = SampleDataGenerator.Categories.FOOD_NAME,
                color = SampleDataGenerator.Categories.FOOD_COLOR,
                isIncome = false
            ),
            Category(
                id = CATEGORY_ID_TRANSPORT,
                name = SampleDataGenerator.Categories.TRANSPORT_NAME,
                color = SampleDataGenerator.Categories.TRANSPORT_COLOR,
                isIncome = false
            )
        )

    fun getCreditCards(): List<CreditCard> =
        listOf(
            CreditCard(
                id = CREDIT_CARD_ID_NUBANK,
                name = SampleDataGenerator.CreditCards.NUBANK_NAME,
                closingDay = CREDIT_CARD_CLOSING_DAY,
                dueDay = CREDIT_CARD_DUE_DAY,
                limit = CREDIT_CARD_LIMIT,
                color = SampleDataGenerator.CreditCards.NUBANK_COLOR
            )
        )

    fun getTransactions(): List<Transaction> {
        val transactions = mutableListOf<Transaction>()
        val accountIds = listOf(ACCOUNT_ID_NUCONTA, ACCOUNT_ID_SANTANDER)
        val incomeCategoryIds = listOf(CATEGORY_ID_SALARY, CATEGORY_ID_GIFT)
        val expenseCategoryIds = listOf(CATEGORY_ID_FOOD, CATEGORY_ID_TRANSPORT)

        var transactionId = 1

        repeat(DEFAULT_TRANSACTION_COUNT) {
            val generatedData =
                SampleDataGenerator.generateTransactionData(
                    accountIds = accountIds,
                    incomeCategoryIds = incomeCategoryIds,
                    expenseCategoryIds = expenseCategoryIds,
                    creditCardId = CREDIT_CARD_ID_NUBANK
                )

            val params =
                TransactionParams(
                    id = transactionId++,
                    value = generatedData.value,
                    description = generatedData.description,
                    date = generatedData.date,
                    categoryId = generatedData.categoryId
                )

            val transaction =
                if (generatedData.useCreditCard) {
                    createCreditCardTransaction(
                        params = params,
                        creditCardId = generatedData.creditCardId!!
                    )
                } else {
                    createAccountTransaction(
                        params = params,
                        accountId = generatedData.accountId!!
                    )
                }

            transactions.add(transaction)
        }

        return transactions
    }

    fun getCategoryKeywords(): List<CategoryKeyword> = emptyList()

    private data class TransactionParams(
        val id: Int,
        val value: Double,
        val description: String,
        val date: java.time.LocalDate,
        val categoryId: Int
    )

    private fun createCreditCardTransaction(
        params: TransactionParams,
        creditCardId: Int
    ) = Transaction(
        id = params.id,
        value = params.value,
        description = params.description,
        date =
            DomainTime(
                day = params.date.dayOfMonth,
                month = params.date.monthValue,
                year = params.date.year
            ),
        accountId = null,
        categoryId = params.categoryId,
        creditCardId = creditCardId,
        invoiceMonth = params.date.monthValue,
        invoiceYear = params.date.year
    )

    private fun createAccountTransaction(
        params: TransactionParams,
        accountId: Int
    ) = Transaction(
        id = params.id,
        value = params.value,
        description = params.description,
        date =
            DomainTime(
                day = params.date.dayOfMonth,
                month = params.date.monthValue,
                year = params.date.year
            ),
        accountId = accountId,
        categoryId = params.categoryId,
        creditCardId = null,
        invoiceMonth = null,
        invoiceYear = null
    )
}
