package lmm.moneylog.ui.features.account.accountdetail

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf

data class AccountDetailModel(
    val name: MutableState<String> = mutableStateOf(""),
    val color: MutableState<Long> = mutableLongStateOf(0),
    val isEdit: Boolean = false,
    val id: Int = -1
)
