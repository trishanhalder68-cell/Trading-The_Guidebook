package com.example.ui.theme

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat


// Clean, minimalist, human-friendly light color scheme
private val FriendlyColorScheme = lightColorScheme(
    primary = TrustBlue,
    onPrimary = CreamSurface,
    primaryContainer = TrustBlueContainer,
    onPrimaryContainer = TrustBlue,
    secondary = SageGreen,
    onSecondary = CreamSurface,
    secondaryContainer = SageGreenContainer,
    onSecondaryContainer = SageGreenText,
    tertiary = BrushedGold,
    onTertiary = ObsidianBlack,
    tertiaryContainer = CreamContainer,
    onTertiaryContainer = SlateCharcoal,
    background = CreamBackground,
    onBackground = SlateCharcoal,
    surface = CreamSurface,
    onSurface = SlateCharcoal,
    surfaceVariant = CreamContainer,
    onSurfaceVariant = SlateMuted,
    outline = CreamBorder,
    outlineVariant = SlateBorder,
    error = CrimsonLoss,
    onError = CreamSurface,
    errorContainer = CrimsonContainer,
    onErrorContainer = CrimsonLossDark
)

private val CitadelDarkColorScheme = darkColorScheme(
    primary = BrushedGold,
    onPrimary = ObsidianBlack,
    primaryContainer = GoldDark,
    onPrimaryContainer = GoldGleam,
    secondary = SageGreenAccent,
    onSecondary = ObsidianBlack,
    secondaryContainer = CitadelCardElevated,
    onSecondaryContainer = SageGreenContainer,
    tertiary = TrustBlueHover,
    onTertiary = CreamSurface,
    background = ObsidianBlack,
    onBackground = CreamSurface,
    surface = CitadelSurface,
    onSurface = CreamSurface,
    surfaceVariant = CitadelCard,
    onSurfaceVariant = SlateSubtle,
    outline = BrushedGold,
    outlineVariant = SlateDark
)

private tailrec fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}


@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = false, // Default to the warm off-white & cream design requested
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) CitadelDarkColorScheme else FriendlyColorScheme
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val activity = view.context.findActivity()
            activity?.window?.let { window ->
                window.statusBarColor = colorScheme.background.toArgb()
                window.navigationBarColor = colorScheme.background.toArgb()
                WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
                WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars = !darkTheme
            }
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

