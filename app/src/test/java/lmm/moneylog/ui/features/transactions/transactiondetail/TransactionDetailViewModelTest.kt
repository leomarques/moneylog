package lmm.moneylog.ui.features.transactions.transactiondetail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import io.mockk.called
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import lmm.moneylog.data.account.repositories.interfaces.GetAccountsRepository
import lmm.moneylog.data.category.repositories.interfaces.GetCategoriesRepository
import lmm.moneylog.data.transaction.model.Transaction
import lmm.moneylog.data.transaction.repositories.interfaces.AddTransactionRepository
import lmm.moneylog.data.transaction.repositories.interfaces.DeleteTransactionRepository
import lmm.moneylog.data.transaction.repositories.interfaces.GetTransactionsRepository
import lmm.moneylog.data.transaction.repositories.interfaces.UpdateTransactionRepository
import lmm.moneylog.data.transaction.time.DomainTime
import lmm.moneylog.data.transaction.time.DomainTimeInteractor
import lmm.moneylog.ui.features.transaction.detail.viewmodel.TransactionDetailViewModel
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class TransactionDetailViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var viewModel: TransactionDetailViewModel
    private val getTransactionsInteractor: GetTransactionsRepository = mockk(relaxed = true)
    private val addTransactionInteractor: AddTransactionRepository = mockk()
    private val deleteTransactionInteractor: DeleteTransactionRepository = mockk()
    private val updateTransactionInteractor: UpdateTransactionRepository = mockk(relaxed = true)
    private val getAccountsRepository: GetAccountsRepository = mockk(relaxed = true)
    private val getCategoriesRepository: GetCategoriesRepository = mockk(relaxed = true)
    private val domainTimeInteractor: DomainTimeInteractor = mockk()
    private val domainTime = DomainTime(
        6,
        1,
        2022
    )

    @Before
    fun setup() {
        Dispatchers.setMain(UnconfinedTestDispatcher())

        every { domainTimeInteractor.timeStampToDomainTime(any()) } returns domainTime
        every { domainTimeInteractor.getCurrentTimeStamp() } returns 0L
        every { domainTimeInteractor.getMonthName(any()) } returns ""

        coEvery { addTransactionInteractor.save(any()) } returns Unit

        coEvery { getTransactionsInteractor.getTransactionById(-1) } returns null

        coEvery { getTransactionsInteractor.getTransactionById(1) } returns
            Transaction(
                id = 1,
                value = 50.0,
                description = "description",
                date = DomainTime(
                    day = 0,
                    month = 0,
                    year = 0
                )
            )
    }

    private fun initViewModel(id: Int) {
        viewModel = TransactionDetailViewModel(
            savedStateHandle = SavedStateHandle().also { it["id"] = id },
            getTransactionsRepository = getTransactionsInteractor,
            addTransactionRepository = addTransactionInteractor,
            deleteTransactionRepository = deleteTransactionInteractor,
            updateTransactionRepository = updateTransactionInteractor,
            domainTimeInteractor = domainTimeInteractor,
            getAccountsRepository = getAccountsRepository,
            getCategoriesRepository = getCategoriesRepository
        )
    }

    @Test
    fun `should save income transaction from model`() {
        initViewModel(-1)

        with(viewModel.uiState.value) {
//            value.value = "50.0"
//            description.value = "description"
//            date = domainTime

            viewModel.onFabClick({}) {}

            coVerify {
                addTransactionInteractor.save(
                    Transaction(
                        value = 50.0,
                        description = "description",
                        date = domainTime
                    )
                )
            }
        }
    }

    @Test
    fun `should update income transaction from model`() {
        initViewModel(1)

        val model = viewModel.uiState.value
        with(model) {
//            value.value = "50.0"
//            description.value = "description"
//            date = domainTime

            viewModel.onFabClick(
                onSuccess = {},
                onError = {}
            )

            coVerify {
                updateTransactionInteractor.update(
                    Transaction(
                        id = 1,
                        value = 50.0,
                        description = "description",
                        date = domainTime
                    )
                )
            }
        }
    }

    @Test
    fun `should not save negative number`() {
        initViewModel(-1)

        with(viewModel.uiState.value) {
//            value.value = "-50.0"
//            isIncome.value = true
//            description.value = "description"

            viewModel.onFabClick({}) {}
            verify { addTransactionInteractor wasNot called }
        }
    }

    @Test
    fun `should not edit negative number`() {
        initViewModel(1)

        val model = viewModel.uiState.value
        with(model) {
//            value.value = "-50.0"
//            isIncome.value = true
//            description.value = "description"

            viewModel.onFabClick({}) {}
            verify { updateTransactionInteractor wasNot called }
        }
    }

    @Test
    fun `should not save invalid number`() {
        initViewModel(-1)

        with(viewModel.uiState.value) {
//            value.value = "1,5"
//            isIncome.value = false
//            description.value = "description"

            viewModel.onFabClick({}) {}
            verify { addTransactionInteractor wasNot called }
        }
    }

    @Test
    fun `should not save invalid number 2`() {
        initViewModel(-1)

        with(viewModel.uiState.value) {
//            value.value = "5 5"
//            isIncome.value = false
//            description.value = "description"

            viewModel.onFabClick({}) {}
            verify { addTransactionInteractor wasNot called }
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}
