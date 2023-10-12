package lmm.moneylog.ui.features.category.categorydetail

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color

data class CategoryDetailModel(
    val name: MutableState<String> = mutableStateOf(""),
    val color: Color = Color.Gray,
    val isEdit: Boolean = false,
    val id: Int = -1,
    val isIncome: MutableState<Boolean> = mutableStateOf(true)
)
