package lmm.moneylog.ui.components.bottomsheet

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    text: String? = null
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (text.isNullOrEmpty().not()) {
            Text(
                text = text,
                modifier = Modifier.padding(vertical = Size.SmallSpaceSize)
            )
        }

        LazyColumn(
            modifier =
                Modifier
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
        onDismiss = {},
        text = "Select account"
    )
}
