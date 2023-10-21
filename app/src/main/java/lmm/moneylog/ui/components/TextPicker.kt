package lmm.moneylog.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
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
import androidx.compose.ui.window.Dialog
import lmm.moneylog.R
import lmm.moneylog.ui.theme.SpaceSize

@Composable
fun TextPicker(
    list: List<String>,
    onConfirm: (Int) -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
        content = {
            LazyColumn(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(
                        horizontal = SpaceSize.DefaultSpaceSize,
                        vertical = SpaceSize.SmallSpaceSize
                    )
            ) {
                itemsIndexed(list) { index, item ->
                    Item(
                        text = item,
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
    )
}

@Composable
fun Item(
    text: String,
    onItemClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surface)
            .fillMaxWidth()
            .height(SpaceSize.OneLineListItemHeight)
            .padding(
                vertical = SpaceSize.SmallSpaceSize,
                horizontal = SpaceSize.DefaultSpaceSize
            )
            .clickable { onItemClick() },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text.ifEmpty {
                stringResource(R.string.gettransactions_nodescription)
            },
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            style = MaterialTheme.typography.bodyLarge,
            color = if (text.isEmpty()) {
                Color.Gray
            } else {
                MaterialTheme.colorScheme.onSurface
            }
        )
    }
}

@Preview
@Composable
fun TextPickerPreview() {
    TextPicker(
        list = listOf(
            "Alimentação",
            "Alimentação",
            "Alimentação",
            "Alimentação",
            "Alimentação"
        ),
        onDismiss = {},
        onConfirm = {}
    )
}

@Preview
@Composable
fun ItemPreview() {
    Item(
        text = "Alimentação",
        onItemClick = {}
    )
}
