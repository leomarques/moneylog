package lmm.moneylog

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import lmm.moneylog.home.HomeViewModel
import lmm.moneylog.ui.composables.MainActivityScreen
import lmm.moneylog.ui.composables.home.HomeFab
import lmm.moneylog.ui.theme.MoneylogTheme
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
                        HomeFab {
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