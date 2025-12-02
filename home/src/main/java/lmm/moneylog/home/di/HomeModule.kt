package lmm.moneylog.home.di

import lmm.moneylog.data.misc.dataModule
import lmm.moneylog.home.viewmodels.BalanceCardViewModel
import lmm.moneylog.home.viewmodels.CreditCardsViewModel
import lmm.moneylog.home.viewmodels.FinancialSummaryViewModel
import lmm.moneylog.home.viewmodels.HeaderViewModel
import lmm.moneylog.home.viewmodels.RecentExpensesViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

/**
 * Koin dependency injection module for the home feature module
 * Provides ViewModels for home screen components
 */
val homeModule =
    module {
        loadKoinModules(dataModule)

        viewModelOf(::HeaderViewModel)
        viewModelOf(::BalanceCardViewModel)
        viewModelOf(::FinancialSummaryViewModel)
        viewModelOf(::CreditCardsViewModel)
        viewModelOf(::RecentExpensesViewModel)
    }
