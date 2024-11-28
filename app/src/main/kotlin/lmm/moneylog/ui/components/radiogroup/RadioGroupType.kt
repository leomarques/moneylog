package lmm.moneylog.ui.components.radiogroup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.R

@Composable
fun RadioGroupType(
    isDebtSelected: Boolean,
    onDebtSelected: () -> Unit,
    onCreditSelected: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        RadioOption(
            text = stringResource(R.string.detail_debt_type),
            selected = isDebtSelected,
            onClick = onDebtSelected
        )

        RadioOption(
            text = stringResource(R.string.detail_credit_type),
            selected = !isDebtSelected,
            onClick = onCreditSelected
        )
    }
}

@Preview
@Composable
private fun RadioGroupTypePreview() {
    RadioGroupType(
        isDebtSelected = true,
        onDebtSelected = {},
        onCreditSelected = {}
    )
}
