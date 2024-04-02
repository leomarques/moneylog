package lmm.moneylog.ui.features.category.detail.model

import androidx.compose.ui.graphics.Color
import lmm.moneylog.ui.theme.neutralColor

data class CategoryDetailUIState(
    val id: Int = -1,
    val name: String = "",
    val color: Color = neutralColor,
    val isIncome: Boolean = true,
    val isEdit: Boolean = false,
    val showFab: Boolean = true
)
