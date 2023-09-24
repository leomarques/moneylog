package lmm.moneylog

import lmm.moneylog.ui.features.home.balancecard.BalanceCardViewModel
import lmm.moneylog.ui.features.transaction.gettransactions.GetTransactionsViewModel
import lmm.moneylog.ui.features.transaction.transactiondetail.TransactionDetailViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {

    viewModelOf(::BalanceCardViewModel)
    viewModelOf(::TransactionDetailViewModel)

    viewModel { parameters ->
        GetTransactionsViewModel(
            typeOfValue = parameters.get(),
            getTransactionsRepository = get()
        )
    }
}
