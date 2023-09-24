package lmm.moneylog.data.category.repositories

import lmm.moneylog.domain.category.Category

interface GetCategoryRepository {
    suspend fun getCategory(id: Int): Category?
}
