package com.genesis.showroom.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.genesis.showroom.ui.components.LanguageToggle
import com.genesis.showroom.ui.language.GenesisLanguage
import com.genesis.showroom.ui.language.GenesisLanguageProvider
import com.genesis.showroom.ui.screens.welcome.WelcomeScreen

@Composable
fun GenesisApp(
    modifier: Modifier = Modifier,
    onExplore: () -> Unit = {},
    onChat: () -> Unit = {},
) {
    GenesisLanguageProvider {
        Box(modifier = modifier.fillMaxSize()) {
            WelcomeScreen(
                onExplore = onExplore,
                onChat = onChat,
            )

            // Physical top-right placement (matches website `top-6 right-6`).
            // Segment order mirrors via nested RTL direction, same as the web toggle inside `dir=rtl`.
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
