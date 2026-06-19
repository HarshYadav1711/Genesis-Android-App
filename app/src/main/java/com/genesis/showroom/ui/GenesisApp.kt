package com.genesis.showroom.ui

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
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.genesis.showroom.data.AppLanguage
import com.genesis.showroom.ui.components.LanguageToggle
import com.genesis.showroom.ui.screens.welcome.WelcomeScreen

@Composable
fun GenesisApp(
    modifier: Modifier = Modifier,
    onExplore: () -> Unit = {},
    onChat: () -> Unit = {},
) {
    var language by remember { mutableStateOf(AppLanguage.EN) }
    val layoutDirection = if (language.isArabic) LayoutDirection.Rtl else LayoutDirection.Ltr

    CompositionLocalProvider(LocalLayoutDirection provides layoutDirection) {
        Box(modifier = modifier.fillMaxSize()) {
            WelcomeScreen(
                language = language,
                onExplore = onExplore,
                onChat = onChat,
            )

            LanguageToggle(
                language = language,
                onLanguageChange = { language = it },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .statusBarsPadding()
                    .padding(top = 24.dp, end = 24.dp),
            )
        }
    }
}
