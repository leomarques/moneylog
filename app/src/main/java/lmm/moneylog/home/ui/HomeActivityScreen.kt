package lmm.moneylog.home.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import lmm.moneylog.home.BalanceModel
import lmm.moneylog.home.HomeViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun HomeActivityScreen(onFabClick: () -> Unit) {
    val viewModel: HomeViewModel = getViewModel()

    val balance by viewModel.balanceModel.observeAsState(
        BalanceModel("", "", "")
    )

    HomeScreen(
        balance.total,
        balance.credit,
        balance.debt,
        onFabClick
    )
}
