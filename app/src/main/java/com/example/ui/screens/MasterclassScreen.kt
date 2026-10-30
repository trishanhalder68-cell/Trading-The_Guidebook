package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.FastForward
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.model.MasterclassChapter
import com.example.ui.components.CitadelBadgeShape
import com.example.ui.components.CitadelCardShape
import com.example.ui.components.GoldSectionHeader
import com.example.ui.components.LuxuryCard
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

    var activeReadingChapter by remember { mutableStateOf<MasterclassChapter?>(null) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(ObsidianBlack)
    ) {
        // Kang's Citadel Black & Gold Marble Texture
        Image(
            painter = painterResource(id = R.drawable.bg_gold_marble),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            alpha = 0.25f,
            modifier = Modifier.fillMaxSize()
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 18.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(16.dp))

                if (activeReadingChapter != null) {
                    // Back Bar
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = { activeReadingChapter = null },
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CutCornerShape(8.dp))
                                .background(CitadelCardElevated)
                                .border(1.dp, MetallicGoldBorder.copy(alpha = 0.5f), CutCornerShape(8.dp))
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back to Chapters",
                                tint = BrushedGold
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "Back to Masterclass Syllabus",
                            fontSize = 13.sp,
                            color = TextSecondary,
                            fontWeight = FontWeight.Medium
                        )
                    }
                    Spacer(modifier = Modifier.height(14.dp))
                } else {
                    // Citadel Masterclass Hero Card
                    LuxuryCard(hasGoldGlow = true) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(46.dp)
                                    .clip(CutCornerShape(8.dp))
                                    .background(BrushedGold.copy(alpha = 0.2f))
                                    .border(1.2.dp, MetallicGoldBorder, CutCornerShape(8.dp)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.School,
                                    contentDescription = null,
                                    tint = BrushedGold,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = "Citadel Trading Academy",
                                    fontSize = 17.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = TextPrimary
                                )
                                Text(
                                    text = "From 1602 Amsterdam to Modern Global Macro",
                                    fontSize = 12.sp,
                                    color = BrushedGold
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        Text(
                            text = "Master the global market machinery from the ground up. Level 1 offers a complete, lucrative briefing of everything that happens in world trading. Progress level by level or unlock the full Annual Sovereign Pass at ₹999/yr.",
                            fontSize = 12.sp,
                            color = TextSecondary,
                            lineHeight = 18.sp
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Box(
                                modifier = Modifier
                                    .clip(CitadelBadgeShape)
                                    .background(EmeraldProfit.copy(alpha = 0.15f))
                                    .border(1.dp, EmeraldProfit.copy(alpha = 0.5f), CitadelBadgeShape)
                                    .padding(horizontal = 10.dp, vertical = 5.dp)
                            ) {
                                Text("Level 1: Free Initiation", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = EmeraldProfit)
                            }
                            Box(
                                modifier = Modifier
                                    .clip(CitadelBadgeShape)
                                    .background(BrushedGold.copy(alpha = 0.15f))
                                    .border(1.dp, MetallicGoldBorder.copy(alpha = 0.5f), CitadelBadgeShape)
                                    .padding(horizontal = 10.dp, vertical = 5.dp)
                            ) {
                                Text(if (isVip) "All Levels Active" else "Annual Pass: ₹999/yr", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = GoldGleam)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    GoldSectionHeader(
                        title = "Progressive Mastery Pathway",
                        subtitle = "Select a level to learn or fast-track ahead",
                        badgeText = "6 Levels"
                    )

                    Spacer(modifier = Modifier.height(10.dp))
                }
            }

            if (activeReadingChapter != null) {
                // Chapter Reader View
                item {
                    val chapter = activeReadingChapter!!
                    val isUnlocked = isVip || unlockedIds.contains(chapter.id)

                    if (!isUnlocked) {
                        // Locked Paywall Gate for Level
                        ChapterLockedGate(
                            chapter = chapter,
                            currencySymbol = region.currencySymbol,
                            onUnlock = { viewModel.unlockChapter(chapter.id) },
                            onOpenVipCheckout = onOpenCheckout
                        )
                    } else {
                        // Full Chapter Reader with Fast-Track Skip Option
                        ChapterFullReader(
                            chapter = chapter,
                            currencySymbol = region.currencySymbol,
                            onAskAiTutor = { prompt ->
                                onNavigateToAiChatWithPrompt(prompt)
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
                // Chapters List Cards
                items(chapters, key = { it.id }) { chapter ->
                    val isUnlocked = isVip || unlockedIds.contains(chapter.id)
                    ChapterSyllabusCard(
                        chapter = chapter,
                        isUnlocked = isUnlocked,
                        currencySymbol = region.currencySymbol,
                        onClick = { activeReadingChapter = chapter }
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }

            item {
                Spacer(modifier = Modifier.height(90.dp))
            }
        }
    }
}

@Composable
fun ChapterSyllabusCard(
    chapter: MasterclassChapter,
    isUnlocked: Boolean,
    currencySymbol: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                1.dp,
                if (isUnlocked) MetallicGoldBorder else Color(0xFF2E2E38),
                CitadelCardShape
            )
            .clickable { onClick() },
        shape = CitadelCardShape,
        colors = CardDefaults.cardColors(containerColor = CitadelCardElevated)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CutCornerShape(6.dp))
                        .background(if (isUnlocked) BrushedGold else Color(0xFF252530)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "${chapter.chapterNumber}",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isUnlocked) ObsidianBlack else TextTertiary
                    )
                }

                Spacer(modifier = Modifier.width(10.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(chapter.era, fontSize = 10.sp, color = BrushedGold, fontWeight = FontWeight.Bold)
                    Text(
                        text = chapter.title,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                }

                Box(
                    modifier = Modifier
                        .clip(CitadelBadgeShape)
                        .background(if (isUnlocked) EmeraldProfit.copy(alpha = 0.15f) else Color(0xFF2E2414))
                        .border(1.dp, if (isUnlocked) EmeraldProfit.copy(alpha = 0.5f) else MetallicGoldBorder.copy(alpha = 0.5f), CitadelBadgeShape)
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = if (isUnlocked) Icons.Default.CheckCircle else Icons.Default.Lock,
                            contentDescription = null,
                            tint = if (isUnlocked) EmeraldProfit else GoldGleam,
                            modifier = Modifier.size(12.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = if (chapter.isFree) "FREE" else if (isUnlocked) "UNLOCKED" else "$currencySymbol${chapter.unlockPriceInr}",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isUnlocked) EmeraldProfit else GoldGleam
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = chapter.subtitle,
                fontSize = 12.sp,
                color = TextSecondary,
                lineHeight = 16.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(imageVector = Icons.Default.MenuBook, contentDescription = null, tint = TextTertiary, modifier = Modifier.size(14.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("${chapter.estimatedReadMinutes} min read", fontSize = 11.sp, color = TextTertiary)

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = if (isUnlocked) "Open Session →" else "Unlock Session →",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = BrushedGold
                )
            }
        }
    }
}

@Composable
fun ChapterLockedGate(
    chapter: MasterclassChapter,
    currencySymbol: String,
    onUnlock: () -> Unit,
    onOpenVipCheckout: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                1.5.dp,
                Brush.verticalGradient(listOf(MetallicGoldBorder, Color(0xFF6B531B))),
                CitadelCardShape
            ),
        shape = CitadelCardShape,
        colors = CardDefaults.cardColors(containerColor = CitadelCardElevated)
    ) {
        Column(
            modifier = Modifier.padding(22.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(CutCornerShape(10.dp))
                    .background(BrushedGold.copy(alpha = 0.15f))
                    .border(1.2.dp, MetallicGoldBorder, CutCornerShape(10.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = null,
                    tint = BrushedGold,
                    modifier = Modifier.size(28.dp)
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = "Level ${chapter.chapterNumber}: Restricted Pro Curriculum",
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )

            Text(
                text = chapter.title,
                fontSize = 13.sp,
                color = GoldGleam,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(top = 2.dp)
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Value Hook
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(CutCornerShape(10.dp))
                    .background(Color(0xFF121217))
                    .border(1.dp, MetallicGoldBorder.copy(alpha = 0.4f), CutCornerShape(10.dp))
                    .padding(14.dp)
            ) {
                Column {
                    Text("Why You Need This Knowledge:", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = BrushedGold)
                    Text(
                        text = chapter.hookStatement,
                        fontSize = 12.sp,
                        color = TextSecondary,
                        lineHeight = 17.sp,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Agenda List
            Text("Session Syllabus Outline:", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = TextPrimary, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(8.dp))
            chapter.agenda.forEach { item ->
                Row(modifier = Modifier.fillMaxWidth().padding(vertical = 3.dp), verticalAlignment = Alignment.Top) {
                    Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null, tint = BrushedGold, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(item, fontSize = 11.sp, color = TextSecondary)
                }
            }

            Spacer(modifier = Modifier.height(22.dp))

            // Micro-Unlock Button for this Level
            Button(
                onClick = onUnlock,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = CutCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = BrushedGold,
                    contentColor = ObsidianBlack
                )
            ) {
                Text("Unlock Level ${chapter.chapterNumber} for $currencySymbol${chapter.unlockPriceInr}", fontSize = 13.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Annual All-Access Pass Option
            OutlinedButton(
                onClick = onOpenVipCheckout,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(46.dp),
                shape = CutCornerShape(8.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = BrushedGold),
                border = androidx.compose.foundation.BorderStroke(1.dp, MetallicGoldBorder)
            ) {
                Icon(imageVector = Icons.Default.AutoAwesome, contentDescription = null, modifier = Modifier.size(15.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("Unlock All Levels (Annual Pass $currencySymbol 999)", fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
            }
        }
    }
}

@Composable
fun ChapterFullReader(
    chapter: MasterclassChapter,
    currencySymbol: String,
    onAskAiTutor: (String) -> Unit,
    onSkipToNext: (String) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        // Fast-Track Skip Option (At Top)
        if (chapter.nextLevelId != null) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, MetallicGoldBorder.copy(alpha = 0.5f), CutCornerShape(8.dp)),
                shape = CutCornerShape(8.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF14141A))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(imageVector = Icons.Default.FastForward, contentDescription = null, tint = BrushedGold, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Already know these fundamentals?", fontSize = 11.sp, color = TextSecondary)
                        Text("Skip straight to Level ${chapter.chapterNumber + 1}", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = GoldGleam)
                    }
                    Button(
                        onClick = { onSkipToNext(chapter.nextLevelId) },
                        shape = CutCornerShape(6.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = BrushedGold,
                            contentColor = ObsidianBlack
                        )
                    ) {
                        Text("Skip Ahead →", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
            Spacer(modifier = Modifier.height(14.dp))
        }

        // Chapter Header Card
        LuxuryCard(hasGoldGlow = true) {
            Text(
                text = "LEVEL ${chapter.chapterNumber} • ${chapter.era.uppercase()}",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = BrushedGold,
                letterSpacing = 1.sp
            )
            Text(
                text = chapter.title,
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary,
                modifier = Modifier.padding(top = 4.dp)
            )
            Text(
                text = chapter.subtitle,
                fontSize = 13.sp,
                color = TextSecondary,
                modifier = Modifier.padding(top = 4.dp)
            )

            Spacer(modifier = Modifier.height(14.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(CutCornerShape(8.dp))
                    .background(Color(0xFF141419))
                    .padding(12.dp)
            ) {
                Text(
                    text = "“${chapter.hookStatement}”",
                    fontSize = 12.sp,
                    fontStyle = FontStyle.Italic,
                    color = GoldGleam,
                    lineHeight = 17.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Core Sections
        chapter.coreContentSections.forEach { section ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, MetallicGoldBorder.copy(alpha = 0.5f), CutCornerShape(12.dp)),
                shape = CutCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = CitadelCardElevated)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(
                        text = section.sectionTitle,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = BrushedGold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = section.body,
                        fontSize = 13.sp,
                        color = TextPrimary,
                        lineHeight = 20.sp
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(CutCornerShape(8.dp))
                            .background(Color(0xFF0F1E16))
                            .border(1.dp, EmeraldProfit.copy(alpha = 0.4f), CutCornerShape(8.dp))
                            .padding(10.dp)
                    ) {
                        Row(verticalAlignment = Alignment.Top) {
                            Icon(imageVector = Icons.Default.AutoAwesome, contentDescription = null, tint = EmeraldProfit, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text("Key Wealth Takeaway:", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = EmeraldProfit)
                                Text(section.keyTakeaway, fontSize = 11.sp, color = TextPrimary, lineHeight = 15.sp)
                            }
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
        }

        // Golden Rules & Sins Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, MetallicGoldBorder.copy(alpha = 0.6f), CutCornerShape(12.dp)),
            shape = CutCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = CitadelCardElevated)
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                Text("Golden Rules For This Level:", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = EmeraldProfit)
                Spacer(modifier = Modifier.height(6.dp))
                chapter.goldenRulesDo.forEach { rule ->
                    Row(modifier = Modifier.padding(vertical = 3.dp), verticalAlignment = Alignment.Top) {
                        Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null, tint = EmeraldProfit, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(rule, fontSize = 12.sp, color = TextPrimary)
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                Text("Cardinal Sins To Avoid (Never Do This):", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = CrimsonLoss)
                Spacer(modifier = Modifier.height(6.dp))
                chapter.cardinalSinsAvoid.forEach { sin ->
                    Row(modifier = Modifier.padding(vertical = 3.dp), verticalAlignment = Alignment.Top) {
                        Text("✕", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = CrimsonLoss)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(sin, fontSize = 12.sp, color = TextPrimary)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Ask Chapter AI Tutor
        Button(
            onClick = { onAskAiTutor(chapter.aiTutorStarterPrompt) },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = CutCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = BrushedGold,
                contentColor = ObsidianBlack
            )
        ) {
            Icon(imageVector = Icons.Default.SmartToy, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Ask Level AI Mentor (Gemini 3.8 Flash)", fontSize = 13.sp, fontWeight = FontWeight.Bold)
        }

        // Fast-Track Skip to Next Session at Bottom
        if (chapter.nextLevelId != null) {
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedButton(
                onClick = { onSkipToNext(chapter.nextLevelId) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(46.dp),
                shape = CutCornerShape(8.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = BrushedGold),
                border = androidx.compose.foundation.BorderStroke(1.dp, MetallicGoldBorder)
            ) {
                Icon(imageVector = Icons.Default.FastForward, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("Proceed to Level ${chapter.chapterNumber + 1} →", fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}
