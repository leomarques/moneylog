package lmm.moneylog.ui.components.bottombar.models

import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Represents a single item in the bottom navigation bar
 *
 * @property label The text label for the navigation item
 * @property icon The icon vector to display
 * @property isSelected Whether this item is currently selected
 */
data class BottomBarItem(
    val label: String,
    val icon: ImageVector,
    val isSelected: Boolean = false
)
