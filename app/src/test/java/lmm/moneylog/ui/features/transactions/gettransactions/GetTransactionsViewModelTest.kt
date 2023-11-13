package lmm.moneylog.ui.features.transactions.gettransactions

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import lmm.moneylog.data.account.repositories.interfaces.GetAccountsRepository
import lmm.moneylog.data.category.repositories.GetCategoriesRepository
import lmm.moneylog.data.transaction.Transaction
import lmm.moneylog.data.transaction.repositories.GetTransactionsRepository
import lmm.moneylog.data.transaction.time.DomainTime
import lmm.moneylog.ui.features.transaction.list.viewmodel.GetTransactionsViewModel
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetTransactionsViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var viewModel: GetTransactionsViewModel
    private val getTransactionsRepository: GetTransactionsRepository = mockk()
    private val getAccountsRepository: GetAccountsRepository = mockk()
    private val getCategoriesRepository: GetCategoriesRepository = mockk()

    @Before
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())

        val transaction = Transaction(
            id = 10,
            value = 0.1,
            description = "desc",
            date = DomainTime(
                day = 1,
                month = 2,
                year = 3
            )
        )
        val listAsFlow = listOf(listOf(transaction)).asFlow()

        every { getTransactionsRepository.getIncomeTransactions() } returns listAsFlow
        every { getTransactionsRepository.getOutcomeTransactions() } returns listAsFlow
        every { getTransactionsRepository.getAllTransactions() } returns listAsFlow
    }

    @Test
    fun `should get income transactions`() {
        viewModel = GetTransactionsViewModel(
            typeOfValue = "income",
            getTransactionsRepository = getTransactionsRepository,
            getAccountsRepository = getAccountsRepository,
            getCategoriesRepository = getCategoriesRepository
        )

        val transactions = viewModel.uiState.value.transactions
        assert(transactions.size == 1)

        val model = transactions[0]
        assert(model.isIncome)
        assertEquals(model.value, "R$0,10")
        assertEquals(model.description, "desc")
        assertEquals(model.date, "1/2/3")
    }

    @Test
    fun `should get outcome transactions`() {
        viewModel = GetTransactionsViewModel(
            typeOfValue = "outcome",
            getTransactionsRepository = getTransactionsRepository,
            getAccountsRepository = getAccountsRepository,
            getCategoriesRepository = getCategoriesRepository
        )

        val transactions = viewModel.uiState.value.transactions
        assert(transactions.size == 1)

        val model = transactions[0]
        assert(model.isIncome)
        assertEquals(model.value, "R$0,10")
        assertEquals(model.description, "desc")
        assertEquals(model.date, "1/2/3")
    }

    @Test
    fun `should get all transactions`() {
        viewModel = GetTransactionsViewModel(
            typeOfValue = "all",
            getTransactionsRepository = getTransactionsRepository,
            getAccountsRepository = getAccountsRepository,
            getCategoriesRepository = getCategoriesRepository
        )

        val transactions = viewModel.uiState.value.transactions
        assert(transactions.size == 1)

        val model = transactions[0]
        assert(model.isIncome)
        assertEquals(model.value, "R$0,10")
        assertEquals(model.description, "desc")
        assertEquals(model.date, "1/2/3")
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}
