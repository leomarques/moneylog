package lmm.moneylog.data.category.repositories.interfaces

import lmm.moneylog.data.category.model.Category

interface UpdateCategoryRepository {
    suspend fun update(category: Category)
}
