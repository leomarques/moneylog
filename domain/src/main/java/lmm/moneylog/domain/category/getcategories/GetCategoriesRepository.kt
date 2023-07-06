package lmm.moneylog.domain.category.getcategories

import kotlinx.coroutines.flow.Flow
import lmm.moneylog.domain.category.Category

interface GetCategoriesRepository {

    fun getCategories(): Flow<List<Category>>
}
