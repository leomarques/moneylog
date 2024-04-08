package lmm.moneylog.ui.components.misc

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
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
    isIncome: Boolean,
    modifier: Modifier = Modifier,
    onClick: (Boolean) -> Unit
) {
    Row(modifier) {
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
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier.clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = isSelected,
            onClick = onClick
        )
        Text(text = text)
    }
}

@Preview(showBackground = true)
@Composable
private fun IncomeRadioGroupPreview() {
    IncomeRadioGroup(
        isIncome = true,
        onClick = {}
    )
}

@Preview(showBackground = true)
@Composable
private fun IncomeRadioButtonPreview() {
    IncomeRadioButton(
        isSelected = true,
        text = stringResource(R.string.income),
        onClick = {}
    )
}
