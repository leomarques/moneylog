package lmm.moneylog.data.category.repositories

import lmm.moneylog.domain.category.Category

interface AddCategoryRepository {
    suspend fun save(category: Category)
}
