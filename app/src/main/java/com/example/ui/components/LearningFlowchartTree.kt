package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Stars
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.BrushedGold
import com.example.ui.theme.EmeraldProfit
import com.example.ui.theme.GoldGleam
import com.example.ui.theme.SageGreen
import com.example.ui.theme.SageGreenContainer
import com.example.ui.theme.SageGreenText
import com.example.ui.theme.SlateCharcoal
import com.example.ui.theme.SlateDark
import com.example.ui.theme.SlateMuted
import com.example.ui.theme.SlateSubtle
import com.example.ui.theme.Squircle12
import com.example.ui.theme.Squircle16
import com.example.ui.theme.Squircle20
import com.example.ui.theme.Squircle24
import com.example.ui.theme.Squircle28
import com.example.ui.theme.TrustBlue
import com.example.ui.theme.TrustBlueContainer

data class TreeLessonNode(
    val id: String,
    val orderNumber: Int,
    val title: String,
    val conceptHeadline: String,
    val description: String,
    val icon: ImageVector,
    val levelTier: String,
    val isFree: Boolean,
    val badgeIconEmoji: String,
    val badgeTitle: String,
    val perkUnlockedText: String,
    val masterclassTargetId: String
)

val CURRICULUM_TREE_NODES = listOf(
    TreeLessonNode(
        id = "tree_lesson_1",
        orderNumber = 1,
        title = "The 1602 VOC Genesis & Double Auction",
        conceptHeadline = "How shares turn paper savings into permanent business ownership",
        description = "Discover the electronic auction house mechanics: why buyers place 'Bids' and sellers place 'Asks', and how capital compounding outpaces central bank currency debasement.",
        icon = Icons.Default.Public,
        levelTier = "Level 1 • Free",
        isFree = true,
        badgeIconEmoji = "🌱",
        badgeTitle = "Seedling Trader",
        perkUnlockedText = "Market Structure Literacy (+20% Readiness)",
        masterclassTargetId = "ch_1"
    ),
    TreeLessonNode(
        id = "tree_lesson_2",
        orderNumber = 2,
        title = "Candlestick Anatomy & Price Mechanics",
        conceptHeadline = "Reading the emotional battlefield of wicks, bodies & institutional volume",
        description = "Master Open-High-Low-Close Japanese candlesticks, bullish/bearish engulfing patterns, rejection wicks at major support, and true volume confirmation.",
        icon = Icons.Default.Visibility,
        levelTier = "Level 1 • Free",
        isFree = true,
        badgeIconEmoji = "🕯️",
        badgeTitle = "Candlestick Reader",
        perkUnlockedText = "Price Action Vision Matrix (+20% Readiness)",
        masterclassTargetId = "ch_2"
    ),
    TreeLessonNode(
        id = "tree_lesson_3",
        orderNumber = 3,
        title = "The Iron Law of Risk: The 1% Rule",
        conceptHeadline = "Never lose sleep: mathematically guaranteed survival across 100 trades",
        description = "Master dynamic position sizing where no single trade risks more than 1% of total equity. Calculate strict 1:2.5 risk-to-reward ratios before placing orders.",
        icon = Icons.Default.Security,
        levelTier = "Level 2 • Pro Pass",
        isFree = false,
        badgeIconEmoji = "🛡️",
        badgeTitle = "Risk Architect",
        perkUnlockedText = "Drawdown Immunity Protocol (+20% Readiness)",
        masterclassTargetId = "ch_3"
    ),
    TreeLessonNode(
        id = "tree_lesson_4",
        orderNumber = 4,
        title = "Trend Confluence: 200 EMA & VWAP",
        conceptHeadline = "Riding the institutional tidal waves with high-probability breakout filters",
        description = "Learn why major pension funds and hedge funds benchmark trades to the Volume-Weighted Average Price (VWAP) and the 200-day Exponential Moving Average.",
        icon = Icons.Default.TrendingUp,
        levelTier = "Level 2 • Pro Pass",
        isFree = false,
        badgeIconEmoji = "⚡",
        badgeTitle = "Breakout Tactician",
        perkUnlockedText = "Algorithmic Momentum Screener (+20% Readiness)",
        masterclassTargetId = "ch_4"
    ),
    TreeLessonNode(
        id = "tree_lesson_5",
        orderNumber = 5,
        title = "Live Execution Clearance: The Apex Citadel",
        conceptHeadline = "Full emotional sovereignty, journaling, and live trade qualification",
        description = "Complete the final milestone checklist: sizing execution, risk-to-reward verification, emotionless order placement, and live capital clearance.",
        icon = Icons.Default.Stars,
        levelTier = "Level 3 • Apex Mastery",
        isFree = false,
        badgeIconEmoji = "👑",
        badgeTitle = "Citadel Sovereign",
        perkUnlockedText = "Official Live Market Clearance (+20% Readiness)",
        masterclassTargetId = "ch_6"
    )
)

