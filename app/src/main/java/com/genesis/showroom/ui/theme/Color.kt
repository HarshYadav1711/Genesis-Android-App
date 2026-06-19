package com.genesis.showroom.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

// ── Brand palette ────────────────────────────────────────────────────────────

val GenesisBlack = Color(0xFF0A0A0A)
val GenesisBlackElevated = Color(0xFF111111)
val GenesisCharcoal = Color(0xFF1A1A1A)
val GenesisPanel = Color(0xFF1E1E1E)
val GenesisSurfaceMuted = Color(0xFF141414)

val GenesisCopper = Color(0xFFC4956A)
val GenesisCopperLight = Color(0xFFD4AE8A)
val GenesisCopperDark = Color(0xFFA67B52)

val GenesisGold = Color(0xFFD4AF37)
val GenesisGoldLight = Color(0xFFE8C96A)
val GenesisGoldMuted = Color(0xFFB8962E)

val GenesisSilver = Color(0xFFE8E8E8)
val GenesisSilverMuted = Color(0xFFC0C0C0)

val GenesisMuted = Color(0xFF8A8A8A)
val GenesisMutedDark = Color(0xFF6B6B6B)

val GenesisBorder = Color(0xFF2A2A2A)
val GenesisBorderSubtle = Color(0xFF1F1F1F)
val GenesisBorderAccent = Color(0x33C4956A)

val GenesisOverlay = Color(0xCC0A0A0A)
val GenesisScrim = Color(0x99000000)
val GenesisAmbientBrown = Color(0xFF1A1208)

// ── Extended semantic tokens (beyond Material ColorScheme) ───────────────────

@Immutable
data class GenesisExtendedColors(
    val copper: Color = GenesisCopper,
    val copperLight: Color = GenesisCopperLight,
    val copperDark: Color = GenesisCopperDark,
    val gold: Color = GenesisGold,
    val goldLight: Color = GenesisGoldLight,
    val goldMuted: Color = GenesisGoldMuted,
    val silver: Color = GenesisSilver,
    val silverMuted: Color = GenesisSilverMuted,
    val muted: Color = GenesisMuted,
    val mutedDark: Color = GenesisMutedDark,
    val border: Color = GenesisBorder,
    val borderSubtle: Color = GenesisBorderSubtle,
    val borderAccent: Color = GenesisBorderAccent,
    val panel: Color = GenesisPanel,
    val surfaceMuted: Color = GenesisSurfaceMuted,
    val overlay: Color = GenesisOverlay,
    val scrim: Color = GenesisScrim,
    val ambientBrown: Color = GenesisAmbientBrown,
    val onCopper: Color = GenesisBlack,
    val onGold: Color = GenesisBlack,
)

val LocalGenesisExtendedColors = staticCompositionLocalOf { GenesisExtendedColors() }
