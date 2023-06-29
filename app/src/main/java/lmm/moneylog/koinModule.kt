package lmm.moneylog

import lmm.moneylog.data.CoroutineDispatcherProvider
import lmm.moneylog.data.CoroutineDispatcherProviderImpl
import lmm.moneylog.data.database.MoneylogDatabase
import lmm.moneylog.data.domaintime.LocalDateToDomainTimeConverterImpl
import lmm.moneylog.data.repositories.AddAccountRepositoryImpl
import lmm.moneylog.data.repositories.AddCategoryRepositoryImpl
import lmm.moneylog.data.repositories.AddTransactionRepositoryImpl
import lmm.moneylog.data.repositories.DeleteAccountRepositoryImpl
import lmm.moneylog.data.repositories.DeleteCategoryRepositoryImpl
import lmm.moneylog.data.repositories.DeleteTransactionRepositoryImpl
import lmm.moneylog.data.repositories.GetAccountRepositoryImpl
import lmm.moneylog.data.repositories.GetAccountsRepositoryImpl
import lmm.moneylog.data.repositories.GetBalanceRepositoryImpl
import lmm.moneylog.data.repositories.GetCategoriesRepositoryImpl
import lmm.moneylog.data.repositories.GetCategoryRepositoryImpl
import lmm.moneylog.data.repositories.GetTransactionRepositoryImpl
import lmm.moneylog.data.repositories.GetTransactionsRepositoryImpl
import lmm.moneylog.data.repositories.UpdateAccountRepositoryImpl
import lmm.moneylog.data.repositories.UpdateCategoryRepositoryImpl
import lmm.moneylog.data.repositories.UpdateTransactionRepositoryImpl
import lmm.moneylog.domain.addaccount.AddAccountInteractor
import lmm.moneylog.domain.addaccount.AddAccountRepository
import lmm.moneylog.domain.addcategory.AddCategoryInteractor
import lmm.moneylog.domain.addcategory.AddCategoryRepository
import lmm.moneylog.domain.addtransaction.AddTransactionInteractor
import lmm.moneylog.domain.addtransaction.AddTransactionRepository
import lmm.moneylog.domain.deleteaccount.DeleteAccountInteractor
import lmm.moneylog.domain.deleteaccount.DeleteAccountRepository
import lmm.moneylog.domain.deletecategory.DeleteCategoryInteractor
import lmm.moneylog.domain.deletecategory.DeleteCategoryRepository
import lmm.moneylog.domain.deletetransaction.DeleteTransactionInteractor
import lmm.moneylog.domain.deletetransaction.DeleteTransactionRepository
import lmm.moneylog.domain.editaccount.UpdateAccountInteractor
import lmm.moneylog.domain.editaccount.UpdateAccountRepository
import lmm.moneylog.domain.editcategory.UpdateCategoryInteractor
import lmm.moneylog.domain.editcategory.UpdateCategoryRepository
import lmm.moneylog.domain.edittransaction.UpdateTransactionInteractor
import lmm.moneylog.domain.edittransaction.UpdateTransactionRepository
import lmm.moneylog.domain.getaccount.GetAccountInteractor
import lmm.moneylog.domain.getaccount.GetAccountRepository
import lmm.moneylog.domain.getaccounts.GetAccountsInteractor
import lmm.moneylog.domain.getaccounts.GetAccountsRepository
import lmm.moneylog.domain.getbalance.GetBalanceInteractor
import lmm.moneylog.domain.getbalance.GetBalanceRepository
import lmm.moneylog.domain.getcategories.GetCategoriesInteractor
import lmm.moneylog.domain.getcategories.GetCategoriesRepository
import lmm.moneylog.domain.getcategory.GetCategoryInteractor
import lmm.moneylog.domain.getcategory.GetCategoryRepository
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
        MoneylogDatabase.getInstance(get()).transactionDao()
    }
    single {
        MoneylogDatabase.getInstance(get()).accountDao()
    }
    single {
        MoneylogDatabase.getInstance(get()).categoryDao()
    }

    factoryOf(::CoroutineDispatcherProviderImpl) { bind<CoroutineDispatcherProvider>() }
    factoryOf(::LocalDateToDomainTimeConverterImpl) { bind<DomainTimeConverter>() }

    factoryOf(::AddTransactionRepositoryImpl) { bind<AddTransactionRepository>() }
    factoryOf(::AddAccountRepositoryImpl) { bind<AddAccountRepository>() }
    factoryOf(::AddCategoryRepositoryImpl) { bind<AddCategoryRepository>() }
    factoryOf(::GetBalanceRepositoryImpl) { bind<GetBalanceRepository>() }
    factoryOf(::GetTransactionsRepositoryImpl) { bind<GetTransactionsRepository>() }
    factoryOf(::GetAccountsRepositoryImpl) { bind<GetAccountsRepository>() }
    factoryOf(::GetCategoriesRepositoryImpl) { bind<GetCategoriesRepository>() }
    factoryOf(::GetTransactionRepositoryImpl) { bind<GetTransactionRepository>() }
    factoryOf(::GetAccountRepositoryImpl) { bind<GetAccountRepository>() }
    factoryOf(::GetCategoryRepositoryImpl) { bind<GetCategoryRepository>() }
    factoryOf(::UpdateTransactionRepositoryImpl) { bind<UpdateTransactionRepository>() }
    factoryOf(::UpdateAccountRepositoryImpl) { bind<UpdateAccountRepository>() }
    factoryOf(::UpdateCategoryRepositoryImpl) { bind<UpdateCategoryRepository>() }
    factoryOf(::DeleteTransactionRepositoryImpl) { bind<DeleteTransactionRepository>() }
    factoryOf(::DeleteAccountRepositoryImpl) { bind<DeleteAccountRepository>() }
    factoryOf(::DeleteCategoryRepositoryImpl) { bind<DeleteCategoryRepository>() }

    factoryOf(::GetBalanceInteractor)
    factoryOf(::AddTransactionInteractor)
    factoryOf(::AddAccountInteractor)
    factoryOf(::AddCategoryInteractor)
    factoryOf(::GetTransactionsInteractor)
    factoryOf(::GetAccountsInteractor)
    factoryOf(::GetCategoriesInteractor)
    factoryOf(::UpdateTransactionInteractor)
    factoryOf(::UpdateAccountInteractor)
    factoryOf(::UpdateCategoryInteractor)
    factoryOf(::GetTransactionInteractor)
    factoryOf(::GetAccountInteractor)
    factoryOf(::GetCategoryInteractor)
    factoryOf(::DeleteTransactionInteractor)
    factoryOf(::DeleteAccountInteractor)
    factoryOf(::DeleteCategoryInteractor)

    viewModelOf(::BalanceCardViewModel)
    viewModelOf(::TransactionDetailViewModel)
    viewModelOf(::GetTransactionsViewModel)
}
