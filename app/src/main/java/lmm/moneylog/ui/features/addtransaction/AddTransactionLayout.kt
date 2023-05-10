package lmm.moneylog.ui.features.addtransaction

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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.R
import lmm.moneylog.ui.components.MyFab
import lmm.moneylog.ui.theme.SpaceSize

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTransactionLayout(
    onArrowBackClick: () -> Unit,
    onFabClick: (AddTransactionModel) -> Unit,
    addTransactionModel: AddTransactionModel,
    onDatePicked: (Long) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.addtransaction_topbar))
                },
                navigationIcon = {
                    IconButton(onClick = onArrowBackClick) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.addtransaction_arrowback_desc)
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            MyFab(
                { onFabClick(addTransactionModel) },
                Icons.Default.Check
            )
        },
        floatingActionButtonPosition = FabPosition.Center,
        content = { paddingValues ->
            Surface(Modifier.padding(paddingValues)) {
                Content(
                    addTransactionModel,
                    onDatePicked
                )
            }
        }
    )
}

@Composable
private fun Content(
    addTransactionModel: AddTransactionModel,
    onDatePicked: (Long) -> Unit
) {
    Column(Modifier.padding(horizontal = SpaceSize.DefaultSpaceSize)) {
        val showDatePicker = remember { mutableStateOf(false) }
        if (showDatePicker.value) {
            AddTransactionDatePicker(
                onConfirm = {
                    onDatePicked(it)
                },
                onDismiss = {
                    showDatePicker.value = false
                }
            )
        }

        Field(
            title = stringResource(R.string.addtransaction_value),
            keyboardType = KeyboardType.Number,
            valueState = addTransactionModel.value
        )
        Field(
            title = stringResource(R.string.addtransaction_date),
            keyboardType = KeyboardType.Text,
            valueState = addTransactionModel.displayDate,
        ) {
            showDatePicker.value = true
        }
        Field(
            title = stringResource(R.string.addtransaction_description),
            keyboardType = KeyboardType.Text,
            valueState = addTransactionModel.description
        )
    }
}

@Composable
private fun Field(
    title: String,
    keyboardType: KeyboardType,
    valueState: MutableState<String>,
    onClick: (() -> Unit)? = null
) {
    StateTextField(
        title = title,
        keyboardType = keyboardType,
        valueState = valueState,
        onClick = onClick ?: {},
        readOnly = onClick != null
    )
}

@Composable
fun StateTextField(
    title: String,
    keyboardType: KeyboardType,
    valueState: MutableState<String>,
    onClick: () -> Unit,
    readOnly: Boolean = false
) {
    val focusManager = LocalFocusManager.current

    OutlinedTextField(
        label = { Text(text = title) },
        value = valueState.value,
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = keyboardType,
            imeAction = ImeAction.Done
        ),
        readOnly = readOnly,
        keyboardActions = KeyboardActions(
            onDone = { focusManager.clearFocus() }
        ),
        interactionSource = remember { MutableInteractionSource() }
            .also { interactionSource ->
                LaunchedEffect(interactionSource) {
                    interactionSource.interactions.collect {
                        if (it is PressInteraction.Release) {
                            onClick()
                        }
                    }
                }
            },
        onValueChange = { value -> valueState.value = value },
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = SpaceSize.SmallSpaceSize)
    )
}

@Preview
@Composable
fun Preview() {
    AddTransactionLayout(
        onArrowBackClick = {},
        onFabClick = {},
        addTransactionModel = AddTransactionModel(),
        onDatePicked = {}
    )
}
