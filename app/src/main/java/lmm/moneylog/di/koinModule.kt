package lmm.moneylog.di

import lmm.moneylog.addtransaction.AddTransactionViewModel
import lmm.moneylog.data.RepositoryImpl
import lmm.moneylog.data.TransactionDatabase
import lmm.moneylog.data.TransactionRepository
import lmm.moneylog.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    // TransactionDatabase
    single { TransactionDatabase.getInstance(get()) }

    // TransactionDao
    single {
        val db: TransactionDatabase = get()
        db.transactionDao()
    }

    // Repository
    single<TransactionRepository> { RepositoryImpl(get()) }

    viewModelOf(::HomeViewModel)
    viewModelOf(::AddTransactionViewModel)
}
