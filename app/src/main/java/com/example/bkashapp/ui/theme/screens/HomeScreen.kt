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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.LocalGasStation
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material.icons.filled.SendToMobile
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bkashapp.ui.theme.BrandPink
import com.example.bkashapp.ui.theme.BrandPinkDark
import com.example.bkashapp.ui.theme.DeepPlum
import com.example.bkashapp.ui.theme.DividerGray
import com.example.bkashapp.ui.theme.NeutralGray
import com.example.bkashapp.ui.theme.SoftPinkTint
import com.example.bkashapp.ui.theme.SuccessGreen

data class ServiceAction(val label: String, val icon: ImageVector)
data class Transaction(val title: String, val subtitle: String, val amount: String, val isCredit: Boolean)

/**
 * Screen 3: Home / Dashboard Screen
 */
@Composable
fun HomeScreen(
    userName: String = "Rafiq",
    balance: String = "12,450.75"
) {
    var balanceVisible by remember { mutableStateOf(true) }

    val services = listOf(
        ServiceAction("Send Money", Icons.Filled.SendToMobile),
        ServiceAction("Mobile Recharge", Icons.Filled.PhoneAndroid),
        ServiceAction("Pay Bill", Icons.Filled.Receipt),
        ServiceAction("Fuel Pay", Icons.Filled.LocalGasStation)
    )

    val transactions = listOf(
        Transaction("Cash Out", "Agent: Karim Store", "- 1,000.00 Tk", isCredit = false),
        Transaction("Mobile Recharge", "Grameenphone · 01711XXXXXX", "- 100.00 Tk", isCredit = false),
        Transaction("Received Money", "From: Anika Rahman", "+ 2,500.00 Tk", isCredit = true),
        Transaction("Bill Payment", "DESCO Electricity", "- 845.00 Tk", isCredit = false)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(BrandPink)
                .padding(horizontal = 20.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(text = "Hello, $userName", color = Color.White, fontSize = 14.sp)
                Text(
                    text = "Good afternoon",
                    color = Color.White.copy(alpha = 0.85f),
                    fontSize = 12.sp
                )
            }
            Icon(
                imageVector = Icons.Filled.NotificationsNone,
                contentDescription = "Notifications",
                tint = Color.White
            )
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(16.dp))
                BalanceCard(
                    balance = balance,
                    visible = balanceVisible,
                    onToggleVisibility = { balanceVisible = !balanceVisible }
                )
                Spacer(modifier = Modifier.height(24.dp))
                ServiceGrid(services)
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "Recent Activity",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = DeepPlum
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
            items(transactions) { tx ->
                TransactionRow(tx)
            }
            item { Spacer(modifier = Modifier.height(24.dp)) }
        }
    }
}

@Composable
private fun BalanceCard(
    balance: String,
    visible: Boolean,
    onToggleVisibility: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .background(
                brush = Brush.linearGradient(listOf(BrandPink, BrandPinkDark)),
                shape = RoundedCornerShape(20.dp)
            )
            .padding(20.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Text(
                text = "Available Balance",
                color = Color.White.copy(alpha = 0.85f),
                fontSize = 13.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = if (visible) "Tk $balance" else "Tk ••••••",
                    color = Color.White,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(10.dp))
                Icon(
                    imageVector = if (visible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                    contentDescription = "Toggle balance visibility",
                    tint = Color.White,
                    modifier = Modifier
                        .size(20.dp)
                        .clickable { onToggleVisibility() }
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Row {
                QuickStat(icon = Icons.Filled.ArrowUpward, label = "Sent this month", value = "Tk 4,200")
                Spacer(modifier = Modifier.width(24.dp))
                QuickStat(icon = Icons.Filled.ArrowDownward, label = "Received", value = "Tk 6,500")
            }
        }
    }
}

@Composable
private fun QuickStat(icon: ImageVector, label: String, value: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color.White.copy(alpha = 0.8f),
            modifier = Modifier.size(14.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = value, color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Medium)
    }
}

@Composable
private fun ServiceGrid(services: List<ServiceAction>) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        services.forEach { service ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.clickable { /* TODO: navigate */ }
            ) {
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .background(SoftPinkTint, shape = CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(imageVector = service.icon, contentDescription = service.label, tint = BrandPink)
                }
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = service.label,
                    fontSize = 11.sp,
                    color = DeepPlum,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.size(width = 64.dp, height = 28.dp)
                )
            }
        }
    }
}

@Composable
private fun TransactionRow(tx: Transaction) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(text = tx.title, fontSize = 14.sp, fontWeight = FontWeight.Medium, color = DeepPlum)
                Text(text = tx.subtitle, fontSize = 12.sp, color = NeutralGray)
            }
            Text(
                text = tx.amount,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = if (tx.isCredit) SuccessGreen else DeepPlum
            )
        }
        Divider(color = DividerGray, thickness = 1.dp)
    }
}