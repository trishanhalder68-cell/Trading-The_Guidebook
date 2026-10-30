package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material.icons.filled.QrCode2
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.BrushedGold
import com.example.ui.theme.CitadelCardElevated
import com.example.ui.theme.EmeraldProfit
import com.example.ui.theme.GoldGleam
import com.example.ui.theme.MetallicGoldBorder
import com.example.ui.theme.ObsidianBlack
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.TextTertiary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentCheckoutModal(
    isAlreadyVip: Boolean,
    currencySymbol: String,
    onDismiss: () -> Unit,
    onUnlockWithCode: (String) -> Boolean,
    onPayWithMethod: (String) -> Unit,
    onResetVip: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var accessCodeInput by remember { mutableStateOf("") }
    var codeError by remember { mutableStateOf<String?>(null) }
    var codeSuccess by remember { mutableStateOf(false) }

    var selectedTier by remember { mutableStateOf("YEARLY") } // "YEARLY" or "LEVEL"
    var selectedPaymentMethod by remember { mutableStateOf("UPI / Google Pay") }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = ObsidianBlack,
        shape = CutCornerShape(topStart = 20.dp, topEnd = 20.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 22.dp)
                .padding(bottom = 36.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // Citadel Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CutCornerShape(8.dp))
                        .background(BrushedGold.copy(alpha = 0.15f))
                        .border(1.2.dp, MetallicGoldBorder, CutCornerShape(8.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(imageVector = Icons.Default.AutoAwesome, contentDescription = null, tint = BrushedGold)
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = "Citadel Trading Pass",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                    Text(
                        text = "Apex Trading Technologies Ltd. • Public Launch Ready",
                        fontSize = 11.sp,
                        color = BrushedGold
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = onDismiss) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "Close", tint = TextTertiary)
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            if (isAlreadyVip) {
                // Active VIP Status Card
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.2.dp, EmeraldProfit, CutCornerShape(14.dp)),
                    colors = CardDefaults.cardColors(containerColor = CitadelCardElevated),
                    shape = CutCornerShape(14.dp)
                ) {
                    Column(modifier = Modifier.padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(imageVector = Icons.Default.Verified, contentDescription = null, tint = EmeraldProfit, modifier = Modifier.size(44.dp))
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Annual All-Access Active", fontSize = 17.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                        Text(
                            text = "You hold full unrestricted access to all 6 Masterclass Levels, the Gemini AI Market Mentor, and live economic telemetry.",
                            fontSize = 12.sp,
                            color = TextSecondary,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                        Spacer(modifier = Modifier.height(14.dp))
                        OutlinedButton(
                            onClick = onResetVip,
                            shape = CutCornerShape(8.dp),
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = TextTertiary)
                        ) {
                            Text("Reset Access State (Dev Mode)", fontSize = 11.sp)
                        }
                    }
                }
            } else {
                // Pricing Switcher Card: Annual ₹999 vs Micro-Level
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            1.5.dp,
                            Brush.linearGradient(listOf(MetallicGoldBorder, Color(0xFF6B531B), MetallicGoldBorder)),
                            CutCornerShape(16.dp)
                        ),
                    colors = CardDefaults.cardColors(containerColor = CitadelCardElevated),
                    shape = CutCornerShape(16.dp)
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .clip(CutCornerShape(6.dp))
                                    .background(BrushedGold)
                                    .padding(horizontal = 8.dp, vertical = 3.dp)
                            ) {
                                Text("MOST POPULAR", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = ObsidianBlack)
                            }
                            Spacer(modifier = Modifier.weight(1f))
                            Text("Just $currencySymbol 83 / month", fontSize = 11.sp, color = GoldGleam, fontWeight = FontWeight.Bold)
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text("Annual Sovereign Pass", fontSize = 17.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                                Text("Complete unlock of all 6 Levels + Gemini AI Advisor", fontSize = 12.sp, color = TextSecondary)
                            }
                            Text("$currencySymbol 999", fontSize = 24.sp, fontWeight = FontWeight.ExtraBold, color = GoldGleam)
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        listOf(
                            "Full access to Levels 1 through 6 (1602 to Modern Macro)",
                            "Unlimited multi-turn Gemini 3.8 Flash AI Market Advisor",
                            "Real-time Macroeconomic Radar & Indian Tax Slab Analysis",
                            "Stock Compounding & Wealth Multiplier Simulators",
                            "Save over 60% compared to buying individual levels"
                        ).forEach { perk ->
                            Row(modifier = Modifier.padding(vertical = 3.dp), verticalAlignment = Alignment.CenterVertically) {
                                Icon(imageVector = Icons.Default.Check, contentDescription = null, tint = BrushedGold, modifier = Modifier.size(15.dp))
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(perk, fontSize = 12.sp, color = TextSecondary)
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Micro-Tier Breakdown Note
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(CutCornerShape(10.dp))
                        .background(Color(0xFF141419))
                        .border(1.dp, MetallicGoldBorder.copy(alpha = 0.4f), CutCornerShape(10.dp))
                        .padding(12.dp)
                ) {
                    Column {
                        Text("Or Learn Level-by-Level as You Grow:", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = BrushedGold)
                        Text(
                            text = "Level 1: Free • Level 2: ₹125 • Level 3: ₹175 • Level 4: ₹200 • Level 5: ₹225 • Level 6: ₹250. Small progressive investments as you master each stage.",
                            fontSize = 11.sp,
                            color = TextTertiary,
                            modifier = Modifier.padding(top = 2.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                // Secret Admin / Access Code Section (No codes displayed publicly)
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, Color(0xFF2B2B38), CutCornerShape(14.dp)),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF121217)),
                    shape = CutCornerShape(14.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Default.Key, contentDescription = null, tint = BrushedGold, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Have an Authorized Access Code?", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                        }
                        Text(
                            text = "Enter your private authorization code to activate access.",
                            fontSize = 11.sp,
                            color = TextTertiary,
                            modifier = Modifier.padding(top = 2.dp, bottom = 10.dp)
                        )

                        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                            OutlinedTextField(
                                value = accessCodeInput,
                                onValueChange = {
                                    accessCodeInput = it
                                    codeError = null
                                },
                                placeholder = { Text("Enter Access Code", fontSize = 12.sp) },
                                modifier = Modifier.weight(1f),
                                shape = CutCornerShape(8.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = BrushedGold,
                                    unfocusedBorderColor = Color(0xFF383848),
                                    focusedTextColor = TextPrimary,
                                    unfocusedTextColor = TextPrimary
                                ),
                                singleLine = true
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Button(
                                onClick = {
                                    val success = onUnlockWithCode(accessCodeInput)
                                    if (success) {
                                        codeSuccess = true
                                        codeError = null
                                    } else {
                                        codeError = "Invalid or expired authorization code."
                                    }
                                },
                                shape = CutCornerShape(8.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = BrushedGold,
                                    contentColor = ObsidianBlack
                                )
                            ) {
                                Icon(imageVector = Icons.Default.LockOpen, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Apply", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            }
                        }

                        if (codeError != null) {
                            Text(codeError!!, color = Color(0xFFEF4444), fontSize = 11.sp, modifier = Modifier.padding(top = 4.dp))
                        }
                        if (codeSuccess) {
                            Text("Access Code Verified. Annual Sovereign Pass Unlocked.", color = EmeraldProfit, fontSize = 12.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 4.dp))
                        }
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                // Payment Gateways
                Text("Select Payment Gateway", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = TextSecondary)
                Spacer(modifier = Modifier.height(8.dp))

                val paymentOptions = listOf(
                    Triple("UPI / Google Pay", Icons.Default.QrCode2, "Zero fees • Instant activation"),
                    Triple("Credit / Debit Card", Icons.Default.CreditCard, "Visa, MasterCard, RuPay, Amex"),
                    Triple("NetBanking / Play Store", Icons.Default.AccountBalance, "All major national & international banks")
                )

                paymentOptions.forEach { (name, icon, subtitle) ->
                    val isSelected = selectedPaymentMethod == name
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .clip(CutCornerShape(10.dp))
                            .background(if (isSelected) BrushedGold.copy(alpha = 0.15f) else CitadelCardElevated)
                            .border(1.dp, if (isSelected) BrushedGold else Color(0xFF282834), CutCornerShape(10.dp))
                            .clickable { selectedPaymentMethod = name }
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(imageVector = icon, contentDescription = null, tint = if (isSelected) BrushedGold else TextSecondary, modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(name, fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = TextPrimary)
                            Text(subtitle, fontSize = 11.sp, color = TextTertiary)
                        }
                        if (isSelected) {
                            Icon(imageVector = Icons.Default.Check, contentDescription = null, tint = BrushedGold, modifier = Modifier.size(18.dp))
                        }
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                // Proceed Button
                Button(
                    onClick = {
                        onPayWithMethod(selectedPaymentMethod)
                        onDismiss()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = CutCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = BrushedGold,
                        contentColor = ObsidianBlack
                    )
                ) {
                    Icon(imageVector = Icons.Default.Security, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Unlock Annual Pass for $currencySymbol 999", fontSize = 14.sp, fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Encrypted by 256-bit SSL • Cancel or manage subscription settings anytime.",
                    fontSize = 10.sp,
                    color = TextTertiary,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}
