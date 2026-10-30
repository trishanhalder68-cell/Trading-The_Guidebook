package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import com.example.data.model.MarketTerm
import com.example.ui.components.GoldSectionHeader
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

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun GlossaryScreen(
    viewModel: WealthViewModel
) {
    val terms by viewModel.marketTerms.collectAsState()
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("All") }

    val categories = listOf("All", "Basics", "Valuation", "Technical Analysis", "Risk Management", "Income", "Derivatives")

    val filteredTerms = remember(terms, searchQuery, selectedCategory) {
        terms.filter { term ->
            val matchesCategory = selectedCategory == "All" || term.category.equals(selectedCategory, ignoreCase = true)
            val matchesSearch = searchQuery.isBlank() ||
                    term.term.contains(searchQuery, ignoreCase = true) ||
                    term.simpleDefinition.contains(searchQuery, ignoreCase = true)
            matchesCategory && matchesSearch
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(ObsidianBlack)
            .padding(horizontal = 18.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(16.dp))

            GoldSectionHeader(
                title = "Benevolent Market Guidebook",
                subtitle = "Demystifying complex stock trade jargon into crystal clarity",
                badgeText = "${terms.size} Concepts"
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Search Bar
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Search term (e.g. P/E, RSI, Call Options, CAGR)...") },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Search, contentDescription = null, tint = ImperialGold)
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = ImperialGold,
                    unfocusedBorderColor = GoldBorder,
                    focusedTextColor = TextPrimary,
                    unfocusedTextColor = TextPrimary
                ),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Category Filter FlowRow
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                categories.forEach { cat ->
                    val isSelected = selectedCategory == cat
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .background(if (isSelected) ImperialGold else ObsidianCardElevated)
                            .border(1.dp, if (isSelected) ImperialGold else GoldBorder, RoundedCornerShape(10.dp))
                            .clickable { selectedCategory = cat }
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = cat,
                            fontSize = 11.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            color = if (isSelected) ObsidianBlack else TextPrimary
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }

        if (filteredTerms.isEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 40.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No matching trading concepts found.", color = TextTertiary, fontSize = 13.sp)
                }
            }
        } else {
            items(filteredTerms, key = { it.term }) { term ->
                BenevolentTermCard(term = term)
                Spacer(modifier = Modifier.height(10.dp))
            }
        }

        item {
            Spacer(modifier = Modifier.height(90.dp))
        }
    }
}

@Composable
fun BenevolentTermCard(term: MarketTerm) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, GoldBorder.copy(alpha = 0.6f), RoundedCornerShape(16.dp))
            .clickable { expanded = !expanded },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = ObsidianCardElevated)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(ImperialGold.copy(alpha = 0.15f))
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Text(term.category, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = GoldGleam)
                }
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    imageVector = if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = null,
                    tint = ImperialGold
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = term.term,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )

            Text(
                text = term.simpleDefinition,
                fontSize = 12.sp,
                color = TextSecondary,
                lineHeight = 17.sp,
                modifier = Modifier.padding(top = 4.dp)
            )

            AnimatedVisibility(visible = expanded) {
                Column(modifier = Modifier.padding(top = 12.dp)) {
                    // Real World Example
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFF131317))
                            .border(1.dp, GoldBorder.copy(alpha = 0.4f), RoundedCornerShape(12.dp))
                            .padding(12.dp)
                    ) {
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(imageVector = Icons.Default.Lightbulb, contentDescription = null, tint = ImperialGold, modifier = Modifier.size(15.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Real-World Lucrative Example:", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = ImperialGold)
                            }
                            Text(term.realWorldExample, fontSize = 11.sp, color = TextPrimary, lineHeight = 16.sp, modifier = Modifier.padding(top = 4.dp))
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Why it matters
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFF101C17))
                            .border(1.dp, EmeraldProfit.copy(alpha = 0.4f), RoundedCornerShape(12.dp))
                            .padding(12.dp)
                    ) {
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(imageVector = Icons.Default.AutoAwesome, contentDescription = null, tint = EmeraldProfit, modifier = Modifier.size(15.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Why It Matters To You:", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = EmeraldProfit)
                            }
                            Text(term.whyItMattersToYou, fontSize = 11.sp, color = TextPrimary, lineHeight = 16.sp, modifier = Modifier.padding(top = 4.dp))
                        }
                    }
                }
            }
        }
    }
}
