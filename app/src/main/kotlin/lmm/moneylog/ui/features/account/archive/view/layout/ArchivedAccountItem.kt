package lmm.moneylog.ui.features.account.archive.view.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import lmm.moneylog.ui.components.misc.MyDivider
import lmm.moneylog.ui.features.account.archive.view.components.MoreOptionsDropDown

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ArchivedAccountItem(
    id: Int,
    name: String,
    showDivider: Boolean,
    onUnArchiveClick: (Int) -> Unit,
    onDeleteClick: (Int) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var offset by remember { mutableStateOf(0.dp) }
    val density = LocalDensity.current

    Box(
        modifier =
            Modifier
                .pointerInteropFilter {
                    offset = with(density) { it.x.toDp() }
                    false
                }
    ) {
        ArchivedAccountItemCell(name) {
            expanded = true
        }

        MoreOptionsDropDown(
            id = id,
            expanded = expanded,
            offset = offset,
            onUnArchiveClick = onUnArchiveClick,
            onDeleteClick = onDeleteClick,
            onDismissRequest = { expanded = false }
        )
    }

    if (showDivider) {
        MyDivider()
    }
}

@Preview
@Composable
fun ArchivedAccountItemPreview() {
    Box(modifier = Modifier.background(MaterialTheme.colorScheme.surface)) {
        ArchivedAccountItem(
            id = 0,
            name = "Itaú",
            showDivider = true,
            onUnArchiveClick = {},
            onDeleteClick = {}
        )
    }
}
