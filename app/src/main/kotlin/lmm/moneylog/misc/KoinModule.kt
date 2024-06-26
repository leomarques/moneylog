package lmm.moneylog.misc

import android.content.Context
import lmm.moneylog.ui.features.account.archive.viewmodel.ArchivedAccountsViewModel
import lmm.moneylog.ui.features.account.detail.viewmodel.AccountDetailViewModel
import lmm.moneylog.ui.features.account.list.viewmodel.AccountsListViewModel
import lmm.moneylog.ui.features.account.transfer.viewmodel.AccountTransferViewModel
import lmm.moneylog.ui.features.balancecard.viewmodel.BalanceCardViewModel
import lmm.moneylog.ui.features.category.detail.viewmodel.CategoryDetailViewModel
import lmm.moneylog.ui.features.category.list.viewmodel.CategoriesListViewModel
import lmm.moneylog.ui.features.home.viewmodel.HomeViewModel
import lmm.moneylog.ui.features.transaction.detail.viewmodel.TransactionDetailViewModel
import lmm.moneylog.ui.features.transaction.list.viewmodel.TransactionsListViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val appModule =
    module {
        viewModelOf(::HomeViewModel)
        viewModelOf(::AccountsListViewModel)
        viewModelOf(::AccountDetailViewModel)

        viewModelOf(::CategoriesListViewModel)
        viewModelOf(::CategoryDetailViewModel)

        viewModelOf(::BalanceCardViewModel)

        viewModel { parameters ->
            TransactionsListViewModel(
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
