package lmm.moneylog.ui.components.bottomsheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.ui.theme.DarkPurple
import lmm.moneylog.ui.theme.DarkRed
import lmm.moneylog.ui.theme.Size

@Composable
fun BottomSheetContent(
    list: List<Pair<String, Color>>,
    onConfirm: (Int) -> Unit,
    onDismiss: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surface)
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
                Divider()
            }
        }
    }
}

@Preview
@Composable
fun BottomSheetContentPreview() {
    BottomSheetContent(
        list = listOf(
            Pair("Nubank", DarkPurple),
            Pair("Santander", DarkRed)
        ),
        onConfirm = {},
        onDismiss = {}
    )
}
