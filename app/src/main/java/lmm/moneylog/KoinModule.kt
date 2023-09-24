package lmm.moneylog

import lmm.moneylog.ui.features.account.getaccounts.GetAccountsViewModel
import lmm.moneylog.ui.features.category.getccategories.GetCategoriesViewModel
import lmm.moneylog.ui.features.home.balancecard.BalanceCardViewModel
import lmm.moneylog.ui.features.transaction.gettransactions.GetTransactionsViewModel
import lmm.moneylog.ui.features.transaction.transactiondetail.TransactionDetailViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {

    viewModelOf(::BalanceCardViewModel)
    viewModelOf(::TransactionDetailViewModel)
    viewModelOf(::GetAccountsViewModel)
    viewModelOf(::GetCategoriesViewModel)

    viewModel { parameters ->
        GetTransactionsViewModel(
            typeOfValue = parameters.get(),
            getTransactionsRepository = get()
        )
    }
}
