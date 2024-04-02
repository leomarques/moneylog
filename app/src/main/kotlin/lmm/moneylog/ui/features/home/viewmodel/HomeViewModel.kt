package lmm.moneylog.ui.features.home.viewmodel

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

const val hideValues = "hide_values"

class HomeViewModel(private val sharedPreferences: SharedPreferences) : ViewModel() {

    private val _uiState = MutableStateFlow(false)
    val uiState: StateFlow<Boolean> = _uiState.asStateFlow()

    init {
        val value = sharedPreferences.getBoolean(hideValues, false)
        _uiState.update { value }
    }

    fun onHideToggle() {
        val value = !_uiState.value
        sharedPreferences.edit().putBoolean(hideValues, value).apply()
        _uiState.update { value }
    }
}
