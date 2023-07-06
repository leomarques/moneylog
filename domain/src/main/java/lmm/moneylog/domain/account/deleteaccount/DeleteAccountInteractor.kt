package lmm.moneylog.domain.account.deleteaccount

class DeleteAccountInteractor(
    private val deleteAccountRepository: DeleteAccountRepository
) {
    suspend fun execute(id: Int) {
        deleteAccountRepository.delete(id)
    }
}
