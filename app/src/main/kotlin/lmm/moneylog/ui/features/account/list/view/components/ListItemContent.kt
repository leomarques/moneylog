package lmm.moneylog.ui.features.account.list.view.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.ui.theme.Size

@Composable
fun ListItemContent(
    modifier: Modifier = Modifier,
    id: Int,
    onItemClick: (Int) -> Unit,
    content: @Composable (RowScope.() -> Unit)
) {
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .height(Size.TwoLinesListItemHeight)
                .padding(
                    vertical = Size.DefaultSpaceSize,
                    horizontal = Size.DefaultSpaceSize
                )
                .clickable { onItemClick(id) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        content()
    }
}

@Preview
@Composable
fun ListItemContentPreview() {
    ListItemContent(
        id = 1,
        onItemClick = {},
        content = {
            Text(text = "Content")
        }
    )
}
