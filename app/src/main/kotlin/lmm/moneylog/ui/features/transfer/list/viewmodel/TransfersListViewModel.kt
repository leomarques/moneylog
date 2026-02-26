package lmm.moneylog.ui.features.transfer.list.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import lmm.moneylog.R
import lmm.moneylog.data.account.repositories.interfaces.GetAccountsRepository
import lmm.moneylog.data.accounttransfer.repositories.AccountTransferRepository
import lmm.moneylog.data.time.repositories.DomainTimeRepository
import lmm.moneylog.ui.features.transfer.list.model.TransfersListUIState

private const val MONTH_JANUARY = 1
private const val MONTH_DECEMBER = 12

class TransfersListViewModel(
    private val accountTransferRepository: AccountTransferRepository,
    private val getAccountsRepository: GetAccountsRepository,
    private val timeRepository: DomainTimeRepository,
) : ViewModel() {
    private val _uiState =
        MutableStateFlow(
            TransfersListUIState(
                titleResourceId = R.string.transfer_list_title
            )
        )
    val uiState: StateFlow<TransfersListUIState> = _uiState.asStateFlow()

    private var month = timeRepository.getCurrentDomainTime().month
    private var year = timeRepository.getCurrentDomainTime().year

    init {
        refreshTransfers()
    }

    private fun refreshTransfers() {
        viewModelScope.launch {
            val accounts = getAccountsRepository.getAccountsSuspend()
            val accountsMap = accounts.associate { it.id to it.name }
            val monthName = timeRepository.getMonthName(month)

            collectTransfers(accountsMap, monthName)
        }
    }

    private suspend fun collectTransfers(
        accountsMap: Map<Int, String>,
        monthName: String
    ) {
        accountTransferRepository
            .getTransfersByMonthYear(month = month, year = year)
            .collect { transfers ->
                _uiState.update {
                    transfers
                        .toTransfersListUiState(
                            accountMap = accountsMap,
                            titleResourceId = R.string.transfer_list_title
                        ).copy(
                            monthName = monthName,
                            total = transfers.sumOf { it.value }
                        )
                }
            }
    }

    fun onPreviousMonthClick() {
        if (month == MONTH_JANUARY) {
            month = MONTH_DECEMBER
            year--
        } else {
            month--
        }

        refreshTransfers()
    }

    fun onNextMonthClick() {
        if (month == MONTH_DECEMBER) {
            month = MONTH_JANUARY
            year++
        } else {
            month++
        }

        refreshTransfers()
    }
}
