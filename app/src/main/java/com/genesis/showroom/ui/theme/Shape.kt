package com.genesis.showroom.ui.theme

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

/**
 * Genesis shape language — sharp editorial corners with restrained rounding.
 * Mirrors the minimal, architectural aesthetic of genesis.com.
 */
@Immutable
data class GenesisShapes(
    val none: Shape = RoundedCornerShape(0.dp),
    val extraSmall: Shape = RoundedCornerShape(2.dp),
    val small: Shape = RoundedCornerShape(4.dp),
    val medium: Shape = RoundedCornerShape(8.dp),
    val large: Shape = RoundedCornerShape(12.dp),
    val extraLarge: Shape = RoundedCornerShape(16.dp),
    val pill: Shape = RoundedCornerShape(percent = 50),
    val circle: Shape = CircleShape,
    val button: Shape = RoundedCornerShape(2.dp),
    val card: Shape = RoundedCornerShape(4.dp),
    val dialog: Shape = RoundedCornerShape(8.dp),
    val bottomSheet: Shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),
)

val LocalGenesisShapes = staticCompositionLocalOf { GenesisShapes() }

val GenesisMaterialShapes = Shapes(
    extraSmall = RoundedCornerShape(2.dp),
    small = RoundedCornerShape(4.dp),
    medium = RoundedCornerShape(8.dp),
    large = RoundedCornerShape(12.dp),
    extraLarge = RoundedCornerShape(16.dp),
)
