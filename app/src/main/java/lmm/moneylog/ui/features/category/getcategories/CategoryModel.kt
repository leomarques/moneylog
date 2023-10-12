package lmm.moneylog.ui.features.category.getcategories

import androidx.compose.ui.graphics.Color

data class CategoryModel(
    val id: Int,
    val name: String,
    val color: Color,
    val isIncome: Boolean
)
