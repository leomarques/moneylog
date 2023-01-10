package lmm.moneylog.di

import lmm.moneylog.addtransaction.AddTransactionViewModel
import lmm.moneylog.data.GetBalanceRepositoryImpl
import lmm.moneylog.data.TransactionDatabase
import lmm.moneylog.data.TransactionRepository
import lmm.moneylog.data.TransactionRepositoryImpl
import lmm.moneylog.domain.GetBalanceInteractor
import lmm.moneylog.domain.GetBalanceRepository
import lmm.moneylog.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val appModule = module {
    // TransactionDao
    single {
        TransactionDatabase.getInstance(get()).transactionDao()
    }

    factoryOf(::TransactionRepositoryImpl) { bind<TransactionRepository>() }

    factoryOf(::GetBalanceRepositoryImpl) { bind<GetBalanceRepository>() }
    factoryOf(::GetBalanceInteractor)

    viewModelOf(::HomeViewModel)
    viewModelOf(::AddTransactionViewModel)
}
