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
import lmm.moneylog.data.categorypredictor.repositories.impls.CategoryKeywordRepositoryImpl
import lmm.moneylog.data.categorypredictor.repositories.interfaces.CategoryKeywordRepository
import lmm.moneylog.data.creditcard.interactors.GetCreditCardHomeInfoInteractor
import lmm.moneylog.data.creditcard.repositories.impls.AddCreditCardRepositoryImpl
import lmm.moneylog.data.creditcard.repositories.impls.DeleteCreditCardRepositoryImpl
import lmm.moneylog.data.creditcard.repositories.impls.GetCreditCardsRepositoryImpl
import lmm.moneylog.data.creditcard.repositories.impls.UpdateCreditCardRepositoryImpl
import lmm.moneylog.data.creditcard.repositories.interfaces.AddCreditCardRepository
import lmm.moneylog.data.creditcard.repositories.interfaces.DeleteCreditCardRepository
import lmm.moneylog.data.creditcard.repositories.interfaces.GetCreditCardsRepository
import lmm.moneylog.data.creditcard.repositories.interfaces.UpdateCreditCardRepository
import lmm.moneylog.data.creditcard.utils.InvoiceCalculator
import lmm.moneylog.data.graphs.interactors.GetMonthlyTotalsInteractor
import lmm.moneylog.data.graphs.interactors.GetNetWorthHistoryInteractor
import lmm.moneylog.data.graphs.interactors.GetTransactionsByCategoryInteractor
import lmm.moneylog.data.invoice.repositories.GetInvoicesRepository
import lmm.moneylog.data.invoice.repositories.GetInvoicesRepositoryImpl
import lmm.moneylog.data.notification.repositories.NotificationSettingsRepository
import lmm.moneylog.data.notification.repositories.NotificationSettingsRepositoryImpl
import lmm.moneylog.data.notification.repositories.NotificationTransactionRepository
import lmm.moneylog.data.notification.repositories.NotificationTransactionRepositoryImpl
import lmm.moneylog.data.time.repositories.DomainTimeRepository
import lmm.moneylog.data.time.repositories.DomainTimeRepositoryImpl
import lmm.moneylog.data.transaction.nubank.converter.NubankTransactionConverter
import lmm.moneylog.data.transaction.nubank.converter.NubankTransactionConverterImpl
import lmm.moneylog.data.transaction.nubank.parser.NubankTransactionParser
import lmm.moneylog.data.transaction.nubank.parser.NubankTransactionParserImpl
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
        single { MoneylogDatabase.getInstance(get()).categoryKeywordDao() }

        factoryOf(::DomainTimeRepositoryImpl) { bind<DomainTimeRepository>() }

        factoryOf(::InvoiceCalculator)
        factoryOf(::GetBalanceInteractor)
        factoryOf(::GetBalanceByAccountInteractor)
        factoryOf(::GetBalanceRepositoryImpl) { bind<GetBalanceRepository>() }

        factoryOf(::GetCreditCardHomeInfoInteractor)

        factoryOf(::GetTransactionsByCategoryInteractor)
        factoryOf(::GetMonthlyTotalsInteractor)
        factoryOf(::GetNetWorthHistoryInteractor)

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

        factoryOf(::CategoryKeywordRepositoryImpl) { bind<CategoryKeywordRepository>() }

        factoryOf(::AddTransactionRepositoryImpl) { bind<AddTransactionRepository>() }
        factoryOf(::GetTransactionsRepositoryImpl) { bind<GetTransactionsRepository>() }
        factoryOf(::UpdateTransactionRepositoryImpl) { bind<UpdateTransactionRepository>() }
        factoryOf(::DeleteTransactionRepositoryImpl) { bind<DeleteTransactionRepository>() }

        factoryOf(::AddCreditCardRepositoryImpl) { bind<AddCreditCardRepository>() }
        factoryOf(::GetCreditCardsRepositoryImpl) { bind<GetCreditCardsRepository>() }
        factoryOf(::UpdateCreditCardRepositoryImpl) { bind<UpdateCreditCardRepository>() }
        factoryOf(::DeleteCreditCardRepositoryImpl) { bind<DeleteCreditCardRepository>() }

        factoryOf(::NotificationSettingsRepositoryImpl) { bind<NotificationSettingsRepository>() }
        factoryOf(::NotificationTransactionRepositoryImpl) { bind<NotificationTransactionRepository>() }

        factoryOf(::NubankTransactionConverterImpl) { bind<NubankTransactionConverter>() }
        factoryOf(::NubankTransactionParserImpl) { bind<NubankTransactionParser>() }

        factoryOf(::GetInvoicesRepositoryImpl) { bind<GetInvoicesRepository>() }
    }
