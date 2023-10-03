package lmm.moneylog.data.category.repositories

import kotlinx.coroutines.flow.Flow
import lmm.moneylog.data.category.Category

interface GetCategoriesRepository {
    fun getCategories(): Flow<List<Category>>
    suspend fun getCategoriesSuspend(): List<Category>
}
