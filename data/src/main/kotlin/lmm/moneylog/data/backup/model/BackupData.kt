package lmm.moneylog.data.backup.model

import lmm.moneylog.data.account.model.Account
import lmm.moneylog.data.accounttransfer.model.AccountTransfer
import lmm.moneylog.data.category.model.Category
import lmm.moneylog.data.categorypredictor.model.CategoryKeyword
import lmm.moneylog.data.creditcard.model.CreditCard
import lmm.moneylog.data.transaction.model.Transaction

data class BackupData(
    val accounts: List<Account>,
    val categories: List<Category>,
    val creditCards: List<CreditCard>,
    val transactions: List<Transaction>,
    val accountTransfers: List<AccountTransfer>,
    val categoryKeywords: List<CategoryKeyword>
)
