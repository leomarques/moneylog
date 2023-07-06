package lmm.moneylog.domain.category.getcategory

import kotlinx.coroutines.flow.Flow
import lmm.moneylog.domain.category.Category

interface GetCategoryRepository {

    fun getCategory(id: Int): Flow<Category?>
}
