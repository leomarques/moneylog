package lmm.moneylog

import android.content.Context
import lmm.moneylog.ui.features.account.accountdetail.AccountDetailViewModel
import lmm.moneylog.ui.features.account.archive.GetArchivedAccountsViewModel
import lmm.moneylog.ui.features.account.getaccounts.GetAccountsViewModel
import lmm.moneylog.ui.features.account.transfer.AccountTransferViewModel
import lmm.moneylog.ui.features.category.categorydetail.CategoryDetailViewModel
import lmm.moneylog.ui.features.category.getcategories.GetCategoriesViewModel
import lmm.moneylog.ui.features.home.HomeViewModel
import lmm.moneylog.ui.features.home.balancecard.BalanceCardViewModel
import lmm.moneylog.ui.features.transaction.gettransactions.GetTransactionsViewModel
import lmm.moneylog.ui.features.transaction.transactiondetail.TransactionDetailViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {

    viewModelOf(::HomeViewModel)
    viewModelOf(::GetAccountsViewModel)
    viewModelOf(::AccountDetailViewModel)

    viewModelOf(::GetCategoriesViewModel)
    viewModelOf(::CategoryDetailViewModel)

    viewModelOf(::BalanceCardViewModel)

    viewModel { parameters ->
        GetTransactionsViewModel(
            typeOfValue = parameters.get(),
            get(),
            get(),
            get()
        )
    }
    viewModelOf(::TransactionDetailViewModel)
    viewModelOf(::GetArchivedAccountsViewModel)
    viewModelOf(::AccountTransferViewModel)

    factory {
        androidContext().getSharedPreferences(
            "moneylog",
            Context.MODE_PRIVATE
        )
    }
}
