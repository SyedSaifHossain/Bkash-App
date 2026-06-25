package com.example.bkashapp.ui.theme.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Backspace
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bkashapp.ui.theme.BrandPink
import com.example.bkashapp.ui.theme.DeepPlum
import com.example.bkashapp.ui.theme.LightGray
import com.example.bkashapp.ui.theme.NeutralGray
import com.example.bkashapp.ui.theme.SoftPinkTint

/**
 * Screen 2: PIN Login Screen
 */
@Composable
fun PinLoginScreen(
    phoneNumber: String = "+880 1XXX-XXXXXX",
    pinLength: Int = 5,
    onPinComplete: (String) -> Unit = {}
) {
    var pin by remember { mutableStateOf("") }

    fun onDigit(d: String) {
        if (pin.length < pinLength) {
            pin += d
            if (pin.length == pinLength) onPinComplete(pin)
        }
    }

    fun onBackspace() {
        if (pin.isNotEmpty()) pin = pin.dropLast(1)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 24.dp)
    ) {
        Spacer(modifier = Modifier.height(64.dp))

        Text(
            text = "Welcome back",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = DeepPlum
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Enter your PIN to continue",
            fontSize = 14.sp,
            color = NeutralGray
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = phoneNumber,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = BrandPink
        )

        Spacer(modifier = Modifier.height(48.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            for (i in 0 until pinLength) {
                val filled = i < pin.length
                Box(
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .size(18.dp)
                        .background(
                            color = if (filled) BrandPink else LightGray,
                            shape = CircleShape
                        )
                )
            }
        }

        Spacer(modifier = Modifier.height(56.dp))

        val rows = listOf(
            listOf("1", "2", "3"),
            listOf("4", "5", "6"),
            listOf("7", "8", "9"),
            listOf("", "0", "back")
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            rows.forEach { row ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    row.forEach { key ->
                        KeypadKey(
                            label = key,
                            onClick = {
                                when (key) {
                                    "back" -> onBackspace()
                                    "" -> { /* empty slot */ }
                                    else -> onDigit(key)
                                }
                            }
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Forgot PIN?",
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = BrandPink,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { /* TODO: PIN recovery flow */ },
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
    }
}

@Composable
private fun KeypadKey(label: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(64.dp)
            .clickable(enabled = label.isNotEmpty()) { onClick() }
            .background(
                color = if (label.isNotEmpty()) SoftPinkTint else Color.Transparent,
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        when (label) {
            "back" -> Icon(
                imageVector = Icons.AutoMirrored.Filled.Backspace,
                contentDescription = "Delete",
                tint = DeepPlum
            )
            "" -> { /* empty */ }
            else -> Text(
                text = label,
                fontSize = 22.sp,
                fontWeight = FontWeight.Medium,
                color = DeepPlum
            )
        }
    }
}