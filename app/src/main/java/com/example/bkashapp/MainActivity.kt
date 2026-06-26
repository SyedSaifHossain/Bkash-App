package com.example.bkashapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay
import com.example.bkashapp.ui.theme.screens.HomeScreen
import com.example.bkashapp.ui.theme.screens.LoginPinScreen
import com.example.bkashapp.ui.theme.screens.PhoneEntryScreen
import kotlinx.coroutines.launch


// The bKash Pink Color
val BkashPink = Color(0xFFE2136E)

// All the screens in your screenshots
sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object LoginRegChoice : Screen("login_reg_choice")
    object PhoneEntry : Screen("phone_entry")
    object NameEntry : Screen("name_entry")

    object PinSetup : Screen("pin_setup")
    object ProfilePicEntry : Screen("profile_pic")
    object LoginPin : Screen("login_pin")
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

                // Global States
                var isLoading by remember { mutableStateOf(false) }
                var globalIsEnglish by remember { mutableStateOf(false) }
                var globalRegisteredPhone by remember { mutableStateOf("") }

                // Root Container to allow Overlay on top of NavHost
                Box(modifier = Modifier.fillMaxSize()) {
                    NavHost(
                        navController = navController,
                        startDestination = Screen.Splash.route
                    ) {
                        // 1. Phone Entry
                        composable(Screen.PhoneEntry.route) {
                            PhoneEntryScreen(
                                isEnglish = globalIsEnglish,
                                onLanguageChange = { globalIsEnglish = it },
                                onBackClick = { navController.popBackStack() },
                                onNextClick = { phoneNum ->
                                    globalRegisteredPhone = phoneNum
                                    // Logic to check if user already exists in your "backend"
                                    if (phoneNum == "01712345678") {
                                        navController.navigate("${Screen.LoginPin.route}/$phoneNum")
                                    } else {
                                        navController.navigate(Screen.NameEntry.route)
                                    }
                                }
                            )
                        }

                        // 2. Login PIN Screen (With Loading Transition)
                        composable("${Screen.LoginPin.route}/{phone}") { backStackEntry ->
                            val phone = backStackEntry.arguments?.getString("phone") ?: ""
                            val savedPin = prefManager.getUserPin()

                            LoginPinScreen(
                                phoneNumber = phone,
                                correctPin = savedPin,
                                isEnglish = globalIsEnglish,
                                onLanguageChange = { globalIsEnglish = it },
                                onBackClick = { navController.popBackStack() },
                                onNextClick = {
                                    // Trigger Loading sequence
                                    scope.launch {
                                        isLoading = true
                                        delay(2000) // 2 second delay for your GIF
                                        navController.navigate(Screen.Home.route) {
                                            popUpTo(0) { inclusive = true }
                                        }
                                        isLoading = false
                                    }
                                }
                            )
                        }

                        // 3. Home Screen
                        composable(Screen.Home.route) {
                            HomeScreen(
                                isEnglish = globalIsEnglish,
                                onLanguageChange = { globalIsEnglish = it }
                            )
                        }
                    }
                }
            }
        }
    }
}

//toggle lang
@Composable
fun LanguageToggle(
    isEnglish: Boolean,
    onLanguageChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .background(BkashPink, RoundedCornerShape(20.dp))
            .border(1.dp, Color.White, RoundedCornerShape(20.dp))
            .padding(2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // English Option
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(18.dp))
                .clickable { onLanguageChange(true) }
                .background(if (isEnglish) Color.White else Color.Transparent)
                .padding(horizontal = 16.dp, vertical = 6.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Eng",
                color = if (isEnglish) BkashPink else Color.White,
                fontSize = 14.sp,
                fontWeight = if (isEnglish) FontWeight.Bold else FontWeight.Medium
            )
        }

        // Bangla Option
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(18.dp))
                .clickable { onLanguageChange(false) }
                .background(if (!isEnglish) Color.White else Color.Transparent)
                .padding(horizontal = 16.dp, vertical = 6.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "বাং",
                color = if (!isEnglish) BkashPink else Color.White,
                fontSize = 14.sp,
                fontWeight = if (!isEnglish) FontWeight.Bold else FontWeight.Medium
            )
        }
    }
}

// --- COMPONENT PREVIEWS ---

@Preview(showBackground = true, name = "Toggle - English")
@Composable
fun PreviewLanguageToggleEng() {
    Box(Modifier.padding(20.dp)) {
        LanguageToggle(isEnglish = true, onLanguageChange = {})
    }
}

@Preview(showBackground = true, name = "Toggle - Bangla")
@Composable
fun PreviewLanguageToggleBng() {
    Box(Modifier.padding(20.dp)) {
        LanguageToggle(isEnglish = false, onLanguageChange = {})
    }
}


// Put this at the very bottom of MainActivity.kt
class PrefManager(context: android.content.Context) {
    private val prefs = context.getSharedPreferences("bkash_prefs", android.content.Context.MODE_PRIVATE)

    fun saveUserPhone(phone: String) {
        prefs.edit().putString("registered_phone", phone).apply()
    }

    fun getUserPhone(): String? = prefs.getString("registered_phone", null)

    // ✅ ADD THESE TWO FUNCTIONS
    fun saveUserPin(pin: String) {
        prefs.edit().putString("user_pin", pin).apply()
    }

    fun getUserPin(): String? = prefs.getString("user_pin", null)
}




// --- UPDATED FULL SCREEN PREVIEWS ---

@Preview(device = "id:pixel_5", showSystemUi = true, name = "3. Phone Entry")
@Composable
fun PreviewPhoneEntry() {
    MaterialTheme {
        PhoneEntryScreen(
            isEnglish = true,
            onLanguageChange = {},
            onBackClick = {},
            onNextClick = {}
        )
    }
}

@Preview(device = "id:pixel_5", showSystemUi = true, name = "7. Login PIN Screen")
@Composable
fun PreviewLoginPin() {
    MaterialTheme {
        LoginPinScreen(
            phoneNumber = "01712345678",
            correctPin = "12345",
            isEnglish = true,
            onLanguageChange = {},
            onBackClick = {},
            onNextClick = {}
        )
    }
}

@Preview(device = "id:pixel_5", showSystemUi = true, name = "8. Home Screen")
@Composable
fun PreviewHome() {
    MaterialTheme {
        HomeScreen(
            isEnglish = true,
            onLanguageChange = {}
        )
    }
}