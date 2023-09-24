package lmm.moneylog.ui.features.transactiondetail

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
import lmm.moneylog.data.transaction.Transaction
import lmm.moneylog.data.transaction.repositories.AddTransactionRepository
import lmm.moneylog.data.transaction.repositories.DeleteTransactionRepository
import lmm.moneylog.data.transaction.repositories.GetTransactionRepository
import lmm.moneylog.data.transaction.repositories.UpdateTransactionRepository
import lmm.moneylog.data.transaction.time.DomainTime
import lmm.moneylog.data.transaction.time.DomainTimeConverter
import lmm.moneylog.ui.features.transaction.transactiondetail.TransactionDetailViewModel
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class TransactionDetailViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var viewModel: TransactionDetailViewModel
    private val getTransactionInteractor: GetTransactionRepository = mockk(relaxed = true)
    private val addTransactionInteractor: AddTransactionRepository = mockk()
    private val deleteTransactionInteractor: DeleteTransactionRepository = mockk()
    private val updateTransactionInteractor: UpdateTransactionRepository = mockk(relaxed = true)
    private val domainTimeConverter: DomainTimeConverter = mockk()
    private val domainTime = DomainTime(
        6,
        1,
        2022
    )

    @Before
    fun setup() {
        Dispatchers.setMain(UnconfinedTestDispatcher())

        every { domainTimeConverter.timeStampToDomainTime(any()) } returns domainTime
        every { domainTimeConverter.getCurrentTimeStamp() } returns 0L
        every { domainTimeConverter.getMonthName(any()) } returns ""

        coEvery { addTransactionInteractor.save(any()) } returns Unit

        coEvery { getTransactionInteractor.getTransactionById(-1) } returns null

        coEvery { getTransactionInteractor.getTransactionById(1) } returns
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
            getTransactionRepository = getTransactionInteractor,
            addTransactionRepository = addTransactionInteractor,
            deleteTransactionRepository = deleteTransactionInteractor,
            updateTransactionRepository = updateTransactionInteractor,
            domainTimeConverter = domainTimeConverter
        )
    }

    @Test
    fun `should save income transaction from model`() {
        initViewModel(-1)

        with(viewModel.model) {
            value.value = "50.0"
            description.value = "description"
            date = domainTime

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

        val model = viewModel.model
        with(model) {
            value.value = "50.0"
            description.value = "description"
            date = domainTime

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

        with(viewModel.model) {
            value.value = "-50.0"
            isIncome.value = true
            description.value = "description"

            viewModel.onFabClick({}) {}
            verify { addTransactionInteractor wasNot called }
        }
    }

    @Test
    fun `should not edit negative number`() {
        initViewModel(1)

        val model = viewModel.model
        with(model) {
            value.value = "-50.0"
            isIncome.value = true
            description.value = "description"

            viewModel.onFabClick({}) {}
            verify { updateTransactionInteractor wasNot called }
        }
    }

    @Test
    fun `should not save invalid number`() {
        initViewModel(-1)

        with(viewModel.model) {
            value.value = "1,5"
            isIncome.value = false
            description.value = "description"

            viewModel.onFabClick({}) {}
            verify { addTransactionInteractor wasNot called }
        }
    }

    @Test
    fun `should not save invalid number 2`() {
        initViewModel(-1)

        with(viewModel.model) {
            value.value = "5 5"
            isIncome.value = false
            description.value = "description"

            viewModel.onFabClick({}) {}
            verify { addTransactionInteractor wasNot called }
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}
