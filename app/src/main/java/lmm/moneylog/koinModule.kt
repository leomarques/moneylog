package lmm.moneylog

import lmm.moneylog.data.CoroutineDispatcherProvider
import lmm.moneylog.data.CoroutineDispatcherProviderImpl
import lmm.moneylog.data.database.TransactionDatabase
import lmm.moneylog.data.domaintime.LocalDateToDomainTimeConverterImpl
import lmm.moneylog.data.repositories.AddTransactionRepositoryImpl
import lmm.moneylog.data.repositories.DeleteTransactionRepositoryImpl
import lmm.moneylog.data.repositories.GetBalanceRepositoryImpl
import lmm.moneylog.data.repositories.GetTransactionRepositoryImpl
import lmm.moneylog.data.repositories.GetTransactionsRepositoryImpl
import lmm.moneylog.data.repositories.UpdateTransactionRepositoryImpl
import lmm.moneylog.domain.addtransaction.AddTransactionInteractor
import lmm.moneylog.domain.addtransaction.AddTransactionRepository
import lmm.moneylog.domain.deletetransaction.DeleteTransactionInteractor
import lmm.moneylog.domain.deletetransaction.DeleteTransactionRepository
import lmm.moneylog.domain.edittransaction.UpdateTransactionInteractor
import lmm.moneylog.domain.edittransaction.UpdateTransactionRepository
import lmm.moneylog.domain.getbalance.GetBalanceInteractor
import lmm.moneylog.domain.getbalance.GetBalanceRepository
import lmm.moneylog.domain.gettransaction.GetTransactionInteractor
import lmm.moneylog.domain.gettransaction.GetTransactionRepository
import lmm.moneylog.domain.gettransactions.GetTransactionsInteractor
import lmm.moneylog.domain.gettransactions.GetTransactionsRepository
import lmm.moneylog.domain.time.DomainTimeConverter
import lmm.moneylog.ui.features.gettransactions.GetTransactionsViewModel
import lmm.moneylog.ui.features.home.balancecard.BalanceCardViewModel
import lmm.moneylog.ui.features.transactiondetail.TransactionDetailViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val appModule = module {
    single {
        TransactionDatabase.getInstance(get()).transactionDao()
    }

    factoryOf(::CoroutineDispatcherProviderImpl) { bind<CoroutineDispatcherProvider>() }
    factoryOf(::LocalDateToDomainTimeConverterImpl) { bind<DomainTimeConverter>() }

    factoryOf(::AddTransactionRepositoryImpl) { bind<AddTransactionRepository>() }
    factoryOf(::GetBalanceRepositoryImpl) { bind<GetBalanceRepository>() }
    factoryOf(::GetTransactionsRepositoryImpl) { bind<GetTransactionsRepository>() }
    factoryOf(::GetTransactionRepositoryImpl) { bind<GetTransactionRepository>() }
    factoryOf(::UpdateTransactionRepositoryImpl) { bind<UpdateTransactionRepository>() }
    factoryOf(::DeleteTransactionRepositoryImpl) { bind<DeleteTransactionRepository>() }

    factoryOf(::GetBalanceInteractor)
    factoryOf(::AddTransactionInteractor)
    factoryOf(::GetTransactionsInteractor)
    factoryOf(::UpdateTransactionInteractor)
    factoryOf(::GetTransactionInteractor)
    factoryOf(::DeleteTransactionInteractor)

    viewModelOf(::BalanceCardViewModel)
    viewModelOf(::TransactionDetailViewModel)
    viewModelOf(::GetTransactionsViewModel)
}
