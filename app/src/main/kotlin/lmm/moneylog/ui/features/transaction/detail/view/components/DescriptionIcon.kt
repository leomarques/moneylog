package lmm.moneylog.ui.features.transaction.detail.view.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.R

@Composable
fun DescriptionIcon(modifier: Modifier = Modifier) {
    Icon(
        imageVector = Icons.Default.Create,
        contentDescription = stringResource(R.string.detail_description)
    )
}

@Preview
@Composable
fun DescriptionIconPreview() {
    DescriptionIcon()
}
