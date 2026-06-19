package com.genesis.showroom.ui.screens.vehicles

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.genesis.showroom.data.Vehicle
import com.genesis.showroom.data.VehicleFilter
import com.genesis.showroom.data.VehicleRepository
import com.genesis.showroom.ui.components.vehicles.VehicleCard
import com.genesis.showroom.ui.language.GenesisLanguage
import com.genesis.showroom.ui.language.GenesisLanguageProvider
import com.genesis.showroom.ui.theme.GenesisBlack
import com.genesis.showroom.ui.theme.GenesisBorder
import com.genesis.showroom.ui.theme.GenesisCopper
import com.genesis.showroom.ui.theme.GenesisFontFamily
import com.genesis.showroom.ui.theme.GenesisMuted
import com.genesis.showroom.ui.theme.GenesisSilver
import com.genesis.showroom.ui.theme.GenesisTheme

@Composable
fun VehicleExplorerScreen(
    repository: VehicleRepository,
    onSelectVehicle: (Vehicle) -> Unit,
    modifier: Modifier = Modifier,
) {
    val isArabic = GenesisLanguage.isArabic
    val language = GenesisLanguage.current
    var activeFilter by remember { mutableStateOf(VehicleFilter.ALL) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(GenesisBlack)
            .statusBarsPadding()
            .navigationBarsPadding(),
    ) {
        VehicleExplorerHeader(isArabic = isArabic)

        VehicleFilterRow(
            activeFilter = activeFilter,
            onFilterSelected = { activeFilter = it },
            language = language,
        )

        AnimatedContent(
            targetState = activeFilter,
            modifier = Modifier.weight(1f),
            transitionSpec = {
                fadeIn(tween(200)) togetherWith fadeOut(tween(200))
            },
            label = "vehicleGrid",
        ) { filter ->
            val vehicles = repository.filterVehicles(filter)

            if (vehicles.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 40.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = if (isArabic) "لم يتم العثور على سيارات" else "No vehicles found",
                        fontFamily = GenesisFontFamily.Body,
                        fontWeight = FontWeight.Normal,
                        fontSize = 18.sp,
                        color = GenesisMuted,
                    )
                }
            } else {
                ResponsiveVehicleGrid(
                    vehicles = vehicles,
                    onSelectVehicle = onSelectVehicle,
                )
            }
        }

        VehicleExplorerStatsBar(isArabic = isArabic)
    }
}

@Composable
private fun VehicleExplorerHeader(isArabic: Boolean) {
    val alpha by animateFloatAsState(
        targetValue = 1f,
        animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing),
        label = "headerAlpha",
    )
    val offsetY by animateFloatAsState(
        targetValue = 0f,
        animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing),
        label = "headerOffset",
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .graphicsLayer {
                this.alpha = alpha
                translationY = offsetY - 16f * (1f - alpha)
            }
            .padding(horizontal = 40.dp)
            .padding(top = 64.dp, bottom = 24.dp),
    ) {
        Text(
            text = if (isArabic) "المجموعة الكاملة" else "Complete Lineup",
            fontFamily = GenesisFontFamily.Body,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            letterSpacing = 3.6.sp,
            color = GenesisCopper,
        )
        Text(
            text = if (isArabic) "اختر سيارتك" else "Select Your Genesis",
            modifier = Modifier.padding(top = 8.dp),
            fontFamily = GenesisFontFamily.Display,
            fontWeight = FontWeight.SemiBold,
            fontSize = 30.sp,
            color = GenesisSilver,
        )
    }
}

@Composable
private fun VehicleFilterRow(
    activeFilter: VehicleFilter,
    onFilterSelected: (VehicleFilter) -> Unit,
    language: com.genesis.showroom.data.AppLanguage,
) {
    val alpha by animateFloatAsState(
        targetValue = 1f,
        animationSpec = tween(durationMillis = 500, delayMillis = 200, easing = FastOutSlowInEasing),
        label = "filterAlpha",
    )

    Row(
        modifier = Modifier
            .graphicsLayer { this.alpha = alpha }
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
            .padding(horizontal = 40.dp)
            .padding(bottom = 24.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        VehicleFilter.entries.forEach { filter ->
            VehicleFilterChip(
                label = filter.label(language),
                selected = activeFilter == filter,
                onClick = { onFilterSelected(filter) },
            )
        }
    }
}

@Composable
private fun VehicleFilterChip(
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
) {
    val backgroundColor = if (selected) GenesisCopper else GenesisBlack
    val contentColor = if (selected) GenesisBlack else GenesisMuted
    val borderModifier = if (selected) {
        Modifier
    } else {
        Modifier.border(
            width = GenesisTheme.spacing.borderWidthSubtle,
            color = GenesisBorder,
            shape = GenesisTheme.shapes.pill,
        )
    }

    Text(
        text = label.uppercase(),
        modifier = Modifier
            .then(borderModifier)
            .background(backgroundColor, GenesisTheme.shapes.pill)
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 6.dp),
        fontFamily = GenesisFontFamily.Body,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        letterSpacing = 1.2.sp,
        color = contentColor,
    )
}

