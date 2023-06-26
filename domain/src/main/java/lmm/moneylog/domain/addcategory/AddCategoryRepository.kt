package lmm.moneylog.domain.addcategory

import lmm.moneylog.domain.models.Category

interface AddCategoryRepository {
    suspend fun save(category: Category)
}
