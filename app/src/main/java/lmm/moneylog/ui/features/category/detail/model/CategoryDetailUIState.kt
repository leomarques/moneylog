package lmm.moneylog.ui.features.category.detail.model

import androidx.compose.ui.graphics.Color

data class CategoryDetailUIState(
    val id: Int = -1,
    val name: String = "",
    val color: Color = Color.Gray,
    val isIncome: Boolean = true,
    val isEdit: Boolean = false,
    val showFab: Boolean = true
)
