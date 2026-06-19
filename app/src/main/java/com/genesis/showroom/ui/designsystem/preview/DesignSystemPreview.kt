package com.genesis.showroom.ui.designsystem.preview

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.genesis.showroom.ui.designsystem.component.GenesisElevatedCard
import com.genesis.showroom.ui.designsystem.component.GenesisFeatureCard
import com.genesis.showroom.ui.designsystem.component.GenesisGoldButton
import com.genesis.showroom.ui.designsystem.component.GenesisOutlineButton
import com.genesis.showroom.ui.designsystem.component.GenesisOutlinedCard
import com.genesis.showroom.ui.designsystem.component.GenesisPrimaryButton
import com.genesis.showroom.ui.designsystem.component.GenesisSecondaryButton
import com.genesis.showroom.ui.designsystem.component.GenesisSurfaceCard
import com.genesis.showroom.ui.designsystem.component.GenesisTextButton
import com.genesis.showroom.ui.theme.GenesisDisplayAccent
import com.genesis.showroom.ui.theme.GenesisTheme

@Preview(showBackground = true, backgroundColor = 0xFF0A0A0A)
@Composable
private fun GenesisDesignSystemPreview() {
    GenesisTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(GenesisTheme.spacing.screenHorizontal),
            verticalArrangement = Arrangement.spacedBy(GenesisTheme.spacing.lg),
        ) {
            Text(
                text = "Genesis",
                style = GenesisDisplayAccent,
                color = MaterialTheme.colorScheme.onBackground,
            )
            Text(
                text = "Design System",
                style = MaterialTheme.typography.headlineMedium,
                color = GenesisTheme.extendedColors.copper,
            )

            Column(verticalArrangement = Arrangement.spacedBy(GenesisTheme.spacing.sm)) {
                GenesisPrimaryButton(text = "Explore", onClick = {}, modifier = Modifier.fillMaxWidth())
                GenesisSecondaryButton(text = "Configure", onClick = {}, modifier = Modifier.fillMaxWidth())
                GenesisOutlineButton(text = "Learn More", onClick = {}, modifier = Modifier.fillMaxWidth())
                GenesisGoldButton(text = "Reserve", onClick = {}, modifier = Modifier.fillMaxWidth())
                GenesisTextButton(text = "View details", onClick = {})
            }

            GenesisSurfaceCard(modifier = Modifier.fillMaxWidth()) {
                Text("Surface Card", style = MaterialTheme.typography.titleMedium)
                Text("Panel background with subtle border.", style = MaterialTheme.typography.bodyMedium)
            }

            GenesisOutlinedCard(onClick = {}, modifier = Modifier.fillMaxWidth()) {
                Text("Outlined Card", style = MaterialTheme.typography.titleMedium)
                Text("Clickable tile with minimal outline.", style = MaterialTheme.typography.bodyMedium)
            }

            GenesisFeatureCard(modifier = Modifier.fillMaxWidth()) {
                Text("Feature Card", style = MaterialTheme.typography.titleMedium)
                Text("Copper-to-gold accent bar for highlights.", style = MaterialTheme.typography.bodyMedium)
            }

            GenesisElevatedCard(modifier = Modifier.fillMaxWidth()) {
                Text("Elevated Card", style = MaterialTheme.typography.titleMedium)
                Text("Floating panel with restrained elevation.", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}
