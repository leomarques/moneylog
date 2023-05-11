package lmm.moneylog

import lmm.moneylog.data.database.TransactionDatabase
import lmm.moneylog.data.domaintime.LocalDateToDomainTimeConverterImpl
import lmm.moneylog.data.repositories.AddTransactionRepositoryImpl
import lmm.moneylog.data.repositories.GetBalanceRepositoryImpl
import lmm.moneylog.data.repositories.GetTransactionsRepositoryImpl
import lmm.moneylog.domain.addtransaction.AddTransactionInteractor
import lmm.moneylog.domain.addtransaction.AddTransactionRepository
import lmm.moneylog.domain.getbalance.GetBalanceInteractor
import lmm.moneylog.domain.getbalance.GetBalanceRepository
import lmm.moneylog.domain.gettransactions.GetTransactionsInteractor
import lmm.moneylog.domain.gettransactions.GetTransactionsRepository
import lmm.moneylog.domain.time.DomainTimeConverter
import lmm.moneylog.ui.features.addtransaction.AddTransactionViewModel
import lmm.moneylog.ui.features.gettransactions.GetTransactionsViewModel
import lmm.moneylog.ui.features.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val appModule = module {
    single {
        TransactionDatabase.getInstance(get()).transactionDao()
    }

    factoryOf(::AddTransactionRepositoryImpl) { bind<AddTransactionRepository>() }
    factoryOf(::GetBalanceRepositoryImpl) { bind<GetBalanceRepository>() }
    factoryOf(::LocalDateToDomainTimeConverterImpl) { bind<DomainTimeConverter>() }
    factoryOf(::GetTransactionsRepositoryImpl) { bind<GetTransactionsRepository>() }

    factoryOf(::GetBalanceInteractor)
    factoryOf(::AddTransactionInteractor)
    factoryOf(::GetTransactionsInteractor)

    viewModelOf(::HomeViewModel)
    viewModelOf(::AddTransactionViewModel)
    viewModelOf(::GetTransactionsViewModel)
}
