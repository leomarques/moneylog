package lmm.moneylog.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import lmm.moneylog.ui.screens.Home
import lmm.moneylog.ui.theme.MoneylogTheme

@Composable
fun MainUI(modifier: Modifier = Modifier) {
    MoneylogTheme {
        Surface(
            modifier = modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Scaffold(
                content = { paddingValues ->
                    Box(
                        modifier =
                            Modifier
                                .fillMaxSize()
                                .padding(paddingValues)
                    ) {
                        Home()
                    }
                }
            )
        }
    }
}
