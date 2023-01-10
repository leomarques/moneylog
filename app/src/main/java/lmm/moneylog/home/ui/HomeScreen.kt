package lmm.moneylog.home.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import lmm.moneylog.home.HomeViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    onFabClick: () -> Unit,
    viewModel: HomeViewModel = koinViewModel()
) {
    val balance by viewModel.balanceModel.observeAsState(
        BalanceModel("0.0", "0.0", "0.0")
    )

    HomeLayout(
        balance.total,
        balance.credit,
        balance.debt,
        onFabClick
    )
}
