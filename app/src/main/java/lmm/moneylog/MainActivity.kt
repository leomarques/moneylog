package lmm.moneylog

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import lmm.moneylog.home.composables.MainActivityScreen
import lmm.moneylog.ui.theme.MoneylogTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MoneylogTheme {
                MainActivityScreen()
            }
        }
    }
}
