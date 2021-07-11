package com.leom.moneylog.di

import com.leom.moneylog.data.Repository
import com.leom.moneylog.data.RepositoryImpl
import com.leom.moneylog.data.TransactionDatabase
import com.leom.moneylog.home.HomeViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    //TransactionDatabase
    single { TransactionDatabase.getInstance(get()) }

    //TransactionDao
    single {
        val db: TransactionDatabase = get()
        db.transactionDao()
    }

    //Repository
    single<Repository> { RepositoryImpl(get()) }

    //HomeViewModel
    viewModel { HomeViewModel(get()) }
}