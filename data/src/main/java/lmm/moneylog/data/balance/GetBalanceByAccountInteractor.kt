package lmm.moneylog.data.balance

class GetBalanceByAccountInteractor(
    private val getBalanceRepository: GetBalanceRepository
) {
    suspend fun execute(accountId: Int): Double {
        return getBalanceRepository.getAllValuesByAccount(accountId).sum()
    }
}
