package lmm.moneylog.ui.features.gettransactions

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import lmm.moneylog.domain.gettransactions.GetTransactionsInteractor
import lmm.moneylog.domain.models.Transaction
import lmm.moneylog.domain.time.DomainTime
import lmm.moneylog.getOrAwaitValue
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class GetTransactionsViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var viewModel: GetTransactionsViewModel
    private val interactor: GetTransactionsInteractor = mockk()

    @Before
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())

        val transaction = Transaction(
            value = 0.1,
            description = "desc",
            date = DomainTime(
                day = 1,
                month = 2,
                year = 3
            )
        )
        val listAsFlow = listOf(listOf(transaction)).asFlow()

        every { interactor.getIncomeTransactions() } returns listAsFlow
        every { interactor.getOutcomeTransactions() } returns listAsFlow
        every { interactor.getAllTransactions() } returns listAsFlow

        viewModel = GetTransactionsViewModel(interactor)
    }

    @Test
    fun `should get income transactions`() {
        val result = viewModel.getTransactionsModel(getTransactionsIncome)

        val transactions = result.getOrAwaitValue().transactions
        assert(transactions.size == 1)

        val model = transactions[0]
        assert(model.isIncome)
        assertEquals(model.value, "R$0,10")
        assertEquals(model.description, "desc")
        assertEquals(model.date, "1/2/3")
    }

    @Test
    fun `should get outcome transactions`() {
        val result = viewModel.getTransactionsModel(getTransactionsOutcome)

        val transactions = result.getOrAwaitValue().transactions
        assert(transactions.size == 1)

        val model = transactions[0]
        assert(model.isIncome)
        assertEquals(model.value, "R$0,10")
        assertEquals(model.description, "desc")
        assertEquals(model.date, "1/2/3")
    }

    @Test
    fun `should get all transactions`() {
        val result = viewModel.getTransactionsModel(getTransactionsAll)

        val transactions = result.getOrAwaitValue().transactions
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
