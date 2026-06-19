package com.genesis.showroom

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.genesis.showroom.data.ChatRepository
import com.genesis.showroom.data.GenesisConfig
import com.genesis.showroom.data.VehicleRepository
import com.genesis.showroom.data.api.GenesisApiService
import com.genesis.showroom.ui.GenesisApp
import com.genesis.showroom.ui.theme.GenesisBlack
import com.genesis.showroom.ui.theme.GenesisTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val repository = VehicleRepository(applicationContext)
        val chatRepository = ChatRepository(
            GenesisApiService(baseUrl = GenesisConfig.BASE_URL),
        )

        setContent {
            GenesisTheme {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(GenesisBlack),
                ) {
                    GenesisApp(
                        repository = repository,
                        chatRepository = chatRepository,
                    )
                }
            }
        }
    }
}
