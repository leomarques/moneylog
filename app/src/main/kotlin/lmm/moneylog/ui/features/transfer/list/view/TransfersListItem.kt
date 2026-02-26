package lmm.moneylog.ui.features.transfer.list.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import lmm.moneylog.R
import lmm.moneylog.ui.features.transfer.list.model.TransferModel
import lmm.moneylog.ui.theme.Size

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
                .height(72.dp)
                .clickable { onItemClick(transfer.id) }
                .padding(
                    horizontal = Size.DefaultSpaceSize,
                    vertical = 8.dp
                ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        with(transfer) {
            // Left side: From/To flow grouped together
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // From account
                AccountChip(
                    label = stringResource(R.string.transfer_label_from),
                    accountName = originAccount
                )

                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(4.dp)
                        .size(16.dp)
                )

                // To account
                AccountChip(
                    label = stringResource(R.string.transfer_label_to),
                    accountName = destinationAccount
                )
            }

            // Right side: Value - pushes to the end
            Text(
                text = value,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.End,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
        }
    }
}

@Composable
private fun AccountChip(
    label: String,
    accountName: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.width(60.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.outline,
            maxLines = 1
        )
        Text(
            text = accountName.ifEmpty { stringResource(R.string.state_no_name) },
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurface,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
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
        HorizontalDivider()
        TransfersListItem(
            transfer = transferModelPreview2,
            onItemClick = {}
        )
        HorizontalDivider()
        TransfersListItem(
            transfer = transferModelPreview3,
            onItemClick = {}
        )
    }
}
