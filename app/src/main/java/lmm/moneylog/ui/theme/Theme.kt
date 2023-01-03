package lmm.moneylog.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.ViewCompat

@Composable
fun MoneylogTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable() () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            (view.context as Activity).window.statusBarColor = colorScheme.primary.toArgb()
            ViewCompat.getWindowInsetsController(view)?.isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

private val LightColorScheme = lightColorScheme(
    primary = primaryLight,
    secondary = secondaryLight,
    tertiary = tertiaryLight,
    error = errorLight,
    background = backgroundLight,
    outline = outlineLight,
    onPrimary = onPrimaryLight,
    onSecondary = onSecondaryLight,
    onTertiary = onTertiaryLight,
    onError = onErrorLight,
    onBackground = onBackgroundLight,
    primaryContainer = primaryContainerLight,
    secondaryContainer = secondaryContainerLight,
    tertiaryContainer = tertiaryContainerLight,
    errorContainer = errorContainerLight,
    surface = surfaceLight,
    surfaceVariant = surfaceVariantLight
)

private val DarkColorScheme = darkColorScheme(
    primary = primaryDark,
    secondary = secondaryDark,
    tertiary = tertiaryDark,
    error = errorDark,
    background = backgroundDark,
    outline = outlineDark,
    onPrimary = onPrimaryDark,
    onSecondary = onSecondaryDark,
    onTertiary = onTertiaryDark,
    onError = onErrorDark,
    onBackground = onBackgroundDark,
    primaryContainer = primaryContainerDark,
    secondaryContainer = secondaryContainerDark,
    tertiaryContainer = tertiaryContainerDark,
    errorContainer = errorContainerDark,
    surface = surfaceDark,
    surfaceVariant = surfaceVariantDark,
    onPrimaryContainer = onPrimaryContainerDark,
    onSecondaryContainer = onSecondaryContainerDark,
    onTertiaryContainer = onTertiaryContainerDark,
    onErrorContainer = onErrorContainerDark,
    onSurface = onSurfaceDark,
    onSurfaceVariant = onSurfaceVariantDark
)
