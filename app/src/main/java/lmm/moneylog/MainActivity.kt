package lmm.moneylog

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import lmm.moneylog.addtransaction.AddTransactionActivityScreen
import lmm.moneylog.home.composables.HomeActivityScreen
import lmm.moneylog.ui.theme.MoneylogTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MoneylogTheme {
                MyNavHost()
            }
        }
    }

    @Composable
    fun MyNavHost(
        modifier: Modifier = Modifier,
        navController: NavHostController = rememberNavController(),
        startDestination: String = "home"
    ) {
        NavHost(
            modifier = modifier,
            navController = navController,
            startDestination = startDestination
        ) {
            composable("home") {
                HomeActivityScreen {
                    navController.navigate("add_transaction")
                }
            }
            composable("add_transaction") {
                AddTransactionActivityScreen {
                    navController.navigate("home")
                }
            }
        }
    }
}
