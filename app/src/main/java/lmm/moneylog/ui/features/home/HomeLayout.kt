package lmm.moneylog.ui.features.home

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.ui.components.BalanceCard
import lmm.moneylog.ui.components.MyFab
import lmm.moneylog.ui.theme.MoneylogTheme
import lmm.moneylog.ui.theme.SpaceSize

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeLayout(
    total: String,
    credit: String,
    debt: String,
    onFabClick: () -> Unit
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
                BalanceCard(
                    total,
                    credit,
                    debt
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
            total = "R$250",
            credit = "R$300",
            debt = "R$50"
        ) {}
    }
}
