package com.genesis.showroom.ui.overlay

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.outlined.Sync
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.genesis.showroom.data.VoiceState
import com.genesis.showroom.ui.language.GenesisLanguageProvider
import com.genesis.showroom.ui.theme.GenesisBlack
import com.genesis.showroom.ui.theme.GenesisCopper
import com.genesis.showroom.ui.theme.GenesisFontFamily
import com.genesis.showroom.ui.theme.GenesisGold
import com.genesis.showroom.ui.theme.GenesisMuted
import com.genesis.showroom.ui.theme.GenesisSilver
import com.genesis.showroom.ui.theme.GenesisTheme

@Composable
fun AudioVisualizer(
    state: VoiceState,
    modifier: Modifier = Modifier,
) {
    val isArabic = com.genesis.showroom.ui.language.GenesisLanguage.isArabic
    val infiniteTransition = rememberInfiniteTransition(label = "visualizer")

    val orbScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = when (state) {
            VoiceState.IDLE -> 1.04f
            VoiceState.THINKING -> 1.06f
            VoiceState.SPEAKING -> 1.08f
            VoiceState.LISTENING -> 1.05f
        },
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = when (state) {
                    VoiceState.THINKING -> 1200
                    VoiceState.SPEAKING -> 800
                    VoiceState.LISTENING -> 1000
                    VoiceState.IDLE -> 3000
                },
                easing = FastOutSlowInEasing,
            ),
            repeatMode = RepeatMode.Reverse,
        ),
        label = "orbScale",
    )

    val glowScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.3f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse,
        ),
        label = "glowScale",
    )

    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2000, easing = LinearEasing),
        ),
        label = "thinkingRotation",
    )

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp),
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.height(160.dp),
        ) {
            if (state == VoiceState.SPEAKING || state == VoiceState.LISTENING) {
                Box(
                    modifier = Modifier
                        .size(160.dp)
                        .scale(glowScale)
                        .clip(CircleShape)
                        .background(
                            Brush.radialGradient(
                                colors = listOf(
                                    orbGlowColor(state).copy(alpha = 0.5f),
                                    Color.Transparent,
                                ),
                            ),
                        ),
                )
                Box(
                    modifier = Modifier
                        .size(130.dp)
                        .scale(glowScale * 0.95f)
                        .clip(CircleShape)
                        .background(
                            Brush.radialGradient(
                                colors = listOf(
                                    orbGlowColor(state).copy(alpha = 0.7f),
                                    Color.Transparent,
                                ),
                            ),
                        ),
                )
            }

            Box(
                modifier = Modifier
                    .size(100.dp)
                    .scale(orbScale)
                    .clip(CircleShape)
                    .background(orbGradient(state)),
                contentAlignment = Alignment.Center,
            ) {
                Box(
                    modifier = Modifier
                        .size(84.dp)
                        .clip(CircleShape)
                        .background(
                            Brush.radialGradient(
                                colors = listOf(
                                    Color.White.copy(alpha = 0.6f),
                                    Color.Transparent,
                                ),
                            ),
                        ),
                )

                when (state) {
                    VoiceState.IDLE, VoiceState.LISTENING -> {
                        Icon(
                            imageVector = Icons.Filled.Mic,
                            contentDescription = null,
                            modifier = Modifier.size(28.dp),
                            tint = GenesisBlack.copy(alpha = if (state == VoiceState.LISTENING) 0.8f else 0.6f),
                        )
                    }
                    VoiceState.THINKING -> {
                        Icon(
                            imageVector = Icons.Outlined.Sync,
                            contentDescription = null,
                            modifier = Modifier
                                .size(24.dp)
                                .graphicsLayer { rotationZ = rotation },
                            tint = GenesisBlack.copy(alpha = 0.7f),
                        )
                    }
                    VoiceState.SPEAKING -> {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.VolumeUp,
                            contentDescription = null,
                            modifier = Modifier.size(28.dp),
                            tint = GenesisBlack.copy(alpha = 0.7f),
                        )
                    }
                }
            }
        }

        when (state) {
            VoiceState.LISTENING -> WaveformBars(light = true)
            VoiceState.SPEAKING -> WaveformBars(light = false)
            VoiceState.THINKING -> ThinkingDots()
            else -> Spacer(modifier = Modifier.height(40.dp))
        }

        Text(
            text = stateLabel(state, isArabic),
            fontFamily = GenesisFontFamily.Body,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            letterSpacing = 2.sp,
            color = GenesisMuted,
        )
    }
}

