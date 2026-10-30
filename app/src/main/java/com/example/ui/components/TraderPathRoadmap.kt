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
        description = "Log your daily income and living expenses, calculate annual savings velocity, and build your primary investment capital vault.",
        icon = Icons.Default.AccountBalance,
        nextActionText = "Open Money Vault",
        stepChecklist = listOf(
            "Record daily income streams & side hustles",
            "Track living expenses & capital burn",
            "Monitor Net Savings Run-Rate"
        ),
        targetTab = "home"
    ),
    RoadmapStage(
        stageNumber = 2,
        title = "Market Basics",
        subtitle = "Historical & Structural Foundations",
        description = "From the 1602 Amsterdam VOC stock exchange to equities, bonds, order books, and centralized vs. decentralized markets.",
        icon = Icons.Default.MenuBook,
        nextActionText = "Explore Market Basics",
        stepChecklist = listOf(
            "1602 VOC to Modern Electronic Exchanges",
            "Equities vs. Sovereign & Corporate Bonds",
            "Centralized Order Books vs. DeFi DEXs",
            "Demystified Institutional Terminology"
        ),
        targetTab = "masterclass"
    ),
    RoadmapStage(
        stageNumber = 3,
        title = "Analysis & Charts",
        subtitle = "Macro Radar & Wealth Multipliers",
        description = "Analyze regional economic criteria (India INR ₹, RBI rates, inflation), live market sentiment, and compound wealth growth models.",
        icon = Icons.Default.TrendingUp,
        nextActionText = "Explore Analysis & Charts",
        stepChecklist = listOf(
            "Evaluate RBI repo rate, CPI inflation & tax slabs",
            "Simulate 15% CAGR Stock Wealth Multiplier",
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
        nextActionText = "Launch Trading Simulator",
        stepChecklist = listOf(
            "Practice placing Market & Limit buy/sell orders",
            "Set protective Stop-Loss & Take-Profit exits",
            "Consult Gemini AI Market Mentor on portfolio posture"
        ),
        targetTab = "simulation"
    )
)

/**
 * Apple HIG Clean Frosted Glass Roadmap Section.
 * Free of cluttered badges or side pills. Focused on generous whitespace,
 * elegant SF typography, and effortless clarity.
 */
@Composable
fun TraderPathRoadmapSection(
    currentStage: Int,
    completedStages: Set<Int>,
    onSelectStage: (RoadmapStage) -> Unit,
    onSkipToStage: (Int) -> Unit,
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
            Text(
                text = "Stage $currentStage of 4",
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = SlateMuted
            )
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
                onSkip = { onSkipToStage(stage.stageNumber) }
            )
        }
    }
}

/**
 * Minimalist Apple Frosted Glass Card for Learning Stages.
 * Soft drop shadow, hairline border, clean SF typography, smooth flat buttons.
 */
@Composable
fun AppleRoadmapStageCard(
    stage: RoadmapStage,
    isCompleted: Boolean,
    isActive: Boolean,
    isUnlocked: Boolean,
    onClick: () -> Unit,
    onSkip: () -> Unit,
    modifier: Modifier = Modifier
) {
    val cardBackground = if (isActive) {
        Color.White.copy(alpha = 0.95f)
    } else {
        Color.White.copy(alpha = 0.80f)
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
                shape = RoundedCornerShape(20.dp),
                spotColor = Color(0x12000000),
                ambientColor = Color(0x06000000)
            )
            .clip(RoundedCornerShape(20.dp))
            .background(cardBackground)
            .border(BorderStroke(1.dp, cardBorder), RoundedCornerShape(20.dp))
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
                    // Minimalist Stage Circle
                    Box(
                        modifier = Modifier
                            .size(34.dp)
                            .clip(CircleShape)
                            .background(
                                when {
                                    isCompleted -> SageGreenContainer
                                    isActive -> TrustBlue.copy(alpha = 0.1f)
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
                                modifier = Modifier.size(16.dp)
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
                        Text(
                            text = "STAGE ${stage.stageNumber}",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isActive) TrustBlue else SlateMuted,
                            letterSpacing = 0.8.sp
                        )
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
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                stage.stepChecklist.forEach { step ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(vertical = 1.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(4.dp)
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

            Spacer(modifier = Modifier.height(16.dp))

            // Smooth Flat Action Button
            if (isUnlocked) {
                Button(
                    onClick = onClick,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isActive) TrustBlue else CreamContainer,
                        contentColor = if (isActive) Color.White else SlateCharcoal
                    ),
                    shape = RoundedCornerShape(12.dp),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(42.dp)
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
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
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
