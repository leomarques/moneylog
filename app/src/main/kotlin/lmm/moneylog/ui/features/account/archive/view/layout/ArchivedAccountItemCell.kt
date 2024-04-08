package lmm.moneylog.ui.features.account.archive.view.layout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.ui.features.account.archive.view.components.ArchivedAccountName
import lmm.moneylog.ui.features.account.archive.view.components.MoreOptionsIcon
import lmm.moneylog.ui.theme.Size

@Composable
fun ArchivedAccountItemCell(
    name: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .height(Size.OneLineListItemHeight)
                .padding(
                    vertical = Size.SmallSpaceSize,
                    horizontal = Size.DefaultSpaceSize
                ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        ArchivedAccountName(name)

        MoreOptionsIcon(onClick = onClick)
    }
}

@Preview
@Composable
private fun ArchivedAccountItemCellPreview() {
    Surface {
        ArchivedAccountItemCell(
            name = "Account Name",
            onClick = {}
        )
    }
}
