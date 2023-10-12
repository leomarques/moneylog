package lmm.moneylog.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun MyCircle(color: Color) {
    Canvas(
        modifier = Modifier.size(25.dp),
        onDraw = {
            drawCircle(color = color)
        }
    )
}

@Preview
@Composable
fun MyCirclePreview() {
    MyCircle(Color.Red)
}
