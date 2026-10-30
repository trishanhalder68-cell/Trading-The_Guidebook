package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CallMade
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Savings
import androidx.compose.material.icons.filled.ShowChart
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.CashflowChart
import com.example.ui.components.DonutCategoryChart
import com.example.ui.components.GoldSectionHeader
import com.example.ui.components.LuxuryCard
import com.example.ui.components.StatMetricPill
import com.example.ui.theme.EmeraldProfit
import com.example.ui.theme.GoldBorder
import com.example.ui.theme.GoldGleam
import com.example.ui.theme.ImperialGold
import com.example.ui.theme.ObsidianBlack
import com.example.ui.theme.ObsidianCardElevated
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.TextTertiary
import com.example.viewmodel.WealthViewModel

@Composable
fun AnalyticsScreen(
    viewModel: WealthViewModel
) {
    val summary by viewModel.cashflowSummary.collectAsState()
    val region by viewModel.currentRegion.collectAsState()
    val transactions by viewModel.allTransactions.collectAsState()
    val compoundResult by viewModel.compoundResult.collectAsState()

    val monthlyInvestment by viewModel.multiplierMonthlyAmount.collectAsState()
    val years by viewModel.multiplierYears.collectAsState()
    val returnRate by viewModel.multiplierReturnRate.collectAsState()

    val currency = region.currencySymbol
    val chartPoints = viewModel.getCashflowTrendPoints(transactions)
    val categorySlices = viewModel.getCategorySlices(transactions)

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(com.example.ui.theme.CreamBackground)
            .padding(horizontal = 18.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(16.dp))

            // Screen Header
            GoldSectionHeader(
                title = "Total Database Analysis",
                subtitle = "Comprehensive cashflow audit & wealth telemetry",
                badgeText = "Live Data"
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Total Analysis Overview Card
            LuxuryCard(hasGoldGlow = true) {
                Text(
                    text = "TOTAL FINANCIAL VELOCITY AUDIT",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = ImperialGold,
                    letterSpacing = 1.sp
                )
                Spacer(modifier = Modifier.height(12.dp))

                // Summary Row 1: Daily
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Column {
                        Text("Daily Cash Flow", fontSize = 11.sp, color = TextTertiary)
                        Text(
                            text = "+$currency${String.format("%,.0f", summary.dailyIncome)} / -$currency${String.format("%,.0f", summary.dailyExpense)}",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                    }
                    Column(horizontalAlignment = Alignment.End) {
                        Text("Daily Net Surplus", fontSize = 11.sp, color = TextTertiary)
                        val dailyNet = summary.dailyIncome - summary.dailyExpense
                        Text(
                            text = "$currency${String.format("%,.0f", dailyNet)}",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (dailyNet >= 0) EmeraldProfit else Color(0xFFEF4444)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Summary Row 2: Monthly
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Column {
                        Text("Monthly Inflow vs Burn", fontSize = 11.sp, color = TextTertiary)
                        Text(
                            text = "$currency${String.format("%,.0f", summary.monthlyIncome)} vs $currency${String.format("%,.0f", summary.monthlyExpense)}",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                    }
                    Column(horizontalAlignment = Alignment.End) {
                        Text("Net Monthly Retained", fontSize = 11.sp, color = TextTertiary)
                        Text(
                            text = "$currency${String.format("%,.0f", summary.netSavings)}",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = GoldGleam
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Summary Row 3: Annual
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Column {
                        Text("Projected Annual Run Rate", fontSize = 11.sp, color = TextTertiary)
                        Text(
                            text = "$currency${String.format("%,.0f", summary.annualIncome)}",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = EmeraldProfit
                        )
                    }
                    Column(horizontalAlignment = Alignment.End) {
                        Text("Annual Net Savings", fontSize = 11.sp, color = TextTertiary)
                        Text(
                            text = "$currency${String.format("%,.0f", summary.netSavings * 12.0)}",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = ImperialGold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Cashflow Bezier Chart
            LuxuryCard {
                GoldSectionHeader(
                    title = "Cashflow Trajectory Curve",
                    subtitle = "Dynamic momentum of recent ledger entries",
                    badgeText = "Bezier 60fps"
                )
                Spacer(modifier = Modifier.height(10.dp))
                CashflowChart(
                    points = chartPoints,
                    currencySymbol = currency
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Outflow Category Donut Chart
            LuxuryCard {
                GoldSectionHeader(
                    title = "Expense Allocation Breakdown",
                    subtitle = "Where your money is dispatched",
                    badgeText = "${categorySlices.size} categories"
                )
                Spacer(modifier = Modifier.height(12.dp))
                DonutCategoryChart(
                    slices = categorySlices,
                    totalAmount = summary.monthlyExpense,
                    currencySymbol = currency
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // STOCK INVESTMENT & COMPOUND INTEREST MULTIPLIER (Key user requirement)
            GoldSectionHeader(
                title = "Stock Investment Multiplier",
                subtitle = "Multiply your savings interest through equity compounding",
                badgeText = "Wealth Accelerator"
            )

            Spacer(modifier = Modifier.height(12.dp))

            LuxuryCard(hasGoldGlow = true) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(ImperialGold.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(imageVector = Icons.Default.TrendingUp, contentDescription = null, tint = ImperialGold)
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "Compound Interest & Stock Engine",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                        Text(
                            text = "Model stock market compounding vs a traditional 3.5% bank deposit",
                            fontSize = 11.sp,
                            color = TextSecondary
                        )
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                // Multiplier Outcome Box
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, GoldBorder, RoundedCornerShape(16.dp)),
                    colors = CardDefaults.cardColors(containerColor = ObsidianCardElevated),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Column {
                                Text("ESTIMATED FUTURE WEALTH", fontSize = 10.sp, color = TextTertiary, fontWeight = FontWeight.Bold)
                                Text(
                                    text = "$currency${String.format("%,.0f", compoundResult.totalFutureValue)}",
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = GoldGleam
                                )
                            }
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(EmeraldProfit.copy(alpha = 0.2f))
                                    .border(1.dp, EmeraldProfit, RoundedCornerShape(12.dp))
                                    .padding(horizontal = 10.dp, vertical = 6.dp)
                            ) {
                                Text(
                                    text = "${String.format("%.1f", compoundResult.wealthMultiplier)}x GAIN",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = EmeraldProfit
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Row(modifier = Modifier.fillMaxWidth()) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text("Total Capital Invested", fontSize = 10.sp, color = TextTertiary)
                                Text(
                                    text = "$currency${String.format("%,.0f", compoundResult.totalInvested)}",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = TextSecondary
                                )
                            }
                            Column(modifier = Modifier.weight(1f)) {
                                Text("Pure Interest Earned", fontSize = 10.sp, color = TextTertiary)
                                Text(
                                    text = "+$currency${String.format("%,.0f", compoundResult.totalInterestEarned)}",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = ImperialGold
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // Comparison with Traditional Bank Account
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color(0xFF131317))
                                .padding(10.dp)
                        ) {
                            val advantage = compoundResult.totalFutureValue - compoundResult.bankSavingsValue
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(imageVector = Icons.Default.Info, contentDescription = null, tint = EmeraldProfit, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Equity compounding creates $currency${String.format("%,.0f", advantage)} MORE than traditional bank interest on the same savings!",
                                    fontSize = 11.sp,
                                    color = TextPrimary
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                // Interactive Slider 1: Monthly Investment
                Column {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Monthly Stock Allocation", fontSize = 12.sp, color = TextSecondary)
                        Text("$currency${String.format("%,.0f", monthlyInvestment)} / month", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = ImperialGold)
                    }
                    Slider(
                        value = monthlyInvestment.toFloat(),
                        onValueChange = { viewModel.multiplierMonthlyAmount.value = (it / 1000).toInt() * 1000.0 },
                        valueRange = 1000f..100000f,
                        colors = SliderDefaults.colors(
                            thumbColor = ImperialGold,
                            activeTrackColor = ImperialGold,
                            inactiveTrackColor = GoldBorder
                        )
                    )
                }

                // Interactive Slider 2: Investment Duration (Years)
                Column {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Compounding Time Horizon", fontSize = 12.sp, color = TextSecondary)
                        Text("$years Years", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = ImperialGold)
                    }
                    Slider(
                        value = years.toFloat(),
                        onValueChange = { viewModel.multiplierYears.value = it.toInt() },
                        valueRange = 1f..35f,
                        steps = 34,
                        colors = SliderDefaults.colors(
                            thumbColor = ImperialGold,
                            activeTrackColor = ImperialGold,
                            inactiveTrackColor = GoldBorder
                        )
                    )
                }

                // Interactive Slider 3: Expected CAGR %
                Column {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Expected Stock Market Return", fontSize = 12.sp, color = TextSecondary)
                        Text("${String.format("%.1f", returnRate)}% CAGR", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = EmeraldProfit)
                    }
                    Slider(
                        value = returnRate.toFloat(),
                        onValueChange = { viewModel.multiplierReturnRate.value = (it * 10).toInt() / 10.0 },
                        valueRange = 8f..22f,
                        colors = SliderDefaults.colors(
                            thumbColor = EmeraldProfit,
                            activeTrackColor = EmeraldProfit,
                            inactiveTrackColor = GoldBorder
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(90.dp))
        }
    }
}
