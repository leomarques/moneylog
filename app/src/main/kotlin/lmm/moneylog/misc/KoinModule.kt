package lmm.moneylog.misc

import android.content.Context
import lmm.moneylog.notification.converter.TransactionConverter
import lmm.moneylog.notification.converter.TransactionConverterImpl
import lmm.moneylog.notification.parser.NubankTransactionParser
import lmm.moneylog.notification.parser.TransactionParser
import lmm.moneylog.notification.predictor.CreditCardPredictor
import lmm.moneylog.ui.features.account.archive.viewmodel.ArchivedAccountsViewModel
import lmm.moneylog.ui.features.account.detail.viewmodel.AccountDetailViewModel
import lmm.moneylog.ui.features.account.list.viewmodel.AccountsListViewModel
import lmm.moneylog.ui.features.account.transfer.viewmodel.AccountTransferViewModel
import lmm.moneylog.ui.features.balancecard.viewmodel.BalanceCardViewModel
import lmm.moneylog.ui.features.category.detail.viewmodel.CategoryDetailViewModel
import lmm.moneylog.ui.features.category.list.viewmodel.CategoriesListViewModel
import lmm.moneylog.ui.features.creditcard.detail.viewmodel.CreditCardDetailViewModel
import lmm.moneylog.ui.features.creditcard.homecard.viewmodel.CreditHomeCardViewModel
import lmm.moneylog.ui.features.creditcard.list.viewmodel.CreditCardsListViewModel
import lmm.moneylog.ui.features.home.viewmodel.HomeViewModel
import lmm.moneylog.ui.features.invoice.viewmodel.InvoiceListViewModel
import lmm.moneylog.ui.features.transaction.detail.viewmodel.TransactionDetailViewModel
import lmm.moneylog.ui.features.transaction.list.viewmodel.TransactionsListViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val appModule =
    module {
        factoryOf(::TransactionConverterImpl) { bind<TransactionConverter>() }
        factoryOf(::CreditCardPredictor)
        factoryOf(::NubankTransactionParser) { bind<TransactionParser>() }

        viewModelOf(::HomeViewModel)

        viewModelOf(::BalanceCardViewModel)
        viewModelOf(::CreditHomeCardViewModel)

        viewModelOf(::AccountsListViewModel)
        viewModelOf(::AccountDetailViewModel)

        viewModelOf(::CategoriesListViewModel)
        viewModelOf(::CategoryDetailViewModel)

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

        factory {
            androidContext().getSharedPreferences(
                "moneylog",
                Context.MODE_PRIVATE
            )
        }
    }
