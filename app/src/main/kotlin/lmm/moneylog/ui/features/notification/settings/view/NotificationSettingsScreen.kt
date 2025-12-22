package lmm.moneylog.ui.features.notification.settings.view

import android.content.Intent
import android.os.Build
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import lmm.moneylog.R
import lmm.moneylog.ui.components.icons.CreditCardIcon
import lmm.moneylog.ui.components.textfields.ClickTextField
import lmm.moneylog.ui.features.notification.NotificationPermissionHelper
import lmm.moneylog.ui.features.notification.settings.viewmodel.NotificationSettingsViewModel
import lmm.moneylog.ui.theme.AppTheme
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationSettingsScreen(
    onArrowBackClick: () -> Unit,
    onCategoryKeywordsClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: NotificationSettingsViewModel = koinViewModel()
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()
    var showCreditCardDialog by remember { mutableStateOf(false) }
    var showCategoryDialog by remember { mutableStateOf(false) }

    var hasListenerPermission by remember {
        mutableStateOf(NotificationPermissionHelper.hasNotificationListenerPermission(context))
    }
    var hasBasicPermission by remember {
        mutableStateOf(NotificationPermissionHelper.hasBasicNotificationPermission(context))
    }

    val requestPermissionLauncher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            hasBasicPermission = isGranted
        }

    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer =
            LifecycleEventObserver { _, event ->
                if (event == Lifecycle.Event.ON_RESUME) {
                    hasListenerPermission =
                        NotificationPermissionHelper.hasNotificationListenerPermission(context)
                    hasBasicPermission =
                        NotificationPermissionHelper.hasBasicNotificationPermission(context)
                }
            }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    LaunchedEffect(hasListenerPermission, hasBasicPermission) {
        viewModel.updatePermissions(hasListenerPermission, hasBasicPermission)
    }

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.notifications_title)) },
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
            NotificationListenerPermissionCard(
                hasListenerPermission = hasListenerPermission
            )

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                BasicNotificationPermissionCard(
                    hasBasicPermission = hasBasicPermission,
                    requestPermissionLauncher = requestPermissionLauncher
                )
            }

            DefaultCreditCardCard(
                selectedCreditCard = uiState.selectedCreditCard,
                onCardClick = { showCreditCardDialog = true },
                onClearSelection = { viewModel.selectCreditCard(null) }
            )

            DefaultCategoryCard(
                selectedCategory = uiState.selectedCategory,
                onCategoryClick = { showCategoryDialog = true },
                onClearSelection = { viewModel.selectCategory(null) }
            )

            CategoryKeywordsCard(
                onManageClick = onCategoryKeywordsClick
            )
        }
    }

    if (showCreditCardDialog) {
        CreditCardSelectorDialog(
            creditCards = uiState.creditCards,
            selectedCreditCard = uiState.selectedCreditCard,
            onDismiss = { showCreditCardDialog = false },
            onCreditCardSelect = { creditCard ->
                viewModel.selectCreditCard(creditCard)
                showCreditCardDialog = false
            }
        )
    }

    if (showCategoryDialog) {
        CategorySelectorDialog(
            categories = uiState.categories,
            selectedCategory = uiState.selectedCategory,
            onDismiss = { showCategoryDialog = false },
            onCategorySelect = { category ->
                viewModel.selectCategory(category)
                showCategoryDialog = false
            }
        )
    }
}

@Composable
private fun NotificationListenerPermissionCard(
    hasListenerPermission: Boolean,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Card(
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = null,
                    tint =
                        if (hasListenerPermission) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.error
                        }
                )

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = stringResource(R.string.notifications_nubank_access),
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text =
                            if (hasListenerPermission) {
                                stringResource(R.string.state_enabled)
                            } else {
                                stringResource(R.string.state_disabled)
                            },
                        style = MaterialTheme.typography.bodyMedium,
                        color =
                            if (hasListenerPermission) {
                                MaterialTheme.colorScheme.primary
                            } else {
                                MaterialTheme.colorScheme.error
                            }
                    )
                }
            }

            Text(
                text = stringResource(R.string.notifications_listener_description),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            if (!hasListenerPermission) {
                Button(
                    onClick = {
                        NotificationPermissionHelper.openNotificationListenerSettings(
                            context
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(stringResource(R.string.notifications_listener_grant))
                }

                Text(
                    text = stringResource(R.string.notifications_listener_steps),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.padding(top = 8.dp)
                )
            } else {
                Surface(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(R.string.notifications_listener_granted),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.padding(12.dp)
                    )
                }

                TextButton(
                    onClick = {
                        NotificationPermissionHelper.openNotificationListenerSettings(
                            context
                        )
                    }
                ) {
                    Text(stringResource(R.string.notifications_listener_manage))
                }
            }
        }
    }
}

