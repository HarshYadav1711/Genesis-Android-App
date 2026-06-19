package com.genesis.showroom.ui.screens.specboard

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.outlined.Shield
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.genesis.showroom.data.SpecTab
import com.genesis.showroom.data.Vehicle
import com.genesis.showroom.data.specEntries
import com.genesis.showroom.ui.designsystem.component.GenesisButton
import com.genesis.showroom.ui.designsystem.component.GenesisButtonSize
import com.genesis.showroom.ui.designsystem.component.GenesisButtonVariant
import com.genesis.showroom.ui.language.GenesisLanguage
import com.genesis.showroom.ui.language.GenesisLanguageProvider
import com.genesis.showroom.ui.theme.GenesisBlack
import com.genesis.showroom.ui.theme.GenesisBorder
import com.genesis.showroom.ui.theme.GenesisBorderAccent
import com.genesis.showroom.ui.theme.GenesisCopper
import com.genesis.showroom.ui.theme.GenesisFontFamily
import com.genesis.showroom.ui.theme.GenesisGold
import com.genesis.showroom.ui.theme.GenesisMuted
import com.genesis.showroom.ui.theme.GenesisSilver
import com.genesis.showroom.ui.theme.GenesisTheme

@Composable
fun SpecBoardScreen(
    vehicle: Vehicle,
    onBack: () -> Unit,
    onChat: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val isArabic = GenesisLanguage.isArabic
    val language = GenesisLanguage.current
    var activeTab by remember(vehicle.id) { mutableStateOf(SpecTab.PERFORMANCE) }

    BackHandler(onBack = onBack)

    BoxWithConstraints(
        modifier = modifier
            .fillMaxSize()
            .background(GenesisBlack)
            .statusBarsPadding()
            .navigationBarsPadding(),
    ) {
        val useSideBySide = maxWidth >= 700.dp

        if (useSideBySide) {
            Row(modifier = Modifier.fillMaxSize()) {
                SpecBoardHeroPanel(
                    vehicle = vehicle,
                    isArabic = isArabic,
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(0.45f),
                )
                SpecBoardDetailsPanel(
                    vehicle = vehicle,
                    isArabic = isArabic,
                    language = language,
                    activeTab = activeTab,
                    onTabSelected = { activeTab = it },
                    onChat = onChat,
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(0.55f),
                )
            }
        } else {
            Column(modifier = Modifier.fillMaxSize()) {
                SpecBoardHeroPanel(
                    vehicle = vehicle,
                    isArabic = isArabic,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(280.dp),
                )
                SpecBoardDetailsPanel(
                    vehicle = vehicle,
                    isArabic = isArabic,
                    language = language,
                    activeTab = activeTab,
                    onTabSelected = { activeTab = it },
                    onChat = onChat,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                )
            }
        }

        IconButton(
            onClick = onBack,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(8.dp),
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = if (isArabic) "رجوع" else "Back",
                tint = GenesisSilver,
            )
        }
    }
}

@Composable
private fun SpecBoardHeroPanel(
    vehicle: Vehicle,
    isArabic: Boolean,
    modifier: Modifier = Modifier,
) {
    var visible by remember(vehicle.id) { mutableStateOf(false) }
    LaunchedEffect(vehicle.id) { visible = true }

    val heroAlpha by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        animationSpec = tween(durationMillis = 600, easing = FastOutSlowInEasing),
        label = "heroAlpha",
    )
    val heroOffsetY by animateFloatAsState(
        targetValue = if (visible) 0f else 20f,
        animationSpec = tween(durationMillis = 600, easing = FastOutSlowInEasing),
        label = "heroOffset",
    )

    Box(modifier = modifier.background(GenesisBlack)) {
        AsyncImage(
            model = vehicle.image,
            contentDescription = vehicle.name,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(
                            Color.Transparent,
                            GenesisBlack.copy(alpha = 0.6f),
                        ),
                    ),
                ),
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.Transparent,
                            GenesisBlack.copy(alpha = 0.7f),
                        ),
                    ),
                ),
        )

        Column(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 24.dp, top = 48.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            vehicle.highlights.take(3).forEachIndexed { index, highlight ->
                HighlightPill(
                    text = highlight,
                    index = index,
                    visible = visible,
                )
            }
        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .fillMaxWidth()
                .graphicsLayer {
                    alpha = heroAlpha
                    translationY = heroOffsetY
                }
                .padding(32.dp),
        ) {
            if (vehicle.isElectric) {
                Text(
                    text = if (isArabic) "⚡ كهربائي بالكامل" else "⚡ Fully Electric",
                    fontFamily = GenesisFontFamily.Body,
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp,
                    letterSpacing = 2.sp,
                    color = Color(0xFF4ADE80),
                    modifier = Modifier.padding(bottom = 8.dp),
                )
            }
            Text(
                text = vehicle.name,
                fontFamily = GenesisFontFamily.Display,
                fontWeight = FontWeight.Bold,
                fontSize = 36.sp,
                lineHeight = 42.sp,
                color = GenesisSilver,
            )
            Text(
                text = vehicle.type,
                modifier = Modifier.padding(top = 4.dp),
                fontFamily = GenesisFontFamily.Body,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                letterSpacing = 1.sp,
                color = GenesisCopper,
            )
            Text(
                text = "\"${vehicle.tagline}\"",
                modifier = Modifier.padding(top = 8.dp),
                fontFamily = GenesisFontFamily.Body,
                fontWeight = FontWeight.Normal,
                fontStyle = FontStyle.Italic,
                fontSize = 14.sp,
                color = GenesisSilver.copy(alpha = 0.7f),
            )
            Row(
                modifier = Modifier.padding(top = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Text(
                    text = if (isArabic) "السعر يبدأ من" else "Starting from",
                    fontFamily = GenesisFontFamily.Body,
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp,
                    letterSpacing = 1.2.sp,
                    color = GenesisMuted,
                )
                Text(
                    text = vehicle.startingPrice,
                    fontFamily = GenesisFontFamily.Body,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,
                    color = GenesisGold,
                )
            }
        }
    }
}

