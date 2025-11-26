package lmm.moneylog.misc

import android.content.Context
import lmm.moneylog.ui.features.account.archive.viewmodel.ArchivedAccountsViewModel
import lmm.moneylog.ui.features.account.detail.viewmodel.AccountDetailViewModel
import lmm.moneylog.ui.features.account.list.viewmodel.AccountsListViewModel
import lmm.moneylog.ui.features.account.transfer.viewmodel.AccountTransferViewModel
import lmm.moneylog.ui.features.balancecard.viewmodel.BalanceCardViewModel
import lmm.moneylog.ui.features.category.detail.viewmodel.CategoryDetailViewModel
import lmm.moneylog.ui.features.category.list.viewmodel.CategoriesListViewModel
import lmm.moneylog.ui.features.categorykeywords.viewmodel.CategoryKeywordsViewModel
import lmm.moneylog.ui.features.creditcard.detail.viewmodel.CreditCardDetailViewModel
import lmm.moneylog.ui.features.creditcard.homecard.viewmodel.CreditHomeCardViewModel
import lmm.moneylog.ui.features.creditcard.list.viewmodel.CreditCardsListViewModel
import lmm.moneylog.ui.features.home.viewmodel.HomeViewModel
import lmm.moneylog.ui.features.invoice.viewmodel.InvoiceListViewModel
import lmm.moneylog.ui.features.notification.settings.viewmodel.NotificationSettingsViewModel
import lmm.moneylog.ui.features.transaction.detail.viewmodel.TransactionDetailViewModel
import lmm.moneylog.ui.features.transaction.list.viewmodel.TransactionsListViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val appModule =
    module {
        viewModelOf(::HomeViewModel)

        viewModelOf(::BalanceCardViewModel)
        viewModelOf(::CreditHomeCardViewModel)

        viewModelOf(::AccountsListViewModel)
        viewModelOf(::AccountDetailViewModel)

        viewModelOf(::CategoriesListViewModel)
        viewModelOf(::CategoryDetailViewModel)
        viewModelOf(::CategoryKeywordsViewModel)

        viewModelOf(::CreditCardsListViewModel)
        viewModelOf(::CreditCardDetailViewModel)

        viewModel { parameters ->
            TransactionsListViewModel(
                typeOfValue = parameters.get(),
                getTransactionsRepository = get(),
                getAccountsRepository = get(),
                getCategoriesRepository = get(),
                getCreditCardsRepository = get(),
                timeRepository = get()
            )
        }

        viewModelOf(::TransactionDetailViewModel)
        viewModelOf(::ArchivedAccountsViewModel)
        viewModelOf(::AccountTransferViewModel)
        viewModelOf(::InvoiceListViewModel)
        viewModelOf(::NotificationSettingsViewModel)

        factory {
            androidContext().getSharedPreferences(
                "moneylog",
                Context.MODE_PRIVATE
            )
        }
    }
