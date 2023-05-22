package lmm.moneylog.ui.features.transactiondetail

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
import lmm.moneylog.domain.addtransaction.AddTransactionInteractor
import lmm.moneylog.domain.edittransaction.UpdateTransactionInteractor
import lmm.moneylog.domain.gettransaction.GetTransactionInteractor
import lmm.moneylog.domain.models.Transaction
import lmm.moneylog.domain.time.DomainTime
import lmm.moneylog.domain.time.DomainTimeConverter
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class TransactionDetailViewModelTest {

    private lateinit var viewModel: TransactionDetailViewModel
    private val getTransactionInteractor: GetTransactionInteractor = mockk()
    private val addTransactionInteractor: AddTransactionInteractor = mockk()
    private val updateTransactionInteractor: UpdateTransactionInteractor = mockk()
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

        coEvery { addTransactionInteractor.execute(any()) } returns Unit

        viewModel = TransactionDetailViewModel(
            getTransactionInteractor = getTransactionInteractor,
            addTransactionInteractor = addTransactionInteractor,
            updateTransactionInteractor = updateTransactionInteractor,
            domainTimeConverter = domainTimeConverter,
            savedStateHandle = SavedStateHandle().also { it["id"] = -1 }
        )
    }

    @Test
    fun `should save income transaction from model`() {
        with(viewModel.transactionDetailModel.value!!) {
            value.value = "50.0"
            description.value = "description"

            viewModel.onFabClick(this) {}

            coVerify {
                addTransactionInteractor.execute(
                    Transaction(
                        id = -1,
                        value = 50.0,
                        description = "description",
                        date = domainTime
                    )
                )
            }
        }
    }

    @Test
    fun `should save outcome transaction from model`() {
        with(viewModel.transactionDetailModel.value!!) {
            value.value = "50.0"
            isIncome.value = false
            description.value = "description"

            viewModel.onFabClick(this) {}

            coVerify {
                addTransactionInteractor.execute(
                    Transaction(
                        id = -1,
                        value = -50.0,
                        description = "description",
                        date = domainTime
                    )
                )
            }
        }
    }

    @Test
    fun `should not save negative number`() {
        with(viewModel.transactionDetailModel.value!!) {
            value.value = "-50.0"
            isIncome.value = true
            description.value = "description"

            viewModel.onFabClick(this) {}
            verify { addTransactionInteractor wasNot called }
        }
    }

    @Test
    fun `should not save invalid number`() {
        with(viewModel.transactionDetailModel.value!!) {
            value.value = "1,5"
            isIncome.value = false
            description.value = "description"

            viewModel.onFabClick(this) {}
            verify { addTransactionInteractor wasNot called }
        }
    }

    @Test
    fun `should not save invalid number 2`() {
        with(viewModel.transactionDetailModel.value!!) {
            value.value = "5 5"
            isIncome.value = false
            description.value = "description"

            viewModel.onFabClick(this) {}
            verify { addTransactionInteractor wasNot called }
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}
