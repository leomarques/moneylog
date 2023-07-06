package lmm.moneylog.domain.category.deletecategory

interface DeleteCategoryRepository {
    suspend fun delete(id: Int)
}
