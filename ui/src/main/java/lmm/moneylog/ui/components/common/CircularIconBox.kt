package lmm.moneylog.ui.components.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * A reusable circular icon box component with customizable background
 *
 * @param icon The icon to display
 * @param contentDescription Accessibility description for the icon
 * @param modifier Modifier for the box
 * @param backgroundColor Background color of the circle
 * @param iconTint Tint color for the icon
 * @param boxSize Size of the circular box
 * @param iconSize Size of the icon within the box
 */
@Composable
fun CircularIconBox(
    icon: ImageVector,
    contentDescription: String?,
    backgroundColor: Color,
    iconTint: Color,
    modifier: Modifier = Modifier,
    boxSize: Dp = CircularIconBoxDefaults.DefaultBoxSize,
    iconSize: Dp = CircularIconBoxDefaults.DefaultIconSize
) {
    Box(
        modifier =
            modifier
                .size(boxSize)
                .clip(CircleShape)
                .background(backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = iconTint,
            modifier = Modifier.size(iconSize)
        )
    }
}

object CircularIconBoxDefaults {
    val DefaultBoxSize = 32.dp
    val DefaultIconSize = 18.dp
}
