package lmm.moneylog.data.misc

import java.time.LocalDate
import kotlin.random.Random

/**
 * Shared data generation logic for both database initialization (PopulateDB)
 * and demo mode (DemoDataProvider).
 *
 * This ensures consistency between the two features and provides a single
 * source of truth for sample data generation.
 */
object SampleDataGenerator {
    // Configuration constants
    const val CREDIT_CARD_CLOSING_DAY = 3
    const val CREDIT_CARD_DUE_DAY = 10
    const val CREDIT_CARD_LIMIT = 10000
    const val YEARS_OF_HISTORY = 3L
    const val DEFAULT_TRANSACTION_COUNT = 500
    const val INCOME_PROBABILITY = 0.3
    const val CREDIT_CARD_PROBABILITY = 0.2
    const val MIN_INCOME = 500.0
    const val MAX_INCOME = 15000.0
    const val MIN_EXPENSE = 10.0
    const val MAX_EXPENSE = 500.0

    // Entity IDs
    const val ACCOUNT_ID_NUCONTA = 1
    const val ACCOUNT_ID_SANTANDER = 2
    const val CATEGORY_ID_SALARY = 1
    const val CATEGORY_ID_GIFT = 2
    const val CATEGORY_ID_FOOD = 3
    const val CATEGORY_ID_TRANSPORT = 4
    const val CREDIT_CARD_ID_NUBANK = 1

    // Account data
    object Accounts {
        const val NUCONTA_NAME = "NuConta"
        const val NUCONTA_COLOR = -51457814194814976L

        const val SANTANDER_NAME = "Santander"
        const val SANTANDER_COLOR = -42442007825612800L
    }

    // Category data
    object Categories {
        const val SALARY_NAME = "Salário"
        const val SALARY_COLOR = -59572381806493696L

        const val GIFT_NAME = "Presente"
        const val GIFT_COLOR = -60722264810717184L

        const val FOOD_NAME = "Alimentação"
        const val FOOD_COLOR = -33386490188791808L

        const val TRANSPORT_NAME = "Transporte"
        const val TRANSPORT_COLOR = -44970936109105152L
    }

    // Credit card data
    object CreditCards {
        const val NUBANK_NAME = "Nubank Violeta"
        const val NUBANK_COLOR = -51457814194814976L
    }

    /**
     * Transaction descriptions for income transactions
     */
    val incomeDescriptions =
        listOf(
            "Salário mensal",
            "Bônus",
            "Freelance",
            "Presente em dinheiro",
            "Reembolso",
            "Venda de item",
            "Renda extra"
        )

    /**
     * Transaction descriptions for expense transactions
     */
    val expenseDescriptions =
        listOf(
            "Supermercado",
            "Restaurante",
            "Lanchonete",
            "Padaria",
            "Café",
            "Uber",
            "Ônibus",
            "Metrô",
            "Gasolina",
            "Estacionamento",
            "Farmácia",
            "Cinema",
            "Streaming",
            "Internet",
            "Água",
            "Energia elétrica",
            "Aluguel",
            "Condomínio",
            "Academia",
            "Livros"
        )

    /**
     * Generates a random date between yearsAgo and today
     */
    fun generateRandomDate(today: LocalDate, yearsAgo: LocalDate): LocalDate {
        val daysBetween = yearsAgo.toEpochDay().let { today.toEpochDay() - it }
        val randomDays = Random.nextLong(daysBetween + 1)
        return yearsAgo.plusDays(randomDays)
    }

    /**
     * Generates a random transaction value
     * @param isIncome true for income (positive), false for expense (negative)
     */
    fun generateValue(isIncome: Boolean): Double =
        if (isIncome) {
            Random.nextDouble(MIN_INCOME, MAX_INCOME)
        } else {
            -Random.nextDouble(MIN_EXPENSE, MAX_EXPENSE)
        }

    /**
     * Determines if a transaction should be income based on probability
     */
    fun shouldBeIncome(): Boolean = Random.nextDouble() < INCOME_PROBABILITY

    /**
     * Determines if an expense should use a credit card based on probability
     */
    fun shouldUseCreditCard(): Boolean = Random.nextDouble() < CREDIT_CARD_PROBABILITY

    /**
     * Gets a random description for the transaction
     * @param isIncome true for income, false for expense
     */
    fun getRandomDescription(isIncome: Boolean): String =
        if (isIncome) {
            incomeDescriptions.random()
        } else {
            expenseDescriptions.random()
        }

    /**
     * Data class representing a generated transaction's core data
     */
    data class GeneratedTransactionData(
        val value: Double,
        val description: String,
        val date: LocalDate,
        val categoryId: Int,
        val isIncome: Boolean,
        val useCreditCard: Boolean,
        val accountId: Int?,
        val creditCardId: Int?
    )

    /**
     * Generates transaction data for a single transaction
     *
     * @param accountIds Available account IDs to use
     * @param incomeCategoryIds Available income category IDs
     * @param expenseCategoryIds Available expense category IDs
     * @param creditCardId Credit card ID to use for credit card transactions
     * @return Generated transaction data
     */
    fun generateTransactionData(
        accountIds: List<Int>,
        incomeCategoryIds: List<Int>,
        expenseCategoryIds: List<Int>,
        creditCardId: Int
    ): GeneratedTransactionData {
        val today = LocalDate.now()
        val yearsAgo = today.minusYears(YEARS_OF_HISTORY)
        val randomDate = generateRandomDate(today, yearsAgo)
        val isIncome = shouldBeIncome()
        val value = generateValue(isIncome)
        val categoryId =
            if (isIncome) {
                incomeCategoryIds.random()
            } else {
                expenseCategoryIds.random()
            }
        val description = getRandomDescription(isIncome)
        val useCreditCard = !isIncome && shouldUseCreditCard()

        return GeneratedTransactionData(
            value = value,
            description = description,
            date = randomDate,
            categoryId = categoryId,
            isIncome = isIncome,
            useCreditCard = useCreditCard,
            accountId = if (useCreditCard) null else accountIds.random(),
            creditCardId = if (useCreditCard) creditCardId else null
        )
    }
}
