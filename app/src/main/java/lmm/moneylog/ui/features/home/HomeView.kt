package lmm.moneylog.ui.features.home

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.ui.components.MyFab
import lmm.moneylog.ui.features.home.balancecard.BalanceCardView
import lmm.moneylog.ui.theme.MoneylogTheme
import lmm.moneylog.ui.theme.SpaceSize
import org.koin.androidx.compose.koinViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeView(
    viewModel: HomeViewModel = koinViewModel(),
    onFabClick: () -> Unit,
    onClick: (String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        modifier = Modifier
            .padding(horizontal = SpaceSize.DefaultSpaceSize)
            .padding(top = SpaceSize.DefaultSpaceSize),
        floatingActionButton = {
            MyFab(
                onFabClick,
                Icons.Default.Add
            )
        },
        floatingActionButtonPosition = FabPosition.Center,
        content = {
            Surface {
                BalanceCardView(
                    onClick = onClick,
                    hideValues = uiState,
                    onHideClick = {
                        viewModel.onHideClick()
                    }
                )
            }
        }
    )
}

@Preview
@Composable
fun HomeLayoutPreview() {
    MoneylogTheme {
        HomeView(
            onFabClick = {},
            onClick = {}
        )
    }
}
