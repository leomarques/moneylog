package lmm.moneylog.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import lmm.moneylog.R
import lmm.moneylog.ui.theme.SpaceSize

@Composable
fun BottomSheetItem(
    text: String,
    onItemClick: () -> Unit,
    color: Color? = null
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
        verticalAlignment = Alignment.CenterVertically
    ) {
        MyCircle(
            color = color ?: Color.Gray,
            size = 30.dp
        )

        Spacer(modifier = Modifier.width(SpaceSize.DefaultSpaceSize))

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
fun ItemPreview() {
    BottomSheetItem(
        text = "Alimentação",
        onItemClick = {}
    )
}
