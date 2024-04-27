package lmm.moneylog.data.misc

import lmm.moneylog.data.account.repositories.impls.AddAccountRepositoryImpl
import lmm.moneylog.data.account.repositories.impls.ArchiveAccountRepositoryImpl
import lmm.moneylog.data.account.repositories.impls.DeleteAccountRepositoryImpl
import lmm.moneylog.data.account.repositories.impls.GetAccountsRepositoryImpl
import lmm.moneylog.data.account.repositories.impls.UpdateAccountRepositoryImpl
import lmm.moneylog.data.account.repositories.interfaces.AddAccountRepository
import lmm.moneylog.data.account.repositories.interfaces.ArchiveAccountRepository
import lmm.moneylog.data.account.repositories.interfaces.DeleteAccountRepository
import lmm.moneylog.data.account.repositories.interfaces.GetAccountsRepository
import lmm.moneylog.data.account.repositories.interfaces.UpdateAccountRepository
import lmm.moneylog.data.accounttransfer.repositories.AccountTransferRepository
import lmm.moneylog.data.accounttransfer.repositories.AccountTransferRepositoryImpl
import lmm.moneylog.data.balance.interactors.GetBalanceByAccountInteractor
import lmm.moneylog.data.balance.interactors.GetBalanceInteractor
import lmm.moneylog.data.balance.repositories.GetBalanceRepository
import lmm.moneylog.data.balance.repositories.GetBalanceRepositoryImpl
import lmm.moneylog.data.category.repositories.impls.AddCategoryRepositoryImpl
import lmm.moneylog.data.category.repositories.impls.DeleteCategoryRepositoryImpl
import lmm.moneylog.data.category.repositories.impls.GetCategoriesRepositoryImpl
import lmm.moneylog.data.category.repositories.impls.UpdateCategoryRepositoryImpl
import lmm.moneylog.data.category.repositories.interfaces.AddCategoryRepository
import lmm.moneylog.data.category.repositories.interfaces.DeleteCategoryRepository
import lmm.moneylog.data.category.repositories.interfaces.GetCategoriesRepository
import lmm.moneylog.data.category.repositories.interfaces.UpdateCategoryRepository
import lmm.moneylog.data.time.repositories.DomainTimeRepository
import lmm.moneylog.data.time.repositories.DomainTimeRepositoryImpl
import lmm.moneylog.data.transaction.repositories.impls.AddTransactionRepositoryImpl
import lmm.moneylog.data.transaction.repositories.impls.DeleteTransactionRepositoryImpl
import lmm.moneylog.data.transaction.repositories.impls.GetTransactionsRepositoryImpl
import lmm.moneylog.data.transaction.repositories.impls.UpdateTransactionRepositoryImpl
import lmm.moneylog.data.transaction.repositories.interfaces.AddTransactionRepository
import lmm.moneylog.data.transaction.repositories.interfaces.DeleteTransactionRepository
import lmm.moneylog.data.transaction.repositories.interfaces.GetTransactionsRepository
import lmm.moneylog.data.transaction.repositories.interfaces.UpdateTransactionRepository
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val dataModule =
    module {
        single { MoneylogDatabase.getInstance(get()).transactionDao() }
        single { MoneylogDatabase.getInstance(get()).accountDao() }
        single { MoneylogDatabase.getInstance(get()).categoryDao() }
        single { MoneylogDatabase.getInstance(get()).accountTransferDao() }
        single { MoneylogDatabase.getInstance(get()).creditCardDao() }

        factoryOf(::DomainTimeRepositoryImpl) { bind<DomainTimeRepository>() }

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
