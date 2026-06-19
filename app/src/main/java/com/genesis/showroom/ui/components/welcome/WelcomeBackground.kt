package com.genesis.showroom.ui.components.welcome

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.genesis.showroom.ui.theme.GenesisAmbientBrown
import com.genesis.showroom.ui.theme.GenesisBlack
import com.genesis.showroom.ui.theme.GenesisCopper

@Composable
fun WelcomeBackground(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit,
) {
    Box(
        modifier = modifier.fillMaxSize(),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.radialGradient(
                        colors = listOf(GenesisAmbientBrown, GenesisBlack, GenesisBlack),
                        radius = 1200f,
                    ),
                ),
        )

        WelcomeGridOverlay(modifier = Modifier.fillMaxSize())

        WelcomeAmbientOrb(modifier = Modifier.align(Alignment.Center))

        WelcomeCornerAccent(
            modifier = Modifier.align(Alignment.TopStart),
            topStart = true,
        )
        WelcomeCornerAccent(
            modifier = Modifier.align(Alignment.BottomEnd),
            topStart = false,
        )

        content()
    }
}

@Composable
private fun WelcomeGridOverlay(modifier: Modifier = Modifier) {
    val lineColor = GenesisCopper.copy(alpha = 0.3f)
    val overlayAlpha = 0.05f

    Canvas(modifier = modifier.graphicsLayer { alpha = overlayAlpha }) {
        val gridStep = 80.dp.toPx()
        var x = 0f
        while (x <= size.width) {
            drawLine(
                color = lineColor,
                start = Offset(x, 0f),
                end = Offset(x, size.height),
                strokeWidth = 1f,
            )
            x += gridStep
        }
        var y = 0f
        while (y <= size.height) {
            drawLine(
                color = lineColor,
                start = Offset(0f, y),
                end = Offset(size.width, y),
                strokeWidth = 1f,
            )
            y += gridStep
        }
    }
}

@Composable
private fun WelcomeAmbientOrb(modifier: Modifier = Modifier) {
    val infiniteTransition = rememberInfiniteTransition(label = "welcomeOrb")
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 6000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse,
        ),
        label = "orbScale",
    )
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.6f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 6000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse,
        ),
        label = "orbAlpha",
    )

    Box(
        modifier = modifier
            .size(600.dp)
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
                this.alpha = alpha
            }
            .background(
                brush = Brush.radialGradient(
                    colors = listOf(
                        GenesisCopper.copy(alpha = 0.08f),
                        Color.Transparent,
                    ),
                ),
            ),
    )
}

@Composable
private fun WelcomeCornerAccent(
    modifier: Modifier = Modifier,
    topStart: Boolean,
) {
    val accentColor = GenesisCopper.copy(alpha = 0.2f)

    Canvas(
        modifier = modifier.size(128.dp),
    ) {
        val stroke = Stroke(width = 1.dp.toPx())
        if (topStart) {
            drawLine(
                color = accentColor,
                start = Offset(0f, 0f),
                end = Offset(size.width, 0f),
                strokeWidth = stroke.width,
            )
            drawLine(
                color = accentColor,
                start = Offset(0f, 0f),
                end = Offset(0f, size.height),
                strokeWidth = stroke.width,
            )
        } else {
            drawLine(
                color = accentColor,
                start = Offset(size.width, size.height),
                end = Offset(0f, size.height),
                strokeWidth = stroke.width,
            )
            drawLine(
                color = accentColor,
                start = Offset(size.width, size.height),
                end = Offset(size.width, 0f),
                strokeWidth = stroke.width,
            )
        }
    }
}
