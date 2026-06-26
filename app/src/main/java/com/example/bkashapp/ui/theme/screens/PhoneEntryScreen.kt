package com.example.bkashapp.ui.theme.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bkashapp.R
import com.example.bkashapp.ui.theme.BkashPink

@Composable
fun PhoneEntryScreen(
    isEnglish: Boolean,
    onLanguageChange: (Boolean) -> Unit,
    onBackClick: () -> Unit,
    onNextClick: (String) -> Unit
) {
    var phoneNumber by remember { mutableStateOf("") }
    val isNumberValid = phoneNumber.length == 11

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .systemBarsPadding()
    ) {
        BkashTopBar(
            onBackClick = onBackClick,
            showLanguageToggle = true,
            isEnglish = isEnglish,
            onLanguageChange = onLanguageChange
        )

        Column(modifier = Modifier.weight(1f).padding(24.dp)) {
            Image(
                painter = painterResource(id = R.drawable.ic_bkash_logo),
                contentDescription = null,
                modifier = Modifier.size(60.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = if (isEnglish) "Log In / Register\nwith Mobile Number" else "মোবাইল নাম্বার দিয়ে\nলগ ইন / রেজিস্ট্রেশন করুন",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(32.dp))
            Text(text = if (isEnglish) "Mobile Number" else "মোবাইল নাম্বার", color = Color.Gray)

            TextField(
                value = phoneNumber,
                onValueChange = { if (it.length <= 11) phoneNumber = it },
                prefix = { Text("+88  ", fontSize = 20.sp) },
                colors = TextFieldDefaults.colors(
                    unfocusedIndicatorColor = if (phoneNumber.isNotEmpty() && !isNumberValid) Color.Red else BkashPink,
                    focusedIndicatorColor = if (phoneNumber.isNotEmpty() && !isNumberValid) Color.Red else BkashPink
                ),
                textStyle = androidx.compose.ui.text.TextStyle(fontSize = 20.sp),
                modifier = Modifier.fillMaxWidth()
            )

            if (phoneNumber.isNotEmpty() && !isNumberValid) {
                Text(
                    text = if (isEnglish) "Number is not valid" else "সঠিক নাম্বার দিন",
                    color = Color.Red,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }

        BkashNumberPad(
            isEnglish = isEnglish,
            isNextEnabled = isNumberValid,
            onNumberClick = { if (phoneNumber.length < 11) phoneNumber += it },
            onBackspaceClick = { if (phoneNumber.isNotEmpty()) phoneNumber = phoneNumber.dropLast(1) },
            onNextClick = { if (isNumberValid) onNextClick(phoneNumber) }
        )
    }
}

@Preview(device = "id:pixel_5", showSystemUi = true, name = "Phone Entry - English")
@Composable
fun PreviewPhoneEntryEnglish() {
    MaterialTheme {
        PhoneEntryScreen(
            isEnglish = true,
            onLanguageChange = {},
            onBackClick = {},
            onNextClick = {}
        )
    }
}

@Preview(device = "id:pixel_5", showSystemUi = true, name = "Phone Entry - Bangla")
@Composable
fun PreviewPhoneEntryBangla() {
    MaterialTheme {
        PhoneEntryScreen(
            isEnglish = false,
            onLanguageChange = {},
            onBackClick = {},
            onNextClick = {}
        )
    }
}