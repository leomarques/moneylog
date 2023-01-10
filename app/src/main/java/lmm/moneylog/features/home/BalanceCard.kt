package lmm.moneylog.features.home

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import lmm.moneylog.R
import lmm.moneylog.ui.theme.MoneylogTheme
import lmm.moneylog.ui.theme.SpaceSize
import lmm.moneylog.ui.theme.balancecard_credit

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
                balancecard_credit
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
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onTertiaryContainer
        )
        Text(
            text = value,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.headlineMedium,
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
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onTertiaryContainer
        )
        Text(
            text = value,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleMedium,
            color = color
        )
    }
}

@Preview
@Composable
fun BalanceCardPreview() {
    MoneylogTheme {
        BalanceCard(total = "RS150.00", credit = "R$200.00", debt = "R$50.00")
    }
}
