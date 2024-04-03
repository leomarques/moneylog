package lmm.moneylog.ui.components.bottomsheet

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.R
import lmm.moneylog.ui.components.misc.MyCircle
import lmm.moneylog.ui.theme.Size
import lmm.moneylog.ui.theme.neutralColor

@Composable
fun BottomSheetItem(
    text: String,
    onItemClick: () -> Unit,
    color: Color? = null
) {
    Row(
        modifier =
            Modifier
                .background(MaterialTheme.colorScheme.surface)
                .fillMaxWidth()
                .height(Size.OneLineListItemHeight)
                .padding(horizontal = Size.DefaultSpaceSize)
                .clickable { onItemClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        MyCircle(
            modifier = Modifier.padding(end = Size.DefaultSpaceSize),
            color = color ?: neutralColor,
            size = Size.SmallCircleSize
        )

        Text(
            text = text.ifEmpty { stringResource(R.string.no_name) },
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            style = MaterialTheme.typography.bodyLarge,
            color =
                if (text.isNotEmpty()) {
                    MaterialTheme.colorScheme.onSurface
                } else {
                    neutralColor
                }
        )
    }
}

@Preview
@Composable
fun BottomSheetItemPreview() {
    BottomSheetItem(
        text = "Alimentação",
        onItemClick = {}
    )
}
