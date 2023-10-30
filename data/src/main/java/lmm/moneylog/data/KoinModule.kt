package lmm.moneylog.data

import lmm.moneylog.data.account.repositories.AddAccountRepository
import lmm.moneylog.data.account.repositories.ArchiveAccountRepository
import lmm.moneylog.data.account.repositories.DeleteAccountRepository
import lmm.moneylog.data.account.repositories.GetAccountsRepository
import lmm.moneylog.data.account.repositories.UpdateAccountRepository
import lmm.moneylog.data.account.repositories.impls.AddAccountRepositoryImpl
import lmm.moneylog.data.account.repositories.impls.ArchiveAccountRepositoryImpl
import lmm.moneylog.data.account.repositories.impls.DeleteAccountRepositoryImpl
import lmm.moneylog.data.account.repositories.impls.GetAccountsRepositoryImpl
import lmm.moneylog.data.account.repositories.impls.UpdateAccountRepositoryImpl
import lmm.moneylog.data.accounttransfer.repositories.AccountTransferRepository
import lmm.moneylog.data.accounttransfer.repositories.AccountTransferRepositoryImpl
import lmm.moneylog.data.balance.GetBalanceByAccountInteractor
import lmm.moneylog.data.balance.GetBalanceInteractor
import lmm.moneylog.data.balance.GetBalanceRepository
import lmm.moneylog.data.balance.GetBalanceRepositoryImpl
import lmm.moneylog.data.category.repositories.AddCategoryRepository
import lmm.moneylog.data.category.repositories.DeleteCategoryRepository
import lmm.moneylog.data.category.repositories.GetCategoriesRepository
import lmm.moneylog.data.category.repositories.UpdateCategoryRepository
import lmm.moneylog.data.category.repositories.impls.AddCategoryRepositoryImpl
import lmm.moneylog.data.category.repositories.impls.DeleteCategoryRepositoryImpl
import lmm.moneylog.data.category.repositories.impls.GetCategoriesRepositoryImpl
import lmm.moneylog.data.category.repositories.impls.UpdateCategoryRepositoryImpl
import lmm.moneylog.data.coroutine.CoroutineDispatcherProvider
import lmm.moneylog.data.coroutine.CoroutineDispatcherProviderImpl
import lmm.moneylog.data.transaction.repositories.AddTransactionRepository
import lmm.moneylog.data.transaction.repositories.DeleteTransactionRepository
import lmm.moneylog.data.transaction.repositories.GetTransactionsRepository
import lmm.moneylog.data.transaction.repositories.UpdateTransactionRepository
import lmm.moneylog.data.transaction.repositories.impls.AddTransactionRepositoryImpl
import lmm.moneylog.data.transaction.repositories.impls.DeleteTransactionRepositoryImpl
import lmm.moneylog.data.transaction.repositories.impls.GetTransactionsRepositoryImpl
import lmm.moneylog.data.transaction.repositories.impls.UpdateTransactionRepositoryImpl
import lmm.moneylog.data.transaction.time.DomainTimeInteractor
import lmm.moneylog.data.transaction.time.LocalDateToDomainTimeInteractorImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val dataModule = module {

    single { MoneylogDatabase.getInstance(get()).transactionDao() }
    single { MoneylogDatabase.getInstance(get()).accountDao() }
    single { MoneylogDatabase.getInstance(get()).categoryDao() }
    single { MoneylogDatabase.getInstance(get()).accountTransferDao() }

    factoryOf(::CoroutineDispatcherProviderImpl) { bind<CoroutineDispatcherProvider>() }
    factoryOf(::LocalDateToDomainTimeInteractorImpl) { bind<DomainTimeInteractor>() }

    factoryOf(::GetBalanceInteractor)
    factoryOf(::GetBalanceByAccountInteractor)
    factoryOf(::GetBalanceRepositoryImpl) { bind<GetBalanceRepository>() }

    factoryOf(::AddAccountRepositoryImpl) { bind<AddAccountRepository>() }
    factoryOf(::GetAccountsRepositoryImpl) { bind<GetAccountsRepository>() }
    factoryOf(::UpdateAccountRepositoryImpl) { bind<UpdateAccountRepository>() }
    factoryOf(::DeleteAccountRepositoryImpl) { bind<DeleteAccountRepository>() }
    factoryOf(::ArchiveAccountRepositoryImpl) { bind<ArchiveAccountRepository>() }
    factoryOf(::AccountTransferRepositoryImpl) { bind<AccountTransferRepository>() }

    factoryOf(::AddCategoryRepositoryImpl) { bind<AddCategoryRepository>() }
    factoryOf(::GetCategoriesRepositoryImpl) { bind<GetCategoriesRepository>() }
    factoryOf(::UpdateCategoryRepositoryImpl) { bind<UpdateCategoryRepository>() }
    factoryOf(::DeleteCategoryRepositoryImpl) { bind<DeleteCategoryRepository>() }

    factoryOf(::AddTransactionRepositoryImpl) { bind<AddTransactionRepository>() }
    factoryOf(::GetTransactionsRepositoryImpl) { bind<GetTransactionsRepository>() }
    factoryOf(::UpdateTransactionRepositoryImpl) { bind<UpdateTransactionRepository>() }
    factoryOf(::DeleteTransactionRepositoryImpl) { bind<DeleteTransactionRepository>() }
}
