package lmm.moneylog.ui.features.settings.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import lmm.moneylog.data.demo.DemoModeHelper

class SettingsViewModel : ViewModel() {
    private val _isDemoMode = MutableStateFlow(DemoModeHelper.isDemoMode())
    val isDemoMode: StateFlow<Boolean> = _isDemoMode.asStateFlow()

    private val _showResetSuccess = MutableStateFlow(false)
    val showResetSuccess: StateFlow<Boolean> = _showResetSuccess.asStateFlow()

    fun toggleDemoMode() {
        viewModelScope.launch {
            DemoModeHelper.toggleDemoMode()
            _isDemoMode.value = DemoModeHelper.isDemoMode()
        }
    }

    fun resetDemoData() {
        viewModelScope.launch {
            DemoModeHelper.resetDemoData()
            _showResetSuccess.value = true
        }
    }

    fun clearResetSuccessMessage() {
        _showResetSuccess.value = false
    }
}
