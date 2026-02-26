package lmm.moneylog.data.balance.interactors

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import lmm.moneylog.data.accounttransfer.repositories.AccountTransferRepository
import lmm.moneylog.data.balance.repositories.GetBalanceRepository

class GetBalancesForAllAccountsWithTransfersInteractor(
    private val getBalanceRepository: GetBalanceRepository,
    private val accountTransferRepository: AccountTransferRepository
) {
    /**
     * Returns a Flow that emits a map of account IDs to their balances considering transfers.
     * Balances are calculated by:
     * 1. Summing all transaction values for each account
     * 2. Subtracting transfers where the account is the origin
     * 3. Adding transfers where the account is the destination
     */
    fun execute(): Flow<Map<Int, Double>> =
        combine(
            getBalanceRepository.getTransactions(),
            accountTransferRepository.getTransfers()
        ) { transactions, transfers ->
            val accountBalances = mutableMapOf<Int, Double>()

            // Group transactions by account
            transactions
                .filter { it.accountId != null }
                .groupBy { it.accountId!! }
                .forEach { (accountId, accountTransactions) ->
                    accountBalances[accountId] = accountTransactions.sumOf { it.value }
                }

            // Adjust for transfers - subtract from origin
            transfers
                .groupBy { it.originAccountId }
                .forEach { (originAccountId, originTransfers) ->
                    accountBalances.getOrDefault(originAccountId, 0.0).let { currentBalance ->
                        accountBalances[originAccountId] = currentBalance - originTransfers.sumOf { it.value }
                    }
                }

            // Adjust for transfers - add to destination
            transfers
                .groupBy { it.destinationAccountId }
                .forEach { (destinationAccountId, destinationTransfers) ->
                    accountBalances.getOrDefault(destinationAccountId, 0.0).let { currentBalance ->
                        accountBalances[destinationAccountId] = currentBalance + destinationTransfers.sumOf { it.value }
                    }
                }

            accountBalances
        }
}
