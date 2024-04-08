package lmm.moneylog.ui.components.icons

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.R
import lmm.moneylog.ui.theme.darkRed

@Composable
fun CategoryIcon(
    tint: Color,
    modifier: Modifier = Modifier
) {
    Icon(
        imageVector = ImageVector.vectorResource(id = R.drawable.outline_category_24),
        contentDescription = stringResource(R.string.category),
        tint = tint
    )
}

@Preview
@Composable
private fun CategoryIconPreview() {
    CategoryIcon(tint = darkRed)
}
