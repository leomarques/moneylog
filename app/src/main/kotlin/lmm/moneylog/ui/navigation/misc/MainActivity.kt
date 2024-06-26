package lmm.moneylog.ui.navigation.misc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import lmm.moneylog.ui.theme.MoneylogTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MoneylogTheme {
                Navigation()
            }
        }
    }
}
