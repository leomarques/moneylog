package lmm.moneylog.domain.editaccount

import lmm.moneylog.domain.models.Account

class UpdateAccountInteractor(private val updateAccountRepository: UpdateAccountRepository) {

    suspend fun execute(account: Account) {
        updateAccountRepository.update(account)
    }
}