@Composable
private fun HighlightPill(
    text: String,
    index: Int,
    visible: Boolean,
) {
    val pillAlpha by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        animationSpec = tween(
            durationMillis = 400,
            delayMillis = 300 + index * 100,
            easing = FastOutSlowInEasing,
        ),
        label = "pillAlpha",
    )
    val pillOffsetX by animateFloatAsState(
        targetValue = if (visible) 0f else -16f,
        animationSpec = tween(
            durationMillis = 400,
            delayMillis = 300 + index * 100,
            easing = FastOutSlowInEasing,
        ),
        label = "pillOffset",
    )

    Text(
        text = text,
        modifier = Modifier
            .graphicsLayer {
                alpha = pillAlpha
                translationX = pillOffsetX
            }
            .background(Color.Black.copy(alpha = 0.6f), RoundedCornerShape(percent = 50))
            .border(
                width = GenesisTheme.spacing.borderWidthSubtle,
                color = GenesisBorderAccent,
                shape = RoundedCornerShape(percent = 50),
            )
            .padding(horizontal = 12.dp, vertical = 6.dp),
        fontFamily = GenesisFontFamily.Body,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        color = GenesisSilver,
    )
}

@Composable
private fun SpecBoardDetailsPanel(
    vehicle: Vehicle,
    isArabic: Boolean,
    language: com.genesis.showroom.data.AppLanguage,
    activeTab: SpecTab,
    onTabSelected: (SpecTab) -> Unit,
    onChat: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(GenesisBlack),
    ) {
        SpecTabBar(
            activeTab = activeTab,
            language = language,
            onTabSelected = onTabSelected,
        )

        AnimatedContent(
            targetState = activeTab,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            transitionSpec = {
                fadeIn(tween(300)) + slideInVertically { it / 4 } togetherWith
                    fadeOut(tween(300)) + slideOutVertically { -it / 4 }
            },
            label = "specContent",
        ) { tab ->
            val entries = vehicle.specEntries(tab.id)

            if (entries.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = if (isArabic) "المواصفات قريباً." else "Specifications coming soon.",
                        fontFamily = GenesisFontFamily.Body,
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp,
                        color = GenesisMuted,
                        textAlign = TextAlign.Center,
                    )
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(32.dp),
                ) {
                    entries.forEachIndexed { index, (label, value) ->
                        SpecRow(label = label, value = value)
                        if (index < entries.lastIndex) {
                            Spacer(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(1.dp)
                                    .background(GenesisBorder.copy(alpha = 0.5f)),
                            )
                        }
                    }
                }
            }
        }

        if (vehicle.competitors.isNotEmpty()) {
            CompetitorsSection(
                competitors = vehicle.competitors,
                isArabic = isArabic,
            )
        }

        SpecBoardCtaBar(
            isArabic = isArabic,
            onChat = onChat,
        )
    }
}

