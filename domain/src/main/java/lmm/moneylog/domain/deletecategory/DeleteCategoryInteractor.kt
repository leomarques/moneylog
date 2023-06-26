package lmm.moneylog.domain.deletecategory

class DeleteCategoryInteractor(
    private val deleteCategoryRepository: DeleteCategoryRepository
) {
    suspend fun execute(id: Int) {
        deleteCategoryRepository.delete(id)
    }
}
