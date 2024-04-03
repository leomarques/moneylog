package lmm.moneylog.ui.features.account.archive.view.components

import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import lmm.moneylog.R

@Composable
fun MoreOptionsDropDown(
    id: Int,
    expanded: Boolean,
    offset: Dp,
    onUnArchiveClick: (Int) -> Unit,
    onDeleteClick: (Int) -> Unit,
    onDismissRequest: () -> Unit
) {
    DropdownMenu(
        expanded = expanded,
        offset =
            DpOffset(
                x = offset,
                y = 0.dp
            ),
        onDismissRequest = onDismissRequest
    ) {
        val unarchive = stringResource(R.string.unarchive)
        val delete = stringResource(R.string.delete)

        listOf(unarchive, delete)
            .forEachIndexed { index, it ->
                DropdownMenuItem(
                    onClick = {
                        onDismissRequest()

                        when (index) {
                            0 -> {
                                onUnArchiveClick(id)
                            }

                            1 -> {
                                onDeleteClick(id)
                            }
                        }
                    },
                    text = {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                )
            }
    }
}

@Preview
@Composable
fun MoreOptionsDropDownPreview() {
    MoreOptionsDropDown(
        id = 1,
        expanded = true,
        offset = 0.dp,
        onUnArchiveClick = {},
        onDeleteClick = {},
        onDismissRequest = {}
    )
}
