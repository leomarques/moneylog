package lmm.moneylog.di

import lmm.moneylog.features.addtransaction.AddTransactionViewModel
import lmm.moneylog.data.repositories.AddTransactionRepositoryImpl
import lmm.moneylog.data.repositories.GetBalanceRepositoryImpl
import lmm.moneylog.data.TransactionDatabase
import lmm.moneylog.domain.addtransaction.AddTransactionInteractor
import lmm.moneylog.domain.addtransaction.AddTransactionRepository
import lmm.moneylog.domain.getbalance.GetBalanceInteractor
import lmm.moneylog.domain.getbalance.GetBalanceRepository
import lmm.moneylog.features.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val appModule = module {
    // TransactionDao
    single {
        TransactionDatabase.getInstance(get()).transactionDao()
    }

    factoryOf(::AddTransactionRepositoryImpl) { bind<AddTransactionRepository>() }
    factoryOf(::GetBalanceRepositoryImpl) { bind<GetBalanceRepository>() }

    factoryOf(::GetBalanceInteractor)
    factoryOf(::AddTransactionInteractor)

    viewModelOf(::HomeViewModel)
    viewModelOf(::AddTransactionViewModel)
}
