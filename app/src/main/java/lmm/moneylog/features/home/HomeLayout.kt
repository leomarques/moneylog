package lmm.moneylog.features.home

import android.annotation.SuppressLint
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.R
import lmm.moneylog.ui.components.balancecard.BalanceCard
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
        floatingActionButton = { HomeFab(onFabClick) },
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

@Composable
fun HomeFab(onClick: () -> Unit) {
    FloatingActionButton(onClick = onClick) {
        Icon(
            Icons.Default.Add,
            contentDescription = stringResource(R.string.home_fab_desc)
        )
    }
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
