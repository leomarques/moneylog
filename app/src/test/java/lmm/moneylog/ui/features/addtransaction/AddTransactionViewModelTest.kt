package lmm.moneylog.ui.features.addtransaction

import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import lmm.moneylog.domain.addtransaction.AddTransactionInteractor
import lmm.moneylog.domain.addtransaction.model.Transaction
import lmm.moneylog.domain.addtransaction.time.DomainTime
import lmm.moneylog.domain.addtransaction.time.DomainTimeConverter
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
        2022,
        21,
        30
    )

    @Before
    fun setup() {
        Dispatchers.setMain(UnconfinedTestDispatcher())

        every { domainTimeConverter.toDomainTime(any()) } returns domainTime
        every { domainTimeConverter.getNowTime() } returns ""
        every { domainTimeConverter.getDatePattern() } returns ""

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
    fun `should save transaction from model`() {
        with(viewModel.addTransactionModel) {
            value.value = "50.0"
            description.value = "description"

            viewModel.saveTransaction(this)

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
}
