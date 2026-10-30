package com.example.ui.screens

import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.FormatQuote
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Savings
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.filled.WorkspacePremium
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.model.TransactionEntity
import com.example.data.model.TransactionType
import com.example.ui.components.CitadelBadgeShape
import com.example.ui.components.CitadelCardShape
import com.example.ui.components.GoldSectionHeader
import com.example.ui.components.LuxuryCard
import com.example.ui.components.StatMetricPill
import com.example.ui.theme.BrushedGold
import com.example.ui.theme.CitadelCardElevated
import com.example.ui.theme.CrimsonLoss
import com.example.ui.theme.EmeraldProfit
import com.example.ui.theme.GoldGleam
import com.example.ui.theme.MetallicGoldBorder
import com.example.ui.theme.ObsidianBlack
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.TextTertiary
import com.example.viewmodel.WealthViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun HomeScreen(
    viewModel: WealthViewModel,
    onOpenAddTransaction: () -> Unit,
    onOpenCheckout: () -> Unit,
    onOpenQuoteDialog: () -> Unit,
    onNavigateToAnalytics: () -> Unit,
    onNavigateToAiMentor: () -> Unit,
    onInitiateMasterclass: () -> Unit
) {
    val summary by viewModel.cashflowSummary.collectAsState()
    val region by viewModel.currentRegion.collectAsState()
    val transactions by viewModel.allTransactions.collectAsState()
    val isVip by viewModel.isVipPro.collectAsState()
    val currentQuote by viewModel.currentQuote.collectAsState()

    val currency = region.currencySymbol

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(ObsidianBlack)
    ) {
        // Kang's Citadel Black & Gold Marble Texture Background
        Image(
            painter = painterResource(id = R.drawable.bg_gold_marble),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            alpha = 0.22f,
            modifier = Modifier.fillMaxSize()
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 18.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(16.dp))

                // Profile Header Bar with Aesthetic PFP Avatar
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Aesthetic Gold Trader PFP with Sharp Geometric Frame
                    Box(
                        modifier = Modifier
                            .size(52.dp)
                            .clip(CutCornerShape(8.dp))
                            .border(1.5.dp, MetallicGoldBorder, CutCornerShape(8.dp))
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.pfp_gold_trader),
                            contentDescription = "Aesthetic Trader Profile Avatar",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "Trading: The Guidebook",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Icon(
                                imageVector = Icons.Default.Verified,
                                contentDescription = "Official",
                                tint = BrushedGold,
                                modifier = Modifier.size(15.dp)
                            )
                        }
                        Text(
                            text = "Apex Trading Technologies Ltd. • Citadel Engine",
                            fontSize = 11.sp,
                            color = TextSecondary
                        )
                    }

                    // VIP Status Chip / Sovereign Pass
                    Box(
                        modifier = Modifier
                            .clip(CitadelBadgeShape)
                            .background(if (isVip) EmeraldProfit.copy(alpha = 0.2f) else BrushedGold.copy(alpha = 0.15f))
                            .border(
                                1.dp,
                                if (isVip) EmeraldProfit else MetallicGoldBorder,
                                CitadelBadgeShape
                            )
                            .clickable { onOpenCheckout() }
                            .padding(horizontal = 10.dp, vertical = 6.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = if (isVip) Icons.Default.Verified else Icons.Default.WorkspacePremium,
                                contentDescription = null,
                                tint = if (isVip) EmeraldProfit else BrushedGold,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = if (isVip) "SOVEREIGN PASS" else "CITADEL PASS",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isVip) EmeraldProfit else GoldGleam
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                // HIGH-IMPACT "INITIATE" MASTERCLASS HERO CARD (Citadel Geometry)
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            1.5.dp,
                            Brush.linearGradient(listOf(MetallicGoldBorder, Color(0xFF6B531B), MetallicGoldBorder)),
                            CitadelCardShape
                        ),
                    shape = CitadelCardShape,
                    colors = CardDefaults.cardColors(containerColor = CitadelCardElevated)
                ) {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        Image(
                            painter = painterResource(id = R.drawable.bg_marble_card),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            alpha = 0.45f,
                            modifier = Modifier.matchParentSize()
                        )

                        Column(modifier = Modifier.padding(20.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .clip(CitadelBadgeShape)
                                        .background(BrushedGold.copy(alpha = 0.2f))
                                        .border(1.dp, MetallicGoldBorder, CitadelBadgeShape)
                                        .padding(horizontal = 8.dp, vertical = 4.dp)
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(imageVector = Icons.Default.AutoAwesome, contentDescription = null, tint = BrushedGold, modifier = Modifier.size(12.dp))
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text("CITADEL ACADEMY", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = GoldGleam)
                                    }
                                }
                                Spacer(modifier = Modifier.weight(1f))
                                Text("Level 1: Free Initiation", fontSize = 11.sp, color = BrushedGold, fontWeight = FontWeight.Bold)
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            Text(
                                text = "Learn How Global Trading Actually Works",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = TextPrimary
                            )

                            Text(
                                text = "From 1602 Amsterdam spice ship shares to modern central banks, bonds, equities, and decentralized blockchains. Zero prior knowledge needed.",
                                fontSize = 12.sp,
                                color = TextSecondary,
                                lineHeight = 17.sp,
                                modifier = Modifier.padding(top = 4.dp, bottom = 16.dp)
                            )

                            // THE LUCRATIVE "INITIATE" BUTTON
                            Button(
                                onClick = onInitiateMasterclass,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(52.dp),
                                shape = CutCornerShape(8.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = BrushedGold,
                                    contentColor = ObsidianBlack
                                )
                            ) {
                                Icon(
                                    imageVector = Icons.Default.PlayArrow,
                                    contentDescription = null,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "INITIATE MASTERCLASS",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    letterSpacing = 1.sp
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                // Motivational Quote Card
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onOpenQuoteDialog() }
                        .border(1.dp, MetallicGoldBorder.copy(alpha = 0.5f), CitadelCardShape),
                    shape = CitadelCardShape,
                    colors = CardDefaults.cardColors(containerColor = CitadelCardElevated)
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(34.dp)
                                .clip(CutCornerShape(6.dp))
                                .background(BrushedGold.copy(alpha = 0.15f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.FormatQuote,
                                contentDescription = null,
                                tint = BrushedGold,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "“${currentQuote.quote}”",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium,
                                color = TextPrimary,
                                maxLines = 2
                            )
                            Text(
                                text = "— ${currentQuote.author} • Tap to view daily quote",
                                fontSize = 10.sp,
                                color = BrushedGold,
                                modifier = Modifier.padding(top = 2.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                // Main Net Savings & Wealth Vault Card
                LuxuryCard(hasGoldGlow = true) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "NET MONTHLY SAVINGS VAULT",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = BrushedGold,
                                letterSpacing = 1.sp
                            )
                            Text(
                                text = "$currency ${String.format("%,.0f", summary.netSavings)}",
                                fontSize = 32.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = GoldGleam
                            )
                        }
                        Box(
                            modifier = Modifier
                                .clip(CitadelBadgeShape)
                                .background(EmeraldProfit.copy(alpha = 0.15f))
                                .border(1.dp, EmeraldProfit.copy(alpha = 0.6f), CitadelBadgeShape)
                                .padding(horizontal = 10.dp, vertical = 6.dp)
                        ) {
                            Text(
                                text = "${String.format("%.1f", summary.savingsRatePercentage)}% SAVED",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = EmeraldProfit
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Progress Bar for Savings Target
                    Column {
                        Row(modifier = Modifier.fillMaxWidth()) {
                            Text("Savings Rate Velocity", fontSize = 11.sp, color = TextSecondary)
                            Spacer(modifier = Modifier.weight(1f))
                            Text("Target: 35%+", fontSize = 11.sp, color = BrushedGold)
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        LinearProgressIndicator(
                            progress = { (summary.savingsRatePercentage / 100f).toFloat().coerceIn(0f, 1f) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(8.dp)
                                .clip(CutCornerShape(2.dp)),
                            color = BrushedGold,
                            trackColor = Color(0xFF202028),
                        )
                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    // Daily & Monthly Stats Grid
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        StatMetricPill(
                            label = "Daily Income",
                            value = "$currency${String.format("%,.0f", summary.dailyIncome)}",
                            icon = Icons.Default.ArrowUpward,
                            iconTint = EmeraldProfit,
                            modifier = Modifier.weight(1f)
                        )
                        StatMetricPill(
                            label = "Daily Burn Rate",
                            value = "$currency${String.format("%,.0f", summary.dailyExpense)}",
                            icon = Icons.Default.ArrowDownward,
                            iconTint = CrimsonLoss,
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        StatMetricPill(
                            label = "Monthly Inflow",
                            value = "$currency${String.format("%,.0f", summary.monthlyIncome)}",
                            icon = Icons.Default.Savings,
                            iconTint = BrushedGold,
                            modifier = Modifier.weight(1f)
                        )
                        StatMetricPill(
                            label = "Annual Projected",
                            value = "$currency${String.format("%,.0f", summary.annualIncome)}",
                            icon = Icons.Default.TrendingUp,
                            iconTint = GoldGleam,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                // Action Quick Buttons with Sharp Angles
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    Button(
                        onClick = onOpenAddTransaction,
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp),
                        shape = CutCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = BrushedGold,
                            contentColor = ObsidianBlack
                        )
                    ) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Record Cashflow", fontSize = 13.sp, fontWeight = FontWeight.Bold)
                    }

                    Button(
                        onClick = onNavigateToAnalytics,
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp),
                        shape = CutCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = CitadelCardElevated,
                            contentColor = BrushedGold
                        ),
                        border = androidx.compose.foundation.BorderStroke(1.dp, MetallicGoldBorder)
                    ) {
                        Icon(imageVector = Icons.Default.TrendingUp, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Stock Multiplier", fontSize = 13.sp, fontWeight = FontWeight.Bold)
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Recent Cashflow Transactions Header
                GoldSectionHeader(
                    title = "Live Cashflow Ledger",
                    subtitle = "Daily expense & income records",
                    badgeText = "${transactions.size} records"
                )

                Spacer(modifier = Modifier.height(10.dp))
            }

            if (transactions.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("No transactions logged yet. Tap Record Cashflow!", color = TextTertiary, fontSize = 13.sp)
                    }
                }
            } else {
                items(transactions, key = { it.id }) { tx ->
                    TransactionRowItem(
                        tx = tx,
                        currencySymbol = currency,
                        onDelete = { viewModel.deleteTransaction(tx) }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }

            item {
                Spacer(modifier = Modifier.height(90.dp))
            }
        }
    }
}

@Composable
fun TransactionRowItem(
    tx: TransactionEntity,
    currencySymbol: String,
    onDelete: () -> Unit
) {
    val isIncome = tx.type == TransactionType.INCOME
    val formattedDate = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(Date(tx.timestamp))

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, MetallicGoldBorder.copy(alpha = 0.4f), CutCornerShape(8.dp)),
        shape = CutCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = CitadelCardElevated)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CutCornerShape(6.dp))
                    .background(if (isIncome) EmeraldProfit.copy(alpha = 0.15f) else CrimsonLoss.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = if (isIncome) Icons.Default.ArrowUpward else Icons.Default.ArrowDownward,
                    contentDescription = null,
                    tint = if (isIncome) EmeraldProfit else CrimsonLoss,
                    modifier = Modifier.size(18.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = tx.title,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = TextPrimary
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "${tx.category} • ${tx.recurrence.name.lowercase().replaceFirstChar { it.uppercase() }}",
                        fontSize = 11.sp,
                        color = TextSecondary
                    )
                    Text(
                        text = " • $formattedDate",
                        fontSize = 10.sp,
                        color = TextTertiary
                    )
                }
            }

            Text(
                text = "${if (isIncome) "+" else "-"}$currencySymbol${String.format("%,.0f", tx.amount)}",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = if (isIncome) EmeraldProfit else CrimsonLoss
            )

            IconButton(onClick = onDelete, modifier = Modifier.size(32.dp)) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = TextTertiary.copy(alpha = 0.6f),
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}
