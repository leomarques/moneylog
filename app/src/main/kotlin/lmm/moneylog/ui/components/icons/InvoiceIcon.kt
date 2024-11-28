package lmm.moneylog.ui.components.icons

import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.R

@Composable
fun InvoiceIcon(
    modifier: Modifier = Modifier,
    tint: Color? = null
) {
    Icon(
        modifier = modifier,
        imageVector = ImageVector.vectorResource(id = R.drawable.outline_receipt_long_24),
        contentDescription = stringResource(R.string.name),
        tint = tint ?: LocalContentColor.current
    )
}

@Preview(showBackground = true)
@Composable
private fun InvoiceIconPreview() {
    InvoiceIcon()
}
