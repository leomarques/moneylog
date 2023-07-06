package lmm.moneylog.domain.category.addcategory

import lmm.moneylog.domain.category.Category

interface AddCategoryRepository {
    suspend fun save(category: Category)
}