/**
 * Interactive Learning Flowchart Tree.
 * Turns ripe vibrant emerald green as lessons are completed.
 * Features Apple-HIG squircle cards, spring animations, badges shelf, and Trading Readiness Score.
 */
@Composable
fun LearningFlowchartTree(
    completedLessonIds: Set<String>,
    onCompleteLesson: (String) -> Unit,
    onNavigateToMasterclassChapter: (String) -> Unit,
    onOpenUpgradeModal: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedNodeId by remember { mutableStateOf<String?>(null) }

    val totalNodes = CURRICULUM_TREE_NODES.size
    val completedCount = CURRICULUM_TREE_NODES.count { completedLessonIds.contains(it.id) }
    val readinessPercentage = ((completedCount.toFloat() / totalNodes) * 100f).toInt()
    val isBattleReady = completedCount >= totalNodes

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        // =========================================================================
        // TRADING READINESS GAUGE CARD
        // =========================================================================
        AppleGlassCard(
            modifier = Modifier.fillMaxWidth(),
            shape = Squircle28,
            backgroundColor = Color.White.copy(alpha = 0.95f),
            borderColor = if (isBattleReady) EmeraldProfit.copy(alpha = 0.4f) else Color(0xFFE2E8F0),
            elevation = 6.dp
        ) {
            Column(modifier = Modifier.padding(22.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "LIVE TRADING READINESS",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isBattleReady) EmeraldProfit else TrustBlue,
                            letterSpacing = 1.3.sp
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = if (isBattleReady) "Battle-Ready Sovereign" else "Curriculum Tree Progress",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = SlateCharcoal
                        )
                    }

                    Box(
                        modifier = Modifier
                            .clip(Squircle16)
                            .background(if (isBattleReady) SageGreenContainer else TrustBlueContainer)
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = "$readinessPercentage%",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = if (isBattleReady) SageGreenText else TrustBlue
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Smooth Progress Bar
                LinearProgressIndicator(
                    progress = { completedCount.toFloat() / totalNodes.toFloat() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .clip(Squircle12),
                    color = if (isBattleReady) EmeraldProfit else TrustBlue,
                    trackColor = Color(0xFFE2E8F0)
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Milestone description
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = if (isBattleReady) Icons.Default.CheckCircle else Icons.Default.AutoAwesome,
                        contentDescription = null,
                        tint = if (isBattleReady) EmeraldProfit else BrushedGold,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = when {
                            isBattleReady -> "CLEARANCE GRANTED: You are mathematically prepared for live markets!"
                            completedCount >= 3 -> "Advanced Tactician: Mastering indicators. Complete final stages for live clearance."
                            completedCount >= 1 -> "Practitioner: Risk & Price action tree active. Keep ripening branches!"
                            else -> "Apprentice: Paper trading only. Complete Node 1 to ignite your tree!"
                        },
                        fontSize = 12.sp,
                        color = SlateDark,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }

        // =========================================================================
        // EARNED BADGES & TRADING PERKS SHELF
        // =========================================================================
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Unlocked Trader Badges & Perks",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = SlateCharcoal
                )
                Text(
                    text = "$completedCount of $totalNodes Earned",
                    fontSize = 11.sp,
                    color = SlateMuted
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(CURRICULUM_TREE_NODES) { node ->
                    val isEarned = completedLessonIds.contains(node.id)
                    val badgeBorderColor by animateColorAsState(
                        targetValue = if (isEarned) EmeraldProfit.copy(alpha = 0.5f) else Color(0xFFCBD5E1),
                        animationSpec = spring(stiffness = Spring.StiffnessLow),
                        label = "badge_border"
                    )

                    Box(
                        modifier = Modifier
                            .shadow(
                                elevation = if (isEarned) 4.dp else 1.dp,
                                shape = Squircle16,
                                spotColor = if (isEarned) EmeraldProfit.copy(alpha = 0.2f) else Color.Transparent
                            )
                            .clip(Squircle16)
                            .background(
                                if (isEarned) {
                                    Brush.verticalGradient(
                                        listOf(
                                            Color.White,
                                            SageGreenContainer.copy(alpha = 0.4f)
                                        )
                                    )
                                } else {
                                    Brush.verticalGradient(
                                        listOf(
                                            Color.White.copy(alpha = 0.6f),
                                            Color(0xFFF1F5F9).copy(alpha = 0.5f)
                                        )
                                    )
                                }
                            )
                            .border(BorderStroke(1.dp, badgeBorderColor), Squircle16)
                            .clickable { selectedNodeId = node.id }
                            .padding(horizontal = 14.dp, vertical = 10.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = node.badgeIconEmoji,
                                fontSize = 18.sp,
                                modifier = Modifier.scale(if (isEarned) 1.15f else 0.85f)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text(
                                    text = node.badgeTitle,
                                    fontSize = 12.sp,
                                    fontWeight = if (isEarned) FontWeight.Bold else FontWeight.Medium,
                                    color = if (isEarned) SlateCharcoal else SlateMuted
                                )
                                Text(
                                    text = if (isEarned) "Active Perk" else "Locked",
                                    fontSize = 10.sp,
                                    color = if (isEarned) SageGreenText else SlateSubtle,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                    }
                }
            }
        }

        // =========================================================================
        // THE INTERACTIVE TREE FLOWCHART
        // =========================================================================
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            CURRICULUM_TREE_NODES.forEachIndexed { index, node ->
                val isCompleted = completedLessonIds.contains(node.id)
                val isSelected = selectedNodeId == node.id
                val isNextUp = !isCompleted && (index == 0 || completedLessonIds.contains(CURRICULUM_TREE_NODES[index - 1].id))

                // The Stem connector between nodes
                if (index > 0) {
                    val prevCompleted = completedLessonIds.contains(CURRICULUM_TREE_NODES[index - 1].id)
                    val stemColor by animateColorAsState(
                        targetValue = if (prevCompleted && isCompleted) EmeraldProfit else if (prevCompleted) TrustBlue.copy(alpha = 0.5f) else Color(0xFFCBD5E1),
                        animationSpec = spring(stiffness = Spring.StiffnessLow),
                        label = "stem_color"
                    )

                    Box(
                        modifier = Modifier
                            .padding(start = 38.dp)
                            .width(3.dp)
                            .height(26.dp)
                            .clip(Squircle12)
                            .background(stemColor)
                    )
                }

                // Interactive Tree Node Card
                val nodeScale by animateFloatAsState(
                    targetValue = if (isSelected) 1.01f else 1.0f,
                    animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessMediumLow),
                    label = "node_scale"
                )

                val nodeBorderColor by animateColorAsState(
                    targetValue = when {
                        isCompleted -> EmeraldProfit.copy(alpha = 0.65f)
                        isNextUp -> TrustBlue
                        else -> Color(0xFFE2E8F0)
                    },
                    animationSpec = spring(stiffness = Spring.StiffnessLow),
                    label = "node_border"
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .scale(nodeScale)
                        .shadow(
                            elevation = if (isCompleted) 6.dp else if (isNextUp) 4.dp else 2.dp,
                            shape = Squircle24,
                            spotColor = if (isCompleted) EmeraldProfit.copy(alpha = 0.25f) else Color(0x0E000000)
                        )
                        .clip(Squircle24)
                        .background(
                            if (isCompleted) {
                                Brush.verticalGradient(
                                    listOf(
                                        Color.White,
                                        SageGreenContainer.copy(alpha = 0.35f)
                                    )
                                )
                            } else {
                                Brush.verticalGradient(
                                    listOf(
                                        Color.White,
                                        Color(0xFFFAFAFC)
                                    )
                                )
                            }
                        )
                        .border(BorderStroke(if (isCompleted || isNextUp) 1.5.dp else 1.dp, nodeBorderColor), Squircle24)
                        .appleSpringPress(
                            onClick = {
                                selectedNodeId = if (isSelected) null else node.id
                            }
                        )
                        .testTag("tree_node_${node.orderNumber}")
                        .padding(18.dp)
                ) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Node Status Circle / Badge
                            Box(
                                modifier = Modifier
                                    .size(42.dp)
                                    .clip(Squircle16)
                                    .background(
                                        when {
                                            isCompleted -> EmeraldProfit
                                            isNextUp -> TrustBlue
                                            else -> Color(0xFFE2E8F0)
                                        }
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                if (isCompleted) {
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = "Completed",
                                        tint = Color.White,
                                        modifier = Modifier.size(22.dp)
                                    )
                                } else if (isNextUp) {
                                    Icon(
                                        imageVector = node.icon,
                                        contentDescription = null,
                                        tint = Color.White,
                                        modifier = Modifier.size(20.dp)
                                    )
                                } else {
                                    Icon(
                                        imageVector = Icons.Default.Lock,
                                        contentDescription = "Locked",
                                        tint = SlateMuted,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.width(14.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(
                                        text = "STAGE 2 • NODE ${node.orderNumber}",
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (isCompleted) SageGreenText else TrustBlue,
                                        letterSpacing = 1.1.sp
                                    )

                                    Box(
                                        modifier = Modifier
                                            .clip(Squircle12)
                                            .background(
                                                if (isCompleted) SageGreenContainer else if (isNextUp) TrustBlueContainer else Color(0xFFF1F5F9)
                                            )
                                            .padding(horizontal = 8.dp, vertical = 2.dp)
                                    ) {
                                        Text(
                                            text = if (isCompleted) "✓ Ripe & Mastered" else if (isNextUp) "● Current Focus" else node.levelTier,
                                            fontSize = 9.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = if (isCompleted) SageGreenText else if (isNextUp) TrustBlue else SlateMuted
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(2.dp))

                                Text(
                                    text = node.title,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = SlateCharcoal
                                )

                                Text(
                                    text = node.conceptHeadline,
                                    fontSize = 12.sp,
                                    color = SlateMuted,
                                    maxLines = if (isSelected) 4 else 1
                                )
                            }
                        }

                        // Expanded interactive card when tapped
                        AnimatedVisibility(
                            visible = isSelected,
                            enter = fadeIn(spring(stiffness = Spring.StiffnessMediumLow)),
                            exit = fadeOut(spring(stiffness = Spring.StiffnessMediumLow))
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 16.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(Squircle16)
                                        .background(Color(0xFFF8FAFC))
                                        .border(BorderStroke(1.dp, Color(0xFFE2E8F0)), Squircle16)
                                        .padding(14.dp)
                                ) {
                                    Column {
                                        Text(
                                            text = "Core Concept Breakdown",
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = SlateCharcoal
                                        )
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(
                                            text = node.description,
                                            fontSize = 12.sp,
                                            color = SlateDark,
                                            lineHeight = 17.sp
                                        )
                                        Spacer(modifier = Modifier.height(8.dp))
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Text(
                                                text = "Reward: ${node.badgeIconEmoji} ${node.badgeTitle} — ${node.perkUnlockedText}",
                                                fontSize = 11.sp,
                                                fontWeight = FontWeight.SemiBold,
                                                color = SageGreenText
                                            )
                                        }
                                    }
                                }

                                Spacer(modifier = Modifier.height(14.dp))

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                                ) {
                                    // Button: Mark as Complete / Ripe
                                    Button(
                                        onClick = {
                                            onCompleteLesson(node.id)
                                        },
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = if (isCompleted) SageGreenContainer else EmeraldProfit,
                                            contentColor = if (isCompleted) SageGreenText else Color.White
                                        ),
                                        shape = Squircle16,
                                        modifier = Modifier
                                            .weight(1f)
                                            .height(44.dp)
                                    ) {
                                        Text(
                                            text = if (isCompleted) "✓ Mastered" else "Mark Node Complete",
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }

                                    // Button: Open full masterclass
                                    OutlinedButton(
                                        onClick = {
                                            if (!node.isFree) {
                                                onOpenUpgradeModal()
                                            } else {
                                                onNavigateToMasterclassChapter(node.masterclassTargetId)
                                            }
                                        },
                                        shape = Squircle16,
                                        border = BorderStroke(1.dp, TrustBlue),
                                        modifier = Modifier
                                            .weight(1f)
                                            .height(44.dp)
                                    ) {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Icon(
                                                imageVector = Icons.AutoMirrored.Filled.MenuBook,
                                                contentDescription = null,
                                                tint = TrustBlue,
                                                modifier = Modifier.size(14.dp)
                                            )
                                            Spacer(modifier = Modifier.width(6.dp))
                                            Text(
                                                text = if (node.isFree) "Read Guide" else "Unlock Pass",
                                                fontSize = 12.sp,
                                                fontWeight = FontWeight.SemiBold,
                                                color = TrustBlue
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
