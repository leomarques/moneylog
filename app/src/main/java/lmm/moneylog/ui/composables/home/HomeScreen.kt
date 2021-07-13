package lmm.moneylog.ui.composables.home

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

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
                if (isSystemInDarkTheme()) {
                    MaterialTheme.colors.background
                } else {
                    Color.LightGray
                }
            )
    ) {
        content()
    }
}