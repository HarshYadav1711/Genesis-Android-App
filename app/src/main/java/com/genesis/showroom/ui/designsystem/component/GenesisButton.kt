package com.genesis.showroom.ui.designsystem.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.genesis.showroom.ui.theme.GenesisBlack
import com.genesis.showroom.ui.theme.GenesisBorder
import com.genesis.showroom.ui.theme.GenesisBorderSubtle
import com.genesis.showroom.ui.theme.GenesisButtonLabel
import com.genesis.showroom.ui.theme.GenesisCharcoal
import com.genesis.showroom.ui.theme.GenesisCopper
import com.genesis.showroom.ui.theme.GenesisGold
import com.genesis.showroom.ui.theme.GenesisMuted
import com.genesis.showroom.ui.theme.GenesisPanel
import com.genesis.showroom.ui.theme.GenesisSilver
import com.genesis.showroom.ui.theme.GenesisTheme

enum class GenesisButtonVariant {
    Primary,
    Secondary,
    Outline,
    Ghost,
    Gold,
}

enum class GenesisButtonSize {
    Default,
    Compact,
}

object GenesisButtonDefaults {
    val minHeight: Dp = 48.dp
    val minHeightCompact: Dp = 40.dp

    @Composable
    fun shape(): Shape = GenesisTheme.shapes.button

    @Composable
    fun contentPadding(size: GenesisButtonSize): PaddingValues {
        val spacing = GenesisTheme.spacing
        return when (size) {
            GenesisButtonSize.Default -> PaddingValues(
                horizontal = spacing.buttonHorizontal,
                vertical = spacing.buttonVertical,
            )
            GenesisButtonSize.Compact -> PaddingValues(
                horizontal = spacing.buttonHorizontalCompact,
                vertical = spacing.buttonVerticalCompact,
            )
        }
    }

    @Composable
    fun colors(variant: GenesisButtonVariant) = when (variant) {
        GenesisButtonVariant.Primary -> ButtonDefaults.buttonColors(
            containerColor = GenesisCopper,
            contentColor = GenesisBlack,
            disabledContainerColor = GenesisCopper.copy(alpha = 0.38f),
            disabledContentColor = GenesisBlack.copy(alpha = 0.38f),
        )
        GenesisButtonVariant.Secondary -> ButtonDefaults.buttonColors(
            containerColor = GenesisPanel,
            contentColor = GenesisCopper,
            disabledContainerColor = GenesisCharcoal,
            disabledContentColor = GenesisMuted,
        )
        GenesisButtonVariant.Gold -> ButtonDefaults.buttonColors(
            containerColor = GenesisGold,
            contentColor = GenesisBlack,
            disabledContainerColor = GenesisGold.copy(alpha = 0.38f),
            disabledContentColor = GenesisBlack.copy(alpha = 0.38f),
        )
        GenesisButtonVariant.Outline -> ButtonDefaults.outlinedButtonColors(
            containerColor = Color.Transparent,
            contentColor = GenesisCopper,
            disabledContentColor = GenesisMuted,
        )
        GenesisButtonVariant.Ghost -> ButtonDefaults.textButtonColors(
            containerColor = Color.Transparent,
            contentColor = GenesisCopper,
            disabledContentColor = GenesisMuted,
        )
    }

    @Composable
    fun border(variant: GenesisButtonVariant): BorderStroke? = when (variant) {
        GenesisButtonVariant.Outline -> BorderStroke(
            width = GenesisTheme.spacing.borderWidth,
            color = GenesisCopper,
        )
        GenesisButtonVariant.Secondary -> BorderStroke(
            width = GenesisTheme.spacing.borderWidthSubtle,
            color = GenesisBorderSubtle,
        )
        else -> null
    }
}

@Composable
fun GenesisButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    variant: GenesisButtonVariant = GenesisButtonVariant.Primary,
    size: GenesisButtonSize = GenesisButtonSize.Default,
    enabled: Boolean = true,
    uppercase: Boolean = true,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    GenesisButton(
        onClick = onClick,
        modifier = modifier,
        variant = variant,
        size = size,
        enabled = enabled,
        interactionSource = interactionSource,
    ) {
        if (leadingIcon != null) {
            leadingIcon()
        }
        Text(
            text = if (uppercase) text.uppercase() else text,
            style = GenesisButtonLabel,
            textAlign = TextAlign.Center,
        )
        if (trailingIcon != null) {
            trailingIcon()
        }
    }
}

