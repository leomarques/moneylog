package lmm.moneylog

import lmm.moneylog.data.account.repositories.AddAccountRepositoryImpl
import lmm.moneylog.data.account.repositories.DeleteAccountRepositoryImpl
import lmm.moneylog.data.account.repositories.GetAccountRepositoryImpl
import lmm.moneylog.data.account.repositories.GetAccountsRepositoryImpl
import lmm.moneylog.data.account.repositories.UpdateAccountRepositoryImpl
import lmm.moneylog.data.balance.repositories.GetBalanceRepositoryImpl
import lmm.moneylog.data.category.repositories.AddCategoryRepositoryImpl
import lmm.moneylog.data.category.repositories.DeleteCategoryRepositoryImpl
import lmm.moneylog.data.category.repositories.GetCategoriesRepositoryImpl
import lmm.moneylog.data.category.repositories.GetCategoryRepositoryImpl
import lmm.moneylog.data.category.repositories.UpdateCategoryRepositoryImpl
import lmm.moneylog.data.coroutine.CoroutineDispatcherProvider
import lmm.moneylog.data.coroutine.CoroutineDispatcherProviderImpl
import lmm.moneylog.data.database.MoneylogDatabase
import lmm.moneylog.data.domaintime.LocalDateToDomainTimeConverterImpl
import lmm.moneylog.data.transaction.repositories.AddTransactionRepositoryImpl
import lmm.moneylog.data.transaction.repositories.DeleteTransactionRepositoryImpl
import lmm.moneylog.data.transaction.repositories.GetTransactionRepositoryImpl
import lmm.moneylog.data.transaction.repositories.GetTransactionsRepositoryImpl
import lmm.moneylog.data.transaction.repositories.UpdateTransactionRepositoryImpl
import lmm.moneylog.domain.account.addaccount.AddAccountInteractor
import lmm.moneylog.domain.account.addaccount.AddAccountRepository
import lmm.moneylog.domain.account.deleteaccount.DeleteAccountInteractor
import lmm.moneylog.domain.account.deleteaccount.DeleteAccountRepository
import lmm.moneylog.domain.account.editaccount.UpdateAccountInteractor
import lmm.moneylog.domain.account.editaccount.UpdateAccountRepository
import lmm.moneylog.domain.account.getaccount.GetAccountInteractor
import lmm.moneylog.domain.account.getaccount.GetAccountRepository
import lmm.moneylog.domain.account.getaccounts.GetAccountsInteractor
import lmm.moneylog.domain.account.getaccounts.GetAccountsRepository
import lmm.moneylog.domain.balance.getbalance.GetBalanceInteractor
import lmm.moneylog.domain.balance.getbalance.GetBalanceRepository
import lmm.moneylog.domain.category.addcategory.AddCategoryInteractor
import lmm.moneylog.domain.category.addcategory.AddCategoryRepository
import lmm.moneylog.domain.category.deletecategory.DeleteCategoryInteractor
import lmm.moneylog.domain.category.deletecategory.DeleteCategoryRepository
import lmm.moneylog.domain.category.editcategory.UpdateCategoryInteractor
import lmm.moneylog.domain.category.editcategory.UpdateCategoryRepository
import lmm.moneylog.domain.category.getcategories.GetCategoriesInteractor
import lmm.moneylog.domain.category.getcategories.GetCategoriesRepository
import lmm.moneylog.domain.category.getcategory.GetCategoryInteractor
import lmm.moneylog.domain.category.getcategory.GetCategoryRepository
import lmm.moneylog.domain.time.DomainTimeConverter
import lmm.moneylog.domain.transaction.addtransaction.AddTransactionInteractor
import lmm.moneylog.domain.transaction.addtransaction.AddTransactionRepository
import lmm.moneylog.domain.transaction.deletetransaction.DeleteTransactionInteractor
import lmm.moneylog.domain.transaction.deletetransaction.DeleteTransactionRepository
import lmm.moneylog.domain.transaction.edittransaction.UpdateTransactionInteractor
import lmm.moneylog.domain.transaction.edittransaction.UpdateTransactionRepository
import lmm.moneylog.domain.transaction.gettransaction.GetTransactionInteractor
import lmm.moneylog.domain.transaction.gettransaction.GetTransactionRepository
import lmm.moneylog.domain.transaction.gettransactions.GetTransactionsInteractor
import lmm.moneylog.domain.transaction.gettransactions.GetTransactionsRepository
import lmm.moneylog.ui.features.home.balancecard.BalanceCardViewModel
import lmm.moneylog.ui.features.transaction.gettransactions.GetTransactionsViewModel
import lmm.moneylog.ui.features.transaction.transactiondetail.TransactionDetailViewModel
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
