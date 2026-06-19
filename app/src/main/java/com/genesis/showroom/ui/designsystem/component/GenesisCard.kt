package com.genesis.showroom.ui.designsystem.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.genesis.showroom.ui.theme.GenesisBlack
import com.genesis.showroom.ui.theme.GenesisBorder
import com.genesis.showroom.ui.theme.GenesisBorderAccent
import com.genesis.showroom.ui.theme.GenesisBorderSubtle
import com.genesis.showroom.ui.theme.GenesisCharcoal
import com.genesis.showroom.ui.theme.GenesisCopper
import com.genesis.showroom.ui.theme.GenesisGold
import com.genesis.showroom.ui.theme.GenesisPanel
import com.genesis.showroom.ui.theme.GenesisTheme

enum class GenesisCardVariant {
    Filled,
    Elevated,
    Outlined,
    Feature,
    Accent,
}

object GenesisCardDefaults {
    @Composable
    fun shape(): Shape = GenesisTheme.shapes.card

    @Composable
    fun contentPadding(compact: Boolean = false) = if (compact) {
        GenesisTheme.spacing.cardPaddingCompact
    } else {
        GenesisTheme.spacing.cardPadding
    }

    @Composable
    fun colors(variant: GenesisCardVariant) = when (variant) {
        GenesisCardVariant.Filled -> CardDefaults.cardColors(
            containerColor = GenesisPanel,
            contentColor = MaterialTheme.colorScheme.onSurface,
        )
        GenesisCardVariant.Elevated -> CardDefaults.elevatedCardColors(
            containerColor = GenesisCharcoal,
            contentColor = MaterialTheme.colorScheme.onSurface,
        )
        GenesisCardVariant.Outlined -> CardDefaults.outlinedCardColors(
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.onSurface,
        )
        GenesisCardVariant.Feature,
        GenesisCardVariant.Accent,
        -> CardDefaults.cardColors(
            containerColor = GenesisCharcoal,
            contentColor = MaterialTheme.colorScheme.onSurface,
        )
    }

    @Composable
    fun elevation(variant: GenesisCardVariant): Dp = when (variant) {
        GenesisCardVariant.Elevated -> 2.dp
        else -> 0.dp
    }

    @Composable
    fun border(variant: GenesisCardVariant): BorderStroke? = when (variant) {
        GenesisCardVariant.Outlined -> BorderStroke(
            width = GenesisTheme.spacing.borderWidthSubtle,
            color = GenesisBorder,
        )
        GenesisCardVariant.Feature -> BorderStroke(
            width = GenesisTheme.spacing.borderWidthSubtle,
            color = GenesisBorderAccent,
        )
        else -> null
    }

    val accentBarHeight: Dp = 2.dp
}

@Composable
fun GenesisCard(
    modifier: Modifier = Modifier,
    variant: GenesisCardVariant = GenesisCardVariant.Filled,
    compact: Boolean = false,
    onClick: (() -> Unit)? = null,
    accentColor: Color = GenesisCopper,
    content: @Composable ColumnScope.() -> Unit,
) {
    val shape = GenesisCardDefaults.shape()
    val padding = GenesisCardDefaults.contentPadding(compact)
    val colors = GenesisCardDefaults.colors(variant)
    val border = GenesisCardDefaults.border(variant)
    val elevation = GenesisCardDefaults.elevation(variant)

    val cardContent: @Composable ColumnScope.() -> Unit = {
        CardInnerContent(
            variant = variant,
            padding = padding,
            accentColor = accentColor,
            content = content,
        )
    }

    when {
        onClick != null && variant == GenesisCardVariant.Elevated -> {
            Card(
                modifier = modifier,
                onClick = onClick,
                shape = shape,
                colors = colors,
                elevation = CardDefaults.elevatedCardElevation(defaultElevation = elevation),
                border = border,
                content = cardContent,
            )
        }
        onClick != null -> {
            Card(
                modifier = modifier,
                onClick = onClick,
                shape = shape,
                colors = colors,
                elevation = CardDefaults.cardElevation(defaultElevation = elevation),
                border = border,
                content = cardContent,
            )
        }
        variant == GenesisCardVariant.Elevated -> {
            Card(
                modifier = modifier,
                shape = shape,
                colors = colors,
                elevation = CardDefaults.elevatedCardElevation(defaultElevation = elevation),
                border = border,
                content = cardContent,
            )
        }
        else -> {
            Card(
                modifier = modifier,
                shape = shape,
                colors = colors,
                elevation = CardDefaults.cardElevation(defaultElevation = elevation),
                border = border,
                content = cardContent,
            )
        }
    }
}

@Composable
private fun CardInnerContent(
    variant: GenesisCardVariant,
    padding: Dp,
    accentColor: Color,
    content: @Composable ColumnScope.() -> Unit,
) {
    Column {
        when (variant) {
            GenesisCardVariant.Feature -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(GenesisCardDefaults.accentBarHeight)
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(accentColor, GenesisGold, accentColor),
                            ),
                        ),
                )
            }
            GenesisCardVariant.Accent -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(GenesisCardDefaults.accentBarHeight)
                        .background(accentColor),
                )
            }
            else -> Unit
        }
        Column(modifier = Modifier.padding(padding), content = content)
    }
}

/** Flat panel surface without elevation — ideal for spec grids and content blocks. */
@Composable
fun GenesisSurfaceCard(
    modifier: Modifier = Modifier,
    compact: Boolean = false,
    content: @Composable ColumnScope.() -> Unit,
) {
    val shape = GenesisCardDefaults.shape()
    val padding = GenesisCardDefaults.contentPadding(compact)

    Surface(
        modifier = modifier,
        shape = shape,
        color = GenesisPanel,
        border = BorderStroke(
            width = GenesisTheme.spacing.borderWidthSubtle,
            color = GenesisBorderSubtle,
        ),
    ) {
        Column(modifier = Modifier.padding(padding), content = content)
    }
}

/** Clickable outlined card for vehicle tiles and selectable items. */
@Composable
fun GenesisOutlinedCard(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    compact: Boolean = false,
    content: @Composable ColumnScope.() -> Unit,
) = GenesisCard(
    modifier = modifier,
    variant = GenesisCardVariant.Outlined,
    compact = compact,
    onClick = onClick,
    content = content,
)

/** Feature card with copper-to-gold accent bar for highlights and hero modules. */
@Composable
fun GenesisFeatureCard(
    modifier: Modifier = Modifier,
    accentColor: Color = GenesisCopper,
    compact: Boolean = false,
    onClick: (() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit,
) = GenesisCard(
    modifier = modifier,
    variant = GenesisCardVariant.Feature,
    compact = compact,
    onClick = onClick,
    accentColor = accentColor,
    content = content,
)

/** Minimal elevated card for floating panels and overlays. */
@Composable
fun GenesisElevatedCard(
    modifier: Modifier = Modifier,
    compact: Boolean = false,
    onClick: (() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit,
) = GenesisCard(
    modifier = modifier,
    variant = GenesisCardVariant.Elevated,
    compact = compact,
    onClick = onClick,
    content = content,
)