@Composable
fun GenesisButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    variant: GenesisButtonVariant = GenesisButtonVariant.Primary,
    size: GenesisButtonSize = GenesisButtonSize.Default,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable RowScope.() -> Unit,
) {
    val shape = GenesisButtonDefaults.shape()
    val contentPadding = GenesisButtonDefaults.contentPadding(size)
    val minHeight = when (size) {
        GenesisButtonSize.Default -> GenesisButtonDefaults.minHeight
        GenesisButtonSize.Compact -> GenesisButtonDefaults.minHeightCompact
    }
    val spacing = GenesisTheme.spacing

    val rowContent: @Composable RowScope.() -> Unit = {
        Row(
            horizontalArrangement = Arrangement.spacedBy(
                space = spacing.iconTextGap,
                alignment = Alignment.CenterHorizontally,
            ),
            verticalAlignment = Alignment.CenterVertically,
            content = content,
        )
    }

    when (variant) {
        GenesisButtonVariant.Primary,
        GenesisButtonVariant.Secondary,
        GenesisButtonVariant.Gold,
        -> {
            Button(
                onClick = onClick,
                modifier = modifier.defaultMinSize(minHeight = minHeight),
                enabled = enabled,
                shape = shape,
                colors = GenesisButtonDefaults.colors(variant),
                contentPadding = contentPadding,
                border = GenesisButtonDefaults.border(variant),
                interactionSource = interactionSource,
                content = rowContent,
            )
        }
        GenesisButtonVariant.Outline -> {
            OutlinedButton(
                onClick = onClick,
                modifier = modifier.defaultMinSize(minHeight = minHeight),
                enabled = enabled,
                shape = shape,
                colors = GenesisButtonDefaults.colors(variant),
                contentPadding = contentPadding,
                border = GenesisButtonDefaults.border(variant)!!,
                interactionSource = interactionSource,
                content = rowContent,
            )
        }
        GenesisButtonVariant.Ghost -> {
            TextButton(
                onClick = onClick,
                modifier = modifier.defaultMinSize(minHeight = minHeight),
                enabled = enabled,
                shape = shape,
                colors = GenesisButtonDefaults.colors(variant),
                contentPadding = contentPadding,
                interactionSource = interactionSource,
                content = rowContent,
            )
        }
    }
}

/** Primary copper CTA — the default Genesis call-to-action. */
@Composable
fun GenesisPrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    size: GenesisButtonSize = GenesisButtonSize.Default,
    leadingIcon: (@Composable () -> Unit)? = null,
) = GenesisButton(
    text = text,
    onClick = onClick,
    modifier = modifier,
    variant = GenesisButtonVariant.Primary,
    size = size,
    enabled = enabled,
    leadingIcon = leadingIcon,
)

/** Subtle panel button with copper label. */
@Composable
fun GenesisSecondaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    size: GenesisButtonSize = GenesisButtonSize.Default,
) = GenesisButton(
    text = text,
    onClick = onClick,
    modifier = modifier,
    variant = GenesisButtonVariant.Secondary,
    size = size,
    enabled = enabled,
)

/** Copper outline button with minimal border. */
@Composable
fun GenesisOutlineButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    size: GenesisButtonSize = GenesisButtonSize.Default,
    leadingIcon: (@Composable () -> Unit)? = null,
) = GenesisButton(
    text = text,
    onClick = onClick,
    modifier = modifier,
    variant = GenesisButtonVariant.Outline,
    size = size,
    enabled = enabled,
    leadingIcon = leadingIcon,
)

/** Gold highlight CTA for premium emphasis. */
@Composable
fun GenesisGoldButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    size: GenesisButtonSize = GenesisButtonSize.Default,
) = GenesisButton(
    text = text,
    onClick = onClick,
    modifier = modifier,
    variant = GenesisButtonVariant.Gold,
    size = size,
    enabled = enabled,
)

/** Text-only button for tertiary actions. */
@Composable
fun GenesisTextButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    uppercase: Boolean = false,
    contentColor: Color = GenesisCopper,
) {
    TextButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        shape = GenesisButtonDefaults.shape(),
        colors = ButtonDefaults.textButtonColors(
            contentColor = contentColor,
            disabledContentColor = GenesisMuted,
        ),
        contentPadding = GenesisButtonDefaults.contentPadding(GenesisButtonSize.Compact),
    ) {
        Text(
            text = if (uppercase) text.uppercase() else text,
            style = GenesisButtonLabel,
            color = LocalContentColor.current,
        )
    }
}
