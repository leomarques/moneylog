package lmm.moneylog.domain.account.editaccount

import lmm.moneylog.domain.account.Account

class UpdateAccountInteractor(private val updateAccountRepository: UpdateAccountRepository) {

    suspend fun execute(account: Account) {
        updateAccountRepository.update(account)
    }
}
