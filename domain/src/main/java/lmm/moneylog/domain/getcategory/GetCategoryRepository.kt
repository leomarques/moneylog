package lmm.moneylog.domain.getcategory

import kotlinx.coroutines.flow.Flow
import lmm.moneylog.domain.models.Category

interface GetCategoryRepository {

    fun getCategory(id: Int): Flow<Category?>
}
