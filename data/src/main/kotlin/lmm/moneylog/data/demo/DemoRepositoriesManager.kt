package lmm.moneylog.data.demo

import lmm.moneylog.data.demo.repositories.InMemoryAccountRepository
import lmm.moneylog.data.demo.repositories.InMemoryAccountTransferRepository
import lmm.moneylog.data.demo.repositories.InMemoryBalanceRepository
import lmm.moneylog.data.demo.repositories.InMemoryCategoryKeywordRepository
import lmm.moneylog.data.demo.repositories.InMemoryCategoryRepository
import lmm.moneylog.data.demo.repositories.InMemoryCreditCardRepository
import lmm.moneylog.data.demo.repositories.InMemoryInvoiceRepository
import lmm.moneylog.data.demo.repositories.InMemoryNotificationSettingsRepository
import lmm.moneylog.data.demo.repositories.InMemoryNotificationTransactionRepository
import lmm.moneylog.data.demo.repositories.InMemoryTransactionRepository
import lmm.moneylog.data.time.repositories.DomainTimeRepository

class DemoRepositoriesManager(
    timeRepository: DomainTimeRepository
) {
    val accountRepository = InMemoryAccountRepository()
    val categoryRepository = InMemoryCategoryRepository()
    val creditCardRepository = InMemoryCreditCardRepository()
    val transactionRepository = InMemoryTransactionRepository()
    val accountTransferRepository = InMemoryAccountTransferRepository()
    val categoryKeywordRepository = InMemoryCategoryKeywordRepository()
    val balanceRepository = InMemoryBalanceRepository(transactionRepository)
    val invoiceRepository = InMemoryInvoiceRepository(timeRepository)
    val notificationSettingsRepository = InMemoryNotificationSettingsRepository()
    val notificationTransactionRepository = InMemoryNotificationTransactionRepository()

    fun initializeWithDemoData() {
        accountRepository.setInitialData(DemoDataProvider.getAccounts())
        categoryRepository.setInitialData(DemoDataProvider.getCategories())
        creditCardRepository.setInitialData(DemoDataProvider.getCreditCards())
        transactionRepository.setInitialData(DemoDataProvider.getTransactions())
        categoryKeywordRepository.setInitialData(DemoDataProvider.getCategoryKeywords())
    }

    fun clearAllData() {
        accountRepository.clear()
        categoryRepository.clear()
        creditCardRepository.clear()
        transactionRepository.clear()
        accountTransferRepository.clear()
        categoryKeywordRepository.clear()
        notificationSettingsRepository.clear()
        notificationTransactionRepository.clear()
    }
}
