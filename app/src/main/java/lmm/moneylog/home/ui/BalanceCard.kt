package lmm.moneylog.home.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import lmm.moneylog.R
import lmm.moneylog.ui.theme.SpaceSize

@Composable
fun BalanceCard(
    total: String,
    credit: String,
    debt: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(
                MaterialTheme.colorScheme.tertiaryContainer
            )
            .padding(
                top = SpaceSize.DefaultSpaceSize,
                bottom = SpaceSize.DefaultSpaceSize
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Balance(total)

        Spacer(Modifier.height(SpaceSize.LargeSpaceSize))

        Row {
            Amount(
                stringResource(R.string.balancecard_income),
                credit,
                Color.Green
            )

            Spacer(Modifier.width(SpaceSize.XLargeSpaceSize))

            Amount(
                stringResource(R.string.balancecard_outcome),
                debt,
                Color.Red
            )
        }
    }
}

@Composable
fun Balance(value: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.balancecard_total),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onTertiaryContainer
        )
        Text(
            text = value,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onTertiaryContainer
        )
    }
}

@Composable
fun Amount(
    title: String,
    value: String,
    color: Color
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onTertiaryContainer
        )
        Text(
            text = value,
            style = MaterialTheme.typography.headlineSmall,
            color = color
        )
    }
}
