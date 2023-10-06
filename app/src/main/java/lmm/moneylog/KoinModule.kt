package lmm.moneylog

import lmm.moneylog.ui.features.account.accountdetail.AccountDetailViewModel
import lmm.moneylog.ui.features.account.getaccounts.GetAccountsViewModel
import lmm.moneylog.ui.features.account.getaccounts.archive.GetArchivedAccountsViewModel
import lmm.moneylog.ui.features.category.categorydetail.CategoryDetailViewModel
import lmm.moneylog.ui.features.category.getcategories.GetCategoriesViewModel
import lmm.moneylog.ui.features.home.balancecard.BalanceCardViewModel
import lmm.moneylog.ui.features.transaction.gettransactions.GetTransactionsViewModel
import lmm.moneylog.ui.features.transaction.transactiondetail.TransactionDetailViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {

    viewModelOf(::GetAccountsViewModel)
    viewModelOf(::AccountDetailViewModel)

    viewModelOf(::GetCategoriesViewModel)
    viewModelOf(::CategoryDetailViewModel)

    viewModelOf(::BalanceCardViewModel)

    viewModel { parameters ->
        GetTransactionsViewModel(
            typeOfValue = parameters.get(),
            getTransactionsRepository = get()
        )
    }
    viewModelOf(::TransactionDetailViewModel)
    viewModelOf(::GetArchivedAccountsViewModel)
}
