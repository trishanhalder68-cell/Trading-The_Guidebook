package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.FastForward
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material.icons.filled.Stars
import androidx.compose.material.icons.filled.Timeline
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.filled.WarningAmber
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.ChapterSection
import com.example.data.model.MasterclassChapter
import com.example.ui.components.AmbientLivingMeshBackground
import com.example.ui.components.AppleGlassCard
import com.example.ui.components.appleSpringPress
import com.example.ui.theme.BrushedGold
import com.example.ui.theme.CreamBackground
import com.example.ui.theme.CreamBorder
import com.example.ui.theme.CreamSurface
import com.example.ui.theme.CrimsonLoss
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
import com.example.ui.theme.Squircle32
import com.example.ui.theme.TrustBlue
import com.example.ui.theme.TrustBlueContainer
import com.example.ui.theme.TrustBlueHover
import com.example.viewmodel.WealthViewModel

@Composable
fun MasterclassScreen(
    viewModel: WealthViewModel,
    onNavigateToAiChatWithPrompt: (String) -> Unit,
    onOpenCheckout: () -> Unit
) {
    val chapters = viewModel.chapters
    val unlockedIds by viewModel.unlockedChapterIds.collectAsState()
    val isVip by viewModel.isVipPro.collectAsState()
    val region by viewModel.currentRegion.collectAsState()
    val completedLessons by viewModel.completedLessons.collectAsState()

    var activeReadingChapter by remember { mutableStateOf<MasterclassChapter?>(null) }
    // 0: All Pillars, 1: Free Foundations, 2: Institutional Alpha, 3: Certification
    var selectedFilterTab by remember { mutableIntStateOf(0) }

    // Calculate overall curriculum mastery
    val totalChapters = chapters.size
    val unlockedCount = chapters.count { it.isFree || isVip || unlockedIds.contains(it.id) }
    val progressFraction = (unlockedCount.toFloat() / totalChapters.toFloat()).coerceIn(0f, 1f)
    val edgeScore = (progressFraction * 100).toInt().coerceAtLeast(18)

    AmbientLivingMeshBackground {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(10.dp))

                if (activeReadingChapter != null) {
                    // Back Bar to Syllabus with Apple Squircle Geometry
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(Squircle16)
                            .background(Color.White.copy(alpha = 0.9f))
                            .border(BorderStroke(1.dp, Color(0xFFE2E8F0)), Squircle16)
                            .padding(horizontal = 12.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = { activeReadingChapter = null },
                            modifier = Modifier
                                .size(36.dp)
                                .clip(Squircle12)
                                .background(Color(0xFFF1F5F9))
                                .appleSpringPress(onClick = { activeReadingChapter = null })
                                .testTag("masterclass_back_button")
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back to Syllabus",
                                tint = TrustBlue,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "RETURN TO SYLLABUS",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = TrustBlue,
                                letterSpacing = 1.sp
                            )
                            Text(
                                text = "Institutional Curriculum Overview",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = SlateCharcoal
                            )
                        }

                        Box(
                            modifier = Modifier
                                .clip(Squircle12)
                                .background(SageGreenContainer)
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = "Edge: $edgeScore%",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = SageGreenText
                            )
                        }
                    }
                } else {
                    // =================================================================
                    // COMPREHENSIVE CURRICULUM HERO & PSYCHOLOGICAL PROOF DASHBOARD
                    // =================================================================
                    AppleGlassCard(
                        modifier = Modifier.fillMaxWidth(),
                        shape = Squircle28,
                        backgroundColor = Color.White.copy(alpha = 0.95f),
                        borderColor = Color(0xFFE2E8F0),
                        elevation = 6.dp
                    ) {
                        Column(modifier = Modifier.padding(22.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Box(
                                        modifier = Modifier
                                            .size(44.dp)
                                            .clip(Squircle16)
                                            .background(TrustBlueContainer),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.School,
                                            contentDescription = null,
                                            tint = TrustBlue,
                                            modifier = Modifier.size(24.dp)
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Column {
                                        Text(
                                            text = "APEX INSTITUTIONAL TRADING",
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = TrustBlue,
                                            letterSpacing = 1.2.sp
                                        )
                                        Text(
                                            text = "The Masterclass",
                                            fontSize = 20.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = SlateCharcoal
                                        )
                                    }
                                }

                                Box(
                                    modifier = Modifier
                                        .clip(Squircle12)
                                        .background(SageGreenContainer)
                                        .padding(horizontal = 9.dp, vertical = 5.dp)
                                ) {
                                    Text(
                                        text = if (isVip) "All-Access Active" else "6 Pillars",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = SageGreenText
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(14.dp))

                            Text(
                                text = "A complete, ground-up institutional market science syllabus. Built to demystify why 95% of retail traders lose capital and train you in the exact order flow, liquidity sweeps, and mathematical asymmetry used by professional hedge funds.",
                                fontSize = 12.sp,
                                color = SlateMuted,
                                lineHeight = 18.sp
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            // Institutional Syllabus Badges
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                SyllabusBadgeChip(
                                    icon = Icons.Default.Timeline,
                                    label = "6 Pillars",
                                    modifier = Modifier.weight(1f)
                                )
                                SyllabusBadgeChip(
                                    icon = Icons.Default.Psychology,
                                    label = "18 Setups",
                                    modifier = Modifier.weight(1f)
                                )
                                SyllabusBadgeChip(
                                    icon = Icons.Default.Verified,
                                    label = "Certification",
                                    modifier = Modifier.weight(1f)
                                )
                            }

                            Spacer(modifier = Modifier.height(18.dp))

                            // Market Readiness Edge Meter (Psychological Progress Bar)
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(Squircle16)
                                    .background(Color(0xFFF8FAFC))
                                    .border(BorderStroke(1.dp, Color(0xFFE2E8F0)), Squircle16)
                                    .padding(14.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "Institutional Edge Readiness",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = SlateCharcoal
                                    )
                                    Text(
                                        text = "$edgeScore% Edge Rating",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (edgeScore >= 75) EmeraldProfit else TrustBlue
                                    )
                                }

                                Spacer(modifier = Modifier.height(8.dp))

                                LinearProgressIndicator(
                                    progress = { progressFraction },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(7.dp)
                                        .clip(CircleShape),
                                    color = if (edgeScore >= 75) EmeraldProfit else TrustBlue,
                                    trackColor = Color(0xFFE2E8F0)
                                )

                                Spacer(modifier = Modifier.height(6.dp))

                                Text(
                                    text = if (edgeScore < 50)
                                        "Complete Pillars 2, 3 & 4 to eliminate retail stop-hunt traps and reach 85%+ institutional edge."
                                    else
                                        "Professional tier edge unlocked. You understand smart money absorption and risk asymmetry.",
                                    fontSize = 11.sp,
                                    color = SlateMuted,
                                    lineHeight = 15.sp
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // =================================================================
                    // ERGONOMIC FILTER TABS: [ All ] [ Free ] [ Institutional ] [ Cert ]
                    // =================================================================
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(Squircle20)
                            .background(Color.White.copy(alpha = 0.9f))
                            .border(BorderStroke(1.dp, Color(0xFFE2E8F0)), Squircle20)
                            .padding(4.dp)
                    ) {
                        Row(modifier = Modifier.fillMaxWidth()) {
                            val tabs = listOf("All Pillars", "Free Grounding", "Pro Alpha", "Licensing")
                            tabs.forEachIndexed { index, title ->
                                val isSelected = selectedFilterTab == index
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(38.dp)
                                        .clip(Squircle16)
                                        .background(if (isSelected) TrustBlue else Color.Transparent)
                                        .clickable { selectedFilterTab = index }
                                        .appleSpringPress(onClick = { selectedFilterTab = index }),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = title,
                                        fontSize = 11.sp,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                        color = if (isSelected) Color.White else SlateCharcoal
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Verified Certification Preview Card (Shown if Licensing tab or All)
                    if (selectedFilterTab == 0 || selectedFilterTab == 3) {
                        CertificatePreviewCard(
                            unlockedCount = unlockedCount,
                            totalCount = totalChapters,
                            onExploreAll = { selectedFilterTab = 0 }
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                }
            }

            // =========================================================================
            // ACTIVE READING CHAPTER OR CHAPTER LIST
            // =========================================================================
            if (activeReadingChapter != null) {
                item {
                    val chapter = activeReadingChapter!!
                    val isUnlocked = chapter.isFree || isVip || unlockedIds.contains(chapter.id)

                    if (!isUnlocked) {
                        // High-Conversion Psychological Locked Gate
                        PsychologicalLockedGate(
                            chapter = chapter,
                            currencySymbol = region.currencySymbol,
                            onUnlock = {
                                viewModel.unlockChapter(chapter.id)
                            },
                            onOpenAllAccessCheckout = onOpenCheckout
                        )
                    } else {
                        // In-Depth Interactive Apple-HIG Chapter Reader
                        ComprehensiveChapterReader(
                            chapter = chapter,
                            currencySymbol = region.currencySymbol,
                            onAskAiTutor = { prompt ->
                                onNavigateToAiChatWithPrompt(prompt)
                            },
                            onCompleteCheckpoint = {
                                viewModel.completeLesson("tree_lesson_${chapter.chapterNumber}")
                            },
                            onSkipToNext = { nextId ->
                                val nextCh = chapters.find { it.id == nextId }
                                if (nextCh != null) {
                                    activeReadingChapter = nextCh
                                }
                            }
                        )
                    }
                }
            } else {
                // Filtered List of Chapter Cards
                val filteredChapters = when (selectedFilterTab) {
                    1 -> chapters.filter { it.isFree }
                    2 -> chapters.filter { !it.isFree }
                    else -> chapters
                }

                items(filteredChapters, key = { it.id }) { chapter ->
                    val isUnlocked = chapter.isFree || isVip || unlockedIds.contains(chapter.id)
                    InstitutionalChapterCard(
                        chapter = chapter,
                        isUnlocked = isUnlocked,
                        currencySymbol = region.currencySymbol,
                        onClick = { activeReadingChapter = chapter }
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(80.dp))
            }
        }
    }
}

// =============================================================================
// SYLLABUS BADGE CHIP
// =============================================================================
@Composable
fun SyllabusBadgeChip(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(Squircle12)
            .background(Color(0xFFF1F5F9))
            .border(BorderStroke(1.dp, Color(0xFFE2E8F0)), Squircle12)
            .padding(vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = TrustBlue,
                modifier = Modifier.size(14.dp)
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = label,
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                color = SlateCharcoal
            )
        }
    }
}

// =============================================================================
// CERTIFICATION PREVIEW CARD
// =============================================================================
@Composable
fun CertificatePreviewCard(
    unlockedCount: Int,
    totalCount: Int,
    onExploreAll: () -> Unit
) {
    AppleGlassCard(
        modifier = Modifier.fillMaxWidth(),
        shape = Squircle24,
        backgroundColor = Color.White.copy(alpha = 0.95f),
        borderColor = Color(0xFFE2E8F0),
        elevation = 4.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(Squircle16)
                    .background(
                        Brush.radialGradient(
                            listOf(BrushedGold.copy(alpha = 0.25f), Color(0xFFFEF3C7))
                        )
                    )
                    .border(BorderStroke(1.2.dp, BrushedGold.copy(alpha = 0.6f)), Squircle16),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Stars,
                    contentDescription = null,
                    tint = BrushedGold,
                    modifier = Modifier.size(26.dp)
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "ACCREDITED CERTIFICATION",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = BrushedGold,
                    letterSpacing = 1.sp
                )
                Text(
                    text = "Certified Market Practitioner",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = SlateCharcoal
                )
                Text(
                    text = "$unlockedCount of $totalCount Pillars Mastered • Unlocks Official Trader Credential",
                    fontSize = 11.sp,
                    color = SlateMuted
                )
            }
        }
    }
}

