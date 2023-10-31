package lmm.moneylog.ui.features.home.balancecard

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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

const val balanceCardAll = "all"
const val balanceCardIncome = "income"
const val balanceCardOutcome = "outcome"

@Composable
fun BalanceCard(
    total: String,
    credit: String,
    debt: String,
    onClick: (String) -> Unit,
    hideValues: Boolean,
    onHideClick: () -> Unit,
    month: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(SpaceSize.DefaultSpaceSize))
            .background(
                MaterialTheme.colorScheme.tertiaryContainer
            )
            .padding(SpaceSize.DefaultSpaceSize),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Balance(
            value = total,
            onClick = { onClick(balanceCardAll) },
            hideValues = hideValues,
            month = month
        )

        IconButton(
            onClick = onHideClick,
            modifier = Modifier.padding(vertical = SpaceSize.XSmallSpaceSize)
        ) {
            Icon(
                imageVector = if (hideValues) Icons.Filled.Lock else Icons.Outlined.Lock,
                contentDescription = stringResource(R.string.arrowback_desc)
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Amount(
                modifier = Modifier.weight(0.5f),
                title = stringResource(R.string.incomes),
                value = credit,
                color = income,
                onClick = { onClick(balanceCardIncome) },
                hideValue = hideValues
            )

            Amount(
                modifier = Modifier.weight(0.5f),
                title = stringResource(R.string.outcome),
                value = debt,
                color = Color.Red,
                onClick = { onClick(balanceCardOutcome) },
                hideValue = hideValues
            )
        }
    }
}

@Composable
fun Balance(
    value: String,
    onClick: () -> Unit,
    hideValues: Boolean,
    month: String
) {
    Column(
        modifier = Modifier.clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(bottom = SpaceSize.SmallSpaceSize),
            text = month,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onTertiaryContainer
        )

        Text(
            text = stringResource(R.string.balancecard_total),
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onTertiaryContainer
        )

        Text(
            text = if (hideValues) "****" else value,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onTertiaryContainer,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
    }
}

@Composable
fun Amount(
    modifier: Modifier,
    title: String,
    value: String,
    color: Color,
    onClick: () -> Unit,
    hideValue: Boolean
) {
    Column(
        modifier = modifier.clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onTertiaryContainer
        )
        Text(
            text = if (hideValue) "****" else value,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleMedium,
            color = color,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
    }
}

@Preview
@Composable
fun BalanceCardPreview() {
    MoneylogTheme {
        BalanceCard(
            total = "R$99999999999999999999999999999999999999999999999999999999999999999999,00,00",
            credit = "R$99999999999999999999999999999999999999999999999999999999999999999999,00,00,00",
            debt = "R$99999999999999999999999999999999999999999999999999999999999999999999,00,00",
            onClick = {},
            hideValues = false,
            onHideClick = {},
            month = "Outubro"
        )
    }
}
