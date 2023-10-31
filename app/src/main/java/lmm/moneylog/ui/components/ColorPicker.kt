package lmm.moneylog.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import lmm.moneylog.ui.theme.DarkBlue
import lmm.moneylog.ui.theme.DarkBrow
import lmm.moneylog.ui.theme.DarkGreen
import lmm.moneylog.ui.theme.DarkOrange
import lmm.moneylog.ui.theme.DarkPink
import lmm.moneylog.ui.theme.DarkPurple
import lmm.moneylog.ui.theme.DarkRed
import lmm.moneylog.ui.theme.DarkYellow
import lmm.moneylog.ui.theme.Size

@Composable
fun ColorPicker(
    onConfirm: (Color) -> Unit,
    onDismiss: () -> Unit
) {
    val list = mutableListOf(
        DarkRed,
        DarkBlue,
        DarkGreen,
        DarkYellow,
        DarkOrange,
        DarkPurple,
        DarkPink,
        DarkBrow
    )

    Dialog(
        onDismissRequest = onDismiss,
        content = {
            LazyColumn(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.surface)
                    .wrapContentWidth()
                    .padding(
                        horizontal = Size.DefaultSpaceSize,
                        vertical = Size.SmallSpaceSize
                    )
            ) {
                items(list) { item ->
                    ColorItem(color = item) {
                        onDismiss()
                        onConfirm(item)
                    }
                }
            }
        }
    )
}

@Composable
fun ColorItem(
    color: Color,
    onItemClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surface)
            .height(Size.TwoLinesListItemHeight)
            .padding(
                vertical = Size.SmallSpaceSize,
                horizontal = Size.DefaultSpaceSize
            )
            .clickable { onItemClick() },
        contentAlignment = Alignment.Center
    ) {
        MyCircle(color = color)
    }
}

@Preview
@Composable
fun ColorPickerPreview() {
    ColorPicker(
        onDismiss = {},
        onConfirm = {}
    )
}

@Preview
@Composable
fun ColorItemPreview() {
    ColorItem(
        color = DarkRed,
        onItemClick = {}
    )
}
