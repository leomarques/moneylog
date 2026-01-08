package lmm.moneylog.data.balance.interactors

import lmm.moneylog.data.accounttransfer.repositories.AccountTransferRepository
import lmm.moneylog.data.balance.repositories.GetBalanceRepository

class GetBalanceByAccountWithTransfersInteractor(
    private val getBalanceRepository: GetBalanceRepository,
    private val accountTransferRepository: AccountTransferRepository
) {
    suspend fun execute(accountId: Int): Double {
        // Calculate balance from transactions
        var balance = getBalanceRepository.getAllValuesByAccount(accountId).sum()

        // Adjust for transfers
        val transfers = accountTransferRepository.getAllTransfers()
        transfers
            .filter { it.originAccountId == accountId }
            .forEach { balance -= it.value }

        transfers
            .filter { it.destinationAccountId == accountId }
            .forEach { balance += it.value }

        return balance
    }
}
