package lmm.moneylog.ui.features.category.detail.view.layout

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.ui.components.fabs.MyFab
import lmm.moneylog.ui.features.category.detail.view.components.CategoryDetailTopBar
import lmm.moneylog.ui.theme.neutralColor

@Composable
fun CategoryDetailLayout(
    name: String,
    color: Color,
    isEdit: Boolean,
    isIncome: Boolean,
    showFab: Boolean,
    onArrowBackClick: () -> Unit,
    onColorPicked: (Color) -> Unit,
    onNameChange: (String) -> Unit,
    onIncomeChange: (Boolean) -> Unit,
    onDeleteConfirmClick: () -> Unit = {},
    onFabClick: () -> Unit
) {
    val showDeleteConfirmDialog = remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            CategoryDetailTopBar(
                isEdit = isEdit,
                onArrowBackClick = onArrowBackClick,
                onDeleteIconClick = { showDeleteConfirmDialog.value = true }
            )
        },
        floatingActionButton = {
            if (showFab) {
                MyFab(
                    onClick = onFabClick,
                    icon = Icons.Default.Check
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End,
        content = { paddingValues ->
            Surface(Modifier.padding(paddingValues)) {
                CategoryDetailContent(
                    name = name,
                    color = color,
                    isEdit = isEdit,
                    isIncome = isIncome,
                    showDeleteConfirmDialog = showDeleteConfirmDialog.value,
                    onColorPicked = onColorPicked,
                    onNameChange = onNameChange,
                    onIncomeChange = onIncomeChange,
                    onDeleteDismiss = { showDeleteConfirmDialog.value = false },
                    onDeleteConfirm = {
                        showDeleteConfirmDialog.value = false
                        onDeleteConfirmClick()
                    }
                )
            }
        }
    )
}

@Preview
@Composable
fun CategoryDetailLayoutPreview() {
    CategoryDetailLayout(
        name = "",
        color = neutralColor,
        isEdit = false,
        isIncome = true,
        showFab = true,
        onArrowBackClick = {},
        onColorPicked = {},
        onDeleteConfirmClick = {},
        onFabClick = {},
        onNameChange = {},
        onIncomeChange = {}
    )
}
