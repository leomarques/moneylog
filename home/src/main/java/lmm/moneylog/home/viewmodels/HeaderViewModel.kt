package lmm.moneylog.home.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import lmm.moneylog.data.time.repositories.DomainTimeRepository

/**
 * Data class representing period information
 *
 * @property monthName Name of the current month
 * @property year Current year
 */
data class PeriodInfo(
    val monthName: String,
    val year: Int
)

/**
 * ViewModel for the home screen header
 * Provides the current period information (month + year)
 *
 * @property domainTimeRepository Repository for time-related operations
 */
class HeaderViewModel(
    private val domainTimeRepository: DomainTimeRepository
) : ViewModel() {
    private val _periodInfo = MutableStateFlow<PeriodInfo?>(null)
    val periodInfo: StateFlow<PeriodInfo?> = _periodInfo.asStateFlow()

    init {
        loadPeriodInfo()
    }

    private fun loadPeriodInfo() {
        val currentDomainTime = domainTimeRepository.getCurrentDomainTime()
        _periodInfo.value =
            PeriodInfo(
                monthName = domainTimeRepository.getCurrentMonthName(),
                year = currentDomainTime.year
            )
    }
}
