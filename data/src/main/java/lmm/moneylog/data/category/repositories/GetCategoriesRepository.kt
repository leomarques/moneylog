package lmm.moneylog.data.category.repositories

import kotlinx.coroutines.flow.Flow
import lmm.moneylog.domain.category.Category

interface GetCategoriesRepository {
    fun getCategories(): Flow<List<Category>>
}
