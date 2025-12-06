package lmm.moneylog.ui.navigation.misc

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.mutableLongStateOf
import lmm.moneylog.misc.nubank.NubankNotificationActionReceiver.Companion.EXTRA_TRANSACTION_ID
import lmm.moneylog.ui.theme.AppTheme

class MainActivity : ComponentActivity() {
    private val pendingTransactionId = mutableLongStateOf(-1L)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val transactionId = intent?.getLongExtra(EXTRA_TRANSACTION_ID, -1L) ?: -1L
        pendingTransactionId.longValue = transactionId

        setContent {
            AppTheme {
                Navigation(
                    pendingTransactionId = pendingTransactionId.longValue
                )
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)

        val transactionId = intent.getLongExtra(EXTRA_TRANSACTION_ID, -1L)
        if (transactionId != -1L) {
            pendingTransactionId.longValue = transactionId
        }
    }
}
