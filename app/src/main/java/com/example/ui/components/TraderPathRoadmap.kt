package com.example.ui.components

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.AutoGraph
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.CreamBackground
import com.example.ui.theme.CreamBorder
import com.example.ui.theme.CreamContainer
import com.example.ui.theme.CreamSurface
import com.example.ui.theme.SageGreen
import com.example.ui.theme.SageGreenContainer
import com.example.ui.theme.SageGreenText
import com.example.ui.theme.SlateCharcoal
import com.example.ui.theme.SlateDark
import com.example.ui.theme.SlateMuted
import com.example.ui.theme.SlateSubtle
import com.example.ui.theme.TrustBlue

import com.example.ui.theme.Squircle12
import com.example.ui.theme.Squircle16
import com.example.ui.theme.Squircle20
import com.example.ui.theme.Squircle24

data class RoadmapStage(
    val stageNumber: Int,
    val title: String,
    val subtitle: String,
    val description: String,
    val icon: ImageVector,
    val nextActionText: String,
    val stepChecklist: List<String>,
    val targetTab: String,
    val tierLabel: String = "Free Core",
    val isProTier: Boolean = false
)

val TRADER_ROADMAP_STAGES = listOf(
    RoadmapStage(
        stageNumber = 1,
        title = "Money Foundations & Cashflow",
        subtitle = "Master Cashflow Velocity",
        description = "Level 1: Core foundational track. Track daily income streams and burn rate to capitalize your primary trading account.",
        icon = Icons.Default.AccountBalance,
        nextActionText = "Open Money Vault",
        stepChecklist = listOf(
            "Record daily income streams & side hustles",
            "Track living expenses & capital burn",
            "Monitor Net Savings Run-Rate"
        ),
        targetTab = "home",
        tierLabel = "Level 1 • Free Track",
        isProTier = false
    ),
    RoadmapStage(
        stageNumber = 2,
        title = "Candlesticks & Market Mechanics",
        subtitle = "Historical & Structural Foundations",
        description = "Level 2: Deep dive into order book liquidity, candlestick dynamics, centralized exchange architecture, and institutional mechanics.",
        icon = Icons.Default.MenuBook,
        nextActionText = "Explore Candlestick Basics",
        stepChecklist = listOf(
            "1602 VOC to Modern Electronic Order Books",
            "Equities vs. Sovereign Debt & Commodities",
            "Centralized Order Books & Spread Mechanics",
            "Institutional Smart Money Concepts (SMC)"
        ),
        targetTab = "masterclass",
        tierLabel = "Level 2 • Pro Pass ₹129",
        isProTier = true
    ),
    RoadmapStage(
        stageNumber = 3,
        title = "Macro Analysis & Multipliers",
        subtitle = "Macro Radar & Wealth Multipliers",
        description = "Level 3: Regional economic criteria (India INR ₹, RBI repo rates, CPI inflation), institutional sentiment radar, and 15% CAGR wealth models.",
        icon = Icons.Default.TrendingUp,
        nextActionText = "Explore Analysis & Charts",
        stepChecklist = listOf(
            "Evaluate RBI repo rate, CPI inflation & tax slabs",
            "Simulate 15% CAGR Stock Wealth Multiplier",
            "Review official market sentiment trends"
        ),
        targetTab = "analytics",
        tierLabel = "Level 3 • Pro Pass ₹129",
        isProTier = true
    ),
    RoadmapStage(
        stageNumber = 4,
        title = "Simulated Execution & AI Mentor",
        subtitle = "Paper Trading & AI Strategy",
        description = "Deploy virtual ₹1,00,000 cash in simulated real-time equity execution with stop-loss orders and AI Mentor posture consultation.",
        icon = Icons.Default.AutoGraph,
        nextActionText = "Launch Trading Simulator",
        stepChecklist = listOf(
            "Practice placing Market & Limit buy/sell orders",
            "Set protective Stop-Loss & Take-Profit exits",
            "Consult Gemini AI Market Mentor on portfolio posture"
        ),
        targetTab = "simulation",
        tierLabel = "Execution Sandbox",
        isProTier = false
    )
)

/**
 * Apple HIG Clean Frosted Glass Roadmap Section.
 * Implements continuous squircle geometry, living spring physics,
 * and high-end non-intrusive micro-transaction triggers for Level 2 & 3.
 */
@Composable
fun TraderPathRoadmapSection(
    currentStage: Int,
    completedStages: Set<Int>,
    onSelectStage: (RoadmapStage) -> Unit,
    onSkipToStage: (Int) -> Unit,
    onOpenCheckout: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Section Header with ultra-clean typography
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "THE TRADER'S PATH",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = TrustBlue,
                    letterSpacing = 1.sp
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "Curriculum Roadmap",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = SlateCharcoal
                )
            }

            // Clean subtle progress indicator
            Box(
                modifier = Modifier
                    .clip(Squircle12)
                    .background(CreamContainer)
                    .padding(horizontal = 10.dp, vertical = 4.dp)
            ) {
                Text(
                    text = "Stage $currentStage of 4",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = SlateMuted
                )
            }
        }

        // Sequential Stage Cards
        TRADER_ROADMAP_STAGES.forEach { stage ->
            val isCompleted = completedStages.contains(stage.stageNumber)
            val isActive = currentStage == stage.stageNumber
            val isUnlocked = isCompleted || isActive || stage.stageNumber <= (completedStages.maxOrNull() ?: 0) + 1

            AppleRoadmapStageCard(
                stage = stage,
                isCompleted = isCompleted,
                isActive = isActive,
                isUnlocked = isUnlocked,
                onClick = { onSelectStage(stage) },
                onSkip = { onSkipToStage(stage.stageNumber) },
                onUpgrade = onOpenCheckout
            )
        }
    }
}

