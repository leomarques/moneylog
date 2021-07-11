package lmm.moneylog.ui.composables

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import lmm.moneylog.home.BalanceModel
import lmm.moneylog.home.HomeViewModel
import lmm.moneylog.ui.composables.home.HomeScreen

@Composable
fun MainActivityScreen(homeViewModel: HomeViewModel) {
    val balance by homeViewModel.balanceModel.observeAsState(
        BalanceModel("", "", "")
    )

    HomeScreen(
        balance.total,
        balance.credit,
        balance.debt
    )
}