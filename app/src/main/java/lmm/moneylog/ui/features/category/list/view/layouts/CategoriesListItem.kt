package lmm.moneylog.ui.features.category.list.view.layouts

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.R
import lmm.moneylog.ui.components.misc.MyCircle
import lmm.moneylog.ui.theme.Size

@Composable
fun CategoriesListItem(
    id: Int,
    name: String,
    color: Color,
    showDivider: Boolean,
    onItemClick: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(Size.TwoLinesListItemHeight)
            .padding(
                vertical = Size.DefaultSpaceSize,
                horizontal = Size.DefaultSpaceSize
            )
            .clickable { onItemClick(id) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        MyCircle(
            color = color,
            letters = name
        )

        Text(
            modifier = Modifier.padding(start = Size.DefaultSpaceSize),
            text = name.ifEmpty {
                stringResource(R.string.no_description)
            },
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            style = MaterialTheme.typography.bodyLarge,
            color = if (name.isEmpty()) {
                Color.Gray
            } else {
                MaterialTheme.colorScheme.onSurface
            }
        )
    }

    if (showDivider) {
        Divider(Modifier.padding(horizontal = Size.DefaultSpaceSize))
    }
}

@Preview
@Composable
fun CategoriesListItemPreview() {
    CategoriesListItem(
        id = 0,
        name = "Category",
        color = Color.Red,
        showDivider = true,
        onItemClick = {}
    )
}
