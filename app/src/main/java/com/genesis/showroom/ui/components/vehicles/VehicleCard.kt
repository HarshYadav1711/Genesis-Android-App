package com.genesis.showroom.ui.components.vehicles

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.genesis.showroom.data.Vehicle
import com.genesis.showroom.ui.language.GenesisLanguage
import com.genesis.showroom.ui.language.GenesisLanguageProvider
import com.genesis.showroom.ui.theme.GenesisBlack
import com.genesis.showroom.ui.theme.GenesisBorder
import com.genesis.showroom.ui.theme.GenesisCharcoal
import com.genesis.showroom.ui.theme.GenesisCopper
import com.genesis.showroom.ui.theme.GenesisFontFamily
import com.genesis.showroom.ui.theme.GenesisMuted
import com.genesis.showroom.ui.theme.GenesisPanel
import com.genesis.showroom.ui.theme.GenesisSilver
import com.genesis.showroom.ui.theme.GenesisTheme

private data class BadgeColors(
    val background: Color,
    val content: Color,
)

private val badgeBackgrounds = mapOf(
    "Sport" to BadgeColors(Color(0xFF7F1D1D), Color(0xFFFCA5A5)),
    "Executive" to BadgeColors(Color(0xFF1E3A8A), Color(0xFF93C5FD)),
    "Flagship" to BadgeColors(Color(0xFF713F12), Color(0xFFFDE047)),
    "Electric" to BadgeColors(Color(0xFF14532D), Color(0xFF86EFAC)),
    "Popular" to BadgeColors(GenesisCopper, GenesisBlack),
    "Family" to BadgeColors(Color(0xFF581C87), Color(0xFFD8B4FE)),
    "Coupe" to BadgeColors(Color(0xFF7C2D12), Color(0xFFFDBA74)),
)

@Composable
fun VehicleCard(
    vehicle: Vehicle,
    index: Int,
    onClick: (Vehicle) -> Unit,
    modifier: Modifier = Modifier,
) {
    val isArabic = GenesisLanguage.isArabic
    var visible by remember(vehicle.id) { mutableStateOf(false) }
    LaunchedEffect(vehicle.id) { visible = true }

    val alpha by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        animationSpec = tween(
            durationMillis = 500,
            delayMillis = index * 70,
            easing = FastOutSlowInEasing,
        ),
        label = "cardAlpha",
    )
    val offsetY by animateFloatAsState(
        targetValue = if (visible) 0f else 24f,
        animationSpec = tween(
            durationMillis = 500,
            delayMillis = index * 70,
            easing = FastOutSlowInEasing,
        ),
        label = "cardOffset",
    )

    Box(
        modifier = modifier
            .graphicsLayer {
                this.alpha = alpha
                translationY = offsetY
            }
            .clip(GenesisTheme.shapes.card)
            .border(
                width = GenesisTheme.spacing.borderWidthSubtle,
                color = GenesisBorder,
                shape = GenesisTheme.shapes.card,
            )
            .background(GenesisCharcoal)
            .clickable { onClick(vehicle) },
    ) {
        Column {
            VehicleCardImage(vehicle = vehicle, isArabic = isArabic)
            VehicleCardContent(vehicle = vehicle, isArabic = isArabic)
        }
    }
}

@Composable
private fun VehicleCardImage(vehicle: Vehicle, isArabic: Boolean) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(176.dp)
            .background(GenesisBlack),
    ) {
        AsyncImage(
            model = vehicle.image,
            contentDescription = vehicle.name,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            alpha = 0.8f,
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.Transparent,
                            GenesisCharcoal,
                        ),
                    ),
                ),
        )

        vehicle.badge?.let { badge ->
            val colors = badgeBackgrounds[badge] ?: BadgeColors(GenesisCopper, GenesisBlack)
            val badgeText = buildString {
                if (vehicle.isElectric && badge == "Electric") append("⚡ ")
                append(badge)
            }

            Text(
                text = badgeText,
                modifier = Modifier
                    .align(if (isArabic) Alignment.TopStart else Alignment.TopEnd)
                    .padding(12.dp)
                    .background(colors.background, RoundedCornerShape(percent = 50))
                    .padding(horizontal = 10.dp, vertical = 4.dp),
                fontFamily = GenesisFontFamily.Body,
                fontWeight = FontWeight.SemiBold,
                fontSize = 10.sp,
                letterSpacing = 1.sp,
                color = colors.content,
            )
        }
    }
}

@Composable
private fun VehicleCardContent(vehicle: Vehicle, isArabic: Boolean) {
    Column(modifier = Modifier.padding(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top,
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = vehicle.name,
                    fontFamily = GenesisFontFamily.Body,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    color = GenesisSilver,
                )
                Text(
                    text = vehicle.type,
                    modifier = Modifier.padding(top = 2.dp),
                    fontFamily = GenesisFontFamily.Body,
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp,
                    letterSpacing = 0.5.sp,
                    color = GenesisMuted,
                )
            }

            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = if (isArabic) "من" else "from",
                    fontFamily = GenesisFontFamily.Body,
                    fontWeight = FontWeight.Medium,
                    fontSize = 12.sp,
                    color = GenesisCopper,
                )
                Text(
                    text = vehicle.startingPrice,
                    fontFamily = GenesisFontFamily.Body,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp,
                    color = GenesisSilver,
                    textAlign = TextAlign.End,
                )
            }
        }

        Text(
            text = "\"${vehicle.tagline}\"",
            modifier = Modifier.padding(top = 8.dp, bottom = 12.dp),
            fontFamily = GenesisFontFamily.Body,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Italic,
            fontSize = 12.sp,
            color = GenesisSilver.copy(alpha = 0.7f),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(GenesisBorder),
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier.weight(1f),
            ) {
                vehicle.highlights.take(2).forEach { highlight ->
                    Text(
                        text = highlight,
                        modifier = Modifier
                            .widthIn(max = 80.dp)
                            .background(GenesisPanel, RoundedCornerShape(percent = 50))
                            .padding(horizontal = 8.dp, vertical = 2.dp),
                        fontFamily = GenesisFontFamily.Body,
                        fontWeight = FontWeight.Normal,
                        fontSize = 10.sp,
                        color = GenesisMuted,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Text(
                    text = if (isArabic) "عرض" else "View",
                    fontFamily = GenesisFontFamily.Body,
                    fontWeight = FontWeight.Medium,
                    fontSize = 10.sp,
                    letterSpacing = 1.5.sp,
                    color = GenesisCopper,
                )
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = null,
                    modifier = Modifier.height(10.dp),
                    tint = GenesisCopper,
                )
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF0A0A0A)
@Composable
private fun VehicleCardPreview() {
    GenesisTheme {
        GenesisLanguageProvider {
            VehicleCard(
                vehicle = Vehicle(
                    id = "g70",
                    name = "Genesis G70",
                    type = "Compact Luxury Sedan",
                    tagline = "Pure driving exhilaration, refined.",
                    image = "",
                    startingPrice = "AED 165,000",
                    badge = "Sport",
                    highlights = listOf("Sport-tuned chassis", "370 HP twin-turbo V6"),
                ),
                index = 0,
                onClick = {},
                modifier = Modifier.padding(16.dp),
            )
        }
    }
}