@Composable
private fun BasicNotificationPermissionCard(
    hasBasicPermission: Boolean,
    requestPermissionLauncher: androidx.activity.result.ActivityResultLauncher<String>,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Card(
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Icon(
                    imageVector =
                        if (hasBasicPermission) {
                            Icons.Default.CheckCircle
                        } else {
                            Icons.Default.Warning
                        },
                    contentDescription = null,
                    tint =
                        if (hasBasicPermission) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.error
                        }
                )

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = stringResource(R.string.notifications_permission),
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text =
                            if (hasBasicPermission) {
                                stringResource(R.string.state_enabled)
                            } else {
                                stringResource(R.string.state_disabled)
                            },
                        style = MaterialTheme.typography.bodyMedium,
                        color =
                            if (hasBasicPermission) {
                                MaterialTheme.colorScheme.primary
                            } else {
                                MaterialTheme.colorScheme.error
                            }
                    )
                }
            }

            Text(
                text = stringResource(R.string.notifications_permission_description),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            if (!hasBasicPermission) {
                Button(
                    onClick = {
                        NotificationPermissionHelper.requestBasicNotificationPermission(
                            requestPermissionLauncher
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(stringResource(R.string.notifications_permission_grant))
                }
            } else {
                Surface(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(R.string.notifications_permission_granted),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.padding(12.dp)
                    )
                }
                TextButton(
                    onClick = {
                        val intent =
                            Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS)
                        intent.putExtra(
                            Settings.EXTRA_APP_PACKAGE,
                            context.packageName
                        )
                        context.startActivity(intent)
                    }
                ) {
                    Text(stringResource(R.string.notifications_settings_manage))
                }
            }
        }
    }
}

@Composable
private fun DefaultCreditCardCard(
    selectedCreditCard: lmm.moneylog.ui.features.notification.settings.model.CreditCardItem?,
    onCardClick: () -> Unit,
    onClearSelection: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = stringResource(R.string.creditcard_default),
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = stringResource(R.string.creditcard_default_description),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            ClickTextField(
                value = selectedCreditCard?.name ?: stringResource(R.string.state_no_card_selected),
                label = stringResource(R.string.creditcard_label),
                onClick = onCardClick,
                leadingIcon = {
                    CreditCardIcon(tint = selectedCreditCard?.color)
                }
            )

            if (selectedCreditCard != null) {
                TextButton(
                    onClick = onClearSelection
                ) {
                    Text(stringResource(R.string.action_clear_selection))
                }
            }
        }
    }
}

@Composable
private fun DefaultCategoryCard(
    selectedCategory: lmm.moneylog.ui.features.notification.settings.model.CategoryItem?,
    onCategoryClick: () -> Unit,
    onClearSelection: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = stringResource(R.string.category_default),
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = stringResource(R.string.category_default_description),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            ClickTextField(
                value =
                    selectedCategory?.name
                        ?: stringResource(R.string.state_no_category_selected),
                label = stringResource(R.string.common_category),
                onClick = onCategoryClick,
                leadingIcon = {
                    Box(
                        modifier =
                            Modifier
                                .size(24.dp)
                                .background(
                                    color =
                                        selectedCategory?.color
                                            ?: MaterialTheme.colorScheme.onSurfaceVariant,
                                    shape = CircleShape
                                )
                    )
                }
            )

            if (selectedCategory != null) {
                TextButton(
                    onClick = onClearSelection
                ) {
                    Text(stringResource(R.string.action_clear_selection))
                }
            }
        }
    }
}

@Composable
private fun CategoryKeywordsCard(
    onManageClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = stringResource(R.string.keywords_title),
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = stringResource(R.string.keywords_description),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Button(
                onClick = onManageClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.keywords_manage))
            }
        }
    }
}

@Preview
@Composable
private fun PreviewNotificationSettingsScreen() {
    AppTheme {
        NotificationSettingsScreen(
            onArrowBackClick = {},
            onCategoryKeywordsClick = {}
        )
    }
}
