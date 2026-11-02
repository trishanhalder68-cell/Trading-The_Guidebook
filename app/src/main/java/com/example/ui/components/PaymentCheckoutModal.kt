package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.BrushedGold
import com.example.ui.theme.CreamBackground
import com.example.ui.theme.CreamBorder
import com.example.ui.theme.CreamContainer
import com.example.ui.theme.CreamSurface
import com.example.ui.theme.EmeraldProfit
import com.example.ui.theme.GoldGleam
import com.example.ui.theme.SageGreen
import com.example.ui.theme.SageGreenContainer
import com.example.ui.theme.SageGreenText
import com.example.ui.theme.SlateCharcoal
import com.example.ui.theme.SlateDark
import com.example.ui.theme.SlateMuted
import com.example.ui.theme.SlateSubtle
import com.example.ui.theme.Squircle12
import com.example.ui.theme.Squircle16
import com.example.ui.theme.Squircle20
import com.example.ui.theme.Squircle24
import com.example.ui.theme.Squircle28
import com.example.ui.theme.Squircle32
import com.example.ui.theme.TrustBlue
import com.example.ui.theme.TrustBlueContainer

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

    // Pricing tier selection: "MICRO_PRO" (₹129 Launch Pass) or "ANNUAL" (₹999 Sovereign)
    var selectedTier by remember { mutableStateOf("MICRO_PRO") }
    var selectedPaymentMethod by remember { mutableStateOf("UPI / Google Pay") }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = Color(0xFFFAF9F7),
        shape = Squircle32
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .padding(bottom = 36.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // Apple-HIG Header with squircle emblem
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .clip(Squircle16)
                        .background(TrustBlueContainer)
                        .border(BorderStroke(1.dp, TrustBlue.copy(alpha = 0.3f)), Squircle16),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.AutoAwesome,
                        contentDescription = null,
                        tint = TrustBlue,
                        modifier = Modifier.size(20.dp)
                    )
                }
                Spacer(modifier = Modifier.width(14.dp))
                Column {
                    Text(
                        text = "Citadel Trading Pass",
                        fontSize = 19.sp,
                        fontWeight = FontWeight.Bold,
                        color = SlateCharcoal
                    )
                    Text(
                        text = "Level 2 & 3 Advanced Execution Unlock",
                        fontSize = 12.sp,
                        color = SlateMuted
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = onDismiss) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                        tint = SlateSubtle
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            if (isAlreadyVip) {
                // Active VIP Status Card in Apple-HIG Squircle
                AppleGlassCard(
                    modifier = Modifier.fillMaxWidth(),
                    shape = Squircle24,
                    backgroundColor = Color.White,
                    borderColor = EmeraldProfit.copy(alpha = 0.4f),
                    elevation = 4.dp
                ) {
                    Column(
                        modifier = Modifier.padding(22.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .size(54.dp)
                                .clip(Squircle20)
                                .background(SageGreenContainer),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Verified,
                                contentDescription = null,
                                tint = SageGreen,
                                modifier = Modifier.size(28.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "Citadel All-Access Unlocked",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = SlateCharcoal
                        )
                        Text(
                            text = "You have full unrestricted access to all 6 Masterclass Levels, the AI Mentor, and live order flow telemetry.",
                            fontSize = 12.sp,
                            color = SlateMuted,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(top = 6.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        OutlinedButton(
                            onClick = onResetVip,
                            shape = Squircle12,
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = SlateMuted)
                        ) {
                            Text("Reset Access State (Dev Mode)", fontSize = 11.sp)
                        }
                    }
                }
            } else {
                // Non-intrusive micro-transaction tier card (₹129 tier)
                AppleGlassCard(
                    modifier = Modifier.fillMaxWidth(),
                    shape = Squircle24,
                    backgroundColor = if (selectedTier == "MICRO_PRO") Color.White else Color(0xFFF8FAFC),
                    borderColor = if (selectedTier == "MICRO_PRO") TrustBlue else CreamBorder,
                    elevation = if (selectedTier == "MICRO_PRO") 6.dp else 2.dp,
                    onClick = { selectedTier = "MICRO_PRO" }
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .clip(Squircle12)
                                    .background(SageGreenContainer)
                                    .padding(horizontal = 9.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    text = "POPULAR MICRO-PASS",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = SageGreenText
                                )
                            }
                            Spacer(modifier = Modifier.weight(1f))
                            Text(
                                text = "One-Time Unlock",
                                fontSize = 11.sp,
                                color = SlateMuted,
                                fontWeight = FontWeight.Medium
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "Level 2 & 3 Pro Pass",
                                    fontSize = 17.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = SlateCharcoal
                                )
                                Text(
                                    text = "Unlock 1% Risk Law, VWAP Breakouts & Order Blocks",
                                    fontSize = 12.sp,
                                    color = SlateMuted
                                )
                            }
                            Text(
                                text = "$currencySymbol 129",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = TrustBlue
                            )
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        listOf(
                            "Immediate unlock for Chapters 3, 4, 5 and 6",
                            "Candlestick & Order Flow Imbalance drills",
                            "Full Clearance Certificate to trade live markets",
                            "Special ₹129 Launch pricing (regular ₹499)"
                        ).forEach { perk ->
                            Row(
                                modifier = Modifier.padding(vertical = 3.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = null,
                                    tint = SageGreen,
                                    modifier = Modifier.size(15.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(perk, fontSize = 12.sp, color = SlateDark)
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Annual Sovereign Tier Card (₹999)
                AppleGlassCard(
                    modifier = Modifier.fillMaxWidth(),
                    shape = Squircle20,
                    backgroundColor = if (selectedTier == "ANNUAL") Color.White else Color(0xFFF8FAFC),
                    borderColor = if (selectedTier == "ANNUAL") BrushedGold else CreamBorder,
                    elevation = if (selectedTier == "ANNUAL") 4.dp else 1.dp,
                    onClick = { selectedTier = "ANNUAL" }
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "Annual All-Access Sovereign Pass",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = SlateCharcoal
                                )
                            }
                            Text(
                                text = "Unlimited AI Mentor + Macro Radar + All Future Levels",
                                fontSize = 11.sp,
                                color = SlateMuted
                            )
                        }
                        Text(
                            text = "$currencySymbol 999/yr",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = SlateCharcoal
                        )
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                // Secret Access / Promo Code Section in Squircle
                AppleGlassCard(
                    modifier = Modifier.fillMaxWidth(),
                    shape = Squircle20,
                    backgroundColor = Color.White,
                    borderColor = Color(0xFFE2E8F0),
                    elevation = 2.dp
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Key,
                                contentDescription = null,
                                tint = TrustBlue,
                                modifier = Modifier.size(17.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Have an Authorized Access Code?",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = SlateCharcoal
                            )
                        }
                        Text(
                            text = "Enter your private VIP invite or student pass code.",
                            fontSize = 11.sp,
                            color = SlateMuted,
                            modifier = Modifier.padding(top = 2.dp, bottom = 10.dp)
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            OutlinedTextField(
                                value = accessCodeInput,
                                onValueChange = {
                                    accessCodeInput = it
                                    codeError = null
                                },
                                placeholder = { Text("e.g. VIPFREE or TRADER", fontSize = 12.sp) },
                                modifier = Modifier.weight(1f),
                                shape = Squircle12,
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = TrustBlue,
                                    unfocusedBorderColor = Color(0xFFCBD5E1),
                                    focusedTextColor = SlateCharcoal,
                                    unfocusedTextColor = SlateCharcoal
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
                                shape = Squircle12,
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = TrustBlue,
                                    contentColor = Color.White
                                )
                            ) {
                                Text("Apply", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            }
                        }

                        if (codeError != null) {
                            Text(
                                text = codeError!!,
                                color = Color(0xFFEF4444),
                                fontSize = 11.sp,
                                modifier = Modifier.padding(top = 4.dp)
                            )
                        }
                        if (codeSuccess) {
                            Text(
                                text = "Code Verified: Sovereign Access Activated!",
                                color = EmeraldProfit,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(top = 4.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                // Payment Gateway Selection
                Text(
                    text = "Select Payment Method",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = SlateCharcoal
                )
                Spacer(modifier = Modifier.height(8.dp))

                val paymentOptions = listOf(
                    Triple("UPI / Google Pay", Icons.Default.QrCode2, "Zero fees • Instant activation"),
                    Triple("Credit / Debit Card", Icons.Default.CreditCard, "Visa, MasterCard, RuPay, Amex"),
                    Triple("NetBanking / Play Store", Icons.Default.AccountBalance, "All major Indian & global banks")
                )

                paymentOptions.forEach { (name, icon, subtitle) ->
                    val isSelected = selectedPaymentMethod == name
                    AppleGlassCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 3.dp),
                        shape = Squircle16,
                        backgroundColor = if (isSelected) TrustBlueContainer.copy(alpha = 0.6f) else Color.White,
                        borderColor = if (isSelected) TrustBlue else CreamBorder,
                        elevation = if (isSelected) 3.dp else 1.dp,
                        onClick = { selectedPaymentMethod = name }
                    ) {
                        Row(
                            modifier = Modifier.padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = icon,
                                contentDescription = null,
                                tint = if (isSelected) TrustBlue else SlateMuted,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = name,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = SlateCharcoal
                                )
                                Text(
                                    text = subtitle,
                                    fontSize = 11.sp,
                                    color = SlateMuted
                                )
                            }
                            if (isSelected) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = null,
                                    tint = TrustBlue,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Primary Unlock CTA Button with Spring Physics
                Button(
                    onClick = {
                        onPayWithMethod(selectedPaymentMethod)
                        onDismiss()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp)
                        .appleSpringPress(
                            onClick = {
                                onPayWithMethod(selectedPaymentMethod)
                                onDismiss()
                            }
                        ),
                    shape = Squircle20,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = TrustBlue,
                        contentColor = Color.White
                    ),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Security,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (selectedTier == "MICRO_PRO") "Unlock Pro Pass for $currencySymbol 129" else "Unlock Annual Pass for $currencySymbol 999",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Encrypted by 256-bit SSL • 100% money-back guarantee if not satisfied.",
                    fontSize = 10.sp,
                    color = SlateSubtle,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}