@Composable
private fun SpecTabBar(
    activeTab: SpecTab,
    language: com.genesis.showroom.data.AppLanguage,
    onTabSelected: (SpecTab) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = GenesisTheme.spacing.borderWidthSubtle,
                color = GenesisBorder,
            )
            .horizontalScroll(rememberScrollState()),
    ) {
        SpecTab.entries.forEach { tab ->
            val selected = activeTab == tab
            Column(
                modifier = Modifier
                    .clickable { onTabSelected(tab) }
                    .padding(horizontal = 20.dp, vertical = 16.dp),
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = tab.icon,
                        fontSize = 12.sp,
                    )
                    Text(
                        text = tab.label(language).uppercase(),
                        fontFamily = GenesisFontFamily.Body,
                        fontWeight = FontWeight.Medium,
                        fontSize = 12.sp,
                        letterSpacing = 1.2.sp,
                        color = if (selected) GenesisCopper else GenesisMuted,
                    )
                }
                Spacer(modifier = Modifier.height(14.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(2.dp)
                        .background(if (selected) GenesisCopper else Color.Transparent),
                )
            }
        }
    }
}

@Composable
private fun SpecRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top,
    ) {
        Text(
            text = label,
            modifier = Modifier.weight(0.45f),
            fontFamily = GenesisFontFamily.Body,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            color = GenesisMuted,
        )
        Text(
            text = value,
            modifier = Modifier.weight(0.55f),
            fontFamily = GenesisFontFamily.Body,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            color = GenesisSilver,
            textAlign = TextAlign.End,
        )
    }
}

@Composable
private fun CompetitorsSection(
    competitors: List<String>,
    isArabic: Boolean,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = GenesisTheme.spacing.borderWidthSubtle,
                color = GenesisBorder,
            )
            .padding(horizontal = 32.dp, vertical = 16.dp),
    ) {
        Text(
            text = if (isArabic) "المنافسون" else "Competes with",
            fontFamily = GenesisFontFamily.Body,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            letterSpacing = 1.2.sp,
            color = GenesisMuted,
            modifier = Modifier.padding(bottom = 8.dp),
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.horizontalScroll(rememberScrollState()),
        ) {
            competitors.forEach { competitor ->
                Text(
                    text = competitor,
                    modifier = Modifier
                        .border(
                            width = GenesisTheme.spacing.borderWidthSubtle,
                            color = GenesisBorder,
                            shape = RoundedCornerShape(percent = 50),
                        )
                        .padding(horizontal = 12.dp, vertical = 4.dp),
                    fontFamily = GenesisFontFamily.Body,
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp,
                    color = GenesisSilver,
                )
            }
        }
    }
}

@Composable
private fun SpecBoardCtaBar(
    isArabic: Boolean,
    onChat: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = GenesisTheme.spacing.borderWidthSubtle,
                color = GenesisBorder,
            )
            .padding(horizontal = 32.dp, vertical = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        GenesisButton(
            onClick = onChat,
            modifier = Modifier.weight(1f),
            variant = GenesisButtonVariant.Primary,
            size = GenesisButtonSize.Compact,
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.Chat,
                contentDescription = null,
            )
            Text(
                text = if (isArabic) {
                    "اسأل Genesis AI عن هذه السيارة"
                } else {
                    "Ask Genesis AI About This Vehicle"
                },
                style = com.genesis.showroom.ui.theme.GenesisButtonLabel,
            )
        }
        GenesisButton(
            onClick = {},
            modifier = Modifier.width(140.dp),
            variant = GenesisButtonVariant.Outline,
            size = GenesisButtonSize.Compact,
        ) {
            Icon(
                imageVector = Icons.Outlined.Shield,
                contentDescription = null,
            )
            Text(
                text = if (isArabic) "تجربة قيادة" else "Test Drive",
                style = com.genesis.showroom.ui.theme.GenesisButtonLabel,
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF0A0A0A, widthDp = 900, heightDp = 600)
@Composable
private fun SpecBoardScreenPreview() {
    GenesisTheme {
        GenesisLanguageProvider {
            SpecBoardScreen(
                vehicle = Vehicle(
                    id = "g70",
                    name = "Genesis G70",
                    type = "Compact Luxury Sedan",
                    tagline = "Pure driving exhilaration, refined.",
                    image = "",
                    startingPrice = "AED 165,000",
                    highlights = listOf("Sport-tuned chassis", "370 HP twin-turbo V6", "RWD option"),
                    competitors = listOf("BMW 3 Series", "Mercedes C-Class", "Audi A4"),
                    specs = mapOf(
                        "performance" to mapOf(
                            "Engine" to "2.0L Turbo I4 / 3.3L Twin-Turbo V6",
                            "Horsepower" to "252 HP / 370 HP",
                        ),
                    ),
                ),
                onBack = {},
                onChat = {},
            )
        }
    }
}
