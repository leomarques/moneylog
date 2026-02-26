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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import lmm.moneylog.R
import lmm.moneylog.ui.features.settings.viewmodel.SettingsViewModel
import lmm.moneylog.ui.theme.AppTheme
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onArrowBackClick: () -> Unit,
    onAccountsClick: () -> Unit,
    onCategoriesClick: () -> Unit,
    onCreditCardsClick: () -> Unit,
    onTransfersListClick: () -> Unit,
    onNotificationSettingsClick: () -> Unit,
    onGraphsClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = koinViewModel()
) {
    val isDemoMode by viewModel.isDemoMode.collectAsState()
    val showResetSuccess by viewModel.showResetSuccess.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    val resetSuccessMessage = stringResource(R.string.demo_mode_reset_success)

    LaunchedEffect(showResetSuccess) {
        if (showResetSuccess) {
            snackbarHostState.showSnackbar(resetSuccessMessage)
            viewModel.clearResetSuccessMessage()
        }
    }

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.nav_settings)) },
                windowInsets = WindowInsets(0.dp),
                scrollBehavior = scrollBehavior,
                navigationIcon = {
                    IconButton(onClick = onArrowBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.action_back)
                        )
                    }
                }
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
                text = stringResource(R.string.settings_management),
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
            )

            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    SettingsItem(
                        title = stringResource(R.string.common_accounts),
                        description = stringResource(R.string.settings_manage_accounts),
                        icon = ImageVector.vectorResource(id = R.drawable.outline_account_balance_24),
                        onClick = onAccountsClick
                    )

                    HorizontalDivider()

                    SettingsItem(
                        title = stringResource(R.string.common_categories),
                        description = stringResource(R.string.settings_manage_categories),
                        icon = ImageVector.vectorResource(id = R.drawable.outline_category_24),
                        onClick = onCategoriesClick
                    )

                    HorizontalDivider()

                    SettingsItem(
                        title = stringResource(R.string.common_cards),
                        description = stringResource(R.string.settings_manage_creditcards),
                        icon = ImageVector.vectorResource(id = R.drawable.outline_credit_card_24),
                        onClick = onCreditCardsClick
                    )

                    HorizontalDivider()

                    SettingsItem(
                        title = stringResource(R.string.transfer_list_title),
                        description = stringResource(R.string.settings_view_transfers),
                        icon = ImageVector.vectorResource(id = R.drawable.outline_currency_exchange_24),
                        onClick = onTransfersListClick
                    )
                }
            }

            Text(
                text = stringResource(R.string.settings_features),
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
            )

            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    SettingsItem(
                        title = stringResource(R.string.graphs_title),
                        description = stringResource(R.string.settings_view_graphs),
                        icon = ImageVector.vectorResource(id = R.drawable.outline_bar_chart_24),
                        onClick = onGraphsClick
                    )

                    HorizontalDivider()

                    SettingsItem(
                        title = stringResource(R.string.settings_notifications),
                        description = stringResource(R.string.settings_configure_nubank),
                        icon = ImageVector.vectorResource(id = R.drawable.outline_receipt_long_24),
                        onClick = onNotificationSettingsClick
                    )
                }
            }

            Text(
                text = stringResource(R.string.settings_development),
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
            )

            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    SettingsSwitchItem(
                        title = stringResource(R.string.demo_mode_title),
                        description =
                            stringResource(
                                if (isDemoMode) {
                                    R.string.demo_mode_enabled
                                } else {
                                    R.string.demo_mode_disabled
                                }
                            ),
                        icon = ImageVector.vectorResource(id = R.drawable.outline_category_24),
                        checked = isDemoMode,
                        onCheckedChange = { viewModel.toggleDemoMode() }
                    )

                    if (isDemoMode) {
                        HorizontalDivider()

                        SettingsItem(
                            title = stringResource(R.string.demo_mode_reset),
                            description = stringResource(R.string.demo_mode_reset_description),
                            icon = ImageVector.vectorResource(id = R.drawable.outline_brush_24),
                            onClick = { viewModel.resetDemoData() }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun SettingsSwitchItem(
    title: String,
    description: String,
    icon: ImageVector,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    Row(
        modifier =
            modifier
                .fillMaxWidth()
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

        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            enabled = enabled
        )
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
            onArrowBackClick = {},
            onAccountsClick = {},
            onCategoriesClick = {},
            onCreditCardsClick = {},
            onTransfersListClick = {},
            onNotificationSettingsClick = {},
            onGraphsClick = {}
        )
    }
}
