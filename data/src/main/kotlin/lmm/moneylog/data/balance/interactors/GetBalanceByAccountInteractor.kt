package lmm.moneylog.data.balance.interactors

import lmm.moneylog.data.balance.repositories.GetBalanceRepository

class GetBalanceByAccountInteractor(
    private val getBalanceRepository: GetBalanceRepository
) {
    suspend fun execute(accountId: Int): Double {
        return getBalanceRepository.getAllValuesByAccount(accountId).sum()
    }
}