@Composable
private fun WaveformBars(light: Boolean) {
    val infiniteTransition = rememberInfiniteTransition(label = "waveform")
    Row(
        modifier = Modifier.height(40.dp),
        horizontalArrangement = Arrangement.spacedBy(2.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        val barCount = if (light) 24 else 20
        repeat(barCount) { index ->
            val height by infiniteTransition.animateFloat(
                initialValue = 8f,
                targetValue = 28f + (index % 5) * 4f,
                animationSpec = infiniteRepeatable(
                    animation = tween(
                        durationMillis = 400 + (index % 4) * 100,
                        easing = FastOutSlowInEasing,
                    ),
                    repeatMode = RepeatMode.Reverse,
                    initialStartOffset = androidx.compose.animation.core.StartOffset(index * 30),
                ),
                label = "bar$index",
            )
            Box(
                modifier = Modifier
                    .size(width = 3.dp, height = height.dp)
                    .clip(CircleShape)
                    .background(
                        brush = if (light) {
                            SolidColor(Color.White)
                        } else {
                            Brush.verticalGradient(listOf(GenesisGold, GenesisCopper))
                        },
                    ),
            )
        }
    }
}

@Composable
private fun ThinkingDots() {
    val infiniteTransition = rememberInfiniteTransition(label = "thinkingDots")
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.height(40.dp),
    ) {
        repeat(3) { index ->
            val offsetY by infiniteTransition.animateFloat(
                initialValue = 0f,
                targetValue = -8f,
                animationSpec = infiniteRepeatable(
                    animation = tween(durationMillis = 800, easing = FastOutSlowInEasing),
                    repeatMode = RepeatMode.Reverse,
                    initialStartOffset = androidx.compose.animation.core.StartOffset(index * 200),
                ),
                label = "dot$index",
            )
            val alpha by infiniteTransition.animateFloat(
                initialValue = 0.4f,
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = tween(durationMillis = 800, easing = FastOutLinearInEasing),
                    repeatMode = RepeatMode.Reverse,
                    initialStartOffset = androidx.compose.animation.core.StartOffset(index * 200),
                ),
                label = "dotAlpha$index",
            )
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .graphicsLayer {
                        translationY = offsetY
                        this.alpha = alpha
                    }
                    .clip(CircleShape)
                    .background(GenesisCopper),
            )
        }
    }
}

private fun orbGlowColor(state: VoiceState): Color = when (state) {
    VoiceState.LISTENING -> Color.White.copy(alpha = 0.2f)
    VoiceState.SPEAKING -> GenesisCopper.copy(alpha = 0.5f)
    VoiceState.THINKING -> GenesisCopper.copy(alpha = 0.35f)
    VoiceState.IDLE -> GenesisCopper.copy(alpha = 0.15f)
}

private fun orbGradient(state: VoiceState): Brush {
    val isListening = state == VoiceState.LISTENING
    return Brush.radialGradient(
        colors = listOf(
            if (isListening) Color.White.copy(alpha = 0.95f) else GenesisCopper.copy(alpha = 0.9f),
            if (isListening) Color(0xFFC8C8DC).copy(alpha = 0.7f) else Color(0xFF8C5A32).copy(alpha = 0.7f),
        ),
    )
}

private fun stateLabel(state: VoiceState, isArabic: Boolean): String = when (state) {
    VoiceState.IDLE -> if (isArabic) "اضغط للتحدث" else "Tap to speak"
    VoiceState.LISTENING -> if (isArabic) "يستمع..." else "Listening..."
    VoiceState.THINKING -> if (isArabic) "جاري المعالجة..." else "Processing..."
    VoiceState.SPEAKING -> if (isArabic) "Genesis AI يتحدث" else "Genesis AI speaking"
}.uppercase()

@Preview(showBackground = true, backgroundColor = 0xFF1A1A1A)
@Composable
private fun AudioVisualizerIdlePreview() {
    GenesisTheme {
        GenesisLanguageProvider {
            AudioVisualizer(state = VoiceState.IDLE)
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF1A1A1A)
@Composable
private fun AudioVisualizerListeningPreview() {
    GenesisTheme {
        GenesisLanguageProvider {
            AudioVisualizer(state = VoiceState.LISTENING)
        }
    }
}
