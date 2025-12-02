package lmm.moneylog.ui.features.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import lmm.moneylog.R
import lmm.moneylog.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onAccountsClick: () -> Unit,
    onCategoriesClick: () -> Unit,
    onCreditCardsClick: () -> Unit,
    onNotificationSettingsClick: () -> Unit,
    onGraphsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.settings)) },
                windowInsets = WindowInsets(0.dp)
            )
        }
    ) { innerPadding ->
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Gerenciamento",
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
            )

            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    SettingsItem(
                        title = stringResource(R.string.accounts),
                        description = "Gerencie suas contas bancárias",
                        icon = ImageVector.vectorResource(id = R.drawable.outline_account_balance_24),
                        onClick = onAccountsClick
                    )

                    HorizontalDivider()

                    SettingsItem(
                        title = stringResource(R.string.categories),
                        description = "Gerencie suas categorias de gastos",
                        icon = ImageVector.vectorResource(id = R.drawable.outline_category_24),
                        onClick = onCategoriesClick
                    )

                    HorizontalDivider()

                    SettingsItem(
                        title = stringResource(R.string.cards),
                        description = "Gerencie seus cartões de crédito",
                        icon = ImageVector.vectorResource(id = R.drawable.outline_credit_card_24),
                        onClick = onCreditCardsClick
                    )
                }
            }

            Text(
                text = "Recursos",
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
            )

            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    SettingsItem(
                        title = "Gráficos",
                        description = "Visualize seus gastos (em breve)",
                        icon = ImageVector.vectorResource(id = R.drawable.outline_bar_chart_24),
                        onClick = onGraphsClick,
                        enabled = false
                    )

                    HorizontalDivider()

                    SettingsItem(
                        title = "Notificações",
                        description = "Configure captura de transações do Nubank",
                        icon = ImageVector.vectorResource(id = R.drawable.outline_receipt_long_24),
                        onClick = onNotificationSettingsClick
                    )
                }
            }
        }
    }
}

@Composable
private fun SettingsItem(
    title: String,
    description: String,
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .clickable(enabled = enabled) { onClick() }
                .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint =
                if (enabled) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.onSurfaceVariant
                }
        )

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color =
                    if (enabled) {
                        MaterialTheme.colorScheme.onSurface
                    } else {
                        MaterialTheme.colorScheme.onSurfaceVariant
                    }
            )
            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
            contentDescription = null,
            tint =
                if (enabled) {
                    MaterialTheme.colorScheme.onSurfaceVariant
                } else {
                    MaterialTheme.colorScheme.outlineVariant
                }
        )
    }
}

@Preview
@Composable
private fun PreviewSettingsScreen() {
    AppTheme {
        SettingsScreen(
            onAccountsClick = {},
            onCategoriesClick = {},
            onCreditCardsClick = {},
            onNotificationSettingsClick = {},
            onGraphsClick = {}
        )
    }
}
