package lmm.moneylog.ui.components.bottombar.mocks

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import lmm.moneylog.ui.components.bottombar.models.BottomBarItem

/**
 * Provides preview/sample data for bottom bar components
 */
object BottomBarPreviewData {
    /**
     * Default selected index for preview data
     */
    const val DEFAULT_SELECTED_INDEX = 0

    /**
     * Empty click handler for preview/testing
     */
    val sampleOnClick: (Int) -> Unit = {}

    /**
     * Creates sample bottom bar items for preview/testing
     * Note: Icons requiring resources are loaded lazily through a factory function
     */
    fun sampleBottomBarItems(receiptIcon: ImageVector): List<BottomBarItem> =
        listOf(
            BottomBarItem(
                label = "Home",
                icon = Icons.Default.Home,
            ),
            BottomBarItem(
                label = "Transactions",
                icon = receiptIcon,
            ),
            BottomBarItem(
                label = "Options",
                icon = Icons.Default.Settings,
            )
        )

    /**
     * Creates sample bottom bar items with a selected index
     * @param iconOne Icon for transactions
     * @param iconTwo Icon for accounts/categories
     * @param selectedIndex The index of the item to mark as selected (uses DEFAULT_SELECTED_INDEX if not specified)
     */
    fun sampleBottomBarItemsWithSelection(
        iconOne: ImageVector,
        selectedIndex: Int = DEFAULT_SELECTED_INDEX
    ): List<BottomBarItem> =
        sampleBottomBarItems(iconOne)
            .mapIndexed { index, item ->
                item.copy(isSelected = index == selectedIndex)
            }
}
