package lmm.moneylog.ui.features.creditcard.detail.view.components

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import lmm.moneylog.R
import lmm.moneylog.ui.components.icons.ArrowBackIcon
import lmm.moneylog.ui.components.misc.DeleteActionButton

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun CreditCardDetailTopBar(
    isEdit: Boolean,
    onArrowBackClick: () -> Unit,
    onDeleteIconClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        modifier = modifier,
        windowInsets = WindowInsets(0.dp),
        title = {
            Text(
                text =
                    stringResource(
                        if (isEdit) {
                            R.string.detail_topbar_creditcard_edit
                        } else {
                            R.string.detail_topbar_creditcard_add
                        }
                    )
            )
        },
        navigationIcon = { ArrowBackIcon(onClick = onArrowBackClick) },
        actions = {
            if (isEdit) {
                DeleteActionButton(onClick = onDeleteIconClick)
            }
        }
    )
}
