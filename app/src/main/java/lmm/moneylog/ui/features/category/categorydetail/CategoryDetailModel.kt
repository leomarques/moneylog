package lmm.moneylog.ui.features.category.categorydetail

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf

data class CategoryDetailModel(
    val name: MutableState<String> = mutableStateOf(""),
    val color: MutableState<Long> = mutableLongStateOf(0),
    val isEdit: Boolean = false,
    val id: Int = -1
)
