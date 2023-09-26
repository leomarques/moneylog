package lmm.moneylog.data.category.repositories

import lmm.moneylog.data.category.Category

interface UpdateCategoryRepository {
    suspend fun update(category: Category)
}
