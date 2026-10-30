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
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.AutoGraph
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.BrushedGold
import com.example.ui.theme.CitadelCard
import com.example.ui.theme.CitadelCardElevated
import com.example.ui.theme.CreamBackground
import com.example.ui.theme.CreamContainer
import com.example.ui.theme.CreamSurface
import com.example.ui.theme.GoldBorder
import com.example.ui.theme.GoldGleam
import com.example.ui.theme.MetallicGoldBorder
import com.example.ui.theme.ObsidianBlack
import com.example.ui.theme.SageGreen
import com.example.ui.theme.SageGreenAccent
import com.example.ui.theme.SageGreenBorder
import com.example.ui.theme.SageGreenContainer
import com.example.ui.theme.SageGreenText
import com.example.ui.theme.SlateCharcoal
import com.example.ui.theme.SlateMuted
import com.example.ui.theme.TrustBlue

data class RoadmapStage(
    val stageNumber: Int,
    val title: String,
    val subtitle: String,
    val description: String,
    val icon: ImageVector,
    val nextActionText: String,
    val stepChecklist: List<String>,
    val targetTab: String
)

val TRADER_ROADMAP_STAGES = listOf(
    RoadmapStage(
        stageNumber = 1,
        title = "Money Foundations & Income Tracker",
        subtitle = "Master Cashflow Velocity",
        description = "Log your daily income & expenses, calculate annual savings velocity, and build your investment capital vault.",
        icon = Icons.Default.AccountBalance,
        nextActionText = "Log Cashflow & Set Target",
        stepChecklist = listOf(
            "Record daily income streams & side hustles",
            "Categorize daily expenses & overhead",
            "Monitor Net Savings & Annual Run-Rate"
        ),
        targetTab = "home"
    ),
    RoadmapStage(
        stageNumber = 2,
        title = "Market Basics",
        subtitle = "Historical & Structural Foundations",
        description = "From the 1602 Amsterdam VOC stock exchange to equities, bonds, order books, and centralized vs. decentralized markets.",
        icon = Icons.Default.MenuBook,
        nextActionText = "Study Market Masterclass",
        stepChecklist = listOf(
            "1602 VOC to Modern Electronic Exchanges",
            "Equities vs. Sovereign & Corporate Bonds",
            "Centralized Order Books vs. DeFi DEXs",
            "Institutional Order Flow & Demystified Glossary"
        ),
        targetTab = "masterclass"
    ),
    RoadmapStage(
        stageNumber = 3,
        title = "Analysis & Charts",
        subtitle = "Macro Radar & Wealth Multipliers",
        description = "Analyze regional economic criteria (India INR ₹, RBI rates, inflation), live market sentiment, and compound stock calculators.",
        icon = Icons.Default.TrendingUp,
        nextActionText = "Analyze Market Radar & Multiplier",
        stepChecklist = listOf(
            "Evaluate RBI repo rate, CPI inflation & tax slabs",
            "Run 15% CAGR Stock Wealth Multiplier simulation",
            "Review official stock market news & sentiment trends"
        ),
        targetTab = "analytics"
    ),
    RoadmapStage(
        stageNumber = 4,
        title = "Simulated Execution",
        subtitle = "Paper Trading & AI Mentor",
        description = "Deploy virtual ₹1,00,000 cash in simulated real-time equity execution with stop-loss orders and AI Mentor consultation.",
        icon = Icons.Default.AutoGraph,
        nextActionText = "Execute Paper Trade",
        stepChecklist = listOf(
            "Practice placing Market & Limit buy/sell orders",
            "Set protective Stop-Loss & Take-Profit exits",
            "Consult Gemini AI Market Mentor on portfolio posture",
            "Earn Sovereign Master Trader Certification"
        ),
        targetTab = "simulation"
    )
)

@Composable
fun TraderPathRoadmapSection(
    currentStage: Int,
    completedStages: Set<Int>,
    onSelectStage: (RoadmapStage) -> Unit,
    onSkipToStage: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val totalProgress = completedStages.size / 4f

    Column(modifier = modifier.fillMaxWidth()) {
        // Roadmap Header with Progress Bar
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .clip(CircleShape)
                            .background(SageGreen)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "THE TRADER’S PATH",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = BrushedGold,
                        letterSpacing = 1.sp
                    )
                }
                Text(
                    text = "Structured 4-Stage Mastery Roadmap",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = SlateCharcoal
                )
            }

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(SageGreenContainer)
                    .border(BorderStroke(1.dp, SageGreenBorder), RoundedCornerShape(20.dp))
                .padding(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Text(
                    text = "${(totalProgress * 100).toInt()}% COMPLETED",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = SageGreenText
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Progress Track
        LinearProgressIndicator(
            progress = { totalProgress },
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp)),
            color = SageGreen,
            trackColor = CreamContainer
        )

        Text(
            text = "Step-by-step sequential path. Complete each stage to unlock advanced market privileges.",
            fontSize = 12.sp,
            color = SlateMuted,
            modifier = Modifier.padding(top = 6.dp, bottom = 14.dp)
        )

        // 4 Sequential Stage Cards in Citadel Aesthetic
        TRADER_ROADMAP_STAGES.forEach { stage ->
            val isCompleted = completedStages.contains(stage.stageNumber)
            val isActive = currentStage == stage.stageNumber
            val isUnlocked = isCompleted || isActive || stage.stageNumber <= (completedStages.maxOrNull() ?: 0) + 1

            RoadmapStageCard(
                stage = stage,
                isCompleted = isCompleted,
                isActive = isActive,
                isUnlocked = isUnlocked,
                onClick = { onSelectStage(stage) },
                onSkip = { onSkipToStage(stage.stageNumber) },
                modifier = Modifier.padding(vertical = 7.dp)
            )
        }
    }
}