@Composable
private fun ResponsiveVehicleGrid(
    vehicles: List<Vehicle>,
    onSelectVehicle: (Vehicle) -> Unit,
) {
    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val columns = when {
            maxWidth >= 900.dp -> 3
            maxWidth >= 600.dp -> 2
            else -> 1
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(columns),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 40.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            itemsIndexed(
                items = vehicles,
                key = { _, vehicle -> vehicle.id },
            ) { index, vehicle ->
                VehicleCard(
                    vehicle = vehicle,
                    index = index,
                    onClick = onSelectVehicle,
                )
            }
        }
    }
}

@Composable
private fun VehicleExplorerStatsBar(isArabic: Boolean) {
    val alpha by animateFloatAsState(
        targetValue = 1f,
        animationSpec = tween(durationMillis = 500, delayMillis = 400, easing = FastOutSlowInEasing),
        label = "statsAlpha",
    )
    val offsetY by animateFloatAsState(
        targetValue = 0f,
        animationSpec = tween(durationMillis = 500, delayMillis = 400, easing = FastOutSlowInEasing),
        label = "statsOffset",
    )

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
            .graphicsLayer {
                this.alpha = alpha
                translationY = offsetY + 16f * (1f - alpha)
            }
            .border(
                width = GenesisTheme.spacing.borderWidthSubtle,
                color = GenesisBorder,
            )
            .padding(horizontal = 40.dp, vertical = 16.dp),
    ) {
        val hint = if (isArabic) {
            "انقر على أي سيارة لعرض المواصفات الكاملة"
        } else {
            "Tap any vehicle to view full specifications"
        }

        if (maxWidth < 600.dp) {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Row(horizontalArrangement = Arrangement.spacedBy(32.dp)) {
                    StatItem(value = "9", label = if (isArabic) "موديلات" else "Models")
                    StatItem(value = "3", label = if (isArabic) "كهربائية" else "Electric")
                    StatItem(value = if (isArabic) "5 سنوات" else "5yr", label = if (isArabic) "ضمان" else "Warranty")
                }
                Text(
                    text = hint,
                    fontFamily = GenesisFontFamily.Body,
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp,
                    color = GenesisMuted,
                )
            }
        } else {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(32.dp)) {
                    StatItem(value = "9", label = if (isArabic) "موديلات" else "Models")
                    StatItem(value = "3", label = if (isArabic) "كهربائية" else "Electric")
                    StatItem(value = if (isArabic) "5 سنوات" else "5yr", label = if (isArabic) "ضمان" else "Warranty")
                }
                Text(
                    text = hint,
                    modifier = Modifier.padding(start = 24.dp),
                    fontFamily = GenesisFontFamily.Body,
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp,
                    color = GenesisMuted,
                )
            }
        }
    }
}

@Composable
private fun StatItem(value: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = value,
            fontFamily = GenesisFontFamily.Body,
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp,
            color = GenesisCopper,
        )
        Text(
            text = label.uppercase(),
            fontFamily = GenesisFontFamily.Body,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            letterSpacing = 1.2.sp,
            color = GenesisMuted,
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF0A0A0A, device = "spec:parent=pixel_5")
@Composable
private fun VehicleExplorerScreenPreview() {
    GenesisTheme {
        GenesisLanguageProvider {
            // Preview requires a repository — show header only via placeholder
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(GenesisBlack),
            ) {
                VehicleExplorerHeader(isArabic = false)
                VehicleFilterRow(
                    activeFilter = VehicleFilter.ALL,
                    onFilterSelected = {},
                    language = com.genesis.showroom.data.AppLanguage.EN,
                )
                Spacer(modifier = Modifier.weight(1f))
                VehicleExplorerStatsBar(isArabic = false)
            }
        }
    }
}
