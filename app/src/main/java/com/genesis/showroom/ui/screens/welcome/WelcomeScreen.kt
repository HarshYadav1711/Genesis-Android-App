package com.genesis.showroom.ui.screens.welcome

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.genesis.showroom.data.AppLanguage
import com.genesis.showroom.ui.components.GenesisLogo
import com.genesis.showroom.ui.components.welcome.WelcomeBackground
import com.genesis.showroom.ui.designsystem.component.GenesisOutlineButton
import com.genesis.showroom.ui.designsystem.component.GenesisPrimaryButton
import com.genesis.showroom.ui.theme.GenesisCopper
import com.genesis.showroom.ui.theme.GenesisFontFamily
import com.genesis.showroom.ui.theme.GenesisGold
import com.genesis.showroom.ui.theme.GenesisMuted
import com.genesis.showroom.ui.theme.GenesisSilver
import com.genesis.showroom.ui.theme.GenesisTheme

@Composable
fun WelcomeScreen(
    language: AppLanguage,
    onExplore: () -> Unit,
    onChat: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var animateIn by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { animateIn = true }

    WelcomeBackground(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            WelcomeLogo(visible = animateIn)

            WelcomeDivider(visible = animateIn)

            WelcomeHeadline(language = language, visible = animateIn)

            WelcomeSubtitle(language = language, visible = animateIn)

            WelcomeCtas(
                language = language,
                visible = animateIn,
                onExplore = onExplore,
                onChat = onChat,
            )

            WelcomeAvailabilityFooter(language = language, visible = animateIn)
        }
    }
}

@Composable
private fun WelcomeLogo(visible: Boolean) {
    val alpha by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        animationSpec = tween(durationMillis = 800, easing = FastOutSlowInEasing),
        label = "logoAlpha",
    )
    val offsetY by animateFloatAsState(
        targetValue = if (visible) 0f else -20f,
        animationSpec = tween(durationMillis = 800, easing = FastOutSlowInEasing),
        label = "logoOffset",
    )

    Box(
        modifier = Modifier
            .graphicsLayer {
                this.alpha = alpha
                translationY = offsetY
            }
            .padding(bottom = 12.dp),
    ) {
        GenesisLogo()
    }
}

@Composable
private fun WelcomeDivider(visible: Boolean) {
    val scaleX by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        animationSpec = tween(
            durationMillis = 800,
            delayMillis = 300,
            easing = FastOutSlowInEasing,
        ),
        label = "dividerScale",
    )

    Box(
        modifier = Modifier
            .padding(bottom = 32.dp)
            .width(96.dp)
            .height(1.dp)
            .graphicsLayer {
                this.scaleX = scaleX
            }
            .background(
                Brush.horizontalGradient(
                    colors = listOf(
                        GenesisCopper.copy(alpha = 0f),
                        GenesisCopper,
                        GenesisCopper.copy(alpha = 0f),
                    ),
                ),
            ),
    )
}

@Composable
private fun WelcomeHeadline(language: AppLanguage, visible: Boolean) {
    val alpha by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        animationSpec = tween(
            durationMillis = 800,
            delayMillis = 400,
            easing = FastOutSlowInEasing,
        ),
        label = "headlineAlpha",
    )
    val offsetY by animateFloatAsState(
        targetValue = if (visible) 0f else 20f,
        animationSpec = tween(
            durationMillis = 800,
            delayMillis = 400,
            easing = FastOutSlowInEasing,
        ),
        label = "headlineOffset",
    )

    Column(
        modifier = Modifier
            .graphicsLayer {
                this.alpha = alpha
                translationY = offsetY
            }
            .padding(bottom = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        if (language.isArabic) {
            Text(
                text = "تجربة Genesis الشخصية",
                fontFamily = GenesisFontFamily.Display,
                fontWeight = FontWeight.Light,
                fontSize = 36.sp,
                lineHeight = 44.sp,
                color = GenesisSilver,
                textAlign = TextAlign.Center,
            )
        } else {
            Text(
                text = "Your Personal",
                fontFamily = GenesisFontFamily.Display,
                fontWeight = FontWeight.Light,
                fontSize = 36.sp,
                lineHeight = 44.sp,
                color = GenesisSilver,
                textAlign = TextAlign.Center,
            )
            Text(
                text = "Genesis Experience",
                fontFamily = GenesisFontFamily.Display,
                fontWeight = FontWeight.SemiBold,
                fontStyle = FontStyle.Italic,
                fontSize = 36.sp,
                lineHeight = 44.sp,
                textAlign = TextAlign.Center,
                style = TextStyle(
                    brush = Brush.linearGradient(
                        colors = listOf(GenesisCopper, GenesisGold),
                    ),
                ),
            )
        }
    }
}

