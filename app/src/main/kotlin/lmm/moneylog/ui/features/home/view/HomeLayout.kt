package lmm.moneylog.ui.features.home.view

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.ui.components.fabs.HideFab
import lmm.moneylog.ui.features.balancecard.view.layout.BalanceCardView
import lmm.moneylog.ui.features.creditcard.homecard.view.layout.CreditHomeCardView
import lmm.moneylog.ui.theme.Size

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeLayout(
    hideValues: Boolean,
    onFabClick: () -> Unit,
    onBalanceClick: (String) -> Unit,
    onHideClick: () -> Unit,
    modifier: Modifier = Modifier,
    onCreditCardClick: () -> Unit
) {
    Scaffold(
        modifier =
            modifier
                .padding(horizontal = Size.DefaultSpaceSize)
                .padding(top = Size.DefaultSpaceSize),
        floatingActionButton = {
            HideFab(
                icon = Icons.Default.Add,
                onClick = onFabClick
            )
        },
        floatingActionButtonPosition = FabPosition.Center,
        content = {
            Surface(Modifier.testTag("HomeScreen")) {
                Column {
                    BalanceCardView(
                        hideValues = hideValues,
                        onClick = onBalanceClick,
                        onHideClick = onHideClick
                    )

                    Spacer(modifier = Modifier.height(Size.DefaultSpaceSize))

                    CreditHomeCardView(
                        hideValues = hideValues,
                        onClick = onCreditCardClick
                    )
                }
            }
        }
    )
}

@Preview
@Composable
private fun HomeLayoutPreview() {
    HomeLayout(
        hideValues = false,
        onFabClick = {},
        onBalanceClick = {},
        onHideClick = {},
        onCreditCardClick = {}
    )
}
