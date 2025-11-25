package lmm.moneylog.ui.features.notification

import android.Manifest
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import androidx.activity.result.ActivityResultLauncher
import androidx.core.app.NotificationManagerCompat
import lmm.moneylog.services.nubank.NubankNotificationListener

object NotificationPermissionHelper {
    fun hasNotificationListenerPermission(context: Context): Boolean {
        val packageName = context.packageName
        val enabledListeners =
            Settings.Secure.getString(
                context.contentResolver,
                "enabled_notification_listeners"
            )
        return enabledListeners?.contains(packageName) == true
    }

    fun openNotificationListenerSettings(context: Context) {
        val intent = Intent(Settings.ACTION_NOTIFICATION_LISTENER_DETAIL_SETTINGS)
        intent.putExtra(
            Settings.EXTRA_NOTIFICATION_LISTENER_COMPONENT_NAME,
            ComponentName(context, NubankNotificationListener::class.java).flattenToString()
        )
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
    }

    fun hasBasicNotificationPermission(context: Context): Boolean = NotificationManagerCompat.from(context).areNotificationsEnabled()

    fun requestBasicNotificationPermission(launcher: ActivityResultLauncher<String>) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            launcher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }
}
