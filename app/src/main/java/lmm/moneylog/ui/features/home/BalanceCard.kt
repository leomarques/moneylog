package lmm.moneylog.ui.features.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.R
import lmm.moneylog.ui.theme.MoneylogTheme
import lmm.moneylog.ui.theme.SpaceSize
import lmm.moneylog.ui.theme.income

@Composable
fun BalanceCard(
    total: String,
    credit: String,
    debt: String,
    onAmountClick: (String) -> Unit,
    onBalanceClick: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(SpaceSize.DefaultSpaceSize))
            .background(
                MaterialTheme.colorScheme.tertiaryContainer
            )
            .padding(SpaceSize.DefaultSpaceSize),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Balance(
            value = total,
            onClick = { onBalanceClick("all") }
        )

        Spacer(Modifier.height(SpaceSize.LargeSpaceSize))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Amount(
                title = stringResource(R.string.balancecard_income),
                value = credit,
                color = income,
                onClick = { onAmountClick("income") }
            )

            Amount(
                title = stringResource(R.string.balancecard_outcome),
                value = debt,
                color = Color.Red,
                onClick = { onAmountClick("outcome") }
            )
        }
    }
}

@Composable
fun Balance(
    value: String,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier.clickable { onClick() },
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
            color = MaterialTheme.colorScheme.onTertiaryContainer,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
        )
    }
}

@Composable
fun Amount(
    title: String,
    value: String,
    color: Color,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier.clickable { onClick() },
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
            color = color,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
        )
    }
}

@Preview
@Composable
fun BalanceCardPreview() {
    MoneylogTheme {
        BalanceCard(
            total = "RS99995.00",
            credit = "R$100000.00",
            debt = "R$50.00",
            onAmountClick = {},
            onBalanceClick = {}
        )
    }
}
