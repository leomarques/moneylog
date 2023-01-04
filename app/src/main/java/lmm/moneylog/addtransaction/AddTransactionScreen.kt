package lmm.moneylog.addtransaction

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
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTransactionScreen(
    onFabClick: (TransactionModel) -> Unit,
    onArrowBackClick: () -> Unit
) {
    val transactionModel = remember { TransactionModel() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Nova transação")
                },
                navigationIcon = {
                    IconButton(onClick = onArrowBackClick) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Localized description"
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            AddFab {
                onFabClick(transactionModel)
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        content = { paddingValues ->
            Surface(Modifier.padding(paddingValues)) {
                MyContent(transactionModel)
            }
        }
    )
}

@Composable
private fun MyContent(transactionModel: TransactionModel) {
    Column(Modifier.padding(horizontal = 16.dp)) {
        Field("Valor", KeyboardType.Number, transactionModel.value)
        Field("Data", KeyboardType.Text, transactionModel.date)
        Field("Descrição", KeyboardType.Text, transactionModel.description)
        Field("Categoria", KeyboardType.Text, transactionModel.category)
        Field("Conta", KeyboardType.Text, transactionModel.account)
    }
}

@Composable
private fun Field(
    title: String,
    keyboardType: KeyboardType,
    valueState: MutableState<String>
) {
    StateTextField(keyboardType, valueState, title)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StateTextField(
    keyboardType: KeyboardType,
    valueState: MutableState<String>,
    title: String
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
            .padding(bottom = 8.dp)
    )
}

@Composable
private fun AddFab(onBtnClick: () -> Unit) {
    FloatingActionButton(
        onClick = onBtnClick
    ) {
        Icon(
            Icons.Default.Check,
            contentDescription = null
        )
    }
}

@Preview
@Composable
fun Preview() {
    AddTransactionScreen(onFabClick = {}, onArrowBackClick = {})
}
