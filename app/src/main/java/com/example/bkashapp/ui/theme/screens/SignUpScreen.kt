package com.example.bkashapp.ui.theme.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bkashapp.R
import com.example.bkashapp.ui.theme.BkashPink
import com.example.bkashapp.ui.theme.BkashTurquoise

@Composable
fun SignUpScreen(
    isEnglish: Boolean,
    onLanguageChange: (Boolean) -> Unit,
    onNextClick: (phone: String, pin: String) -> Unit,
    onLoginClick: () -> Unit
) {
    var accountNumber by remember { mutableStateOf("") }
    var pin by remember { mutableStateOf("") }

    val isNumberValid = accountNumber.length == 11
    val isPinValid = pin.length == 5
    val isFormValid = isNumberValid && isPinValid

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .systemBarsPadding()
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, end = 20.dp, start = 20.dp),
            contentAlignment = Alignment.TopEnd
        ) {
            LightLanguageToggle(
                isEnglish = isEnglish,
                onLanguageChange = onLanguageChange
            )
        }

        // ==================== LOGO + QR CODE ROW ====================
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.bkash_bird),
                contentDescription = "bKash Logo",
                modifier = Modifier.size(40.dp)
            )

            Icon(
                imageVector = Icons.Default.QrCode,
                contentDescription = "QR Code",
                tint = BkashPink,
                modifier = Modifier.size(32.dp)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))

        // Main Content
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp)
        ) {
            Text(
                text = if (isEnglish) "Sign Up" else "সাইন আপ",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = if (isEnglish) "Create your bKash account" else "আপনার বিকাশ অ্যাকাউন্ট খুলুন",
                fontSize = 16.sp,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = if (isEnglish) "Account Number" else "অ্যাকাউন্ট নাম্বার",
                color = Color.Gray,
                fontSize = 13.sp
            )

            TextField(
                value = accountNumber,
                onValueChange = {
                    if (it.length <= 11 && it.all { char -> char.isDigit() }) accountNumber = it
                },
                prefix = { Text("+88 ", color = Color.Black) },
                placeholder = { Text("01XXXXXXXXX", color = Color.LightGray) },
                keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(keyboardType = KeyboardType.Number),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White,
                    unfocusedIndicatorColor = Color.LightGray,
                    focusedIndicatorColor = BkashPink
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = if (isEnglish) "bKash PIN" else "বিকাশ পিন",
                color = Color.Gray,
                fontSize = 13.sp
            )
            TextField(
                value = pin,
                onValueChange = {
                    if (it.length <= 5 && it.all { char -> char.isDigit() }) pin = it
                },
                placeholder = {
                    Text(
                        if (isEnglish) "Enter bKash PIN" else "বিকাশ পিন দিন",
                        color = Color.LightGray
                    )
                },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                trailingIcon = {
                    Icon(
                        Icons.Default.Fingerprint,
                        "Fingerprint",
                        tint = BkashTurquoise,
                        modifier = Modifier.size(28.dp)
                    )
                },
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White,
                    unfocusedIndicatorColor = Color.LightGray,
                    focusedIndicatorColor = BkashPink
                ),
                textStyle = androidx.compose.ui.text.TextStyle(
                    fontSize = 20.sp,
                    letterSpacing = 6.sp
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = if (isEnglish) "I have account? Login" else "অ্যাকাউন্ট আছে? লগ ইন করুন",
                color = BkashPink,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.clickable { onLoginClick() }
            )
        }

        // Bottom Button
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(if (isFormValid) BkashPink else Color(0xFFBDBDBD))
                .then(if (isFormValid) Modifier.clickable {
                    onNextClick(
                        accountNumber,
                        pin
                    )
                } else Modifier)
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            Text(
                text = if (isEnglish) "Sign Up" else "সাইন আপ",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterStart)
            )
            Text(
                "→",
                color = Color.White,
                fontSize = 20.sp,
                modifier = Modifier.align(Alignment.CenterEnd)
            )
        }
    }
}

// ==================== PREVIEW ====================
@Preview(device = "id:pixel_5", showSystemUi = true, name = "Sign Up Screen - English")
@Composable
fun PreviewSignUpEnglish() {
    MaterialTheme {
        SignUpScreen(
            isEnglish = true,
            onLanguageChange = {},
            onNextClick = { _, _ -> },
            onLoginClick = {}
        )
    }
}

@Preview(device = "id:pixel_5", showSystemUi = true, name = "Sign Up Screen - Bangla")
@Composable
fun PreviewSignUpBangla() {
    MaterialTheme {
        SignUpScreen(
            isEnglish = false,
            onLanguageChange = {},
            onNextClick = { _, _ -> },
            onLoginClick = {}
        )
    }
}