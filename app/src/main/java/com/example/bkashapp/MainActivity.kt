package com.example.bkashapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bkashapp.ui.theme.screens.HomeScreen
import com.example.bkashapp.ui.theme.screens.LoginScreen
import com.example.bkashapp.ui.theme.screens.SignUpScreen
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// ==================== SCREENS ====================
sealed class Screen(val route: String) {
    object Login : Screen("login")
    object SignUp : Screen("signup")
    object Home : Screen("home")
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                val navController = rememberNavController()
                val prefManager = remember { PrefManager(this) }
                val scope = rememberCoroutineScope()

                var isLoading by remember { mutableStateOf(false) }
                var globalIsEnglish by remember { mutableStateOf(true) }
                // ✅ NEW: holds whichever phone number was typed on Login or Sign Up
                var globalPhoneNumber by remember { mutableStateOf("") }

                Box(modifier = Modifier.fillMaxSize()) {
                    NavHost(
                        navController = navController,
                        startDestination = Screen.Login.route   // Start with Login Screen
                    ) {
                        // Login Screen
                        composable(Screen.Login.route) {
                            LoginScreen(
                                isEnglish = globalIsEnglish,
                                onLanguageChange = { globalIsEnglish = it },
                                onNextClick = { phone, pin ->
                                    // ✅ NEW: capture the typed phone number
                                    globalPhoneNumber = phone
                                    // TODO: Add your login validation logic here
                                    scope.launch {
                                        isLoading = true
                                        delay(1500)
                                        navController.navigate(Screen.Home.route) {
                                            popUpTo(0) { inclusive = true }
                                        }
                                        isLoading = false
                                    }
                                },
                                onSignUpClick = {
                                    navController.navigate(Screen.SignUp.route)
                                }
                            )
                        }

                        // Sign Up Screen
                        composable(Screen.SignUp.route) {
                            SignUpScreen(
                                isEnglish = globalIsEnglish,
                                onLanguageChange = { globalIsEnglish = it },
                                onNextClick = { phone, pin ->
                                    // ✅ NEW: capture the typed phone number
                                    globalPhoneNumber = phone
                                    // TODO: Save user data and go to Home
                                    prefManager.saveUserPin(pin)   // Example
                                    scope.launch {
                                        isLoading = true
                                        delay(1500)
                                        navController.navigate(Screen.Home.route) {
                                            popUpTo(0) { inclusive = true }
                                        }
                                        isLoading = false
                                    }
                                },
                                onLoginClick = {
                                    navController.popBackStack()   // Back to Login
                                }
                            )
                        }

                        // Home Screen
                        composable(Screen.Home.route) {
                            HomeScreen(
                                isEnglish = globalIsEnglish,
                                onLanguageChange = { globalIsEnglish = it },
                                phoneNumber = globalPhoneNumber,
                                onLogoutClick = {
                                    globalPhoneNumber = ""
                                    navController.navigate(Screen.Login.route) {
                                        popUpTo(0) { inclusive = true }
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

// ==================== PrefManager ====================
class PrefManager(context: android.content.Context) {
    private val prefs =
        context.getSharedPreferences("bkash_prefs", android.content.Context.MODE_PRIVATE)

    fun saveUserPin(pin: String) {
        prefs.edit().putString("user_pin", pin).apply()
    }

    fun getUserPin(): String? = prefs.getString("user_pin", null)
}