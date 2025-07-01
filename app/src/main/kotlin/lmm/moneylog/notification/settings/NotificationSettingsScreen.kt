package lmm.moneylog.notification.settings

import android.content.Intent
import android.os.Build
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import lmm.moneylog.notification.helper.NotificationPermissionHelper
import lmm.moneylog.ui.theme.MoneylogTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationSettingsScreen(
    onArrowBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
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

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text("Notification Settings") },
                windowInsets = WindowInsets(0.dp),
                navigationIcon = {
                    IconButton(onClick = onArrowBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
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
                    .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth()
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
                                text = "Nubank Notification Access",
                                style = MaterialTheme.typography.titleMedium
                            )
                            Text(
                                text = if (hasListenerPermission) "Enabled" else "Disabled",
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
                        text =
                            "Allow MoneyLog to intercept Nubank notifications " +
                                "to automatically track your transactions.",
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
                            Text("Grant Listener Permission")
                        }

                        Text(
                            text =
                                "Steps:\n1. Find 'MoneyLog' in the list" +
                                    "\n2. Toggle the switch to enable" +
                                    "\n3. Return to this screen",
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
                                text = "✓ Listener permission granted!",
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
                            Text("Manage Listener Permissions")
                        }
                    }
                }
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                Card(
                    modifier = Modifier.fillMaxWidth()
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
                                    text = "Notification Permission",
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Text(
                                    text = if (hasBasicPermission) "Enabled" else "Disabled",
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
                            text =
                                "Allow MoneyLog to show notifications. " +
                                    "This is required for the app to display notifications " +
                                    "about intercepted transactions.",
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
                                Text("Grant Notification Permission")
                            }
                        } else {
                            Surface(
                                color = MaterialTheme.colorScheme.primaryContainer,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = "✓ Notification permission granted!",
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
                                Text("Manage Notification Settings")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewNotificationSettingsScreen() {
    MoneylogTheme { NotificationSettingsScreen(onArrowBackClick = {}) }
}
