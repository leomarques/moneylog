package lmm.moneylog.ui.features.addtransaction

import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import lmm.moneylog.MainDispatcherRule
import lmm.moneylog.data.domaintime.LocalDateTimeToDomainTimeConverterImpl
import lmm.moneylog.domain.addtransaction.AddTransactionInteractor
import lmm.moneylog.domain.addtransaction.model.Transaction
import lmm.moneylog.domain.addtransaction.time.DomainTime
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AddTransactionViewModelTest {

    private lateinit var viewModel: AddTransactionViewModel
    private val interactor: AddTransactionInteractor = mockk()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()
    private val dispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)

        viewModel = AddTransactionViewModel(
            interactor,
            LocalDateTimeToDomainTimeConverterImpl()
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `should save transaction from model`() {
        with(viewModel.addTransactionModel) {
            value.value = "51.0"
            date.value = "2022-01-06 21:30"
            description.value = "description"

            viewModel.saveTransaction(this)

            coVerify {
                interactor.execute(
                    Transaction(
                        50.0,
                        "description",
                        DomainTime(
                            6,
                            1,
                            2022,
                            21,
                            30
                        )
                    )
                )
            }
        }
    }
}