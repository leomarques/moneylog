package lmm.moneylog.ui.features.transactiondetail

import android.annotation.SuppressLint
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
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
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import lmm.moneylog.R
import lmm.moneylog.domain.time.DomainTime
import lmm.moneylog.ui.components.MyFab
import lmm.moneylog.ui.theme.SpaceSize

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionDetailLayout(
    onArrowBackClick: () -> Unit,
    onFabClick: (TransactionDetailModel) -> Unit,
    transactionDetailModel: TransactionDetailModel,
    onDatePicked: (Long) -> Unit,
    onTypeOfValueSelected: (Boolean) -> Unit,
    onDeleteConfirmClick: (Int) -> Unit = {}
) {
    val showDeleteConfirmDialog = remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(transactionDetailModel.titleResourceId))
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
                    if (transactionDetailModel.isEdit) {
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
                { onFabClick(transactionDetailModel) },
                Icons.Default.Check
            )
        },
        floatingActionButtonPosition = FabPosition.Center,
        content = { paddingValues ->
            Surface(Modifier.padding(paddingValues)) {
                Content(
                    transactionDetailModel = transactionDetailModel,
                    onDatePicked = onDatePicked,
                    onTypeOfValueSelected = onTypeOfValueSelected,
                    onDeleteConfirm = {
                        onDeleteConfirmClick(transactionDetailModel.id)
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
    transactionDetailModel: TransactionDetailModel,
    onDatePicked: (Long) -> Unit,
    onTypeOfValueSelected: (Boolean) -> Unit,
    showDeleteConfirmDialog: Boolean,
    onDeleteConfirm: () -> Unit,
    onDeleteDismiss: () -> Unit
) {
    Column(Modifier.padding(horizontal = SpaceSize.DefaultSpaceSize)) {
        val showDatePicker = remember { mutableStateOf(false) }

        if (showDatePicker.value) {
            TransactionDetailDatePicker(
                onConfirm = {
                    onDatePicked(it)
                },
                onDismiss = {
                    showDatePicker.value = false
                }
            )
        }
        if (showDeleteConfirmDialog) {
            DeleteTransactionConfirmDialog(
                onConfirm = onDeleteConfirm,
                onDismiss = onDeleteDismiss
            )
        }

        StateTextField(
            title = stringResource(R.string.detailtransaction_value),
            keyboardType = KeyboardType.Number,
            valueState = transactionDetailModel.value,
            getFocus = !transactionDetailModel.isEdit
        )

        Row {
            Row(
                Modifier
                    .selectable(
                        selected = transactionDetailModel.isIncome.value,
                        onClick = { onTypeOfValueSelected(true) }
                    ),
                verticalAlignment = CenterVertically
            ) {
                RadioButton(
                    selected = transactionDetailModel.isIncome.value,
                    onClick = { onTypeOfValueSelected(true) }
                )
                Text(
                    text = stringResource(R.string.detailtransaction_income)
                )
            }

            Row(
                Modifier.selectable(
                    selected = !transactionDetailModel.isIncome.value,
                    onClick = { onTypeOfValueSelected(false) }
                ),
                verticalAlignment = CenterVertically
            ) {
                RadioButton(
                    selected = !transactionDetailModel.isIncome.value,
                    onClick = { onTypeOfValueSelected(false) }
                )
                Text(
                    text = stringResource(R.string.detailtransaction_outcome)
                )
            }
        }

        StateTextField(
            title = stringResource(R.string.detailtransaction_date),
            keyboardType = KeyboardType.Text,
            valueState = transactionDetailModel.displayDate,
            onClick = {
                showDatePicker.value = true
            }
        )
        StateTextField(
            title = stringResource(R.string.detailtransaction_description),
            keyboardType = KeyboardType.Text,
            valueState = transactionDetailModel.description,
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

@Composable
fun OnLifecycleEvent(onEvent: (owner: LifecycleOwner, event: Lifecycle.Event) -> Unit) {
    val eventHandler = rememberUpdatedState(onEvent)
    val lifecycleOwner = rememberUpdatedState(LocalLifecycleOwner.current)

    DisposableEffect(lifecycleOwner.value) {
        val lifecycle = lifecycleOwner.value.lifecycle
        val observer = LifecycleEventObserver { owner, event ->
            eventHandler.value(owner, event)
        }

        lifecycle.addObserver(observer)
        onDispose {
            lifecycle.removeObserver(observer)
        }
    }
}

@SuppressLint("UnrememberedMutableState")
@Preview
@Composable
fun Preview() {
    TransactionDetailLayout(
        onArrowBackClick = {},
        onFabClick = {},
        transactionDetailModel = TransactionDetailModel(
            value = mutableStateOf(""),
            isIncome = mutableStateOf(true),
            displayDate = mutableStateOf(""),
            description = mutableStateOf(""),
            date = DomainTime(0, 0, 0),
            isEdit = false,
            id = 0,
            titleResourceId = R.string.detailtransaction_topbar_title_add
        ),
        onDatePicked = {},
        onTypeOfValueSelected = {},
        onDeleteConfirmClick = {}
    )
}
