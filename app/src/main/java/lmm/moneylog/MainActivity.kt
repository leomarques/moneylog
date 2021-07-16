package lmm.moneylog

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import lmm.moneylog.addtransaction.AddTransactionScreen
import lmm.moneylog.home.composables.HomeFab
import lmm.moneylog.home.composables.MainActivityScreen
import lmm.moneylog.ui.Screen
import lmm.moneylog.ui.theme.MoneylogTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MoneylogTheme {
                val navController = rememberNavController()
                val currentScreen = remember {
                    mutableStateOf(Screen.Home.name)
                }

                Scaffold(
                    topBar = {
                        TopAppBar(title = { Text("Moneylog") })
                    },
                    floatingActionButton = {
                        if (currentScreen.value == Screen.Home.name)
                            HomeFab {
                                navController.navigate(Screen.AddTransaction.name)
                            }
                    },
                    content = {
                        MoneylogNavHost(navController, currentScreen)
                    }
                )
            }
        }
    }

    @Composable
    private fun MoneylogNavHost(
        navController: NavHostController,
        currentScreen: MutableState<String>
    ) {
        NavHost(
            navController = navController,
            startDestination = currentScreen.value
        ) {
            composable(Screen.Home.name) {
                MainActivityScreen()
                currentScreen.value = Screen.Home.name
            }
            composable(Screen.AddTransaction.name) {
                AddTransactionScreen {
                    navController.popBackStack()
                }
                currentScreen.value = Screen.AddTransaction.name
            }
        }
    }
}