package lmm.moneylog.ui.features.transaction.detail.view.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.R

@Composable
fun DateIcon() {
    Icon(
        imageVector = Icons.Default.DateRange,
        contentDescription = stringResource(R.string.date_desc)
    )
}

@Preview
@Composable
fun DateIconPreview() {
    DateIcon()
}
