package lmm.moneylog.home.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun HomeScreen(total: String, credit: String, debt: String) {
    Background {
        BalanceCard(total, credit, debt)
    }
}

@Composable
private fun Background(content: @Composable () -> Unit = {}) {
    Column(
        Modifier
            .fillMaxSize()
            .background(
                MaterialTheme.colorScheme.background
            )
    ) {
        content()
    }
}
