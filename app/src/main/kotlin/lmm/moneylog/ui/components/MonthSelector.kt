package lmm.moneylog.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.ui.theme.Size

@Composable
fun MonthSelector(
    onPreviousMonthClick: () -> Unit,
    onNextMonthClick: () -> Unit,
    monthName: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        MonthButton(
            onClick = onPreviousMonthClick,
            imageVector = Icons.AutoMirrored.Filled.ArrowBack
        )

        MonthName(monthName, modifier = Modifier.weight(1f))

        MonthButton(
            onClick = onNextMonthClick,
            imageVector = Icons.AutoMirrored.Filled.ArrowForward
        )
    }
}

@Composable
fun MonthName(
    monthName: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        Text(monthName)
    }
}

@Composable
fun MonthButton(
    onClick: () -> Unit,
    imageVector: ImageVector,
    modifier: Modifier = Modifier
) {
    Box(modifier.padding(Size.SmallSpaceSize)) {
        IconButton(onClick = onClick) {
            Icon(
                imageVector = imageVector,
                contentDescription = null
            )
        }
    }
}

@Preview
@Composable
private fun MonthSelectorPreview() {
    MonthSelector(
        onPreviousMonthClick = {},
        onNextMonthClick = {},
        monthName = "Janeiro"
    )
}
