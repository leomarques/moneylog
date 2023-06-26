package lmm.moneylog.domain.addaccount

import lmm.moneylog.domain.models.Account

class AddAccountInteractor(private val addAccountRepository: AddAccountRepository) {
    suspend fun execute(account: Account) {
        addAccountRepository.save(account)
    }
}
