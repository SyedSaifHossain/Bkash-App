package com.example.bkashapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.bkashapp.ui.theme.BkashAppTheme
import com.example.bkashapp.ui.theme.screens.HomeScreen
import com.example.bkashapp.ui.theme.screens.PinLoginScreen
import com.example.bkashapp.ui.theme.screens.SplashScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BkashAppTheme {
                AppRoot()
            }
        }
    }
}

private enum class Screen { SPLASH, LOGIN, HOME }

@Composable
fun AppRoot() {
    var current by remember { mutableStateOf(Screen.SPLASH) }

    Surface(modifier = Modifier.fillMaxSize()) {
        when (current) {
            Screen.SPLASH -> SplashScreen(onTimeout = { current = Screen.LOGIN })
            Screen.LOGIN -> PinLoginScreen(onPinComplete = { current = Screen.HOME })
            Screen.HOME -> HomeScreen()
        }
    }
}

@Preview(showBackground = true, name = "1. Splash")
@Composable
fun SplashPreview() {
    BkashAppTheme { SplashScreen() }
}

@Preview(showBackground = true, name = "2. PIN Login")
@Composable
fun LoginPreview() {
    BkashAppTheme { PinLoginScreen() }
}

@Preview(showBackground = true, name = "3. Home", heightDp = 800)
@Composable
fun HomePreview() {
    BkashAppTheme { HomeScreen() }
}