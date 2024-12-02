package lmm.moneylog.ui.components.bottomsheet

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.ui.theme.Size
import lmm.moneylog.ui.theme.darkPurple
import lmm.moneylog.ui.theme.darkRed

@Composable
fun BottomSheetContent(
    list: List<Pair<String, Color?>>,
    onConfirm: (Int) -> Unit,
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit
) {
    LazyColumn(
        modifier =
            modifier
                .padding(horizontal = Size.DefaultSpaceSize)
                .padding(
                    top = Size.SmallSpaceSize,
                    bottom = Size.XLargeSpaceSize
                )
    ) {
        itemsIndexed(list) { index, item ->
            BottomSheetItem(
                text = item.first,
                color = item.second,
                onItemClick = {
                    onDismiss()
                    onConfirm(index)
                }
            )

            if (index != list.size - 1) {
                HorizontalDivider()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun BottomSheetContentPreview() {
    BottomSheetContent(
        list =
            listOf(
                Pair("Nubank", darkPurple),
                Pair("Santander", darkRed)
            ),
        onConfirm = {},
        onDismiss = {}
    )
}