// =============================================================================
// INSTITUTIONAL CHAPTER SYLLABUS CARD
// =============================================================================
@Composable
fun InstitutionalChapterCard(
    chapter: MasterclassChapter,
    isUnlocked: Boolean,
    currencySymbol: String,
    onClick: () -> Unit
) {
    AppleGlassCard(
        modifier = Modifier
            .fillMaxWidth()
            .appleSpringPress(targetScale = 0.98f, onClick = onClick)
            .testTag("chapter_card_${chapter.id}"),
        shape = Squircle24,
        backgroundColor = Color.White.copy(alpha = 0.95f),
        borderColor = if (isUnlocked) TrustBlue.copy(alpha = 0.35f) else Color(0xFFE2E8F0),
        elevation = 3.dp
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            // Header Row: Pillar number, Title & Lock/Unlock Badge
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(Squircle12)
                        .background(
                            if (isUnlocked) TrustBlue else Color(0xFFF1F5F9)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "${chapter.pillarNumber}",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isUnlocked) Color.White else SlateMuted
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "PILLAR ${chapter.pillarNumber} • ${chapter.pillarName.uppercase()}",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isUnlocked) TrustBlue else SlateMuted,
                        letterSpacing = 0.8.sp
                    )
                    Text(
                        text = chapter.title,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = SlateCharcoal
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                // Price / Status Badge
                Box(
                    modifier = Modifier
                        .clip(Squircle12)
                        .background(
                            if (isUnlocked) SageGreenContainer else Color(0xFFFEF3C7)
                        )
                        .border(
                            BorderStroke(
                                1.dp,
                                if (isUnlocked) SageGreen.copy(alpha = 0.3f) else BrushedGold.copy(alpha = 0.4f)
                            ),
                            Squircle12
                        )
                        .padding(horizontal = 9.dp, vertical = 5.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = if (isUnlocked) Icons.Default.CheckCircle else Icons.Default.Lock,
                            contentDescription = null,
                            tint = if (isUnlocked) SageGreen else BrushedGold,
                            modifier = Modifier.size(12.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = if (chapter.isFree) "FREE" else if (isUnlocked) "UNLOCKED" else "$currencySymbol${chapter.unlockPriceInr}",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isUnlocked) SageGreenText else Color(0xFFB45309)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = chapter.subtitle,
                fontSize = 12.sp,
                color = SlateMuted,
                lineHeight = 17.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Retail vs Smart Money Contrast Hook
            if (chapter.retailTrapNotice.isNotEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(Squircle12)
                        .background(Color(0xFFF8FAFC))
                        .border(BorderStroke(1.dp, Color(0xFFE2E8F0)), Squircle12)
                        .padding(10.dp)
                ) {
                    Row(verticalAlignment = Alignment.Top) {
                        Icon(
                            imageVector = Icons.Default.Psychology,
                            contentDescription = null,
                            tint = TrustBlue,
                            modifier = Modifier.size(15.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text(
                                text = "Tactical Playbook Setup:",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = TrustBlue
                            )
                            Text(
                                text = "${chapter.confidentialSetupTitle} (${chapter.confidentialSetupWinRate})",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = SlateCharcoal
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))
            }

            // Bottom row: Reading time & Action prompt
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.MenuBook,
                    contentDescription = null,
                    tint = SlateMuted,
                    modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = "${chapter.estimatedReadMinutes} min intensive reading",
                    fontSize = 11.sp,
                    color = SlateMuted
                )

                Spacer(modifier = Modifier.weight(1f))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = if (isUnlocked) "Open Lesson" else "Unlock Access",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = TrustBlue
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = null,
                        tint = TrustBlue,
                        modifier = Modifier.size(14.dp)
                    )
                }
            }
        }
    }
}

