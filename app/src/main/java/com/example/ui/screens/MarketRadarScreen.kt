package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.ShowChart
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.TrendingDown
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.IncomeOpportunity
import com.example.data.model.MarketNewsItem
import com.example.data.model.MarketSentiment
import com.example.data.model.RegionRepository
import com.example.ui.components.GoldSectionHeader
import com.example.ui.components.LuxuryCard
import com.example.ui.theme.CrimsonLoss
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
fun MarketRadarScreen(
    viewModel: WealthViewModel
) {
    val region by viewModel.currentRegion.collectAsState()
    val opportunities by viewModel.incomeOpportunities.collectAsState()
    val isGenerating by viewModel.isGeneratingIdeas.collectAsState()
    val newsList by viewModel.marketNews.collectAsState()

    var activeDifficultyFilter by remember { mutableStateOf("All") }

    val filteredOpportunities = remember(opportunities, activeDifficultyFilter) {
        if (activeDifficultyFilter == "All") opportunities
        else opportunities.filter { it.difficulty.equals(activeDifficultyFilter, ignoreCase = true) }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(com.example.ui.theme.CreamBackground)
            .padding(horizontal = 18.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(16.dp))

            // Region Selector Pills
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = Icons.Default.Public, contentDescription = null, tint = ImperialGold, modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("Select Precise Region:", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                Spacer(modifier = Modifier.weight(1f))

                RegionRepository.ALL_REGIONS.forEach { reg ->
                    val isSelected = reg.code == region.code
                    Box(
                        modifier = Modifier
                            .padding(start = 6.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(if (isSelected) ImperialGold else ObsidianCardElevated)
                            .border(1.dp, if (isSelected) ImperialGold else GoldBorder, RoundedCornerShape(10.dp))
                            .clickable { viewModel.selectRegion(reg.code) }
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = "${if (reg.code == "IN") "🇮🇳" else "🇺🇸"} ${reg.name} (${reg.currencySymbol})",
                            fontSize = 11.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            color = if (isSelected) ObsidianBlack else TextPrimary
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Regional Macroeconomic Criteria Analysis Card
            LuxuryCard(hasGoldGlow = true) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(ImperialGold.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(imageVector = Icons.Default.Speed, contentDescription = null, tint = ImperialGold)
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "${region.name} Economic Criteria & Market Radar",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                        Text(
                            text = "Current regional conditions affecting income and wealth",
                            fontSize = 11.sp,
                            color = TextSecondary
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Indicators Grid
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    IndicatorChip(
                        title = "Inflation Rate",
                        value = region.inflationRate,
                        subtitle = "Cost of Living Pressure",
                        modifier = Modifier.weight(1f)
                    )
                    IndicatorChip(
                        title = "Central Bank Repo",
                        value = region.centralBankRepoRate,
                        subtitle = "Debt & FD Baseline",
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    IndicatorChip(
                        title = "Benchmark Index",
                        value = region.primaryStockIndex,
                        subtitle = "${region.indexChangePercent} (Bullish)",
                        isPositive = region.isIndexBullish,
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Criteria list
                Text("Key Criteria Affecting Your Income:", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = GoldGleam)
                Spacer(modifier = Modifier.height(6.dp))
                region.keyCriteriaAffectingIncome.forEach { criteria ->
                    Row(modifier = Modifier.padding(vertical = 3.dp), verticalAlignment = Alignment.Top) {
                        Box(
                            modifier = Modifier
                                .padding(top = 5.dp)
                                .size(5.dp)
                                .clip(CircleShape)
                                .background(ImperialGold)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(criteria, fontSize = 11.sp, color = TextSecondary, lineHeight = 16.sp)
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Tax Slabs
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFF141419))
                        .border(1.dp, GoldBorder.copy(alpha = 0.5f), RoundedCornerShape(12.dp))
                        .padding(12.dp)
                ) {
                    Column {
                        Text("Tax Framework & Exemption Thresholds", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = ImperialGold)
                        Text(region.taxSlabsSummary, fontSize = 10.sp, color = TextTertiary, modifier = Modifier.padding(top = 2.dp))
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // ENDLESS INCOME OPPORTUNITIES ENGINE (User requirement: never stops giving new ideas)
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Endless Regional Income Engine",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                    Text(
                        text = "Actionable streams valid for ${region.name}",
                        fontSize = 11.sp,
                        color = TextSecondary
                    )
                }
                // Generate Fresh Ideas Button
                Button(
                    onClick = { viewModel.generateMoreIncomeIdeas() },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = ImperialGold,
                        contentColor = ObsidianBlack
                    ),
                    enabled = !isGenerating
                ) {
                    if (isGenerating) {
                        CircularProgressIndicator(modifier = Modifier.size(14.dp), color = ObsidianBlack, strokeWidth = 2.dp)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Synthesizing...", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    } else {
                        Icon(imageVector = Icons.Default.Refresh, contentDescription = null, modifier = Modifier.size(15.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("New Ideas", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Difficulty filter chips
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf("All", "Beginner", "Intermediate", "Pro").forEach { diff ->
                    val isSelected = activeDifficultyFilter == diff
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .background(if (isSelected) ImperialGold.copy(alpha = 0.2f) else ObsidianCardElevated)
                            .border(1.dp, if (isSelected) ImperialGold else GoldBorder, RoundedCornerShape(10.dp))
                            .clickable { activeDifficultyFilter = diff }
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = diff,
                            fontSize = 11.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                            color = if (isSelected) ImperialGold else TextSecondary
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))
        }

        // List of Income Opportunities
        items(filteredOpportunities, key = { it.id }) { opp ->
            IncomeOpportunityCard(opportunity = opp, currencySymbol = region.currencySymbol)
            Spacer(modifier = Modifier.height(10.dp))
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))

            // OFFICIAL CRUCIAL STOCK MARKET NEWS
            GoldSectionHeader(
                title = "Crucial Market News & Catalysts",
                subtitle = "Official data releases driving stock market momentum",
                badgeText = "Live Feed"
            )

            Spacer(modifier = Modifier.height(12.dp))
        }

        items(newsList, key = { it.id }) { news ->
            MarketNewsCard(news = news)
            Spacer(modifier = Modifier.height(10.dp))
        }

        item {
            Spacer(modifier = Modifier.height(90.dp))
        }
    }
}

@Composable
fun IndicatorChip(
    title: String,
    value: String,
    subtitle: String,
    isPositive: Boolean? = null,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.border(1.dp, GoldBorder.copy(alpha = 0.5f), RoundedCornerShape(12.dp)),
        colors = CardDefaults.cardColors(containerColor = ObsidianCardElevated),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Text(title, fontSize = 10.sp, color = TextTertiary)
            Text(
                text = value,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = if (isPositive == true) EmeraldProfit else TextPrimary
            )
            Text(subtitle, fontSize = 9.sp, color = TextSecondary)
        }
    }
}

@Composable
fun IncomeOpportunityCard(
    opportunity: IncomeOpportunity,
    currencySymbol: String
) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                1.dp,
                if (opportunity.isRegionalHighlight) ImperialGold.copy(alpha = 0.7f) else GoldBorder.copy(alpha = 0.5f),
                RoundedCornerShape(16.dp)
            )
            .clickable { expanded = !expanded },
        colors = CardDefaults.cardColors(containerColor = ObsidianCardElevated),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(ImperialGold.copy(alpha = 0.15f))
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Text(opportunity.category, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = GoldGleam)
                }
                Spacer(modifier = Modifier.width(8.dp))
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xFF22222B))
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Text("Level: ${opportunity.difficulty}", fontSize = 10.sp, color = TextSecondary)
                }
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    imageVector = if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = null,
                    tint = ImperialGold
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = opportunity.title,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )

            Text(
                text = "Potential: ${opportunity.estimatedEarnings}",
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = EmeraldProfit,
                modifier = Modifier.padding(top = 2.dp)
            )

            Text(
                text = opportunity.description,
                fontSize = 12.sp,
                color = TextSecondary,
                modifier = Modifier.padding(top = 4.dp)
            )

            AnimatedVisibility(visible = expanded) {
                Column(modifier = Modifier.padding(top = 12.dp)) {
                    Text("Action Blueprint:", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = ImperialGold)
                    Spacer(modifier = Modifier.height(4.dp))
                    opportunity.actionSteps.forEachIndexed { i, step ->
                        Row(modifier = Modifier.padding(vertical = 3.dp), verticalAlignment = Alignment.Top) {
                            Text("${i + 1}.", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = ImperialGold)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(step, fontSize = 11.sp, color = TextPrimary)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MarketNewsCard(news: MarketNewsItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, GoldBorder.copy(alpha = 0.5f), RoundedCornerShape(16.dp)),
        colors = CardDefaults.cardColors(containerColor = ObsidianCardElevated),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                val badgeColor = when (news.sentiment) {
                    MarketSentiment.BULLISH -> EmeraldProfit
                    MarketSentiment.BEARISH -> CrimsonLoss
                    MarketSentiment.NEUTRAL -> ImperialGold
                }
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(badgeColor.copy(alpha = 0.15f))
                        .border(1.dp, badgeColor.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Text(news.sentiment.name, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = badgeColor)
                }

                Spacer(modifier = Modifier.width(8.dp))
                Text(news.source, fontSize = 11.sp, color = TextTertiary)
                Spacer(modifier = Modifier.weight(1f))
                Text(news.timeAgo, fontSize = 10.sp, color = TextTertiary)
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = news.title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )

            Text(
                text = news.summary,
                fontSize = 12.sp,
                color = TextSecondary,
                modifier = Modifier.padding(top = 4.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color(0xFF121217))
                    .padding(10.dp)
            ) {
                Row(verticalAlignment = Alignment.Top) {
                    Icon(imageVector = Icons.Default.TrendingUp, contentDescription = null, tint = ImperialGold, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text("Trader's Key Takeaway:", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = ImperialGold)
                        Text(news.keyTakeawayForTraders, fontSize = 11.sp, color = TextPrimary, lineHeight = 15.sp)
                    }
                }
            }
        }
    }
}
