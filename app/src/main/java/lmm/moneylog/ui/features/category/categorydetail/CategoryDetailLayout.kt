package lmm.moneylog.ui.features.category.categorydetail

import android.annotation.SuppressLint
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Lifecycle
import lmm.moneylog.R
import lmm.moneylog.ui.components.MyFab
import lmm.moneylog.ui.components.OnLifecycleEvent
import lmm.moneylog.ui.features.transaction.transactiondetail.DeleteTransactionConfirmDialog
import lmm.moneylog.ui.theme.SpaceSize

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryDetailLayout(
    onArrowBackClick: () -> Unit,
    onFabClick: () -> Unit,
    model: CategoryDetailModel,
    onDeleteConfirmClick: (Int) -> Unit = {}
) {
    val showDeleteConfirmDialog = remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.topbar_title_category))
                },
                navigationIcon = {
                    IconButton(onClick = onArrowBackClick) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.detailtransaction_arrowback_desc)
                        )
                    }
                },
                actions = {
                    if (model.isEdit) {
                        IconButton(
                            onClick = { showDeleteConfirmDialog.value = true },
                            content = {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = stringResource(R.string.detailtransaction_delete_desc)
                                )
                            }
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            MyFab(
                onClick = onFabClick,
                icon = Icons.Default.Check
            )
        },
        floatingActionButtonPosition = FabPosition.Center,
        content = { paddingValues ->
            Surface(Modifier.padding(paddingValues)) {
                Content(
                    model = model,
                    onDeleteConfirm = {
                        onDeleteConfirmClick(model.id)
                    },
                    showDeleteConfirmDialog = showDeleteConfirmDialog.value,
                    onDeleteDismiss = {
                        showDeleteConfirmDialog.value = false
                    }
                )
            }
        }
    )
}

@Composable
private fun Content(
    model: CategoryDetailModel,
    showDeleteConfirmDialog: Boolean,
    onDeleteConfirm: () -> Unit,
    onDeleteDismiss: () -> Unit
) {
    Column(Modifier.padding(horizontal = SpaceSize.DefaultSpaceSize)) {
        if (showDeleteConfirmDialog) {
            DeleteTransactionConfirmDialog(
                onConfirm = onDeleteConfirm,
                onDismiss = onDeleteDismiss
            )
        }

        StateTextField(
            title = stringResource(R.string.name),
            keyboardType = KeyboardType.Text,
            valueState = model.name,
            getFocus = !model.isEdit
        )
    }
}

@Composable
fun StateTextField(
    title: String,
    keyboardType: KeyboardType,
    valueState: MutableState<String>,
    onClick: (() -> Unit)? = null,
    getFocus: Boolean = false
) {
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }

    OutlinedTextField(
        value = valueState.value,
        label = { Text(text = title) },
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = keyboardType,
            imeAction = ImeAction.Done
        ),
        readOnly = onClick != null,
        keyboardActions = KeyboardActions(
            onDone = { focusManager.clearFocus() }
        ),
        interactionSource = remember { MutableInteractionSource() }
            .also { interactionSource ->
                LaunchedEffect(interactionSource) {
                    interactionSource.interactions.collect {
                        if (it is PressInteraction.Release) {
                            onClick?.invoke()
                        }
                    }
                }
            },
        onValueChange = { value -> valueState.value = value },
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = SpaceSize.SmallSpaceSize)
            .focusRequester(focusRequester)
    )

    if (getFocus) {
        OnLifecycleEvent { _, event ->
            when (event) {
                Lifecycle.Event.ON_RESUME -> {
                    focusRequester.requestFocus()
                }

                else -> {}
            }
        }
    }
}

@SuppressLint("UnrememberedMutableState")
@Preview
@Composable
fun CategoryDetailLayoutPreview() {
    CategoryDetailLayout(
        onArrowBackClick = {},
        onFabClick = {},
        model = CategoryDetailModel(
            name = mutableStateOf(""),
            isEdit = false,
            id = 0
        ),
        onDeleteConfirmClick = {}
    )
}
