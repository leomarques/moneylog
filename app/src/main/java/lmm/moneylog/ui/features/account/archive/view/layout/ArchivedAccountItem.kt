package lmm.moneylog.ui.features.account.archive.view.layout

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import lmm.moneylog.R
import lmm.moneylog.ui.theme.Size

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ArchivedAccountItem(
    id: Int,
    name: String,
    onItemClick: (Int) -> Unit,
    onUnArchiveClick: (Int) -> Unit,
    onDeleteClick: (Int) -> Unit,
    showDivider: Boolean
) {
    var expanded by remember { mutableStateOf(false) }
    var offset by remember { mutableStateOf(0.dp) }
    val density = LocalDensity.current

    Box(
        modifier = Modifier.pointerInteropFilter {
            offset = with(density) { it.x.toDp() }
            false
        }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(Size.OneLineListItemHeight)
                .padding(
                    vertical = Size.SmallSpaceSize,
                    horizontal = Size.DefaultSpaceSize
                )
                .clickable { onItemClick(id) },
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
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

            IconButton(onClick = { expanded = true }) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = stringResource(R.string.more_options_desc)
                )
            }
        }

        DropdownMenu(
            expanded = expanded,
            offset = DpOffset(
                x = offset,
                y = 0.dp
            ),
            onDismissRequest = {
                expanded = false
            }
        ) {
            listOf(
                stringResource(R.string.unarchive),
                stringResource(R.string.delete)
            ).forEachIndexed { index, it ->
                DropdownMenuItem(
                    onClick = {
                        when (index) {
                            0 -> {
                                onUnArchiveClick(id)
                            }

                            1 -> {
                                onDeleteClick(id)
                            }
                        }
                        expanded = false
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

    if (showDivider) {
        Divider(Modifier.padding(horizontal = Size.DefaultSpaceSize))
    }
}
