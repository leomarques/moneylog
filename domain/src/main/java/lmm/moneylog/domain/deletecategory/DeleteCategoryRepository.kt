package lmm.moneylog.domain.deletecategory

interface DeleteCategoryRepository {
    suspend fun delete(id: Int)
}
