package lmm.moneylog.domain.getcategories

import kotlinx.coroutines.flow.Flow
import lmm.moneylog.domain.models.Category

interface GetCategoriesRepository {

    fun getCategories(): Flow<List<Category>>
}
