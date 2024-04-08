package lmm.moneylog.ui.features.category.detail.view.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import lmm.moneylog.R
import lmm.moneylog.ui.components.icons.ArrowBackIcon
import lmm.moneylog.ui.components.misc.DeleteActionButton

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun CategoryDetailTopBar(
    isEdit: Boolean,
    onArrowBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteIconClick: () -> Unit
) {
    TopAppBar(
        modifier = modifier,
        title = {
            Text(
                text =
                    stringResource(
                        if (isEdit) {
                            R.string.detail_topbar_category_edit
                        } else {
                            R.string.detail_topbar_category_add
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
