package lmm.moneylog.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.R

@Composable
fun IncomeRadioGroup(
    modifier: Modifier = Modifier,
    isIncome: Boolean,
    onClick: (Boolean) -> Unit
) {
    Row(modifier = modifier.background(MaterialTheme.colorScheme.surface)) {
        IncomeRadioButton(
            isSelected = isIncome,
            text = stringResource(R.string.income),
            onClick = {
                onClick(true)
            }
        )

        IncomeRadioButton(
            isSelected = !isIncome,
            text = stringResource(R.string.outcome),
            onClick = {
                onClick(false)
            }
        )
    }
}

@Composable
private fun IncomeRadioButton(
    isSelected: Boolean,
    text: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier.clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = isSelected,
            onClick = onClick
        )
        Text(text = text)
    }
}

@Preview
@Composable
fun IncomeRadioGroupPreview() {
    IncomeRadioGroup(
        isIncome = true,
        onClick = {}
    )
}

@Preview
@Composable
fun IncomeRadioButtonPreview() {
    IncomeRadioButton(
        isSelected = true,
        text = stringResource(R.string.income),
        onClick = {}
    )
}
