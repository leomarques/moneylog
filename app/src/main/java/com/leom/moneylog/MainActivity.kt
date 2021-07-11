package com.leom.moneylog

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import com.leom.moneylog.home.HomeViewModel
import com.leom.moneylog.ui.composables.MainActivityScreen
import com.leom.moneylog.ui.composables.home.homeFab
import com.leom.moneylog.ui.theme.MoneylogTheme
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val homeViewModel: HomeViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MoneylogTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(title = { Text("Moneylog") })
                    },
                    floatingActionButton = {
                        homeFab {
                            homeViewModel.addRandomTransaction()
                        }
                    },
                    content = {
                        MainActivityScreen(homeViewModel)
                    }
                )
            }
        }
    }
}