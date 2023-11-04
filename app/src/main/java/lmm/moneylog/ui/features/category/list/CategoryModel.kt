package lmm.moneylog.ui.features.category.list

import androidx.compose.ui.graphics.Color

data class CategoryModel(
    val id: Int,
    val name: String,
    val color: Color,
    val isIncome: Boolean
)
