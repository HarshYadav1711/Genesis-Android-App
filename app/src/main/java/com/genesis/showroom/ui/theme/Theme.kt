package com.genesis.showroom.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val GenesisColorScheme = darkColorScheme(
    primary = GenesisCopper,
    onPrimary = GenesisBlack,
    primaryContainer = GenesisCopperDark,
    onPrimaryContainer = GenesisSilver,
    secondary = GenesisGold,
    onSecondary = GenesisBlack,
    secondaryContainer = GenesisGoldMuted,
    onSecondaryContainer = GenesisSilver,
    tertiary = GenesisGoldLight,
    onTertiary = GenesisBlack,
    background = GenesisBlack,
    onBackground = GenesisSilver,
    surface = GenesisCharcoal,
    onSurface = GenesisSilver,
    surfaceVariant = GenesisPanel,
    onSurfaceVariant = GenesisMuted,
    surfaceTint = GenesisCopper,
    outline = GenesisBorder,
    outlineVariant = GenesisBorderSubtle,
    scrim = GenesisScrim,
    inverseSurface = GenesisSilver,
    inverseOnSurface = GenesisBlack,
    inversePrimary = GenesisCopperDark,
    error = GenesisCopperDark,
    onError = GenesisSilver,
)

@Composable
fun GenesisTheme(content: @Composable () -> Unit) {
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = GenesisBlack.toArgb()
            window.navigationBarColor = GenesisBlack.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
            WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars = false
        }
    }

    CompositionLocalProvider(
        LocalGenesisExtendedColors provides GenesisExtendedColors(),
        LocalGenesisSpacing provides GenesisSpacing(),
        LocalGenesisShapes provides GenesisShapes(),
    ) {
        MaterialTheme(
            colorScheme = GenesisColorScheme,
            typography = GenesisTypography,
            shapes = GenesisMaterialShapes,
            content = content,
        )
    }
}

/** Access Genesis design tokens from composable scope. */
object GenesisTheme {
    val extendedColors: GenesisExtendedColors
        @Composable
        @ReadOnlyComposable
        get() = LocalGenesisExtendedColors.current

    val spacing: GenesisSpacing
        @Composable
        @ReadOnlyComposable
        get() = LocalGenesisSpacing.current

    val shapes: GenesisShapes
        @Composable
        @ReadOnlyComposable
        get() = LocalGenesisShapes.current
}
