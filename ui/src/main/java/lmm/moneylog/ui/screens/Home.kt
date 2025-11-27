package lmm.moneylog.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.ui.components.HomeHeader
import lmm.moneylog.ui.components.MyCard
import lmm.moneylog.ui.theme.MoneylogTheme
import lmm.moneylog.ui.theme.Size

@Composable
fun Home(modifier: Modifier = Modifier) {
    Column(
        modifier =
            modifier
                .fillMaxSize()
                .padding(Size.DefaultSpaceSize)
    ) {
        HomeHeader(
            text = "October 2023",
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(bottom = Size.LargeSpaceSize)
        )

        MyCard(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(bottom = Size.DefaultSpaceSize)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(Size.DefaultSpaceSize)
        ) {
            MyCard(modifier = Modifier.weight(1f))
            MyCard(modifier = Modifier.weight(1f))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomePreview() {
    MoneylogTheme {
        Home()
    }
}