/**
 * Minimalist Apple Frosted Glass Card for Learning Stages with continuous squircle curvature.
 */
@Composable
fun AppleRoadmapStageCard(
    stage: RoadmapStage,
    isCompleted: Boolean,
    isActive: Boolean,
    isUnlocked: Boolean,
    onClick: () -> Unit,
    onSkip: () -> Unit,
    onUpgrade: () -> Unit,
    modifier: Modifier = Modifier
) {
    val cardBackground = if (isActive) {
        Color.White.copy(alpha = 0.96f)
    } else {
        Color.White.copy(alpha = 0.85f)
    }

    val cardBorder = if (isActive) {
        TrustBlue.copy(alpha = 0.35f)
    } else {
        Color(0xFFE2E8F0)
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                elevation = if (isActive) 6.dp else 2.dp,
                shape = Squircle24,
                spotColor = Color(0x12000000),
                ambientColor = Color(0x06000000)
            )
            .clip(Squircle24)
            .background(cardBackground)
            .border(BorderStroke(1.dp, cardBorder), Squircle24)
            .clickable(enabled = isUnlocked, onClick = onClick)
            .padding(20.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            // Stage Number & Header Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Minimalist Squircle Stage Badge
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(Squircle12)
                            .background(
                                when {
                                    isCompleted -> SageGreenContainer
                                    isActive -> TrustBlue.copy(alpha = 0.12f)
                                    else -> CreamContainer
                                }
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        if (isCompleted) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = "Completed",
                                tint = SageGreenText,
                                modifier = Modifier.size(18.dp)
                            )
                        } else {
                            Text(
                                text = "0${stage.stageNumber}",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isActive) TrustBlue else SlateMuted
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "STAGE ${stage.stageNumber}",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isActive) TrustBlue else SlateMuted,
                                letterSpacing = 0.8.sp
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            // Tier indicator pill with continuous squircle
                            Box(
                                modifier = Modifier
                                    .clip(Squircle12)
                                    .background(
                                        if (stage.isProTier) Color(0xFFFBF4E4) else SageGreenContainer
                                    )
                                    .padding(horizontal = 7.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text = stage.tierLabel,
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (stage.isProTier) Color(0xFF9E7715) else SageGreenText
                                )
                            }
                        }
                        Text(
                            text = stage.title,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = SlateCharcoal
                        )
                    }
                }

                // Minimalist State Icon / Label
                if (isCompleted) {
                    Text(
                        text = "Completed",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium,
                        color = SageGreenText
                    )
                } else if (!isUnlocked) {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Locked",
                        tint = SlateSubtle,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Clean Description
            Text(
                text = stage.description,
                fontSize = 13.sp,
                color = SlateMuted,
                lineHeight = 18.sp
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Clean, uncluttered checklist items
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                stage.stepChecklist.forEach { step ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(vertical = 1.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(5.dp)
                                .clip(CircleShape)
                                .background(if (isActive) TrustBlue else SlateSubtle)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = step,
                            fontSize = 11.sp,
                            color = if (isActive) SlateDark else SlateMuted
                        )
                    }
                }
            }

            // High-End Freemium Micro-Transaction Upgrade Trigger for Level 2 & Level 3
            if (stage.isProTier && !isCompleted) {
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(Squircle12)
                        .background(Color(0xFFFBF4E4).copy(alpha = 0.8f))
                        .border(BorderStroke(1.dp, Color(0xFFE6D3A3)), Squircle12)
                        .clickable { onUpgrade() }
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "⚡",
                            fontSize = 13.sp
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Unlock Level ${stage.stageNumber} • ₹129 Micro-Pass",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF7A570C)
                        )
                    }
                    Text(
                        text = "Instant Access →",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF9E7715)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Smooth Flat Action Button with Squircle and Spring Physics
            if (isUnlocked) {
                Button(
                    onClick = onClick,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isActive) TrustBlue else CreamContainer,
                        contentColor = if (isActive) Color.White else SlateCharcoal
                    ),
                    shape = Squircle16,
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp)
                        .appleSpringPress(onClick = onClick)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = if (isCompleted) "Review Stage ${stage.stageNumber}" else stage.nextActionText,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 13.sp
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = null,
                            modifier = Modifier.size(15.dp)
                        )
                    }
                }
            } else {
                OutlinedButton(
                    onClick = onSkip,
                    border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
                    shape = Squircle16,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(42.dp)
                        .appleSpringPress(onClick = onSkip)
                ) {
                    Text(
                        text = "Fast-track to this stage",
                        color = SlateMuted,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}
