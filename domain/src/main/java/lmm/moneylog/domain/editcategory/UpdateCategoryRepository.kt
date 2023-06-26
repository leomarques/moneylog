package lmm.moneylog.domain.editcategory

import lmm.moneylog.domain.models.Category

interface UpdateCategoryRepository {

    suspend fun update(category: Category)
}
