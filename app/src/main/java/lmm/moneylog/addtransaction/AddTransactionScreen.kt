package lmm.moneylog.addtransaction

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import lmm.moneylog.data.Repository
import org.koin.androidx.compose.get

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTransactionScreen(closeScreen: () -> Unit) {
    val repository: Repository = get()
    val viewModel = remember { AddTransactionViewModel(repository) }
    val transactionModel = remember { TransactionModel() }

    Scaffold(
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
            Fab {
                viewModel.onBtnClick(transactionModel)
                closeScreen()
            }
        },
        content = {
            Column {
                val modifier = Modifier.padding(16.dp)
                Field("Valor", KeyboardType.Number, transactionModel.value, modifier)
                Field("Data", KeyboardType.Text, transactionModel.date, modifier)
                Field("Descrição", KeyboardType.Text, transactionModel.description, modifier)
                Field("Categoria", KeyboardType.Text, transactionModel.category, modifier)
                Field("Conta", KeyboardType.Text, transactionModel.account, modifier)
            }
        }
    )
}

@Composable
private fun Field(
    title: String,
    keyboardType: KeyboardType,
    valueState: MutableState<String>,
    modifier: Modifier
) {
    Column(modifier) {
        Text(title)
        StateTextField(keyboardType, valueState)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StateTextField(keyboardType: KeyboardType, valueState: MutableState<String>) {
    val focusManager = LocalFocusManager.current

    TextField(
        value = valueState.value,
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = keyboardType, imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(
            onDone = { focusManager.clearFocus() }
        ),
        onValueChange = { value -> valueState.value = value },
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
private fun Fab(onBtnClick: () -> Unit) {
    FloatingActionButton(
        onClick = onBtnClick,
//        backgroundColor = Color.Green,
    ) {
        Icon(
            Icons.Default.Check,
            contentDescription = null
        )
    }
}