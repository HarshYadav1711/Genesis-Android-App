package com.genesis.showroom.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.dp
import com.genesis.showroom.data.AppScreen
import com.genesis.showroom.data.Vehicle
import com.genesis.showroom.data.VehicleRepository
import com.genesis.showroom.ui.components.LanguageToggle
import com.genesis.showroom.ui.overlay.GenesisAiOverlay
import com.genesis.showroom.ui.language.GenesisLanguage
import com.genesis.showroom.ui.language.GenesisLanguageProvider
import com.genesis.showroom.ui.screens.specboard.SpecBoardScreen
import com.genesis.showroom.ui.screens.vehicles.VehicleExplorerScreen
import com.genesis.showroom.ui.screens.welcome.WelcomeScreen

@Composable
fun GenesisApp(
    repository: VehicleRepository,
    modifier: Modifier = Modifier,
) {
    GenesisLanguageProvider {
        var screen by remember { mutableStateOf(AppScreen.WELCOME) }
        var selectedVehicle by remember { mutableStateOf<Vehicle?>(null) }
        var isChatOpen by remember { mutableStateOf(false) }

        Box(modifier = modifier.fillMaxSize()) {
            AnimatedContent(
                targetState = screen,
                modifier = Modifier.fillMaxSize(),
                transitionSpec = {
                    if (targetState.ordinal > initialState.ordinal) {
                        slideInHorizontally { it / 3 } + fadeIn() togetherWith
                            slideOutHorizontally { -it / 3 } + fadeOut()
                    } else {
                        slideInHorizontally { -it / 3 } + fadeIn() togetherWith
                            slideOutHorizontally { it / 3 } + fadeOut()
                    }
                },
                label = "appScreen",
            ) { currentScreen ->
                when (currentScreen) {
                    AppScreen.WELCOME -> WelcomeScreen(
                        onExplore = { screen = AppScreen.VEHICLES },
                        onChat = { isChatOpen = true },
                    )
                    AppScreen.VEHICLES -> VehicleExplorerScreen(
                        repository = repository,
                        onSelectVehicle = { vehicle ->
                            selectedVehicle = vehicle
                            screen = AppScreen.SPEC_BOARD
                        },
                    )
                    AppScreen.SPEC_BOARD -> {
                        val vehicle = selectedVehicle
                        if (vehicle != null) {
                            SpecBoardScreen(
                                vehicle = vehicle,
                                onBack = { screen = AppScreen.VEHICLES },
                                onChat = { isChatOpen = true },
                            )
                        }
                    }
                }
            }

            GenesisAiOverlay(
                isOpen = isChatOpen,
                onClose = { isChatOpen = false },
                currentVehicle = selectedVehicle,
            )

            if (screen != AppScreen.SPEC_BOARD && !isChatOpen) {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .statusBarsPadding()
                        .padding(top = 24.dp, end = 24.dp),
                ) {
                    CompositionLocalProvider(
                        LocalLayoutDirection provides GenesisLanguage.layoutDirection,
                    ) {
                        LanguageToggle()
                    }
                }
            }
        }
    }
}
