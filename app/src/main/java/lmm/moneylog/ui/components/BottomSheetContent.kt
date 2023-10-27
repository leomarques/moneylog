package lmm.moneylog.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import lmm.moneylog.ui.theme.SpaceSize

@Composable
fun BottomSheetContent(
    list: List<Pair<String, Color>>,
    onConfirm: (Int) -> Unit,
    onDismiss: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surface)
            .padding(horizontal = SpaceSize.DefaultSpaceSize)
            .padding(
                top = SpaceSize.SmallSpaceSize,
                bottom = SpaceSize.XLargeSpaceSize
            )
    ) {
        itemsIndexed(list) { index, item ->
            BottomSheetItem(
                text = item.first,
                color = item.second,
                onItemClick = {
                    onConfirm(index)
                    onDismiss()
                }
            )

            if (index != list.size - 1) {
                Divider()
            }
        }
    }
}
