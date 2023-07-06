package lmm.moneylog.domain.account.addaccount

import lmm.moneylog.domain.account.Account

class AddAccountInteractor(private val addAccountRepository: AddAccountRepository) {
    suspend fun execute(account: Account) {
        addAccountRepository.save(account)
    }
}
