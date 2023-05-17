package lmm.moneylog.ui.features.addtransaction

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
import lmm.moneylog.domain.addtransaction.AddTransactionInteractor
import lmm.moneylog.domain.models.Transaction
import lmm.moneylog.domain.time.DomainTime
import lmm.moneylog.domain.time.DomainTimeConverter
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AddTransactionViewModelTest {

    private lateinit var viewModel: AddTransactionViewModel
    private val interactor: AddTransactionInteractor = mockk()
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

        coEvery { interactor.execute(any()) } returns Unit

        viewModel = AddTransactionViewModel(
            interactor,
            domainTimeConverter
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `should save income transaction from model`() {
        with(viewModel.addTransactionModel) {
            value.value = "50.0"
            description.value = "description"

            viewModel.saveTransaction(this) {}

            coVerify {
                interactor.execute(
                    Transaction(
                        50.0,
                        "description",
                        domainTime
                    )
                )
            }
        }
    }

    @Test
    fun `should save outcome transaction from model`() {
        with(viewModel.addTransactionModel) {
            value.value = "50.0"
            isIncome = false
            description.value = "description"

            viewModel.saveTransaction(this) {}

            coVerify {
                interactor.execute(
                    Transaction(
                        -50.0,
                        "description",
                        domainTime
                    )
                )
            }
        }
    }

    @Test
    fun `should not save negative number`() {
        with(viewModel.addTransactionModel) {
            value.value = "-50.0"
            isIncome = true
            description.value = "description"

            val onValueError = {}
            viewModel.saveTransaction(this, onValueError)
            verify { interactor wasNot called }
        }
    }

    @Test
    fun `should not save invalid number`() {
        with(viewModel.addTransactionModel) {
            value.value = "1,5"
            isIncome = false
            description.value = "description"

            val onValueError = {}
            viewModel.saveTransaction(this, onValueError)
            verify { interactor wasNot called }
        }
    }

    @Test
    fun `should not save invalid number 2`() {
        with(viewModel.addTransactionModel) {
            value.value = "5 5"
            isIncome = false
            description.value = "description"

            val onValueError = {}
            viewModel.saveTransaction(this, onValueError)
            verify { interactor wasNot called }
        }
    }
}
