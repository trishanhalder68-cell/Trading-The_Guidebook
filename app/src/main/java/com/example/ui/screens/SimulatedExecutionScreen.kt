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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.ShowChart
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.CitadelCardShape
import com.example.ui.components.LuxuryCard
import com.example.ui.theme.BrushedGold
import com.example.ui.theme.CitadelCard
import com.example.ui.theme.CreamBackground
import com.example.ui.theme.CreamBorder
import com.example.ui.theme.CreamContainer
import com.example.ui.theme.CreamSurface
import com.example.ui.theme.CrimsonContainer
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
import com.example.ui.theme.TrustBlue

data class PaperStock(
    val ticker: String,
    val name: String,
    val currentPrice: Double,
    val dailyChangePercent: Double,
    val category: String
)

data class PaperPosition(
    val ticker: String,
    val shares: Int,
    val avgEntryPrice: Double,
    val currentPrice: Double,
    val stopLossPrice: Double?,
    val takeProfitPrice: Double?
) {
    val totalInvested: Double get() = shares * avgEntryPrice
    val currentValue: Double get() = shares * currentPrice
    val unrealizedPnL: Double get() = currentValue - totalInvested
    val returnPercent: Double get() = if (totalInvested > 0) (unrealizedPnL / totalInvested) * 100 else 0.0
}

