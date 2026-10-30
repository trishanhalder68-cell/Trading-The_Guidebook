package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CloudDone
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.FormatQuote
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Savings
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Stars
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.TransactionEntity
import com.example.data.model.TransactionType
import com.example.ui.auth.GoogleSignInButton
import com.example.ui.components.AppleGlassCard
import com.example.ui.components.LucrativeAttentionButton
import com.example.ui.components.RoadmapStage
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
    val currentQuote by viewModel.currentQuote.collectAsState()
    val activeStage by viewModel.activeStage.collectAsState()
    val completedStages by viewModel.completedStages.collectAsState()

    val currency = region.currencySymbol
    val currentUser = Firebase.auth.currentUser

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(CreamBackground)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(8.dp))

                // Apple HIG Top Header Bar
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "TRADING: THE GUIDEBOOK",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = TrustBlue,
                                letterSpacing = 1.2.sp
                            )
                            if (isVip) {
                                Spacer(modifier = Modifier.width(6.dp))
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(6.dp))
                                        .background(BrushedGold)
                                        .padding(horizontal = 6.dp, vertical = 2.dp)
                                ) {
                                    Text(
                                        text = "VIP ALL-ACCESS",
                                        fontSize = 9.sp,
                                        fontWeight = FontWeight.ExtraBold,
                                        color = ObsidianBlack
                                    )
                                }
                            }
                        }
                        Text(
                            text = "Wealth & Execution",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Black,
                            color = SlateCharcoal
                        )
                    }

                    // Region Badge & Google Cloud Sync Status
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        if (currentUser != null) {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(20.dp))
                                    .background(SageGreenContainer)
                                    .border(BorderStroke(1.dp, SageGreen), RoundedCornerShape(20.dp))
                                    .padding(horizontal = 10.dp, vertical = 6.dp)
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.CloudDone,
                                        contentDescription = "Cloud Synced",
                                        tint = SageGreen,
                                        modifier = Modifier.size(13.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = currentUser.displayName?.take(10) ?: "Synced",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
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
                                .background(CreamContainer)
                                .border(BorderStroke(1.dp, CreamBorder), RoundedCornerShape(20.dp))
                                .padding(horizontal = 10.dp, vertical = 6.dp)
                        ) {
                            Text(
                                text = "${region.code} $currency",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = SlateDark
                            )
                        }
                    }
                }
            }

            // =========================================================================
            // THE OFFICIAL DEDICATED LUCRATIVE ATTENTION-SEEKING BUTTON
            // Visceral psychology trigger: sparks intense urge to learn & compound capital
            // =========================================================================
            item {
                LucrativeAttentionButton(
                    title = "INITIATE MARKET MASTERY",
                    subtitle = "Enter 1602 VOC Citadel Academy • Free Level 1 Pass",
                    badgeText = "HIGH WEALTH ASYMMETRY",
                    onClick = {
                        viewModel.selectRoadmapStage(2)
                        onNavigateToStage("masterclass")
                    }
                )
            }

            // =========================================================================
            // PSYCHOLOGICAL CONVERSION HERO: WHY 95% FAIL VS THE CITADEL EDGE
            // Compels the user to invest their brain into learning before trading
            // =========================================================================
            item {
                AppleGlassCard(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(22.dp),
                    backgroundColor = Color.White.copy(alpha = 0.95f),
                    borderColor = TrustBlue.copy(alpha = 0.2f),
                    elevation = 6.dp
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(34.dp)
                                        .clip(CircleShape)
                                        .background(TrustBlue.copy(alpha = 0.12f)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Psychology,
                                        contentDescription = null,
                                        tint = TrustBlue,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.width(10.dp))
                                Column {
                                    Text(
                                        text = "PSYCHOLOGICAL REALITY CHECK",
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = TrustBlue,
                                        letterSpacing = 1.sp
                                    )
                                    Text(
                                        text = "Why 95% of Retailers Hand Over Money",
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.ExtraBold,
                                        color = SlateCharcoal
                                    )
                                }
                            }

                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(Color(0xFFFEF2F2))
                                    .border(BorderStroke(1.dp, CrimsonLoss.copy(alpha = 0.3f)), RoundedCornerShape(8.dp))
                                    .padding(horizontal = 8.dp, vertical = 3.dp)
                            ) {
                                Text(
                                    text = "CRUCIAL",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = CrimsonLoss
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        Text(
                            text = "Unprepared retail traders trade on emotional impulse, lose capital to institutional market makers within 90 days, and suffer from unhedged inflation drag. The Citadel Guidebook rewires your thinking into institutional-grade execution.",
                            fontSize = 12.sp,
                            color = SlateMuted,
                            lineHeight = 17.sp
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        // Asymmetry comparison cards
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            // Retail Trap Pill
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(14.dp))
                                    .background(Color(0xFFFFF1F2))
                                    .border(BorderStroke(1.dp, Color(0xFFFECDD3)), RoundedCornerShape(14.dp))
                                    .padding(12.dp)
                            ) {
                                Column {
                                    Text(
                                        text = "⚠️ THE RETAIL TRAP",
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = CrimsonLoss
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = "• Gambles on random tips\n• Zero stop-loss discipline\n• Loses ₹14k+ to cash drag\n• High drawdown anxiety",
                                        fontSize = 11.sp,
                                        color = SlateCharcoal,
                                        lineHeight = 15.sp
                                    )
                                }
                            }

                            // Citadel Sovereign Edge Pill
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(14.dp))
                                    .background(SageGreenContainer.copy(alpha = 0.7f))
                                    .border(BorderStroke(1.dp, SageGreen.copy(alpha = 0.4f)), RoundedCornerShape(14.dp))
                                    .padding(12.dp)
                            ) {
                                Column {
                                    Text(
                                        text = "🛡️ THE CITADEL EDGE",
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = SageGreenText
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = "• 1602 VOC structure rules\n• Strict 1-2% risk capping\n• Compounds at 15-22% CAGR\n• Paper-tested execution",
                                        fontSize = 11.sp,
                                        color = SlateCharcoal,
                                        lineHeight = 15.sp
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        // Quick Knowledge Unlock Bar
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                                .background(CreamContainer)
                                .border(BorderStroke(1.dp, CreamBorder), RoundedCornerShape(12.dp))
                                .clickable {
                                    viewModel.selectRoadmapStage(2)
                                    onNavigateToStage("masterclass")
                                }
                                .padding(horizontal = 14.dp, vertical = 10.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Stars,
                                    contentDescription = null,
                                    tint = BrushedGold,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Column {
                                    Text(
                                        text = "Curriculum Level 1 is completely free to read",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = SlateCharcoal
                                    )
                                    Text(
                                        text = "Progressively unlock deeper chapters from just $currency 125",
                                        fontSize = 10.sp,
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
                }
            }

            // Daily Motivational Psychology Quote (Minimalist Apple Card)
            item {
                AppleGlassCard(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    backgroundColor = Color.White.copy(alpha = 0.9f),
                    borderColor = CreamBorder,
                    elevation = 3.dp,
                    onClick = onOpenQuoteDialog
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(TrustBlue.copy(alpha = 0.1f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.FormatQuote,
                                contentDescription = null,
                                tint = TrustBlue,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "\"${currentQuote.quote}\"",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium,
                                color = SlateCharcoal,
                                lineHeight = 16.sp
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "— ${currentQuote.author} • ${currentQuote.category} (Tap for fresh wisdom)",
                                fontSize = 10.sp,
                                color = SlateMuted
                            )
                        }
                    }
                }
            }

            // =========================================================================
            // THE TRADER'S PATH: 4-STAGE SEQUENTIAL MASTER ROADMAP
            // (Obsidian Black & Brushed Gold Citadel Cards with Apple HIG Polish)
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
            // STAGE 1 QUICK LEDGER: MONEY FOUNDATIONS & SAVINGS VAULT
            // Apple Glassmorphism Card with Tactile Add Button
            // =========================================================================
            item {
                AppleGlassCard(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(22.dp),
                    backgroundColor = Color.White.copy(alpha = 0.95f),
                    borderColor = CreamBorder,
                    elevation = 4.dp
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "STAGE 1 • MONEY FOUNDATIONS",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = TrustBlue,
                                    letterSpacing = 0.8.sp
                                )
                                Text(
                                    text = "Net Savings & Run-Rate Vault",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Black,
                                    color = SlateCharcoal
                                )
                            }

                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(SageGreenContainer)
                                    .padding(horizontal = 10.dp, vertical = 5.dp)
                            ) {
                                Text(
                                    text = "${summary.savingsRatePercentage.toInt()}% Saved",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = SageGreenText
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        // Net Savings & Velocity Gauge
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "Total Capital Accumulated",
                                    fontSize = 11.sp,
                                    color = SlateMuted
                                )
                                Text(
                                    text = "$currency${"%,.2f".format(summary.netSavings)}",
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = if (summary.netSavings >= 0) SageGreenAccent else CrimsonLoss
                                )
                            }

                            Column(horizontalAlignment = Alignment.End) {
                                Text(
                                    text = "Projected Annual Run-Rate",
                                    fontSize = 11.sp,
                                    color = SlateMuted
                                )
                                Text(
                                    text = "$currency${"%,.0f".format(summary.annualIncome)}",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = SlateCharcoal
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        // Daily Cashflow Split
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            // Income Pill
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(SageGreenContainer.copy(alpha = 0.6f))
                                    .padding(12.dp)
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.ArrowUpward,
                                        contentDescription = "Income",
                                        tint = SageGreen,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Column {
                                        Text(
                                            text = "Daily Income",
                                            fontSize = 10.sp,
                                            color = SageGreenText
                                        )
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
                                    .background(Color(0xFFFEE2E2).copy(alpha = 0.6f))
                                    .padding(12.dp)
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.ArrowDownward,
                                        contentDescription = "Expense",
                                        tint = CrimsonLoss,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Column {
                                        Text(
                                            text = "Daily Outflow",
                                            fontSize = 10.sp,
                                            color = CrimsonLoss
                                        )
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

                        Spacer(modifier = Modifier.height(14.dp))

                        // Log Daily Cashflow Button with Apple Spring Press
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(TrustBlue)
                                .appleSpringPress(onClick = onOpenAddTransaction),
                            contentAlignment = Alignment.Center
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Log Daily Income / Expense",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    color = Color.White
                                )
                            }
                        }
                    }
                }
            }

            // Recent Cashflow Transactions Header
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
                        text = "Tap + to add entries",
                        fontSize = 12.sp,
                        color = SlateMuted
                    )
                }
            }

            // Transactions List
            if (transactions.isEmpty()) {
                item {
                    AppleGlassCard(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        backgroundColor = Color.White.copy(alpha = 0.85f),
                        borderColor = CreamBorder,
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
                                modifier = Modifier.size(36.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "No Transactions Recorded Yet",
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = SlateCharcoal
                            )
                            Text(
                                text = "Start by logging your daily income or expense to complete Stage 1.",
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
                        borderColor = CreamBorder,
                        elevation = 2.dp
                    ) {
                        Row(
                            modifier = Modifier.padding(14.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(36.dp)
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
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.width(12.dp))
                                Column {
                                    Text(
                                        text = tx.title,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
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
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}
