package com.genesis.showroom.ui.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.genesis.showroom.ui.theme.GenesisCopper
import com.genesis.showroom.ui.theme.GenesisFontFamily
import com.genesis.showroom.ui.theme.GenesisGold

@Composable
fun GenesisLogo(modifier: Modifier = Modifier) {
    Text(
        text = "GENESIS",
        modifier = modifier,
        fontFamily = GenesisFontFamily.Display,
        fontWeight = FontWeight.SemiBold,
        fontSize = 48.sp,
        letterSpacing = 12.sp,
        style = TextStyle(
            brush = Brush.linearGradient(
                colors = listOf(GenesisCopper, GenesisGold, GenesisCopper),
            ),
        ),
    )
}
