package lmm.moneylog.ui.components.misc

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import lmm.moneylog.ui.theme.Size
import lmm.moneylog.ui.theme.darkBlue
import lmm.moneylog.ui.theme.darkBrow
import lmm.moneylog.ui.theme.darkGreen
import lmm.moneylog.ui.theme.darkOrange
import lmm.moneylog.ui.theme.darkPink
import lmm.moneylog.ui.theme.darkPurple
import lmm.moneylog.ui.theme.darkRed
import lmm.moneylog.ui.theme.darkYellow

@Composable
fun ColorPicker(
    onConfirm: (Color) -> Unit,
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit
) {
    val list =
        mutableListOf(
            darkRed,
            darkBlue,
            darkGreen,
            darkYellow,
            darkOrange,
            darkPurple,
            darkPink,
            darkBrow
        )

    Dialog(
        onDismissRequest = onDismiss,
        content = {
            LazyColumn(
                modifier =
                    modifier
                        .wrapContentWidth()
                        .padding(
                            horizontal = Size.DefaultSpaceSize,
                            vertical = Size.SmallSpaceSize
                        )
            ) {
                items(list) { item ->
                    ColorItem(
                        color = item
                    ) {
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
    modifier: Modifier = Modifier,
    onItemClick: () -> Unit
) {
    Box(
        modifier =
            modifier
                .height(Size.TwoLinesListItemHeight)
                .padding(
                    vertical = Size.SmallSpaceSize,
                    horizontal = Size.DefaultSpaceSize
                )
                .clickable { onItemClick() },
        contentAlignment = Alignment.Center
    ) {
        ColoredCircle(color = color)
    }
}

@Preview(showBackground = true)
@Composable
private fun ColorPickerPreview() {
    ColorPicker(
        onDismiss = {},
        onConfirm = {}
    )
}

@Preview(showBackground = true)
@Composable
private fun ColorItemPreview() {
    ColorItem(
        color = darkRed,
        onItemClick = {}
    )
}
