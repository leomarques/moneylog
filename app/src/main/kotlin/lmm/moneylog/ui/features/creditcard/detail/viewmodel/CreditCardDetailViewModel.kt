package lmm.moneylog.ui.features.creditcard.detail.viewmodel

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import lmm.moneylog.R
import lmm.moneylog.data.creditcard.model.CreditCard
import lmm.moneylog.data.creditcard.repositories.interfaces.AddCreditCardRepository
import lmm.moneylog.data.creditcard.repositories.interfaces.DeleteCreditCardRepository
import lmm.moneylog.data.creditcard.repositories.interfaces.GetCreditCardsRepository
import lmm.moneylog.data.creditcard.repositories.interfaces.UpdateCreditCardRepository
import lmm.moneylog.ui.extensions.getIdParam
import lmm.moneylog.ui.extensions.toComposeColor
import lmm.moneylog.ui.features.creditcard.detail.model.CreditCardDetailUIState

class CreditCardDetailViewModel(
    savedStateHandle: SavedStateHandle,
    getCreditCardsRepository: GetCreditCardsRepository,
    private val addCreditCardRepository: AddCreditCardRepository,
    private val updateCreditCardRepository: UpdateCreditCardRepository,
    private val deleteCreditCardRepository: DeleteCreditCardRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(CreditCardDetailUIState())
    val uiState: StateFlow<CreditCardDetailUIState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            savedStateHandle.getIdParam()?.let { id ->
                getCreditCardsRepository.getCreditCardById(id)?.let { creditCard ->
                    _uiState.update {
                        with(creditCard) {
                            CreditCardDetailUIState(
                                id = id,
                                name = name,
                                color = color.toComposeColor(),
                                closingDay = closingDay.toString(),
                                dueDay = dueDay.toString(),
                                limit = limit.toString(),
                                isEdit = true
                            )
                        }
                    }
                }
            }
        }
    }

    fun onNameChange(name: String) {
        _uiState.update { it.copy(name = name) }
    }

    fun onColorPick(color: Color) {
        _uiState.update { it.copy(color = color) }
    }

    fun onClosingDayChange(closingDay: String) {
        _uiState.update { it.copy(closingDay = closingDay) }
    }

    fun onDueDayDayChange(dueDay: String) {
        _uiState.update { it.copy(dueDay = dueDay) }
    }

    fun onLimitChange(limit: String) {
        _uiState.update { it.copy(limit = limit) }
    }

    fun deleteCreditCard() {
        viewModelScope.launch {
            deleteCreditCardRepository.delete(_uiState.value.id)
        }
    }

    fun onFabClick(
        onSuccess: () -> Unit,
        onError: (Int) -> Unit
    ) {
        val state = _uiState.value
        if (state.name.trim().isEmpty()) {
            onError(R.string.detail_no_name)
            return
        }
        if (state.closingDay.isEmpty()) {
            onError(R.string.detail_no_closingday)
            return
        }
        if (state.dueDay.isEmpty()) {
            onError(R.string.detail_no_dueday)
            return
        }
        if (state.limit.isEmpty()) {
            onError(R.string.detail_no_limit)
            return
        }

        viewModelScope.launch {
            try {
                if (state.isEdit) {
                    updateCreditCardRepository.update(state.toCreditCard())
                } else {
                    addCreditCardRepository.save(state.toCreditCard())
                }
                _uiState.update { it.copy(showFab = false) }
                onSuccess()
            } catch (_: NumberFormatException) {
                onError(R.string.detail_invalid_data)
            }
        }
    }
}

fun CreditCardDetailUIState.toCreditCard() =
    CreditCard(
        id = id,
        name = name.trim(),
        color = color.value.toLong(),
        closingDay = closingDay.toInt(),
        dueDay = dueDay.toInt(),
        limit = limit.toInt()
    )
