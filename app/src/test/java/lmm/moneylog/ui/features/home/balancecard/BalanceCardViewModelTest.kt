package lmm.moneylog.ui.features.home.balancecard

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import lmm.moneylog.domain.balance.getbalance.BalanceModel
import lmm.moneylog.domain.balance.getbalance.GetBalanceInteractor
import lmm.moneylog.getOrAwaitValue
import org.junit.After
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class BalanceCardViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var viewModel: BalanceCardViewModel
    private val interactor: GetBalanceInteractor = mockk()

    @Test
    fun `should convert balance`() {
        Dispatchers.setMain(UnconfinedTestDispatcher())

        every { interactor.execute() } returns flow {
            emit(
                BalanceModel(
                    total = 10.0,
                    credit = 15.0,
                    debt = 5.0
                )
            )
        }

        viewModel = BalanceCardViewModel(interactor)
        val model = viewModel.balanceCardModel.getOrAwaitValue()
        assertEquals("R$10,00", model.total)
        assertEquals("R$15,00", model.credit)
        assertEquals("R$5,00", model.debt)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}
