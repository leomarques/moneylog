package lmm.moneylog.di

import lmm.moneylog.addtransaction.AddTransactionViewModel
import lmm.moneylog.data.TransactionDatabase
import lmm.moneylog.data.TransactionRepository
import lmm.moneylog.data.TransactionRepositoryImpl
import lmm.moneylog.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    // TransactionDao
    single {
        TransactionDatabase.getInstance(get()).transactionDao()
    }

    // TransactionRepository
    single<TransactionRepository> { TransactionRepositoryImpl(get()) }

    viewModelOf(::HomeViewModel)
    viewModelOf(::AddTransactionViewModel)
}
