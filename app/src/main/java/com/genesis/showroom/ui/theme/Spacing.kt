package com.genesis.showroom.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Elegant spacing scale aligned with Genesis web layout rhythm.
 * Use generous section gaps; keep component internals tight.
 */
@Immutable
data class GenesisSpacing(
    val none: Dp = 0.dp,
    val hairline: Dp = 2.dp,
    val xxs: Dp = 4.dp,
    val xs: Dp = 8.dp,
    val sm: Dp = 12.dp,
    val md: Dp = 16.dp,
    val lg: Dp = 24.dp,
    val xl: Dp = 32.dp,
    val xxl: Dp = 48.dp,
    val xxxl: Dp = 64.dp,
    val section: Dp = 80.dp,
    val hero: Dp = 120.dp,

  // Component-specific
    val screenHorizontal: Dp = 24.dp,
    val screenVertical: Dp = 24.dp,
    val cardPadding: Dp = 20.dp,
    val cardPaddingCompact: Dp = 16.dp,
    val buttonHorizontal: Dp = 32.dp,
    val buttonVertical: Dp = 16.dp,
    val buttonHorizontalCompact: Dp = 24.dp,
    val buttonVerticalCompact: Dp = 12.dp,
    val iconTextGap: Dp = 12.dp,
    val listItemGap: Dp = 16.dp,
    val sectionGap: Dp = 40.dp,
    val borderWidth: Dp = 1.dp,
    val borderWidthSubtle: Dp = 0.5.dp,
)

val LocalGenesisSpacing = staticCompositionLocalOf { GenesisSpacing() }
