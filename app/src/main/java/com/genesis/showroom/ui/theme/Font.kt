package com.genesis.showroom.ui.theme

import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight

/**
 * Genesis typography families.
 *
 * Drop Playfair Display and Inter `.ttf` files into `res/font/` and wire them here
 * for pixel-accurate parity with the Genesis website.
 */
object GenesisFontFamily {
    val Display = FontFamily.Serif
    val Body = FontFamily.SansSerif

    val DisplayLight = FontWeight.Light
    val DisplayRegular = FontWeight.Normal
    val DisplaySemiBold = FontWeight.SemiBold
    val DisplayBold = FontWeight.Bold

    val BodyRegular = FontWeight.Normal
    val BodyMedium = FontWeight.Medium
    val BodySemiBold = FontWeight.SemiBold

    val Italic = FontStyle.Italic
}
