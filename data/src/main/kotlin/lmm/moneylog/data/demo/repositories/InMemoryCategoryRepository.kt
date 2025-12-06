package lmm.moneylog.data.demo.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import lmm.moneylog.data.category.model.Category
import lmm.moneylog.data.category.repositories.interfaces.AddCategoryRepository
import lmm.moneylog.data.category.repositories.interfaces.DeleteCategoryRepository
import lmm.moneylog.data.category.repositories.interfaces.GetCategoriesRepository
import lmm.moneylog.data.category.repositories.interfaces.UpdateCategoryRepository

class InMemoryCategoryRepository :
    GetCategoriesRepository,
    AddCategoryRepository,
    UpdateCategoryRepository,
    DeleteCategoryRepository {
    private val categories = MutableStateFlow<List<Category>>(emptyList())
    private var nextId = 1

    override fun getCategories(): Flow<List<Category>> = categories

    override suspend fun getCategoriesSuspend(): List<Category> = categories.value

    override suspend fun getCategoryById(id: Int): Category? =
        categories.value.firstOrNull { it.id == id }

    override suspend fun save(category: Category) {
        val newCategory = category.copy(id = nextId++)
        categories.value = categories.value + newCategory
    }

    override suspend fun update(category: Category) {
        categories.value =
            categories.value.map {
                if (it.id == category.id) category else it
            }
    }

    override suspend fun delete(id: Int) {
        categories.value = categories.value.filterNot { it.id == id }
    }

    internal fun clear() {
        categories.value = emptyList()
        nextId = 1
    }

    internal fun setInitialData(data: List<Category>) {
        categories.value = data
        nextId = (data.maxOfOrNull { it.id } ?: 0) + 1
    }
}
