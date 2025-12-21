package lmm.moneylog.ui.features.category.list.model

data class CategoriesListUIState(
    val list: List<CategoryModel> = emptyList(),
    val selectedTabIndex: Int = 0
)
