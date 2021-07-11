package com.leom.moneylog.ui.composables

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import com.leom.moneylog.home.BalanceModel
import com.leom.moneylog.home.HomeViewModel
import com.leom.moneylog.ui.composables.home.HomeScreen

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