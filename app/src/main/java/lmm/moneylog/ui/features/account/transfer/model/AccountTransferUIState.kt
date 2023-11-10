package lmm.moneylog.ui.features.account.transfer.model

import androidx.compose.ui.graphics.Color
import lmm.moneylog.data.transaction.time.DomainTime
import lmm.moneylog.ui.theme.neutralColor

data class AccountTransferUIState(
    val value: String = "",
    val date: DomainTime = DomainTime(0, 0, 0),
    val accounts: List<AccountTransferModel> = emptyList(),
    val originAccountId: Int = -1,
    val destinationAccountId: Int = -1,
    val originAccountDisplay: String = "",
    val destinationAccountDisplay: String = "",
    val originAccountColor: Color = neutralColor,
    val destinationAccountColor: Color = neutralColor
)
