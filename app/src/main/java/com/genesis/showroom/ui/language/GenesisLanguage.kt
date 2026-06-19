package com.genesis.showroom.ui.language

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import com.genesis.showroom.data.AppLanguage

@Stable
class GenesisLanguageState(
    initial: AppLanguage = AppLanguage.EN,
) {
    var language by mutableStateOf(initial)
        private set

    val isArabic: Boolean get() = language.isArabic

    val layoutDirection: LayoutDirection
        get() = if (isArabic) LayoutDirection.Rtl else LayoutDirection.Ltr

    fun selectLanguage(language: AppLanguage) {
        this.language = language
    }
}

val LocalGenesisLanguage = staticCompositionLocalOf<GenesisLanguageState> {
    error("GenesisLanguageState not provided. Wrap content in GenesisLanguageProvider.")
}

/** Global language accessors — use anywhere below [GenesisLanguageProvider]. */
object GenesisLanguage {
    val current: AppLanguage
        @Composable
        get() = LocalGenesisLanguage.current.language

    val isArabic: Boolean
        @Composable
        get() = LocalGenesisLanguage.current.isArabic

    val layoutDirection: LayoutDirection
        @Composable
        get() = LocalGenesisLanguage.current.layoutDirection

    @Composable
    fun selectLanguage(language: AppLanguage) {
        LocalGenesisLanguage.current.selectLanguage(language)
    }
}

/**
 * Provides app-wide language state, RTL layout direction, and mirrored layouts.
 *
 * Mirrors the website's `dir={language === 'ar' ? 'rtl' : 'ltr'}` on the root container.
 */
@Composable
fun GenesisLanguageProvider(
    content: @Composable () -> Unit,
) {
    val languageState = remember { GenesisLanguageState() }

    CompositionLocalProvider(
        LocalGenesisLanguage provides languageState,
        LocalLayoutDirection provides languageState.layoutDirection,
    ) {
        content()
    }
}