@Composable
private fun WelcomeSubtitle(language: AppLanguage, visible: Boolean) {
    val alpha by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        animationSpec = tween(
            durationMillis = 800,
            delayMillis = 600,
            easing = FastOutSlowInEasing,
        ),
        label = "subtitleAlpha",
    )

    Text(
        text = if (language.isArabic) "صُنع للاستثنائي" else "Crafted for the exceptional",
        modifier = Modifier
            .alpha(alpha)
            .padding(bottom = 64.dp),
        fontFamily = GenesisFontFamily.Body,
        fontWeight = FontWeight.Light,
        fontSize = 14.sp,
        letterSpacing = 3.sp,
        color = GenesisMuted,
        textAlign = TextAlign.Center,
    )
}

@Composable
private fun WelcomeCtas(
    language: AppLanguage,
    visible: Boolean,
    onExplore: () -> Unit,
    onChat: () -> Unit,
) {
    val alpha by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        animationSpec = tween(
            durationMillis = 800,
            delayMillis = 800,
            easing = FastOutSlowInEasing,
        ),
        label = "ctaAlpha",
    )
    val offsetY by animateFloatAsState(
        targetValue = if (visible) 0f else 20f,
        animationSpec = tween(
            durationMillis = 800,
            delayMillis = 800,
            easing = FastOutSlowInEasing,
        ),
        label = "ctaOffset",
    )

    Column(
        modifier = Modifier
            .graphicsLayer {
                this.alpha = alpha
                translationY = offsetY
            },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        GenesisPrimaryButton(
            text = if (language.isArabic) "استعرض سياراتنا" else "Explore Our Vehicles",
            onClick = onExplore,
            modifier = Modifier.widthIn(min = 220.dp),
        )
        GenesisOutlineButton(
            text = if (language.isArabic) "تحدث مع Genesis AI" else "Talk to Genesis AI",
            onClick = onChat,
            modifier = Modifier.widthIn(min = 220.dp),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Mic,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                )
            },
        )
    }
}

@Composable
private fun WelcomeAvailabilityFooter(language: AppLanguage, visible: Boolean) {
    val alpha by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        animationSpec = tween(
            durationMillis = 1000,
            delayMillis = 1200,
            easing = FastOutSlowInEasing,
        ),
        label = "footerAlpha",
    )

    Column(
        modifier = Modifier
            .alpha(alpha)
            .padding(top = 80.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(
            text = if (language.isArabic) {
                "متاح في دبي • أبوظبي • الشارقة"
            } else {
                "Available in Dubai • Abu Dhabi • Sharjah"
            },
            fontFamily = GenesisFontFamily.Body,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            letterSpacing = 2.sp,
            color = GenesisMuted,
            textAlign = TextAlign.Center,
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            repeat(3) { index ->
                PulsingDot(delayMillis = index * 400)
            }
        }
    }
}

@Composable
private fun PulsingDot(delayMillis: Int) {
    val infiniteTransition = rememberInfiniteTransition(label = "footerDot")
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2000, delayMillis = delayMillis),
            repeatMode = RepeatMode.Reverse,
        ),
        label = "dotAlpha",
    )

    Box(
        modifier = Modifier
            .size(4.dp)
            .alpha(alpha)
            .background(GenesisCopper, CircleShape),
    )
}

@Preview(showBackground = true, backgroundColor = 0xFF0A0A0A)
@Composable
private fun WelcomeScreenPreview() {
    GenesisTheme {
        WelcomeScreen(
            language = AppLanguage.EN,
            onExplore = {},
            onChat = {},
        )
    }
}
