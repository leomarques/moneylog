package lmm.moneylog.ui.components.misc

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import lmm.moneylog.ui.theme.darkBlue
import lmm.moneylog.ui.theme.darkBrow
import lmm.moneylog.ui.theme.darkGreen
import lmm.moneylog.ui.theme.darkOrange
import lmm.moneylog.ui.theme.darkPink
import lmm.moneylog.ui.theme.darkPurple
import lmm.moneylog.ui.theme.darkRed
import lmm.moneylog.ui.theme.darkYellow

@Composable
fun ColorPicker(
    onConfirm: (Color) -> Unit,
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    selectedColor: Color? = null
) {
    val list =
        listOf(
            darkRed,
            darkBlue,
            darkGreen,
            darkYellow,
            darkOrange,
            darkPurple,
            darkPink,
            darkBrow
        )

    var currentSelection by remember { mutableStateOf(selectedColor) }

    Dialog(
        onDismissRequest = onDismiss,
        content = {
            Card(
                modifier = modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Choose a Color",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(bottom = 20.dp)
                    )

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(4),
                        contentPadding = PaddingValues(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(list) { color ->
                            ColorItem(
                                color = color,
                                isSelected = currentSelection == color,
                                onItemClick = {
                                    currentSelection = color
                                    onConfirm(color)
                                    onDismiss()
                                }
                            )
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun ColorItem(
    color: Color,
    onItemClick: () -> Unit,
    modifier: Modifier = Modifier,
    isSelected: Boolean = false
) {
    val scale by animateFloatAsState(
        targetValue = if (isSelected) 0.9f else 1f,
        animationSpec = tween(durationMillis = 150),
        label = "colorItemScale"
    )

    Box(
        modifier =
            modifier
                .aspectRatio(1f)
                .clip(CircleShape)
                .scale(scale)
                .background(
                    color = color,
                    shape = CircleShape
                )
                .then(
                    if (isSelected) {
                        Modifier.border(
                            width = 3.dp,
                            color = MaterialTheme.colorScheme.primary,
                            shape = CircleShape
                        )
                    } else {
                        Modifier.border(
                            width = 2.dp,
                            color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
                            shape = CircleShape
                        )
                    }
                )
                .clickable { onItemClick() },
        contentAlignment = Alignment.Center
    ) {
        if (isSelected) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "Selected",
                tint = Color.White,
                modifier = Modifier.size(32.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ColorPickerPreview() {
    ColorPicker(
        onDismiss = {},
        onConfirm = {}
    )
}

@Preview(showBackground = true)
@Composable
private fun ColorItemPreview() {
    ColorItem(
        color = darkRed,
        onItemClick = {}
    )
}
