package lmm.moneylog.home.composables

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import lmm.moneylog.data.Repository
import lmm.moneylog.home.BalanceModel
import lmm.moneylog.home.HomeViewModel
import org.koin.androidx.compose.get

@Composable
fun MainActivityScreen() {
    val repository: Repository = get()
    val homeViewModel = remember { HomeViewModel(repository) }

    val balance by homeViewModel.balanceModel.observeAsState(
        BalanceModel("", "", "")
    )

    HomeScreen(
        balance.total,
        balance.credit,
        balance.debt
    )
}