@Composable
fun RoadmapStageCard(
    stage: RoadmapStage,
    isCompleted: Boolean,
    isActive: Boolean,
    isUnlocked: Boolean,
    onClick: () -> Unit,
    onSkip: () -> Unit,
    modifier: Modifier = Modifier
) {
    val borderColor = when {
        isCompleted -> SageGreen
        isActive -> MetallicGoldBorder
        isUnlocked -> BrushedGold.copy(alpha = 0.5f)
        else -> Color(0xFF2E2E38)
    }

    val glowAlpha = when {
        isActive -> 0.45f
        isCompleted -> 0.25f
        else -> 0.15f
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = if (isActive) 1.5.dp else 1.dp,
                color = borderColor,
                shape = CitadelCardShape
            )
            .clickable(enabled = isUnlocked) { onClick() },
        shape = CitadelCardShape,
        colors = CardDefaults.cardColors(containerColor = CitadelCard),
        elevation = CardDefaults.cardElevation(defaultElevation = if (isActive) 6.dp else 2.dp)
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            // Citadel dark gradient overlay
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(
                        Brush.horizontalGradient(
                            listOf(
                                ObsidianBlack,
                                if (isActive) Color(0xFF1E1A10) else CitadelCardElevated
                            )
                        )
                    )
            )

            Column(modifier = Modifier.padding(16.dp)) {
                // Top Row: Stage Indicator & Status Badge
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        // Stage Number Badge
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CutCornerShape(6.dp))
                                .background(
                                    when {
                                        isCompleted -> SageGreen
                                        isActive -> BrushedGold
                                        else -> Color(0xFF2A2A36)
                                    }
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            if (isCompleted) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = "Completed",
                                    tint = Color.White,
                                    modifier = Modifier.size(18.dp)
                                )
                            } else {
                                Text(
                                    text = "${stage.stageNumber}",
                                    fontWeight = FontWeight.ExtraBold,
                                    fontSize = 15.sp,
                                    color = if (isActive) ObsidianBlack else Color.White
                                )
                            }
                        }

                        Spacer(modifier = Modifier.width(10.dp))

                        Column {
                            Text(
                                text = "STAGE ${stage.stageNumber}",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isActive) GoldGleam else SlateMuted,
                                letterSpacing = 0.8.sp
                            )
                            Text(
                                text = stage.title,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }

                    // Status Badge
                    when {
                        isCompleted -> {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(SageGreen.copy(alpha = 0.2f))
                                    .border(BorderStroke(1.dp, SageGreen), RoundedCornerShape(12.dp))
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.CheckCircle,
                                        contentDescription = null,
                                        tint = SageGreen,
                                        modifier = Modifier.size(12.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = "COMPLETED",
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = SageGreen
                                    )
                                }
                            }
                        }
                        isActive -> {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(BrushedGold.copy(alpha = 0.2f))
                                    .border(BorderStroke(1.dp, BrushedGold), RoundedCornerShape(12.dp))
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    text = "LEARN NOW",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = GoldGleam
                                )
                            }
                        }
                        else -> {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Lock,
                                    contentDescription = "Locked",
                                    tint = SlateMuted,
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "NEXT",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = SlateMuted
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Description
                Text(
                    text = stage.description,
                    fontSize = 12.sp,
                    color = Color(0xFFC7C7D4),
                    lineHeight = 17.sp
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Step checklist highlights
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(6.dp))
                        .background(Color(0xFF14141E))
                        .padding(horizontal = 10.dp, vertical = 8.dp)
                ) {
                    stage.stepChecklist.forEach { step ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(vertical = 2.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(5.dp)
                                    .clip(CircleShape)
                                    .background(if (isCompleted) SageGreen else BrushedGold)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = step,
                                fontSize = 11.sp,
                                color = if (isCompleted) Color(0xFFA0AEC0) else Color(0xFFE2E8F0)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Bottom Action Button
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (isUnlocked) {
                        Button(
                            onClick = onClick,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (isActive) TrustBlue else Color(0xFF242436),
                                contentColor = Color.White
                            ),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = if (isCompleted) Icons.Default.Check else Icons.Default.PlayArrow,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = if (isCompleted) "Review Stage ${stage.stageNumber}" else stage.nextActionText,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp
                                )
                            }
                        }
                    } else {
                        OutlinedButton(
                            onClick = onSkip,
                            border = BorderStroke(1.dp, BrushedGold.copy(alpha = 0.5f)),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = "Already know this? Fast-Track",
                                color = GoldGleam,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }
        }
    }
}
