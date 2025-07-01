package lmm.moneylog.ui.navigation.misc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import lmm.moneylog.notification.service.NotificationActionRemoveService
import lmm.moneylog.ui.theme.MoneylogTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val transactionId = intent.getIntExtra(NotificationActionRemoveService.EXTRA_TRANSACTION_ID, -1)
        
        setContent {
            MoneylogTheme {
                Navigation(transactionId = transactionId)
            }
        }
    }
}
