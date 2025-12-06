package lmm.moneylog.ui.features.account.detail.view.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import lmm.moneylog.R
import lmm.moneylog.ui.components.icons.AccountIcon
import lmm.moneylog.ui.components.icons.BrushIcon
import lmm.moneylog.ui.components.textfields.ColorTextField
import lmm.moneylog.ui.components.textfields.StateTextField
import lmm.moneylog.ui.theme.outcome

@Composable
fun AccountDetailFields(
    name: String,
    color: Color,
    isEdit: Boolean,
    onNameChange: (String) -> Unit,
    onColorDialogClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Account Information Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            colors =
                CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
        ) {
            Column(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
            ) {
                Text(
                    text = stringResource(R.string.account_section_info),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                StateTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = name,
                    title = stringResource(R.string.common_name),
                    keyboardType = KeyboardType.Text,
                    getFocus = !isEdit,
                    leadingIcon = { AccountIcon() },
                    onValueChange = onNameChange,
                    testTag = "AccountNameTextField"
                )

                Spacer(modifier = Modifier.height(12.dp))

                ColorTextField(
                    modifier = Modifier.fillMaxWidth(),
                    color = color,
                    leadingIcon = { BrushIcon() },
                    onClick = onColorDialogClick
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AccountDetailFieldsPreview() {
    AccountDetailFields(
        name = "Account",
        color = outcome,
        isEdit = false,
        onNameChange = {},
        onColorDialogClick = {}
    )
}
