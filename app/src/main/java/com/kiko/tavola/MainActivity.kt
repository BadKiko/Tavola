package com.kiko.tavola

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import com.kiko.tavola.ui.navigation.TavolaBottomBar
import com.kiko.tavola.ui.navigation.TavolaNavigation
import com.kiko.tavola.ui.settings.ThemeSettings
import com.kiko.tavola.ui.theme.TavolaTheme
import javax.inject.Inject
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    
    @Inject
    lateinit var themeSettings: ThemeSettings
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val useDynamicColor by themeSettings.useDynamicColor.collectAsState(initial = true)
            val scope = rememberCoroutineScope()
            
            TavolaTheme(useDynamicColor = useDynamicColor) {
                val navController = rememberNavController()
                
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        TavolaBottomBar(navController = navController)
                    }
                ) { innerPadding ->
                    TavolaNavigation(
                        navController = navController,
                        useDynamicColor = useDynamicColor,
                        onUseDynamicColorChange = { enabled ->
                            scope.launch { themeSettings.setUseDynamicColor(enabled) }
                        },
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}