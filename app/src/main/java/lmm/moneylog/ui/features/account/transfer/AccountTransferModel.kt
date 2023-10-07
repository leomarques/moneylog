package lmm.moneylog.ui.features.account.transfer

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import lmm.moneylog.data.transaction.time.DomainTime

data class AccountTransferModel(
    val accounts: List<Pair<Int, String>> = emptyList(),
    val date: DomainTime = DomainTime(0, 0, 0),
    val value: MutableState<String> = mutableStateOf(""),
    val originAccountId: Int = -1,
    val destinationAccountId: Int = -1,
    val originAccountDisplay: String = "",
    val destinationAccountDisplay: String = ""
)
