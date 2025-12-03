package lmm.moneylog.ui.components.radiogroup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.ui.R

@Composable
fun RadioGroupType(
    isDebtSelect: Boolean,
    onDebtSelect: () -> Unit,
    onCreditSelect: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        RadioOption(
            text = stringResource(R.string.detail_debt_type),
            selected = isDebtSelect,
            onClick = onDebtSelect
        )

        RadioOption(
            text = stringResource(R.string.detail_credit_type),
            selected = !isDebtSelect,
            onClick = onCreditSelect
        )
    }
}

@Preview
@Composable
private fun RadioGroupTypePreview() {
    RadioGroupType(
        isDebtSelect = true,
        onDebtSelect = {},
        onCreditSelect = {}
    )
}
