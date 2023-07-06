package lmm.moneylog.domain.category.editcategory

import lmm.moneylog.domain.category.Category

interface UpdateCategoryRepository {

    suspend fun update(category: Category)
}