@Composable
fun SimulatedExecutionScreen(
    onConsultAiMentor: (String) -> Unit,
    onCompleteStage4: () -> Unit,
    modifier: Modifier = Modifier
) {
    val stockMarketTickers = remember {
        listOf(
            PaperStock("NIFTY50", "Nifty 50 Index ETF", 245.50, +0.85, "Index ETF"),
            PaperStock("RELIANCE", "Reliance Industries", 2980.00, +1.42, "Energy / Conglomerate"),
            PaperStock("HDFCBANK", "HDFC Bank Ltd.", 1640.00, +0.65, "Banking & Finance"),
            PaperStock("TCS", "Tata Consultancy Services", 4120.00, -0.35, "Information Tech"),
            PaperStock("GOLDBEES", "Nippon Gold ETF", 68.20, +0.45, "Precious Metals"),
            PaperStock("GLOBAL500", "Global S&P Tech ETF", 480.00, +1.15, "Global Equity")
        )
    }

    var virtualCashBalance by remember { mutableDoubleStateOf(100000.0) }
    var selectedStock by remember { mutableStateOf(stockMarketTickers[0]) }
    var orderType by remember { mutableStateOf("BUY") } // BUY or SELL
    var sharesQuantityInput by remember { mutableStateOf("10") }
    var enableStopLoss by remember { mutableStateOf(true) }
    var enableTakeProfit by remember { mutableStateOf(true) }
    var executedTradeSuccessMessage by remember { mutableStateOf<String?>(null) }

    val positions = remember {
        mutableStateListOf(
            PaperPosition("NIFTY50", 40, 241.00, 245.50, 230.0, 260.0),
            PaperPosition("GOLDBEES", 100, 66.50, 68.20, 64.0, 72.0)
        )
    }

    val totalHoldingsValue = positions.sumOf { it.currentValue }
    val totalPortfolioValue = virtualCashBalance + totalHoldingsValue
    val totalUnrealizedPnL = positions.sumOf { it.unrealizedPnL }

    var selectedTab by remember { mutableIntStateOf(0) } // 0: Order Desk, 1: Positions & Portfolio, 2: Certification

    Scaffold(
        containerColor = CreamBackground,
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(4.dp))
                // Stage Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .clip(CutCornerShape(4.dp))
                                    .background(BrushedGold)
                                    .padding(horizontal = 8.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text = "STAGE 4 OF 4",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = ObsidianBlack
                                )
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "THE TRADER'S PATH",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = SlateMuted
                            )
                        }
                        Text(
                            text = "Simulated Execution Desk",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = SlateCharcoal
                        )
                    }

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(SageGreenContainer)
                            .border(BorderStroke(1.dp, SageGreen), RoundedCornerShape(12.dp))
                            .padding(horizontal = 10.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = "ZERO RISK",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = SageGreenText
                        )
                    }
                }
            }

            // Portfolio Balance Summary Card (Citadel Obsidian Aesthetic)
            item {
                LuxuryCard {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "SIMULATED NET WORTH",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = GoldGleam,
                                letterSpacing = 0.5.sp
                            )
                            Text(
                                text = "₹${"%,.2f".format(totalPortfolioValue)}",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color.White
                            )
                        }

                        Column(horizontalAlignment = Alignment.End) {
                            Text(
                                text = "TOTAL UNREALIZED P&L",
                                fontSize = 10.sp,
                                color = Color(0xFFA0AEC0)
                            )
                            val isProfitable = totalUnrealizedPnL >= 0
                            Text(
                                text = "${if (isProfitable) "+" else ""}₹${"%,.2f".format(totalUnrealizedPnL)}",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isProfitable) SageGreenAccent else CrimsonLoss
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(0xFF161622))
                            .padding(10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.AccountBalanceWallet,
                                contentDescription = null,
                                tint = BrushedGold,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Virtual Cash: ₹${"%,.2f".format(virtualCashBalance)}",
                                fontSize = 12.sp,
                                color = Color.White
                            )
                        }

                        Text(
                            text = "Holdings: ₹${"%,.2f".format(totalHoldingsValue)}",
                            fontSize = 12.sp,
                            color = GoldGleam,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            // Tab Navigation: Order Desk / Active Holdings / Certification
            item {
                TabRow(
                    selectedTabIndex = selectedTab,
                    containerColor = CreamContainer,
                    contentColor = TrustBlue,
                    indicator = { tabPositions ->
                        TabRowDefaults.SecondaryIndicator(
                            Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                            color = TrustBlue,
                            height = 3.dp
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                ) {
                    Tab(
                        selected = selectedTab == 0,
                        onClick = { selectedTab = 0 },
                        text = { Text("Order Desk", fontWeight = FontWeight.Bold, fontSize = 13.sp) }
                    )
                    Tab(
                        selected = selectedTab == 1,
                        onClick = { selectedTab = 1 },
                        text = { Text("Holdings (${positions.size})", fontWeight = FontWeight.Bold, fontSize = 13.sp) }
                    )
                    Tab(
                        selected = selectedTab == 2,
                        onClick = { selectedTab = 2 },
                        text = { Text("Certification", fontWeight = FontWeight.Bold, fontSize = 13.sp) }
                    )
                }
            }

            // Tab 0: Order Desk
            if (selectedTab == 0) {
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(BorderStroke(1.dp, CreamBorder), RoundedCornerShape(12.dp)),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = CreamSurface)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "1. Select Market Instrument",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = SlateCharcoal
                            )
                            Spacer(modifier = Modifier.height(8.dp))

                            // Horizontal ticker selector pills
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                stockMarketTickers.take(3).forEach { stock ->
                                    val isSelected = selectedStock.ticker == stock.ticker
                                    Box(
                                        modifier = Modifier
                                            .weight(1f)
                                            .clip(RoundedCornerShape(8.dp))
                                            .background(if (isSelected) TrustBlue else CreamContainer)
                                            .clickable { selectedStock = stock }
                                            .padding(vertical = 10.dp, horizontal = 6.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                            Text(
                                                text = stock.ticker,
                                                fontSize = 12.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = if (isSelected) Color.White else SlateCharcoal
                                            )
                                            Text(
                                                text = "₹${stock.currentPrice}",
                                                fontSize = 11.sp,
                                                color = if (isSelected) Color(0xFF93C5FD) else SlateMuted
                                            )
                                        }
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(14.dp))

                            // Selected Instrument Header
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text(
                                        text = selectedStock.name,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = SlateCharcoal
                                    )
                                    Text(
                                        text = "${selectedStock.category} • Live Simulated Feed",
                                        fontSize = 12.sp,
                                        color = SlateMuted
                                    )
                                }
                                Column(horizontalAlignment = Alignment.End) {
                                    Text(
                                        text = "₹${selectedStock.currentPrice}",
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.ExtraBold,
                                        color = SlateCharcoal
                                    )
                                    val isUp = selectedStock.dailyChangePercent >= 0
                                    Text(
                                        text = "${if (isUp) "+" else ""}${selectedStock.dailyChangePercent}%",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (isUp) SageGreenAccent else CrimsonLoss
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(14.dp))

                            // Order Type Toggles (BUY vs SELL)
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Button(
                                    onClick = { orderType = "BUY" },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = if (orderType == "BUY") SageGreen else CreamContainer,
                                        contentColor = if (orderType == "BUY") Color.White else SlateCharcoal
                                    ),
                                    shape = RoundedCornerShape(8.dp),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text("BUY (Long)", fontWeight = FontWeight.Bold)
                                }

                                Button(
                                    onClick = { orderType = "SELL" },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = if (orderType == "SELL") CrimsonLoss else CreamContainer,
                                        contentColor = if (orderType == "SELL") Color.White else SlateCharcoal
                                    ),
                                    shape = RoundedCornerShape(8.dp),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text("SELL / Exit", fontWeight = FontWeight.Bold)
                                }
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            // Shares Quantity Input
                            OutlinedTextField(
                                value = sharesQuantityInput,
                                onValueChange = { sharesQuantityInput = it },
                                label = { Text("Number of Shares / Units") },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                singleLine = true,
                                modifier = Modifier.fillMaxWidth(),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = TrustBlue,
                                    unfocusedBorderColor = SlateBorder
                                )
                            )

                            val sharesCount = sharesQuantityInput.toIntOrNull() ?: 1
                            val totalOrderCost = sharesCount * selectedStock.currentPrice

                            Spacer(modifier = Modifier.height(10.dp))

                            // Order Cost Preview
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(CreamContainer)
                                    .padding(10.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "Estimated Order Total:",
                                    fontSize = 12.sp,
                                    color = SlateMuted
                                )
                                Text(
                                    text = "₹${"%,.2f".format(totalOrderCost)}",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = SlateCharcoal
                                )
                            }

                            Spacer(modifier = Modifier.height(14.dp))

                            // Execute Trade Button (Deep Trust-Blue)
                            Button(
                                onClick = {
                                    if (orderType == "BUY") {
                                        if (virtualCashBalance >= totalOrderCost) {
                                            virtualCashBalance -= totalOrderCost
                                            val existingIndex = positions.indexOfFirst { it.ticker == selectedStock.ticker }
                                            if (existingIndex >= 0) {
                                                val existing = positions[existingIndex]
                                                val newShares = existing.shares + sharesCount
                                                val newAvgPrice = (existing.totalInvested + totalOrderCost) / newShares
                                                positions[existingIndex] = existing.copy(
                                                    shares = newShares,
                                                    avgEntryPrice = newAvgPrice
                                                )
                                            } else {
                                                positions.add(
                                                    PaperPosition(
                                                        ticker = selectedStock.ticker,
                                                        shares = sharesCount,
                                                        avgEntryPrice = selectedStock.currentPrice,
                                                        currentPrice = selectedStock.currentPrice,
                                                        stopLossPrice = selectedStock.currentPrice * 0.95,
                                                        takeProfitPrice = selectedStock.currentPrice * 1.10
                                                    )
                                                )
                                            }
                                            executedTradeSuccessMessage = "Successfully bought $sharesCount shares of ${selectedStock.ticker} for ₹${"%,.2f".format(totalOrderCost)}!"
                                            onCompleteStage4()
                                        } else {
                                            executedTradeSuccessMessage = "Insufficient virtual cash balance for this order!"
                                        }
                                    } else {
                                        // SELL
                                        val existingIndex = positions.indexOfFirst { it.ticker == selectedStock.ticker }
                                        if (existingIndex >= 0 && positions[existingIndex].shares >= sharesCount) {
                                            val existing = positions[existingIndex]
                                            virtualCashBalance += totalOrderCost
                                            if (existing.shares == sharesCount) {
                                                positions.removeAt(existingIndex)
                                            } else {
                                                positions[existingIndex] = existing.copy(shares = existing.shares - sharesCount)
                                            }
                                            executedTradeSuccessMessage = "Successfully sold $sharesCount shares of ${selectedStock.ticker}!"
                                            onCompleteStage4()
                                        } else {
                                            executedTradeSuccessMessage = "You do not own enough shares of ${selectedStock.ticker} to sell!"
                                        }
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (orderType == "BUY") TrustBlue else CrimsonLoss,
                                    contentColor = Color.White
                                ),
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(48.dp)
                            ) {
                                Text(
                                    text = "Execute Paper $orderType Order",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp
                                )
                            }

                            if (executedTradeSuccessMessage != null) {
                                Spacer(modifier = Modifier.height(10.dp))
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(SageGreenContainer)
                                        .border(BorderStroke(1.dp, SageGreen), RoundedCornerShape(8.dp))
                                        .padding(10.dp)
                                ) {
                                    Text(
                                        text = executedTradeSuccessMessage!!,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = SageGreenText
                                    )
                                }
                            }
                        }
                    }
                }

                // AI Consult Button
                item {
                    OutlinedButton(
                        onClick = {
                            onConsultAiMentor("What is the risk-to-reward ratio and outlook for trading ${selectedStock.ticker} right now?")
                        },
                        border = BorderStroke(1.dp, TrustBlue),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Psychology,
                                contentDescription = null,
                                tint = TrustBlue,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Consult AI Mentor on ${selectedStock.ticker}",
                                color = TrustBlue,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp
                            )
                        }
                    }
                }
            }

            // Tab 1: Active Positions & Portfolio
            if (selectedTab == 1) {
                if (positions.isEmpty()) {
                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = CreamSurface)
                        ) {
                            Column(
                                modifier = Modifier.padding(24.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "No Active Positions",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp,
                                    color = SlateCharcoal
                                )
                                Text(
                                    text = "Use the Order Desk tab to place your first simulated trade.",
                                    fontSize = 12.sp,
                                    color = SlateMuted
                                )
                            }
                        }
                    }
                } else {
                    items(positions) { position ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .border(BorderStroke(1.dp, CreamBorder), RoundedCornerShape(12.dp)),
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = CreamSurface)
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column {
                                        Text(
                                            text = position.ticker,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 15.sp,
                                            color = SlateCharcoal
                                        )
                                        Text(
                                            text = "${position.shares} Shares @ ₹${position.avgEntryPrice}",
                                            fontSize = 12.sp,
                                            color = SlateMuted
                                        )
                                    }
                                    Column(horizontalAlignment = Alignment.End) {
                                        Text(
                                            text = "₹${"%,.2f".format(position.currentValue)}",
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 15.sp,
                                            color = SlateCharcoal
                                        )
                                        val isUp = position.unrealizedPnL >= 0
                                        Text(
                                            text = "${if (isUp) "+" else ""}₹${"%,.2f".format(position.unrealizedPnL)} (${"%.2f".format(position.returnPercent)}%)",
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = if (isUp) SageGreenAccent else CrimsonLoss
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(8.dp))

                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(6.dp))
                                        .background(CreamContainer)
                                        .padding(8.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = "Stop-Loss: ₹${position.stopLossPrice ?: "None"}",
                                        fontSize = 11.sp,
                                        color = SlateMuted
                                    )
                                    Text(
                                        text = "Take-Profit: ₹${position.takeProfitPrice ?: "None"}",
                                        fontSize = 11.sp,
                                        color = SageGreenText,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Tab 2: Sovereign Trader Certification
            if (selectedTab == 2) {
                item {
                    LuxuryCard(hasGoldGlow = true) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                imageVector = Icons.Default.Verified,
                                contentDescription = null,
                                tint = BrushedGold,
                                modifier = Modifier.size(48.dp)
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = "TRADER'S PATH CERTIFICATION",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = GoldGleam,
                                letterSpacing = 1.sp
                            )
                            Text(
                                text = "Master Trader Accreditation",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color.White
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            Text(
                                text = "Awarded to practitioners who master Money Foundations, Market History & Structure, Macroeconomic Analysis, and Simulated Risk-Controlled Execution.",
                                fontSize = 12.sp,
                                color = Color(0xFFD1D5DB),
                                lineHeight = 18.sp
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(Color(0xFF161622))
                                    .border(BorderStroke(1.dp, MetallicGoldBorder), RoundedCornerShape(8.dp))
                                    .padding(12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "Status: STAGE 4 COMPLETE",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = SageGreenAccent
                                )
                                Text(
                                    text = "VERIFIED IN FIREBASE",
                                    fontSize = 11.sp,
                                    color = GoldGleam
                                )
                            }
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}
