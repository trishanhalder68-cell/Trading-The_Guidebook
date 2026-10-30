package com.example.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.CloudDone
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.FormatQuote
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Savings
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material.icons.filled.Stars
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.data.model.TransactionEntity
import com.example.data.model.TransactionType
import com.example.ui.auth.GoogleSignInButton
import com.example.ui.components.AppleGlassCard
import com.example.ui.components.TraderPathRoadmapSection
import com.example.ui.components.appleSpringPress
import com.example.ui.theme.BrushedGold
import com.example.ui.theme.CreamBackground
import com.example.ui.theme.CreamBorder
import com.example.ui.theme.CreamContainer
import com.example.ui.theme.CreamSurface
import com.example.ui.theme.CrimsonLoss
import com.example.ui.theme.EmeraldProfit
import com.example.ui.theme.GoldDark
import com.example.ui.theme.GoldGleam
import com.example.ui.theme.MetallicGoldBorder
import com.example.ui.theme.ObsidianBlack
import com.example.ui.theme.SageGreen
import com.example.ui.theme.SageGreenAccent
import com.example.ui.theme.SageGreenContainer
import com.example.ui.theme.SageGreenText
import com.example.ui.theme.SlateCharcoal
import com.example.ui.theme.SlateDark
import com.example.ui.theme.SlateMuted
import com.example.ui.theme.SlateSubtle
import com.example.ui.theme.TrustBlue
import com.example.ui.theme.TrustBlueContainer
import com.example.ui.theme.TrustBlueHover
import com.example.viewmodel.WealthViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun HomeScreen(
    viewModel: WealthViewModel,
    onOpenAddTransaction: () -> Unit,
    onOpenCheckout: () -> Unit,
    onOpenQuoteDialog: () -> Unit,
    onNavigateToStage: (String) -> Unit
) {
    val summary by viewModel.cashflowSummary.collectAsState()
    val region by viewModel.currentRegion.collectAsState()
    val transactions by viewModel.allTransactions.collectAsState()
    val isVip by viewModel.isVipPro.collectAsState()
    val activeStage by viewModel.activeStage.collectAsState()
    val completedStages by viewModel.completedStages.collectAsState()

    val currency = region.currencySymbol
    val currentUser = Firebase.auth.currentUser

    // Live cycling button state: alternates seamlessly between "Start Learning" and "Start Income"
    var liveMode by remember { mutableIntStateOf(0) } // 0: Start Learning, 1: Start Income
    LaunchedEffect(Unit) {
        while (true) {
            delay(3600)
            liveMode = (liveMode + 1) % 2
        }
    }

    // Buttery smooth Windows 11 / Gemini style pop-up modal state
    var showSmoothLaunchModal by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(CreamBackground)
    ) {
        // Delicate subtle ambient aura in the upper center, mimicking Gemini app fluidity
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(380.dp)
                .drawBehind {
                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                TrustBlue.copy(alpha = 0.05f),
                                BrushedGold.copy(alpha = 0.03f),
                                Color.Transparent
                            ),
                            center = Offset(size.width * 0.5f, size.height * 0.42f),
                            radius = size.width * 0.65f
                        )
                    )
                }
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(10.dp))

                // Minimalist, high-end Apple HIG Top Bar
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "TRADING: THE GUIDEBOOK",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = TrustBlue,
                            letterSpacing = 1.2.sp
                        )
                        Text(
                            text = "Apex Citadel",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = SlateCharcoal
                        )
                    }

                    // Clean Region & Account Indicator
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        if (currentUser != null) {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(20.dp))
                                    .background(SageGreenContainer)
                                    .border(BorderStroke(1.dp, SageGreen.copy(alpha = 0.3f)), RoundedCornerShape(20.dp))
                                    .padding(horizontal = 10.dp, vertical = 5.dp)
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.CloudDone,
                                        contentDescription = null,
                                        tint = SageGreen,
                                        modifier = Modifier.size(12.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = currentUser.displayName?.take(10) ?: "Synced",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = SageGreenText
                                    )
                                }
                            }
                        } else {
                            GoogleSignInButton(
                                onAuthSuccess = { },
                                onAuthError = { }
                            )
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(20.dp))
                                .background(Color.White)
                                .border(BorderStroke(1.dp, Color(0xFFE2E8F0)), RoundedCornerShape(20.dp))
                                .padding(horizontal = 10.dp, vertical = 5.dp)
                        ) {
                            Text(
                                text = "${region.code} $currency",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = SlateDark
                            )
                        }
                    }
                }
            }

            // =========================================================================
            // CENTER STAGE: FLUID GEMINI-STYLE "START NOW" HERO
            // Pure distraction-free focus: Live cycling button in the immediate middle
            // =========================================================================
            item {
                Spacer(modifier = Modifier.height(12.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(
                            elevation = 10.dp,
                            shape = RoundedCornerShape(28.dp),
                            spotColor = Color(0x14000000),
                            ambientColor = Color(0x06000000)
                        )
                        .clip(RoundedCornerShape(28.dp))
                        .background(
                            Brush.verticalGradient(
                                listOf(
                                    Color.White.copy(alpha = 0.96f),
                                    Color.White.copy(alpha = 0.90f)
                                )
                            )
                        )
                        .border(
                            BorderStroke(1.dp, Color(0xFFE2E8F0)),
                            RoundedCornerShape(28.dp)
                        )
                        .padding(26.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        // Subtle stage eyebrow
                        Text(
                            text = "IMMEDIATE SESSION",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = TrustBlue,
                            letterSpacing = 1.4.sp
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        // Large fluid title focusing on immediate start
                        Text(
                            text = "Start Your Journey",
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            color = SlateCharcoal,
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = "From 1602 VOC to modern algorithmic execution. Distraction-free mastery.",
                            fontSize = 13.sp,
                            color = SlateMuted,
                            textAlign = TextAlign.Center,
                            lineHeight = 18.sp,
                            modifier = Modifier.padding(horizontal = 8.dp)
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        // =============================================================
                        // THE LIVE DUAL-STATE BUTTON (Start Learning / Start Income)
                        // Smoothly cycles with buttery crossfade, zero chunky animations
                        // =============================================================
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp)
                                .shadow(
                                    elevation = 8.dp,
                                    shape = RoundedCornerShape(16.dp),
                                    spotColor = TrustBlue.copy(alpha = 0.35f),
                                    ambientColor = Color(0x0A000000)
                                )
                                .clip(RoundedCornerShape(16.dp))
                                .background(
                                    Brush.horizontalGradient(
                                        if (liveMode == 0) {
                                            listOf(TrustBlue, TrustBlueHover)
                                        } else {
                                            listOf(SlateCharcoal, Color(0xFF1E293B))
                                        }
                                    )
                                )
                                .appleSpringPress(onClick = { showSmoothLaunchModal = true })
                                .testTag("live_hero_action_button"),
                            contentAlignment = Alignment.Center
                        ) {
                            AnimatedContent(
                                targetState = liveMode,
                                transitionSpec = {
                                    fadeIn(tween(350, easing = LinearOutSlowInEasing)) togetherWith
                                            fadeOut(tween(250, easing = FastOutLinearInEasing))
                                },
                                label = "live_button_transition"
                            ) { mode ->
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center,
                                    modifier = Modifier.padding(horizontal = 20.dp)
                                ) {
                                    Icon(
                                        imageVector = if (mode == 0) Icons.AutoMirrored.Filled.MenuBook else Icons.Default.AccountBalance,
                                        contentDescription = null,
                                        tint = Color.White,
                                        modifier = Modifier.size(19.dp)
                                    )
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Text(
                                        text = if (mode == 0) "START LEARNING" else "START INCOME",
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White,
                                        letterSpacing = 1.sp
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                        contentDescription = null,
                                        tint = Color.White.copy(alpha = 0.8f),
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        // Quick secondary trigger: direct open launch sheet
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .clickable { showSmoothLaunchModal = true }
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = "Tap to explore full options",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium,
                                color = SlateMuted
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowDown,
                                contentDescription = null,
                                tint = SlateMuted,
                                modifier = Modifier.size(14.dp)
                            )
                        }
                    }
                }
            }

            // Scroll down hint for deeper features
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Scroll down for Stage 1 Vault & Curriculum Roadmap",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium,
                        color = SlateSubtle
                    )
                }
            }

            // =========================================================================
            // STAGE 1: MONEY FOUNDATIONS & CAPITAL VAULT
            // Apple Frosted Glass Card with whitespace and tactile ledger
            // =========================================================================
            item {
                AppleGlassCard(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    backgroundColor = Color.White.copy(alpha = 0.95f),
                    borderColor = Color(0xFFE2E8F0),
                    elevation = 4.dp
                ) {
                    Column(modifier = Modifier.padding(22.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "STAGE 1 • CAPITAL VAULT",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = TrustBlue,
                                    letterSpacing = 1.sp
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = "Money Foundations",
                                    fontSize = 19.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = SlateCharcoal
                                )
                            }

                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(SageGreenContainer)
                                    .padding(horizontal = 10.dp, vertical = 5.dp)
                            ) {
                                Text(
                                    text = "${summary.savingsRatePercentage.toInt()}% Saved",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = SageGreenText
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Large Net Savings Display
                        Text(
                            text = "Net Wealth Vault",
                            fontSize = 12.sp,
                            color = SlateMuted
                        )
                        Text(
                            text = "$currency${"%,.2f".format(summary.netSavings)}",
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (summary.netSavings >= 0) SlateCharcoal else CrimsonLoss
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        // Daily Inflow vs Burn row
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            // Income Pill
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(SageGreenContainer.copy(alpha = 0.5f))
                                    .padding(12.dp)
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.ArrowUpward,
                                        contentDescription = "Income",
                                        tint = SageGreen,
                                        modifier = Modifier.size(14.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Column {
                                        Text(text = "Daily Inflow", fontSize = 10.sp, color = SageGreenText)
                                        Text(
                                            text = "$currency${"%,.0f".format(summary.dailyIncome)}",
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = SageGreenText
                                        )
                                    }
                                }
                            }

                            // Expense Pill
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(Color(0xFFFEE2E2).copy(alpha = 0.5f))
                                    .padding(12.dp)
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.ArrowDownward,
                                        contentDescription = "Expense",
                                        tint = CrimsonLoss,
                                        modifier = Modifier.size(14.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Column {
                                        Text(text = "Daily Burn", fontSize = 10.sp, color = CrimsonLoss)
                                        Text(
                                            text = "$currency${"%,.0f".format(summary.dailyExpense)}",
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = CrimsonLoss
                                        )
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Smooth Flat Button: Log Cashflow
                        Button(
                            onClick = onOpenAddTransaction,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = TrustBlue,
                                contentColor = Color.White
                            ),
                            shape = RoundedCornerShape(12.dp),
                            elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(46.dp)
                                .appleSpringPress(onClick = onOpenAddTransaction)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "Log Cashflow Transaction",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                    }
                }
            }

            // =========================================================================
            // THE TRADER'S PATH: 4-STAGE SEQUENTIAL ROADMAP
            // Ultra-clean Apple HIG cards without cluttered badges or side pills
            // =========================================================================
            item {
                TraderPathRoadmapSection(
                    currentStage = activeStage,
                    completedStages = completedStages,
                    onSelectStage = { stage ->
                        viewModel.selectRoadmapStage(stage.stageNumber)
                        onNavigateToStage(stage.targetTab)
                    },
                    onSkipToStage = { stageNum ->
                        viewModel.selectRoadmapStage(stageNum)
                        val target = when (stageNum) {
                            1 -> "home"
                            2 -> "masterclass"
                            3 -> "analytics"
                            4 -> "simulation"
                            else -> "home"
                        }
                        onNavigateToStage(target)
                    }
                )
            }

            // =========================================================================
            // RECENT CASHFLOW TRANSACTIONS
            // =========================================================================
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Cashflow History (${transactions.size})",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = SlateCharcoal
                    )
                    Text(
                        text = "Stage 1 Activity",
                        fontSize = 12.sp,
                        color = SlateMuted
                    )
                }
            }

            if (transactions.isEmpty()) {
                item {
                    AppleGlassCard(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        backgroundColor = Color.White.copy(alpha = 0.85f),
                        borderColor = Color(0xFFE2E8F0),
                        elevation = 2.dp
                    ) {
                        Column(
                            modifier = Modifier.padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = Icons.Default.Savings,
                                contentDescription = null,
                                tint = SlateSubtle,
                                modifier = Modifier.size(32.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Your Capital Vault is Ready",
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = SlateCharcoal
                            )
                            Text(
                                text = "Log your first income or expense to activate Stage 1.",
                                fontSize = 12.sp,
                                color = SlateMuted
                            )
                        }
                    }
                }
            } else {
                items(transactions.take(5)) { tx ->
                    AppleGlassCard(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        backgroundColor = Color.White.copy(alpha = 0.9f),
                        borderColor = Color(0xFFE2E8F0),
                        elevation = 1.dp
                    ) {
                        Row(
                            modifier = Modifier.padding(14.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(34.dp)
                                        .clip(CircleShape)
                                        .background(
                                            if (tx.type == TransactionType.INCOME) SageGreenContainer else Color(0xFFFEE2E2)
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = if (tx.type == TransactionType.INCOME) Icons.Default.ArrowUpward else Icons.Default.ArrowDownward,
                                        contentDescription = tx.type.name,
                                        tint = if (tx.type == TransactionType.INCOME) SageGreen else CrimsonLoss,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.width(12.dp))
                                Column {
                                    Text(
                                        text = tx.title,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = SlateCharcoal
                                    )
                                    Text(
                                        text = "${tx.category} • ${SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(Date(tx.timestamp))}",
                                        fontSize = 11.sp,
                                        color = SlateMuted
                                    )
                                }
                            }

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "${if (tx.type == TransactionType.INCOME) "+" else "-"}$currency${"%,.2f".format(tx.amount)}",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (tx.type == TransactionType.INCOME) SageGreenAccent else CrimsonLoss
                                )
                                IconButton(
                                    onClick = { viewModel.deleteTransaction(tx) },
                                    modifier = Modifier.appleSpringPress()
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = "Delete",
                                        tint = SlateSubtle,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(30.dp))
            }
        }

        // =============================================================================
        // BUTTERY SMOOTH MAIN POP-UP (Windows 11 / Gemini Style Fluidity)
        // Light, feathery elevation, gentle fade & vertical glide, zero harsh zoom
        // =============================================================================
        if (showSmoothLaunchModal) {
            Dialog(
                onDismissRequest = { showSmoothLaunchModal = false },
                properties = DialogProperties(usePlatformDefaultWidth = false)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.35f))
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = { showSmoothLaunchModal = false }
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.92f)
                            .shadow(
                                elevation = 16.dp,
                                shape = RoundedCornerShape(28.dp),
                                spotColor = Color(0x24000000),
                                ambientColor = Color(0x0E000000)
                            )
                            .clip(RoundedCornerShape(28.dp))
                            .background(Color(0xFFFAF9F6))
                            .border(BorderStroke(1.dp, Color(0xFFE2E8F0)), RoundedCornerShape(28.dp))
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null,
                                onClick = { /* consume click inside card */ }
                            )
                            .padding(24.dp)
                    ) {
                        Column(modifier = Modifier.fillMaxWidth()) {
                            // Top Row: Dismiss & Title
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Box(
                                        modifier = Modifier
                                            .size(8.dp)
                                            .clip(CircleShape)
                                            .background(EmeraldProfit)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "SESSION LAUNCHPAD",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = TrustBlue,
                                        letterSpacing = 1.sp
                                    )
                                }

                                IconButton(
                                    onClick = { showSmoothLaunchModal = false },
                                    modifier = Modifier.size(28.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Close,
                                        contentDescription = "Close",
                                        tint = SlateMuted,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            Text(
                                text = "What would you like to launch?",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = SlateCharcoal
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Choose your active focus. Seamlessly navigate directly to the core lesson.",
                                fontSize = 12.sp,
                                color = SlateMuted,
                                lineHeight = 16.sp
                            )

                            Spacer(modifier = Modifier.height(20.dp))

                            // Choice 1: Start Learning (Masterclass)
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(Color.White)
                                    .border(BorderStroke(1.dp, Color(0xFFE2E8F0)), RoundedCornerShape(16.dp))
                                    .clickable {
                                        showSmoothLaunchModal = false
                                        viewModel.selectRoadmapStage(2)
                                        onNavigateToStage("masterclass")
                                    }
                                    .appleSpringPress(onClick = {
                                        showSmoothLaunchModal = false
                                        viewModel.selectRoadmapStage(2)
                                        onNavigateToStage("masterclass")
                                    })
                                    .padding(16.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Box(
                                            modifier = Modifier
                                                .size(40.dp)
                                                .clip(CircleShape)
                                                .background(TrustBlue.copy(alpha = 0.1f)),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Icon(
                                                imageVector = Icons.AutoMirrored.Filled.MenuBook,
                                                contentDescription = null,
                                                tint = TrustBlue,
                                                modifier = Modifier.size(20.dp)
                                            )
                                        }
                                        Spacer(modifier = Modifier.width(14.dp))
                                        Column {
                                            Text(
                                                text = "Start Learning",
                                                fontSize = 15.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = SlateCharcoal
                                            )
                                            Text(
                                                text = "1602 VOC to Global Markets (Free Chapter 1)",
                                                fontSize = 11.sp,
                                                color = SlateMuted
                                            )
                                        }
                                    }

                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                        contentDescription = null,
                                        tint = TrustBlue,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            // Choice 2: Start Income (Money Foundations)
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(Color.White)
                                    .border(BorderStroke(1.dp, Color(0xFFE2E8F0)), RoundedCornerShape(16.dp))
                                    .clickable {
                                        showSmoothLaunchModal = false
                                        onOpenAddTransaction()
                                    }
                                    .appleSpringPress(onClick = {
                                        showSmoothLaunchModal = false
                                        onOpenAddTransaction()
                                    })
                                    .padding(16.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Box(
                                            modifier = Modifier
                                                .size(40.dp)
                                                .clip(CircleShape)
                                                .background(SageGreenContainer),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.AccountBalance,
                                                contentDescription = null,
                                                tint = SageGreenText,
                                                modifier = Modifier.size(20.dp)
                                            )
                                        }
                                        Spacer(modifier = Modifier.width(14.dp))
                                        Column {
                                            Text(
                                                text = "Start Income Tracker",
                                                fontSize = 15.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = SlateCharcoal
                                            )
                                            Text(
                                                text = "Log Inflow & Build Compounding Vault",
                                                fontSize = 11.sp,
                                                color = SlateMuted
                                            )
                                        }
                                    }

                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                        contentDescription = null,
                                        tint = SageGreen,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            // Choice 3: Consult AI Mentor
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(Color.White)
                                    .border(BorderStroke(1.dp, Color(0xFFE2E8F0)), RoundedCornerShape(16.dp))
                                    .clickable {
                                        showSmoothLaunchModal = false
                                        onNavigateToStage("mentor")
                                    }
                                    .appleSpringPress(onClick = {
                                        showSmoothLaunchModal = false
                                        onNavigateToStage("mentor")
                                    })
                                    .padding(16.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Box(
                                            modifier = Modifier
                                                .size(40.dp)
                                                .clip(CircleShape)
                                                .background(Color(0xFFEDE9FE)),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.SmartToy,
                                                contentDescription = null,
                                                tint = Color(0xFF7C3AED),
                                                modifier = Modifier.size(20.dp)
                                            )
                                        }
                                        Spacer(modifier = Modifier.width(14.dp))
                                        Column {
                                            Text(
                                                text = "Consult AI Wealth Advisor",
                                                fontSize = 15.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = SlateCharcoal
                                            )
                                            Text(
                                                text = "Ask questions on market terms & strategy",
                                                fontSize = 11.sp,
                                                color = SlateMuted
                                            )
                                        }
                                    }

                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                        contentDescription = null,
                                        tint = Color(0xFF7C3AED),
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
