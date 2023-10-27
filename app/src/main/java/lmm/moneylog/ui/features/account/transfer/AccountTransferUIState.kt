package lmm.moneylog.ui.features.account.transfer

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import lmm.moneylog.data.transaction.time.DomainTime

data class AccountTransferUIState(
    val accounts: List<AccountTransferModel> = emptyList(),
    val date: DomainTime = DomainTime(0, 0, 0),
    val value: MutableState<String> = mutableStateOf(""),
    val originAccountId: Int = -1,
    val destinationAccountId: Int = -1,
    val originAccountDisplay: String = "",
    val destinationAccountDisplay: String = "",
    val originAccountColor: Color = Color.Gray,
    val destinationAccountColor: Color = Color.Gray
)
