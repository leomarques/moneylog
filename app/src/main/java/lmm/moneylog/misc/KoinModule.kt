package lmm.moneylog.misc

import android.content.Context
import lmm.moneylog.ui.features.account.archive.viewmodel.ArchivedAccountsViewModel
import lmm.moneylog.ui.features.account.detail.viewmodel.AccountDetailViewModel
import lmm.moneylog.ui.features.account.list.viewmodel.AccountsListViewModel
import lmm.moneylog.ui.features.account.transfer.viewmodel.AccountTransferViewModel
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
    viewModelOf(::AccountsListViewModel)
    viewModelOf(::AccountDetailViewModel)

    viewModelOf(::GetCategoriesViewModel)
    viewModelOf(::CategoryDetailViewModel)

    viewModelOf(::BalanceCardViewModel)

    viewModel { parameters ->
        GetTransactionsViewModel(
            typeOfValue = parameters.get(),
            getTransactionsRepository = get(),
            getAccountsRepository = get(),
            getCategoriesRepository = get()
        )
    }
    viewModelOf(::TransactionDetailViewModel)
    viewModelOf(::ArchivedAccountsViewModel)
    viewModelOf(::AccountTransferViewModel)

    factory {
        androidContext().getSharedPreferences(
            "moneylog",
            Context.MODE_PRIVATE
        )
    }
}
