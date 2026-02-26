package lmm.moneylog.ui.features.transfer.list.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.R
import lmm.moneylog.ui.features.transfer.list.model.TransferModel
import lmm.moneylog.ui.theme.Size
import lmm.moneylog.ui.theme.neutralColor

@Composable
fun TransfersListItem(
    transfer: TransferModel,
    onItemClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .height(Size.TwoLinesListItemHeight)
                .padding(
                    vertical = Size.SmallSpaceSize,
                    horizontal = Size.DefaultSpaceSize
                ).clickable { onItemClick(transfer.id) },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        with(transfer) {
            Row(
                Modifier
                    .weight(0.7f)
                    .padding(end = Size.SmallSpaceSize),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = null,
                    tint = neutralColor,
                    modifier = Modifier.padding(end = Size.DefaultSpaceSize)
                )

                Column {
                    Text(
                        text = originAccount.ifEmpty {
                            stringResource(R.string.state_no_name)
                        },
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 1,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Text(
                        text = destinationAccount.ifEmpty {
                            stringResource(R.string.state_no_name)
                        },
                        style = MaterialTheme.typography.bodySmall,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Text(
                modifier =
                    Modifier
                        .weight(0.3f)
                        .padding(start = Size.DefaultSpaceSize),
                text = value,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.End,
                style = MaterialTheme.typography.titleSmall,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TransfersListItemPreview() {
    Column {
        TransfersListItem(
            transfer = transferModelPreview1,
            onItemClick = {}
        )
        TransfersListItem(
            transfer = transferModelPreview2,
            onItemClick = {}
        )
    }
}
