package lmm.moneylog.features.addtransaction

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
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.R
import lmm.moneylog.ui.theme.SpaceSize

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTransactionLayout(
    onArrowBackClick: () -> Unit,
    onFabClick: (AddTransactionModel) -> Unit
) {
    val addTransactionModel = AddTransactionModel()

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
            AddFab {
                onFabClick(addTransactionModel)
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        content = { paddingValues ->
            Surface(Modifier.padding(paddingValues)) {
                Content(addTransactionModel)
            }
        }
    )
}

@Composable
private fun Content(addTransactionModel: AddTransactionModel) {
    Column(Modifier.padding(horizontal = SpaceSize.DefaultSpaceSize)) {
        Field(
            stringResource(R.string.addtransaction_value),
            KeyboardType.Number,
            addTransactionModel.value
        )
        Field(
            stringResource(R.string.addtransaction_date),
            KeyboardType.Text,
            addTransactionModel.date
        )
        Field(
            stringResource(R.string.addtransaction_description),
            KeyboardType.Text,
            addTransactionModel.description
        )
        Field(
            stringResource(R.string.addtransaction_category),
            KeyboardType.Text,
            addTransactionModel.category
        )
        Field(
            stringResource(R.string.addtransaction_account),
            KeyboardType.Text,
            addTransactionModel.account
        )
    }
}

@Composable
private fun Field(
    title: String,
    keyboardType: KeyboardType,
    valueState: MutableState<String>
) {
    StateTextField(
        title,
        keyboardType,
        valueState
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StateTextField(
    title: String,
    keyboardType: KeyboardType,
    valueState: MutableState<String>
) {
    val focusManager = LocalFocusManager.current

    OutlinedTextField(
        label = { Text(text = title) },
        value = valueState.value,
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = keyboardType,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = { focusManager.clearFocus() }
        ),
        onValueChange = { value -> valueState.value = value },
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = SpaceSize.SmallSpaceSize)
    )
}

@Composable
private fun AddFab(onClick: () -> Unit) {
    FloatingActionButton(onClick = onClick) {
        Icon(
            Icons.Default.Check,
            contentDescription = stringResource(R.string.addtransaction_fab_desc)
        )
    }
}

@Preview
@Composable
fun Preview() {
    AddTransactionLayout(onFabClick = {}, onArrowBackClick = {})
}
