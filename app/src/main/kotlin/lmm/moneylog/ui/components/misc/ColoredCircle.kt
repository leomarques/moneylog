package lmm.moneylog.ui.components.misc

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import lmm.moneylog.ui.theme.Size
import lmm.moneylog.ui.theme.outcome

@Composable
fun ColoredCircle(
    color: Color,
    modifier: Modifier = Modifier,
    letters: String? = null,
    size: Dp = Size.DefaultCircleSize
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Canvas(
            modifier = Modifier.size(size),
            onDraw = { drawCircle(color = color) }
        )
        letters?.let {
            Text(
                text =
                    it
                        .substring(
                            startIndex = 0,
                            endIndex =
                                Integer.min(
                                    it.length,
                                    2
                                )
                        )
                        .uppercase(),
                style = MaterialTheme.typography.titleMedium,
                color = Color.White,
                modifier = Modifier.offset(y = (-1).dp)
            )
        }
    }
}

@Preview
@Composable
private fun MyCirclePreview() {
    ColoredCircle(
        color = outcome,
        letters = "AL"
    )
}
