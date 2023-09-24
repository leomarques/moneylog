package lmm.moneylog.data.category.repositories

import lmm.moneylog.domain.category.Category

interface UpdateCategoryRepository {
    suspend fun update(category: Category)
}
