package lmm.moneylog.home.composables

import android.annotation.SuppressLint
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(total: String, credit: String, debt: String, onFabClick: () -> Unit) {
    Scaffold(
        floatingActionButton = { HomeFab(onFabClick) },
        floatingActionButtonPosition = FabPosition.Center,
        content = { BalanceCard(total, credit, debt) }
    )
}

@Composable
fun HomeFab(onClick: () -> Unit) {
    FloatingActionButton(onClick = onClick) {
        Icon(
            Icons.Default.Add,
            contentDescription = "home fab"
        )
    }
}
