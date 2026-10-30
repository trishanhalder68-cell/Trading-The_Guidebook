package com.example.ui.screens

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
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.CloudDone
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.FormatQuote
import androidx.compose.material.icons.filled.Savings
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.TransactionEntity
import com.example.data.model.TransactionType
import com.example.ui.auth.GoogleSignInButton
import com.example.ui.components.RoadmapStage
import com.example.ui.components.TraderPathRoadmapSection
import com.example.ui.theme.BrushedGold
import com.example.ui.theme.CreamBackground
import com.example.ui.theme.CreamBorder
import com.example.ui.theme.CreamContainer
import com.example.ui.theme.CreamSurface
import com.example.ui.theme.CrimsonLoss
import com.example.ui.theme.GoldGleam
import com.example.ui.theme.MetallicGoldBorder
import com.example.ui.theme.ObsidianBlack
import com.example.ui.theme.SageGreen
import com.example.ui.theme.SageGreenAccent
import com.example.ui.theme.SageGreenContainer
import com.example.ui.theme.SageGreenText
import com.example.ui.theme.SlateBorder
import com.example.ui.theme.SlateCharcoal
import com.example.ui.theme.SlateDark
import com.example.ui.theme.SlateMuted
import com.example.ui.theme.SlateSubtle
import com.example.ui.theme.TrustBlue
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

                // Modern, Minimalist Top Header
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
                                letterSpacing = 1.sp
                            )
                            if (isVip) {
                                Spacer(modifier = Modifier.width(6.dp))
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(4.dp))
                                        .background(BrushedGold)
                                        .padding(horizontal = 6.dp, vertical = 2.dp)
                                ) {
                                    Text(
                                        text = "VIP PRO",
                                        fontSize = 9.sp,
                                        fontWeight = FontWeight.ExtraBold,
                                        color = ObsidianBlack
                                    )
                                }
                            }
                        }
                        Text(
                            text = "Wealth & Execution",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = SlateCharcoal
                        )
                    }

                    // Google Auth Status / Region Pill
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
                                        modifier = Modifier.size(14.dp)
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

            // Daily Motivational Quote (Minimalist Human-Friendly Banner)
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onOpenQuoteDialog() },
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = CreamSurface),
                    border = BorderStroke(1.dp, CreamBorder),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
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
                                text = "— ${currentQuote.author} • ${currentQuote.category}",
                                fontSize = 10.sp,
                                color = SlateMuted
                            )
                        }
                    }
                }
            }

            // =========================================================================
            // THE TRADER'S PATH: 4-STAGE SEQUENTIAL ROADMAP
            // (Obsidian Black & Brushed Gold Citadel Cards)
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
            // =========================================================================
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(BorderStroke(1.dp, CreamBorder), RoundedCornerShape(14.dp)),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = CreamSurface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
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
                                    color = TrustBlue
                                )
                                Text(
                                    text = "Net Savings & Run-Rate Vault",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = SlateCharcoal
                                )
                            }

                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(SageGreenContainer)
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
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
                                    .clip(RoundedCornerShape(10.dp))
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
                                    .clip(RoundedCornerShape(10.dp))
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

                        // Add Transaction Button (Deep Trust-Blue)
                        Button(
                            onClick = onOpenAddTransaction,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = TrustBlue,
                                contentColor = Color.White
                            ),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(46.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = null,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Log Daily Income / Expense",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp
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
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = CreamSurface),
                        border = BorderStroke(1.dp, CreamBorder),
                        shape = RoundedCornerShape(12.dp)
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
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = CreamSurface),
                        border = BorderStroke(1.dp, CreamBorder),
                        shape = RoundedCornerShape(10.dp)
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
                                IconButton(onClick = { viewModel.deleteTransaction(tx) }) {
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
