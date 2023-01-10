package lmm.moneylog.home.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.res.stringResource
import lmm.moneylog.R
import lmm.moneylog.home.HomeViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    onFabClick: () -> Unit,
    viewModel: HomeViewModel = koinViewModel()
) {
    val balance by viewModel.balanceCardModel.observeAsState(
        BalanceCardModel(
            stringResource(R.string.home_placeholdervalue),
            stringResource(R.string.home_placeholdervalue),
            stringResource(R.string.home_placeholdervalue)
        )
    )

    HomeLayout(
        balance.total,
        balance.credit,
        balance.debt,
        onFabClick
    )
}
