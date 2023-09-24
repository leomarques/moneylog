package lmm.moneylog.data.category.repositories

import kotlinx.coroutines.flow.Flow
import lmm.moneylog.domain.category.Category

interface GetCategoryRepository {

    fun getCategory(id: Int): Flow<Category?>
}
