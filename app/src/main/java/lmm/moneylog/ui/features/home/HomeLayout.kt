package lmm.moneylog.ui.features.home

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.ui.components.MyFab
import lmm.moneylog.ui.features.home.balancecard.BalanceCardView
import lmm.moneylog.ui.theme.MoneylogTheme
import lmm.moneylog.ui.theme.SpaceSize

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeLayout(
    onFabClick: () -> Unit,
    onAmountClick: (String) -> Unit,
    onBalanceClick: (String) -> Unit
) {
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
                    onAmountClick,
                    onBalanceClick
                )
            }
        }
    )
}

@Preview
@Composable
fun HomeLayoutPreview() {
    MoneylogTheme {
        HomeLayout(
            {},
            {},
            {}
        )
    }
}