// =============================================================================
// PSYCHOLOGICAL LOCKED GATE (Irresistible Value vs Cost of Ignorance)
// =============================================================================
@Composable
fun PsychologicalLockedGate(
    chapter: MasterclassChapter,
    currencySymbol: String,
    onUnlock: () -> Unit,
    onOpenAllAccessCheckout: () -> Unit
) {
    AppleGlassCard(
        modifier = Modifier.fillMaxWidth(),
        shape = Squircle28,
        backgroundColor = Color.White.copy(alpha = 0.98f),
        borderColor = Color(0xFFE2E8F0),
        elevation = 8.dp
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Lock badge with gold gleam
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clip(Squircle20)
                    .background(Color(0xFFFEF3C7))
                    .border(BorderStroke(1.5.dp, BrushedGold), Squircle20),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = null,
                    tint = BrushedGold,
                    modifier = Modifier.size(30.dp)
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = "PILLAR ${chapter.pillarNumber} RESTRICTED ACCESS",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = BrushedGold,
                letterSpacing = 1.2.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = chapter.title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = SlateCharcoal,
                textAlign = TextAlign.Center
            )
            Text(
                text = chapter.subtitle,
                fontSize = 12.sp,
                color = SlateMuted,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 4.dp)
            )

            Spacer(modifier = Modifier.height(18.dp))

            // Loss Aversion Framing Card
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(Squircle16)
                    .background(Color(0xFFFFF7ED))
                    .border(BorderStroke(1.dp, Color(0xFFFDBA74)), Squircle16)
                    .padding(14.dp)
            ) {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.WarningAmber,
                            contentDescription = null,
                            tint = Color(0xFFC2410C),
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "The Cost of Trading Unprepared:",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFC2410C)
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "95% of retail traders lose ₹25,000 to ₹100,000+ in their first year from emotional stop placement and chasing breakouts. A single prevented bad trade pays for this micro-pass 50 times over.",
                        fontSize = 11.sp,
                        color = Color(0xFF7C2D12),
                        lineHeight = 16.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Confidential Playbook Setup Preview
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(Squircle16)
                    .background(TrustBlueContainer.copy(alpha = 0.5f))
                    .border(BorderStroke(1.dp, TrustBlue.copy(alpha = 0.3f)), Squircle16)
                    .padding(14.dp)
            ) {
                Column {
                    Text(
                        text = "Institutional Edge Inside This Pillar:",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = TrustBlue
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "• ${chapter.confidentialSetupTitle} (${chapter.confidentialSetupWinRate})\n• ${chapter.institutionalEdgeNotice}",
                        fontSize = 11.sp,
                        color = SlateCharcoal,
                        lineHeight = 16.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Micro-Unlock Button
            Button(
                onClick = onUnlock,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .shadow(elevation = 6.dp, shape = Squircle16, spotColor = TrustBlue.copy(alpha = 0.4f))
                    .appleSpringPress(onClick = onUnlock)
                    .testTag("unlock_pillar_button"),
                shape = Squircle16,
                colors = ButtonDefaults.buttonColors(
                    containerColor = TrustBlue,
                    contentColor = Color.White
                )
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Unlock Pillar ${chapter.pillarNumber} for $currencySymbol${chapter.unlockPriceInr}",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // All-Access Annual Pass Option
            OutlinedButton(
                onClick = onOpenAllAccessCheckout,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .appleSpringPress(onClick = onOpenAllAccessCheckout)
                    .testTag("unlock_all_pillars_button"),
                shape = Squircle16,
                colors = ButtonDefaults.outlinedButtonColors(contentColor = TrustBlue),
                border = BorderStroke(1.2.dp, TrustBlue)
            ) {
                Icon(
                    imageVector = Icons.Default.AutoAwesome,
                    contentDescription = null,
                    modifier = Modifier.size(15.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Unlock All 6 Pillars + Certification ($currencySymbol 999)",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

// =============================================================================
// COMPREHENSIVE CHAPTER FULL READER
// =============================================================================
@Composable
fun ComprehensiveChapterReader(
    chapter: MasterclassChapter,
    currencySymbol: String,
    onAskAiTutor: (String) -> Unit,
    onCompleteCheckpoint: () -> Unit,
    onSkipToNext: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Fast-Track Skip to Next Pillar (If available)
        if (chapter.nextLevelId != null) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(Squircle16)
                    .background(Color.White.copy(alpha = 0.9f))
                    .border(BorderStroke(1.dp, Color(0xFFE2E8F0)), Squircle16)
                    .padding(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.FastForward,
                        contentDescription = null,
                        tint = TrustBlue,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Already confident with these principles?",
                            fontSize = 11.sp,
                            color = SlateMuted
                        )
                        Text(
                            text = "Skip ahead to Pillar ${chapter.chapterNumber + 1}",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = SlateCharcoal
                        )
                    }
                    Button(
                        onClick = { onSkipToNext(chapter.nextLevelId) },
                        shape = Squircle12,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = TrustBlue,
                            contentColor = Color.White
                        ),
                        modifier = Modifier
                            .height(36.dp)
                            .appleSpringPress(onClick = { onSkipToNext(chapter.nextLevelId) })
                    ) {
                        Text("Next Pillar →", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        // Chapter Header Card
        AppleGlassCard(
            modifier = Modifier.fillMaxWidth(),
            shape = Squircle28,
            backgroundColor = Color.White.copy(alpha = 0.95f),
            borderColor = Color(0xFFE2E8F0),
            elevation = 4.dp
        ) {
            Column(modifier = Modifier.padding(22.dp)) {
                Text(
                    text = "PILLAR ${chapter.pillarNumber} • ${chapter.pillarName.uppercase()}",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = TrustBlue,
                    letterSpacing = 1.2.sp
                )
                Text(
                    text = chapter.title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = SlateCharcoal,
                    modifier = Modifier.padding(top = 4.dp)
                )
                Text(
                    text = chapter.subtitle,
                    fontSize = 13.sp,
                    color = SlateMuted,
                    modifier = Modifier.padding(top = 4.dp)
                )

                Spacer(modifier = Modifier.height(14.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(Squircle16)
                        .background(Color(0xFFF8FAFC))
                        .border(BorderStroke(1.dp, Color(0xFFE2E8F0)), Squircle16)
                        .padding(14.dp)
                ) {
                    Text(
                        text = "“${chapter.hookStatement}”",
                        fontSize = 12.sp,
                        fontStyle = FontStyle.Italic,
                        color = SlateCharcoal,
                        lineHeight = 18.sp
                    )
                }
            }
        }

        // =====================================================================
        // INTERACTIVE VISUAL DIAGRAM
        // =====================================================================
        MasterclassInteractiveDiagram(diagramType = chapter.diagramType)

        // =====================================================================
        // RETAIL TRAP VS INSTITUTIONAL EDGE CALLOUT BOX
        // =====================================================================
        if (chapter.retailTrapNotice.isNotEmpty()) {
            RetailVsInstitutionalCard(
                retailTrap = chapter.retailTrapNotice,
                institutionalEdge = chapter.institutionalEdgeNotice
            )
        }

        // =====================================================================
        // CORE CONTENT SECTIONS
        // =====================================================================
        chapter.coreContentSections.forEach { section ->
            AppleGlassCard(
                modifier = Modifier.fillMaxWidth(),
                shape = Squircle24,
                backgroundColor = Color.White.copy(alpha = 0.95f),
                borderColor = Color(0xFFE2E8F0),
                elevation = 3.dp
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        text = section.sectionTitle,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = TrustBlue
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = section.body,
                        fontSize = 13.sp,
                        color = SlateCharcoal,
                        lineHeight = 21.sp
                    )
                    Spacer(modifier = Modifier.height(14.dp))

                    // Key Institutional Takeaway
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(Squircle16)
                            .background(SageGreenContainer)
                            .border(BorderStroke(1.dp, SageGreen.copy(alpha = 0.35f)), Squircle16)
                            .padding(12.dp)
                    ) {
                        Row(verticalAlignment = Alignment.Top) {
                            Icon(
                                imageVector = Icons.Default.AutoAwesome,
                                contentDescription = null,
                                tint = SageGreen,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text(
                                    text = "Key Institutional Takeaway:",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = SageGreenText
                                )
                                Text(
                                    text = section.keyTakeaway,
                                    fontSize = 11.sp,
                                    color = SlateCharcoal,
                                    lineHeight = 16.sp
                                )
                            }
                        }
                    }
                }
            }
        }

        // =====================================================================
        // INTERACTIVE COMPREHENSION CHECKPOINT QUIZ (Psychological edge booster)
        // =====================================================================
        if (chapter.quizQuestion.isNotEmpty()) {
            InteractiveQuizCheckpoint(
                chapter = chapter,
                onComplete = onCompleteCheckpoint
            )
        }

        // =====================================================================
        // GOLDEN RULES & CARDINAL SINS
        // =====================================================================
        AppleGlassCard(
            modifier = Modifier.fillMaxWidth(),
            shape = Squircle24,
            backgroundColor = Color.White.copy(alpha = 0.95f),
            borderColor = Color(0xFFE2E8F0),
            elevation = 3.dp
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    text = "Golden Rules for this Level:",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = SageGreenText
                )
                Spacer(modifier = Modifier.height(6.dp))
                chapter.goldenRulesDo.forEach { rule ->
                    Row(
                        modifier = Modifier.padding(vertical = 3.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = SageGreen,
                            modifier = Modifier.size(15.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = rule,
                            fontSize = 12.sp,
                            color = SlateCharcoal,
                            lineHeight = 17.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = "Cardinal Sins to Avoid (Never Do This):",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = CrimsonLoss
                )
                Spacer(modifier = Modifier.height(6.dp))
                chapter.cardinalSinsAvoid.forEach { sin ->
                    Row(
                        modifier = Modifier.padding(vertical = 3.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Text(
                            text = "✕",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = CrimsonLoss
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = sin,
                            fontSize = 12.sp,
                            color = SlateCharcoal,
                            lineHeight = 17.sp
                        )
                    }
                }
            }
        }

        // =====================================================================
        // AI TUTOR MULTI-TURN BUTTON (Gemini 3.8 Flash)
        // =====================================================================
        Button(
            onClick = { onAskAiTutor(chapter.aiTutorStarterPrompt) },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .shadow(elevation = 6.dp, shape = Squircle16, spotColor = TrustBlue.copy(alpha = 0.3f))
                .appleSpringPress(onClick = { onAskAiTutor(chapter.aiTutorStarterPrompt) })
                .testTag("ask_ai_tutor_button"),
            shape = Squircle16,
            colors = ButtonDefaults.buttonColors(
                containerColor = TrustBlue,
                contentColor = Color.White
            )
        ) {
            Icon(imageVector = Icons.Default.SmartToy, contentDescription = null, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Ask AI Mentor About Pillar ${chapter.pillarNumber} (Gemini)",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold
            )
        }

        // Bottom Proceed to Next Session
        if (chapter.nextLevelId != null) {
            OutlinedButton(
                onClick = { onSkipToNext(chapter.nextLevelId) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .appleSpringPress(onClick = { onSkipToNext(chapter.nextLevelId) }),
                shape = Squircle16,
                colors = ButtonDefaults.outlinedButtonColors(contentColor = TrustBlue),
                border = BorderStroke(1.2.dp, TrustBlue)
            ) {
                Icon(imageVector = Icons.Default.FastForward, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Proceed to Pillar ${chapter.chapterNumber + 1} →",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

// =============================================================================
// INTERACTIVE VISUAL DIAGRAM
// =============================================================================
@Composable
fun MasterclassInteractiveDiagram(diagramType: String) {
    AppleGlassCard(
        modifier = Modifier.fillMaxWidth(),
        shape = Squircle24,
        backgroundColor = Color.White.copy(alpha = 0.95f),
        borderColor = Color(0xFFE2E8F0),
        elevation = 3.dp
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Text(
                text = "INTERACTIVE VISUAL TELEMETRY",
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = TrustBlue,
                letterSpacing = 1.sp
            )
            Text(
                text = when (diagramType) {
                    "candlestick" -> "Candlestick Auction Anatomy"
                    "order_cycle" -> "Central Bank Liquidity Pump"
                    "valuation_matrix" -> "Free Cash Flow vs Debt Health"
                    "liquidity_sweep" -> "Institutional Stop-Loss Sweep"
                    "custody_ledger" -> "TradFi vs DeFi Custody Layer"
                    else -> "80/20 Core-Satellite Multiplier"
                },
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = SlateCharcoal
            )

            Spacer(modifier = Modifier.height(12.dp))

            when (diagramType) {
                "candlestick" -> CandlestickAnatomyCanvas()
                "order_cycle" -> MacroLiquidityCycleView()
                "liquidity_sweep" -> LiquiditySweepCanvas()
                else -> CoreSatelliteCompoundingView()
            }
        }
    }
}

@Composable
fun CandlestickAnatomyCanvas() {
    var isBullishRejection by remember { mutableStateOf(true) }

    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp)
                .clip(Squircle16)
                .background(Color(0xFF0F172A))
                .padding(12.dp),
            contentAlignment = Alignment.Center
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val midX = size.width / 2
                val greenColor = Color(0xFF10B981)
                val redColor = Color(0xFFEF4444)
                val candleColor = if (isBullishRejection) greenColor else redColor

                if (isBullishRejection) {
                    // Bullish Rejection Hammer: Long lower wick, small real body near top
                    val highY = size.height * 0.15f
                    val openY = size.height * 0.40f
                    val closeY = size.height * 0.25f
                    val lowY = size.height * 0.90f

                    // Upper & Lower Wick
                    drawLine(
                        color = candleColor,
                        start = Offset(midX, highY),
                        end = Offset(midX, lowY),
                        strokeWidth = 3f,
                        cap = StrokeCap.Round
                    )

                    // Real Body
                    drawRect(
                        color = candleColor,
                        topLeft = Offset(midX - 24f, closeY),
                        size = Size(48f, openY - closeY)
                    )
                } else {
                    // Bearish Liquidity Rejection: Long upper wick, close near low
                    val highY = size.height * 0.10f
                    val openY = size.height * 0.60f
                    val closeY = size.height * 0.75f
                    val lowY = size.height * 0.85f

                    drawLine(
                        color = candleColor,
                        start = Offset(midX, highY),
                        end = Offset(midX, lowY),
                        strokeWidth = 3f,
                        cap = StrokeCap.Round
                    )

                    drawRect(
                        color = candleColor,
                        topLeft = Offset(midX - 24f, openY),
                        size = Size(48f, closeY - openY)
                    )
                }
            }

            // Annotation labels
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 14.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = if (isBullishRejection) "High (Aggressive Buyers Retain Control)" else "High (Institutional Supply Dump)",
                    fontSize = 10.sp,
                    color = Color.White.copy(alpha = 0.8f)
                )
                Text(
                    text = if (isBullishRejection) "Lower Wick: Proof of Smart Money Limit Absorption" else "Upper Wick: Trapped Retail Breakout Buyers",
                    fontSize = 10.sp,
                    color = if (isBullishRejection) Color(0xFF34D399) else Color(0xFFF87171),
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = { isBullishRejection = true },
                modifier = Modifier
                    .weight(1f)
                    .height(36.dp),
                shape = Squircle12,
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isBullishRejection) TrustBlue else Color(0xFFF1F5F9),
                    contentColor = if (isBullishRejection) Color.White else SlateCharcoal
                )
            ) {
                Text("Bullish Absorption Wick", fontSize = 11.sp, fontWeight = FontWeight.Bold)
            }
            Button(
                onClick = { isBullishRejection = false },
                modifier = Modifier
                    .weight(1f)
                    .height(36.dp),
                shape = Squircle12,
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (!isBullishRejection) TrustBlue else Color(0xFFF1F5F9),
                    contentColor = if (!isBullishRejection) Color.White else SlateCharcoal
                )
            ) {
                Text("Bearish Supply Dump", fontSize = 11.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun MacroLiquidityCycleView() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(Squircle16)
            .background(Color(0xFFF8FAFC))
            .border(BorderStroke(1.dp, Color(0xFFE2E8F0)), Squircle16)
            .padding(14.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        MacroStepRow(step = "1", title = "Central Bank Repo Rate Cut", desc = "Borrowing cost drops; banking reserves expand")
        MacroStepRow(step = "2", title = "Bond Yields Collapse", desc = "Multi-billion pension funds forced to exit bonds")
        MacroStepRow(step = "3", title = "Liquidity Gushes to Equities", desc = "P/E multiples expand, creating runaway bull markets")
    }
}

@Composable
fun MacroStepRow(step: String, title: String, desc: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(24.dp)
                .clip(CircleShape)
                .background(TrustBlue),
            contentAlignment = Alignment.Center
        ) {
            Text(step, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color.White)
        }
        Spacer(modifier = Modifier.width(10.dp))
        Column {
            Text(title, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = SlateCharcoal)
            Text(desc, fontSize = 10.sp, color = SlateMuted)
        }
    }
}

@Composable
fun LiquiditySweepCanvas() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(130.dp)
            .clip(Squircle16)
            .background(Color(0xFF0F172A))
            .padding(12.dp)
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val supportY = size.height * 0.55f

            // Key Support Line
            drawLine(
                color = Color.White.copy(alpha = 0.35f),
                start = Offset(0f, supportY),
                end = Offset(size.width, supportY),
                strokeWidth = 2f
            )

            // Price path with downward sweep below support, then rocket
            val path = androidx.compose.ui.graphics.Path().apply {
                moveTo(size.width * 0.05f, supportY - 20f)
                lineTo(size.width * 0.25f, supportY)
                lineTo(size.width * 0.40f, supportY - 10f)
                // The sweep: dives sharply under support
                lineTo(size.width * 0.55f, supportY + 35f)
                // Reclaim and rocket
                lineTo(size.width * 0.70f, supportY - 15f)
                lineTo(size.width * 0.95f, size.height * 0.15f)
            }

            drawPath(
                path = path,
                color = Color(0xFF38BDF8),
                style = Stroke(width = 4f, cap = StrokeCap.Round)
            )

            // Stop Loss cluster circle
            drawCircle(
                color = Color(0xFFEF4444),
                radius = 7f,
                center = Offset(size.width * 0.55f, supportY + 35f)
            )
        }

        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween) {
            Text("Key Support Level (Retail Clusters Stop Losses Here)", fontSize = 10.sp, color = Color.White.copy(alpha = 0.7f))
            Text("🔴 Retail Stops Swept → Smart Money Fills ₹500 Cr Buy Orders → 3.5x Breakout", fontSize = 10.sp, color = Color(0xFF34D399), fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun CoreSatelliteCompoundingView() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(Squircle16)
            .background(Color(0xFFF8FAFC))
            .border(BorderStroke(1.dp, Color(0xFFE2E8F0)), Squircle16)
            .padding(14.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Box(
            modifier = Modifier
                .weight(1.6f)
                .clip(Squircle12)
                .background(TrustBlueContainer)
                .padding(12.dp)
        ) {
            Column {
                Text("80% Core Foundation", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = TrustBlue)
                Text("Automated Nifty 50 / S&P 500 Index SIPs compounding uninterrupted at 14% CAGR.", fontSize = 10.sp, color = SlateCharcoal)
            }
        }

        Box(
            modifier = Modifier
                .weight(1f)
                .clip(Squircle12)
                .background(SageGreenContainer)
                .padding(12.dp)
        ) {
            Column {
                Text("20% Alpha", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = SageGreenText)
                Text("Tactical 1:3 R:R setups with max 1% capital risk.", fontSize = 10.sp, color = SlateCharcoal)
            }
        }
    }
}

// =============================================================================
// RETAIL TRAP VS INSTITUTIONAL EDGE CARD
// =============================================================================
@Composable
fun RetailVsInstitutionalCard(retailTrap: String, institutionalEdge: String) {
    AppleGlassCard(
        modifier = Modifier.fillMaxWidth(),
        shape = Squircle24,
        backgroundColor = Color.White.copy(alpha = 0.95f),
        borderColor = Color(0xFFE2E8F0),
        elevation = 3.dp
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Text(
                text = "THE AMATEUR VS. INSTITUTIONAL DIVIDE",
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = TrustBlue,
                letterSpacing = 1.sp
            )
            Spacer(modifier = Modifier.height(10.dp))

            // Retail trap (Red)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(Squircle12)
                    .background(Color(0xFFFEF2F2))
                    .border(BorderStroke(1.dp, Color(0xFFFECACA)), Squircle12)
                    .padding(10.dp)
            ) {
                Row(verticalAlignment = Alignment.Top) {
                    Text("✕", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = CrimsonLoss)
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text("What 95% of Retail Gamblers Do:", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = CrimsonLoss)
                        Text(retailTrap, fontSize = 11.sp, color = SlateCharcoal, lineHeight = 15.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Institutional Edge (Green)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(Squircle12)
                    .background(SageGreenContainer)
                    .border(BorderStroke(1.dp, SageGreen.copy(alpha = 0.35f)), Squircle12)
                    .padding(10.dp)
            ) {
                Row(verticalAlignment = Alignment.Top) {
                    Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null, tint = SageGreen, modifier = Modifier.size(15.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text("How Smart Money Algorithms Profit:", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = SageGreenText)
                        Text(institutionalEdge, fontSize = 11.sp, color = SlateCharcoal, lineHeight = 15.sp)
                    }
                }
            }
        }
    }
}

// =============================================================================
// INTERACTIVE COMPREHENSION CHECKPOINT QUIZ
// =============================================================================
@Composable
fun InteractiveQuizCheckpoint(
    chapter: MasterclassChapter,
    onComplete: () -> Unit
) {
    var selectedOption by remember { mutableIntStateOf(-1) }
    var hasAnswered by remember { mutableStateOf(false) }

    AppleGlassCard(
        modifier = Modifier.fillMaxWidth(),
        shape = Squircle24,
        backgroundColor = Color.White.copy(alpha = 0.98f),
        borderColor = if (hasAnswered && selectedOption == chapter.quizCorrectIndex) SageGreen else Color(0xFFE2E8F0),
        elevation = 4.dp
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Psychology,
                        contentDescription = null,
                        tint = TrustBlue,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "EDGE COMPREHENSION CHECKPOINT",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = TrustBlue,
                        letterSpacing = 1.sp
                    )
                }

                if (hasAnswered && selectedOption == chapter.quizCorrectIndex) {
                    Box(
                        modifier = Modifier
                            .clip(Squircle12)
                            .background(SageGreenContainer)
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text("✓ Edge Verified", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = SageGreenText)
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = chapter.quizQuestion,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = SlateCharcoal,
                lineHeight = 19.sp
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Options List
            chapter.quizOptions.forEachIndexed { index, optionText ->
                val isSelected = selectedOption == index
                val isCorrect = index == chapter.quizCorrectIndex

                val backgroundColor = when {
                    !hasAnswered && isSelected -> TrustBlueContainer
                    hasAnswered && isCorrect -> Color(0xFFDCFCE7)
                    hasAnswered && isSelected && !isCorrect -> Color(0xFFFEE2E2)
                    else -> Color(0xFFF8FAFC)
                }

                val borderColor = when {
                    hasAnswered && isCorrect -> Color(0xFF16A34A)
                    hasAnswered && isSelected && !isCorrect -> Color(0xFFDC2626)
                    isSelected -> TrustBlue
                    else -> Color(0xFFE2E8F0)
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .clip(Squircle16)
                        .background(backgroundColor)
                        .border(BorderStroke(1.2.dp, borderColor), Squircle16)
                        .clickable(enabled = !hasAnswered) {
                            selectedOption = index
                            hasAnswered = true
                            if (isCorrect) {
                                onComplete()
                            }
                        }
                        .appleSpringPress(
                            onClick = {
                                if (!hasAnswered) {
                                    selectedOption = index
                                    hasAnswered = true
                                    if (isCorrect) {
                                        onComplete()
                                    }
                                }
                            }
                        )
                        .padding(14.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .clip(CircleShape)
                                .background(
                                    when {
                                        hasAnswered && isCorrect -> Color(0xFF16A34A)
                                        hasAnswered && isSelected -> Color(0xFFDC2626)
                                        else -> Color.White
                                    }
                                )
                                .border(BorderStroke(1.dp, borderColor), CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = ('A' + index).toString(),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (hasAnswered && (isCorrect || isSelected)) Color.White else SlateCharcoal
                            )
                        }

                        Spacer(modifier = Modifier.width(10.dp))

                        Text(
                            text = optionText,
                            fontSize = 12.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                            color = SlateCharcoal,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }

            // Explanation Feedback Box
            if (hasAnswered) {
                Spacer(modifier = Modifier.height(12.dp))
                val isUserCorrect = selectedOption == chapter.quizCorrectIndex

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(Squircle16)
                        .background(if (isUserCorrect) SageGreenContainer else Color(0xFFFFF1F2))
                        .border(
                            BorderStroke(1.dp, if (isUserCorrect) SageGreen else Color(0xFFFDA4AF)),
                            Squircle16
                        )
                        .padding(14.dp)
                ) {
                    Column {
                        Text(
                            text = if (isUserCorrect) "✓ Correct! Institutional Intuition Confirmed." else "✕ Not quite. Review the institutional dynamic:",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isUserCorrect) SageGreenText else CrimsonLoss
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = chapter.quizExplanation,
                            fontSize = 11.sp,
                            color = SlateCharcoal,
                            lineHeight = 16.sp
                        )
                    }
                }
            }
        }
    }
}
