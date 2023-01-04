package lmm.moneylog.home.composables

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(total: String, credit: String, debt: String, onFabClick: () -> Unit) {
    Scaffold(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .padding(top = 16.dp),
        floatingActionButton = { HomeFab(onFabClick) },
        floatingActionButtonPosition = FabPosition.Center,
        content = { paddingValues ->
            Surface(modifier = Modifier.padding(paddingValues)) {
                BalanceCard(total, credit, debt)
            }
        }
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
