package lmm.moneylog.data.category.repositories.interfaces

import kotlinx.coroutines.flow.Flow
import lmm.moneylog.data.category.model.Category

interface GetCategoriesRepository {
    fun getCategories(): Flow<List<Category>>
    suspend fun getCategoriesSuspend(): List<Category>
    suspend fun getCategoryById(id: Int): Category?
}
