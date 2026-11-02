package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.material.icons.filled.Terminal
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.BrushedGold
import com.example.ui.theme.EmeraldProfit
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
import com.example.ui.theme.Squircle32
import com.example.ui.theme.TrustBlue
import com.example.ui.theme.TrustBlueContainer
import com.example.ui.theme.WarmBeigeBackground
import com.example.ui.theme.WarmBeigeBorder
import com.example.ui.theme.WarmBeigeCard
import com.example.ui.theme.WarmBeigeContainer

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

    // Pricing tier selection: "MICRO_PRO" (₹20 Launch Pass) or "ANNUAL" (₹999 Sovereign)
    var selectedTier by remember { mutableStateOf("MICRO_PRO") }
    var selectedPaymentMethod by remember { mutableStateOf("UPI / Google Pay") }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = WarmBeigeBackground,
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
                        text = "Institutional Execution & Masterclass Unlock",
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
                    backgroundColor = WarmBeigeCard,
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
                            text = "All Courses & Levels Unlocked",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = SlateCharcoal
                        )
                        Text(
                            text = "You have full unrestricted access to all 6 Masterclass Pillars, the AI Mentor desk, and live institutional order flow radar.",
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
                // Micro-transaction tier card (Reduced price to ₹20 per lesson)
                AppleGlassCard(
                    modifier = Modifier.fillMaxWidth(),
                    shape = Squircle24,
                    backgroundColor = if (selectedTier == "MICRO_PRO") Color.White else WarmBeigeCard,
                    borderColor = if (selectedTier == "MICRO_PRO") TrustBlue else WarmBeigeBorder,
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
                                    text = "SPECIAL PRICE • ₹20 ONLY",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = SageGreenText
                                )
                            }
                            Spacer(modifier = Modifier.weight(1f))
                            Text(
                                text = "Single Lesson Pass",
                                fontSize = 11.sp,
                                color = SlateMuted,
                                fontWeight = FontWeight.Medium
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "Single Lesson / Pillar Pass",
                                    fontSize = 17.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = SlateCharcoal
                                )
                                Text(
                                    text = "Unlock 1% Risk Law, VWAP Breakouts & Liquidity Blocks",
                                    fontSize = 12.sp,
                                    color = SlateMuted
                                )
                            }
                            Text(
                                text = "$currencySymbol 20",
                                fontSize = 26.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = TrustBlue
                            )
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        listOf(
                            "Immediate unlock for any paid Masterclass Lesson",
                            "Candlestick & Order Flow Imbalance interactive drills",
                            "Full Clearance Certificate to trade live markets",
                            "Reduced launch price: ₹20 per lesson"
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
                    backgroundColor = if (selectedTier == "ANNUAL") Color.White else WarmBeigeCard,
                    borderColor = if (selectedTier == "ANNUAL") BrushedGold else WarmBeigeBorder,
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
                                text = "All 6 Pillars + Unlimited AI Mentor + Macro Radar",
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

                // =============================================================
                // DEDICATED DEVELOPER CODE SECTION (Near the Merchant Area)
                // Entering code "TRISH30" unlocks all courses.
                // =============================================================
                AppleGlassCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("developer_code_card"),
                    shape = Squircle20,
                    backgroundColor = Color.White,
                    borderColor = if (codeSuccess) EmeraldProfit else BrushedGold.copy(alpha = 0.5f),
                    elevation = 3.dp
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(Squircle12)
                                    .background(WarmBeigeContainer),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Key,
                                    contentDescription = null,
                                    tint = BrushedGold,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "Developer Code",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = SlateCharcoal
                                )
                                Text(
                                    text = "Enter authorized code to unlock all courses",
                                    fontSize = 11.sp,
                                    color = SlateMuted
                                )
                            }
                            Box(
                                modifier = Modifier
                                    .clip(Squircle12)
                                    .background(WarmBeigeContainer)
                                    .padding(horizontal = 8.dp, vertical = 3.dp)
                            ) {
                                Text(
                                    text = "VIP UNLOCK",
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = SlateMuted
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

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
                                placeholder = { Text("Enter TRISH30", fontSize = 12.sp) },
                                modifier = Modifier
                                    .weight(1f)
                                    .testTag("developer_code_input"),
                                shape = Squircle12,
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = BrushedGold,
                                    unfocusedBorderColor = WarmBeigeBorder,
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
                                        codeError = "Invalid code. Only 'TRISH30' unlocks all courses."
                                    }
                                },
                                shape = Squircle12,
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = TrustBlue,
                                    contentColor = Color.White
                                ),
                                modifier = Modifier.testTag("apply_developer_code_button")
                            ) {
                                Text("Unlock All", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            }
                        }

                        if (codeError != null) {
                            Text(
                                text = codeError!!,
                                color = Color(0xFFEF4444),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier.padding(top = 6.dp)
                            )
                        }
                        if (codeSuccess) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 8.dp)
                                    .clip(Squircle12)
                                    .background(SageGreenContainer)
                                    .padding(horizontal = 10.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = null,
                                    tint = SageGreen,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "Code TRISH30 Verified: All Courses & Stages Unlocked!",
                                    color = SageGreenText,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                // Payment Gateway / Merchant Selection Section
                Text(
                    text = "Select Merchant Payment Method",
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
                        borderColor = if (isSelected) TrustBlue else WarmBeigeBorder,
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
                        .testTag("confirm_payment_button")
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
                        text = if (selectedTier == "MICRO_PRO") "Unlock Lesson for $currencySymbol 20" else "Unlock Annual Pass for $currencySymbol 999",
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
