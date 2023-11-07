package lmm.moneylog.ui.features.home.balancecard

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import lmm.moneylog.data.balance.BalanceModel
import lmm.moneylog.data.balance.GetBalanceInteractor
import lmm.moneylog.data.transaction.time.DomainTimeInteractorImpl
import lmm.moneylog.ui.features.balancecard.viewmodel.BalanceCardViewModel
import org.junit.After
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class BalanceCardViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var viewModel: BalanceCardViewModel
    private val interactor: GetBalanceInteractor = mockk()

    @Test
    fun `should convert balance`() {
        Dispatchers.setMain(UnconfinedTestDispatcher())

        every { interactor.execute(0, 0) } returns flow {
            emit(
                BalanceModel(
                    total = 10.0,
                    credit = 15.0,
                    debt = 5.0
                )
            )
        }

        viewModel = BalanceCardViewModel(interactor, DomainTimeInteractorImpl())
        assertEquals("R$10,00", viewModel.uiState.value.total)
        assertEquals("R$15,00", viewModel.uiState.value.credit)
        assertEquals("R$5,00", viewModel.uiState.value.debt)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}
