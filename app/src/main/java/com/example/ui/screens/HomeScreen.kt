package com.example.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.CloudDone
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Forest
import androidx.compose.material.icons.filled.FormatQuote
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Savings
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material.icons.filled.Stars
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.data.model.TransactionEntity
import com.example.data.model.TransactionType
import com.example.ui.auth.CompactGoogleSignInChip
import com.example.ui.components.AmbientLivingMeshBackground
import com.example.ui.components.AppleGlassCard
import com.example.ui.components.CURRICULUM_TREE_NODES
import com.example.ui.components.LearningFlowchartTree
import com.example.ui.components.TraderPathRoadmapSection
import com.example.ui.components.appleSpringPress
import com.example.ui.theme.WarmBeigeBackground
import com.example.ui.theme.WarmBeigeBorder
import com.example.ui.theme.WarmBeigeCard
import com.example.ui.theme.WarmBeigeContainer
import com.example.ui.theme.WarmBeigeSurface
import com.example.ui.theme.BrushedGold
import com.example.ui.theme.CreamBackground
import com.example.ui.theme.CreamBorder
import com.example.ui.theme.CreamContainer
import com.example.ui.theme.CreamSurface
import com.example.ui.theme.CrimsonLoss
import com.example.ui.theme.EmeraldProfit
import com.example.ui.theme.GoldDark
import com.example.ui.theme.GoldGleam
import com.example.ui.theme.MetallicGoldBorder
import com.example.ui.theme.ObsidianBlack
import com.example.ui.theme.SageGreen
import com.example.ui.theme.SageGreenAccent
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
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun HomeScreen(
    viewModel: WealthViewModel,
    onOpenAddTransaction: () -> Unit,
    onOpenCheckout: () -> Unit,
    onOpenQuoteDialog: () -> Unit,
    onNavigateToStage: (String) -> Unit
) {
    val summary by viewModel.cashflowSummary.collectAsState()
    val region by viewModel.currentRegion.collectAsState()
    val transactions by viewModel.allTransactions.collectAsState()
    val isVip by viewModel.isVipPro.collectAsState()
    val activeStage by viewModel.activeStage.collectAsState()
    val completedStages by viewModel.completedStages.collectAsState()
    val completedLessons by viewModel.completedLessons.collectAsState()

    val currency = region.currencySymbol
    val currentUser = try {
        Firebase.auth.currentUser
    } catch (_: Throwable) {
        null
    }

    // 0: Learning Tree & Badges (Default focus), 1: Capital Vault (Income Calculator)
    var homeTabState by remember { mutableIntStateOf(0) }

    // Dynamic next chapter or resume point in Masterclass
    val nextLessonToComplete = CURRICULUM_TREE_NODES.firstOrNull { !completedLessons.contains(it.id) }
    val lastLeftLesson = nextLessonToComplete ?: CURRICULUM_TREE_NODES.last()
    val hasStartedAnyLesson = completedLessons.isNotEmpty()

    // Live Masterclass Action Button:
    // Phase 0: "START LEARNING" (General Invitation / Pro Curriculum)
    // Phase 1: Dynamic Chapter (In beginning: First comprehensive masterclass; Later: Chapter where user last left)
    var liveMasterclassPhase by remember { mutableIntStateOf(0) }

    // Live, breathing active cycle between "Start Learning" and the chapter where user last left
    LaunchedEffect(Unit) {
        while (true) {
            delay(3800L)
            liveMasterclassPhase = (liveMasterclassPhase + 1) % 2
        }
    }

    var showSmoothLaunchModal by remember { mutableStateOf(false) }

    AmbientLivingMeshBackground {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(10.dp))

                // =====================================================================
                // MINIMALIST APPLE-HIG TOP BAR
                // Clean left identity & sleek top-right corner sign-in + currency chips
                // =====================================================================
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "TRADING: THE GUIDEBOOK",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = TrustBlue,
                            letterSpacing = 1.3.sp
                        )
                        Text(
                            text = "Apex Citadel",
                            fontSize = 19.sp,
                            fontWeight = FontWeight.Bold,
                            color = SlateCharcoal
                        )
                    }

                    // Top-right corner minimalist controls
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        // Region indicator chip
                        Box(
                            modifier = Modifier
                                .clip(Squircle12)
                                .background(WarmBeigeCard)
                                .border(BorderStroke(1.dp, WarmBeigeBorder), Squircle12)
                                .padding(horizontal = 9.dp, vertical = 5.dp)
                        ) {
                            Text(
                                text = "${region.code} $currency",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = SlateDark
                            )
                        }

                        Spacer(modifier = Modifier.width(6.dp))

                        // Compact account avatar or minimalist sign-in chip in top-right
                        if (currentUser != null) {
                            Box(
                                modifier = Modifier
                                    .clip(Squircle12)
                                    .background(SageGreenContainer)
                                    .border(BorderStroke(1.dp, SageGreen.copy(alpha = 0.35f)), Squircle12)
                                    .padding(horizontal = 9.dp, vertical = 5.dp)
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.CloudDone,
                                        contentDescription = null,
                                        tint = SageGreen,
                                        modifier = Modifier.size(13.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = currentUser.displayName?.take(8) ?: "Synced",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = SageGreenText
                                    )
                                }
                            }
                        } else {
                            CompactGoogleSignInChip(
                                onAuthSuccess = { },
                                onAuthError = { }
                            )
                        }
                    }
                }
            }

            // =========================================================================
            // APPLE-HIG SQUIRCLE SEGMENTED TAB SWITCHER
            // [ 🌳 Learning Tree & Badges ]    [ 💰 Capital Vault ]
            // Gives immediate 1-tap access to the income calculator side tab
            // =========================================================================
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(Squircle20)
                        .background(WarmBeigeCard.copy(alpha = 0.95f))
                        .border(BorderStroke(1.dp, WarmBeigeBorder), Squircle20)
                        .padding(5.dp)
                ) {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        // Tab 0: Learning Tree
                        val isTreeSelected = homeTabState == 0
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(44.dp)
                                .clip(Squircle16)
                                .background(
                                    if (isTreeSelected) TrustBlue else Color.Transparent
                                )
                                .clickable { homeTabState = 0 }
                                .appleSpringPress(onClick = { homeTabState = 0 }),
                            contentAlignment = Alignment.Center
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Forest,
                                    contentDescription = null,
                                    tint = if (isTreeSelected) Color.White else SlateCharcoal,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "Learning Tree",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isTreeSelected) Color.White else SlateCharcoal
                                )
                            }
                        }

                        // Tab 1: Capital Vault (Income Calculator side tab)
                        val isVaultSelected = homeTabState == 1
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(44.dp)
                                .clip(Squircle16)
                                .background(
                                    if (isVaultSelected) TrustBlue else Color.Transparent
                                )
                                .clickable { homeTabState = 1 }
                                .appleSpringPress(onClick = { homeTabState = 1 }),
                            contentAlignment = Alignment.Center
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.AccountBalanceWallet,
                                    contentDescription = null,
                                    tint = if (isVaultSelected) Color.White else SlateCharcoal,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "Capital Vault",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isVaultSelected) Color.White else SlateCharcoal
                                )
                            }
                        }
                    }
                }
            }

            // =========================================================================
            // MAIN VIEW AREA: SWITCHES SEAMLESSLY WITH SPRING PHYSICS
            // =========================================================================
            if (homeTabState == 0) {
                // =====================================================================
                // START MASTERCLASS: HIGH-END LIVE DYNAMIC ACTION BUTTON
                // Positioned prominently in the middle of the home page, directly above
                // where the interactive learning tree begins.
                // Live active button: alternates smoothly between "START LEARNING" and
                // the chapter where the user last left (in beginning: Chapter 1).
                // Warm minimalist beige styling with tactile spring feedback.
                // =====================================================================
                item {
                    val infiniteTransition = rememberInfiniteTransition(label = "focusPulse")
                    val pulseGlow by infiniteTransition.animateFloat(
                        initialValue = 0.35f,
                        targetValue = 0.85f,
                        animationSpec = infiniteRepeatable(
                            animation = tween(2200, easing = FastOutSlowInEasing),
                            repeatMode = RepeatMode.Reverse
                        ),
                        label = "pulseGlow"
                    )

                    AppleGlassCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("focal_action_panel"),
                        shape = Squircle28,
                        backgroundColor = WarmBeigeCard.copy(alpha = 0.96f),
                        borderColor = TrustBlue.copy(alpha = pulseGlow * 0.75f),
                        elevation = 6.dp
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp)
                        ) {
                            // Header: Live Indicator & Status Chip
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Box(
                                        modifier = Modifier
                                            .size(8.dp)
                                            .clip(CircleShape)
                                            .background(SageGreen)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = "LIVE MASTERCLASS ENGINE",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = TrustBlue,
                                        letterSpacing = 1.3.sp
                                    )
                                }

                                Box(
                                    modifier = Modifier
                                        .clip(Squircle12)
                                        .background(WarmBeigeContainer)
                                        .border(BorderStroke(1.dp, WarmBeigeBorder), Squircle12)
                                        .padding(horizontal = 9.dp, vertical = 4.dp)
                                ) {
                                    Text(
                                        text = if (!hasStartedAnyLesson) "PILLAR 1: START HERE" else "RESUME CH ${lastLeftLesson.orderNumber}",
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = TrustBlue
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            // Educational Value Proposition
                            Column(modifier = Modifier.fillMaxWidth()) {
                                Text(
                                    text = "The Sovereign Institutional Masterclass",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = SlateCharcoal
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = "6 Comprehensive Pillars • VOC 1602 roots to Smart Money Concepts & Liquidity Spreads. Master the complete system before committing capital.",
                                    fontSize = 12.sp,
                                    color = SlateMuted
                                )
                            }

                            Spacer(modifier = Modifier.height(14.dp))

                            // =========================================================
                            // THE LIVE BUTTON: Alternates between "START LEARNING" and
                            // "CHAPTER WHERE USER LAST LEFT" (or Chapter 1 in beginning)
                            // =========================================================
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .shadow(
                                        elevation = 10.dp,
                                        shape = Squircle24,
                                        spotColor = TrustBlue.copy(alpha = 0.45f)
                                    )
                                    .clip(Squircle24)
                                    .background(
                                        Brush.horizontalGradient(
                                            listOf(
                                                TrustBlue,
                                                TrustBlueHover,
                                                Color(0xFF0369A1)
                                            )
                                        )
                                    )
                                    .border(
                                        BorderStroke(1.5.dp, Color.White.copy(alpha = 0.45f)),
                                        Squircle24
                                    )
                                    .appleSpringPress(
                                        onClick = {
                                            viewModel.selectRoadmapStage(2)
                                            onNavigateToStage("masterclass")
                                        }
                                    )
                                    .testTag("start_masterclass_button")
                                    .padding(vertical = 18.dp, horizontal = 20.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                AnimatedContent(
                                    targetState = liveMasterclassPhase,
                                    transitionSpec = {
                                        (fadeIn(animationSpec = spring(stiffness = Spring.StiffnessMediumLow)) +
                                            slideInVertically(
                                                animationSpec = spring(stiffness = Spring.StiffnessMediumLow),
                                                initialOffsetY = { it / 2 }
                                            )) togetherWith
                                            (fadeOut(animationSpec = spring(stiffness = Spring.StiffnessMediumLow)) +
                                                slideOutVertically(
                                                    animationSpec = spring(stiffness = Spring.StiffnessMediumLow),
                                                    targetOffsetY = { -it / 2 }
                                                ))
                                    },
                                    label = "liveMasterclassButtonContent"
                                ) { phase ->
                                    if (phase == 0) {
                                        // STATE A: "START LEARNING"
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                modifier = Modifier.weight(1f)
                                            ) {
                                                Box(
                                                    modifier = Modifier
                                                        .size(46.dp)
                                                        .clip(Squircle16)
                                                        .background(Color.White.copy(alpha = 0.2f))
                                                        .border(BorderStroke(1.dp, Color.White.copy(alpha = 0.4f)), Squircle16),
                                                    contentAlignment = Alignment.Center
                                                ) {
                                                    Icon(
                                                        imageVector = Icons.AutoMirrored.Filled.MenuBook,
                                                        contentDescription = null,
                                                        tint = Color.White,
                                                        modifier = Modifier.size(24.dp)
                                                    )
                                                }

                                                Spacer(modifier = Modifier.width(14.dp))

                                                Column {
                                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                                        Text(
                                                            text = "START LEARNING",
                                                            fontSize = 17.sp,
                                                            fontWeight = FontWeight.Bold,
                                                            color = Color.White,
                                                            letterSpacing = 0.8.sp
                                                        )
                                                        Spacer(modifier = Modifier.width(6.dp))
                                                        Box(
                                                            modifier = Modifier
                                                                .clip(Squircle12)
                                                                .background(Color.White)
                                                                .padding(horizontal = 6.dp, vertical = 2.dp)
                                                        ) {
                                                            Text(
                                                                text = "MASTERCLASS",
                                                                fontSize = 9.sp,
                                                                fontWeight = FontWeight.Black,
                                                                color = TrustBlue
                                                            )
                                                        }
                                                    }
                                                    Spacer(modifier = Modifier.height(2.dp))
                                                    Text(
                                                        text = "Comprehensive 6 Pillars • Candlesticks & Liquidity",
                                                        fontSize = 11.sp,
                                                        color = Color.White.copy(alpha = 0.88f),
                                                        maxLines = 1
                                                    )
                                                }
                                            }

                                            Icon(
                                                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                                contentDescription = null,
                                                tint = Color.White,
                                                modifier = Modifier.size(20.dp)
                                            )
                                        }
                                    } else {
                                        // STATE B: Dynamic Chapter (Beginning: Chapter 1; Later: Chapter where user last left)
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                modifier = Modifier.weight(1f)
                                            ) {
                                                Box(
                                                    modifier = Modifier
                                                        .size(46.dp)
                                                        .clip(Squircle16)
                                                        .background(GoldDark.copy(alpha = 0.3f))
                                                        .border(BorderStroke(1.dp, GoldGleam.copy(alpha = 0.6f)), Squircle16),
                                                    contentAlignment = Alignment.Center
                                                ) {
                                                    Icon(
                                                        imageVector = Icons.Default.School,
                                                        contentDescription = null,
                                                        tint = GoldGleam,
                                                        modifier = Modifier.size(24.dp)
                                                    )
                                                }

                                                Spacer(modifier = Modifier.width(14.dp))

                                                Column {
                                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                                        Text(
                                                            text = if (!hasStartedAnyLesson) {
                                                                "CHAPTER 1: VOC GENESIS"
                                                            } else {
                                                                "RESUME CH ${lastLeftLesson.orderNumber}: ${lastLeftLesson.title.take(18)}..."
                                                            },
                                                            fontSize = 15.sp,
                                                            fontWeight = FontWeight.Bold,
                                                            color = Color.White,
                                                            letterSpacing = 0.6.sp
                                                        )
                                                        Spacer(modifier = Modifier.width(6.dp))
                                                        Box(
                                                            modifier = Modifier
                                                                .clip(Squircle12)
                                                                .background(GoldGleam)
                                                                .padding(horizontal = 6.dp, vertical = 2.dp)
                                                        ) {
                                                            Text(
                                                                text = if (!hasStartedAnyLesson) "FIRST MASTERCLASS" else "WHERE YOU LEFT",
                                                                fontSize = 9.sp,
                                                                fontWeight = FontWeight.Black,
                                                                color = ObsidianBlack
                                                            )
                                                        }
                                                    }
                                                    Spacer(modifier = Modifier.height(2.dp))
                                                    Text(
                                                        text = if (!hasStartedAnyLesson) {
                                                            "Double Auction & 1602 Roots • Tap to Start"
                                                        } else {
                                                            "${lastLeftLesson.conceptHeadline.take(40)}... • Tap to Continue"
                                                        },
                                                        fontSize = 11.sp,
                                                        color = Color.White.copy(alpha = 0.90f),
                                                        maxLines = 1
                                                    )
                                                }
                                            }

                                            Icon(
                                                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                                contentDescription = null,
                                                tint = GoldGleam,
                                                modifier = Modifier.size(20.dp)
                                            )
                                        }
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            // Live Status & Cycling Indicator Dots
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = if (liveMasterclassPhase == 0) {
                                        "• Live Desk: Active • Alternating with curriculum syllabus"
                                    } else {
                                        if (!hasStartedAnyLesson) "• Comprehensive Pillar 1: 100% Free Foundation Course"
                                        else "• Resume Point: Chapter ${lastLeftLesson.orderNumber} (${lastLeftLesson.levelTier})"
                                    },
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = SlateMuted
                                )

                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(width = if (liveMasterclassPhase == 0) 18.dp else 6.dp, height = 6.dp)
                                            .clip(CircleShape)
                                            .background(
                                                if (liveMasterclassPhase == 0) TrustBlue else Color(0xFFCBD5E1)
                                            )
                                    )
                                    Box(
                                        modifier = Modifier
                                            .size(width = if (liveMasterclassPhase == 1) 18.dp else 6.dp, height = 6.dp)
                                            .clip(CircleShape)
                                            .background(
                                                if (liveMasterclassPhase == 1) GoldDark else Color(0xFFCBD5E1)
                                            )
                                    )
                                }
                            }
                        }
                    }
                }

                // =====================================================================
                // TAB 0: INTERACTIVE LEARNING FLOWCHART TREE & BADGES
                // (Starts right here, immediately below Start Masterclass)
                // =====================================================================
                item {
                    LearningFlowchartTree(
                        completedLessonIds = completedLessons,
                        onCompleteLesson = { lessonId ->
                            viewModel.completeLesson(lessonId)
                        },
                        onNavigateToMasterclassChapter = { chapterId ->
                            onNavigateToStage("masterclass")
                        },
                        onOpenUpgradeModal = onOpenCheckout
                    )
                }

                // 4-Stage Sequential Roadmap Section
                item {
                    TraderPathRoadmapSection(
                        currentStage = activeStage,
                        completedStages = completedStages,
                        onSelectStage = { stage ->
                            viewModel.selectRoadmapStage(stage.stageNumber)
                            onNavigateToStage(stage.targetTab)
                        },
                        onSkipToStage = { stageNum ->
                            viewModel.selectRoadmapStage(stageNum)
                            val target = when (stageNum) {
                                1 -> "home"
                                2 -> "masterclass"
                                3 -> "analytics"
                                4 -> "mentor"
                                else -> "home"
                            }
                            onNavigateToStage(target)
                        },
                        onOpenCheckout = onOpenCheckout
                    )
                }
            } else {
                // =====================================================================
                // TAB 1: CAPITAL VAULT & INCOME TRACKER SIDE TAB
                // (Relocated as requested: accessible cleanly with a single tap)
                // =====================================================================
                item {
                    AppleGlassCard(
                        modifier = Modifier.fillMaxWidth(),
                        shape = Squircle28,
                        backgroundColor = WarmBeigeCard.copy(alpha = 0.96f),
                        borderColor = WarmBeigeBorder,
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
                                        text = "STAGE 1 • CAPITAL VAULT",
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = TrustBlue,
                                        letterSpacing = 1.2.sp
                                    )
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        text = "Money Foundations & Run-Rate",
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = SlateCharcoal
                                    )
                                }

                                Box(
                                    modifier = Modifier
                                        .clip(Squircle12)
                                        .background(SageGreenContainer)
                                        .padding(horizontal = 10.dp, vertical = 5.dp)
                                ) {
                                    Text(
                                        text = "${summary.savingsRatePercentage.toInt()}% Saved",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = SageGreenText
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            Text(
                                text = "Net Wealth Vault",
                                fontSize = 12.sp,
                                color = SlateMuted
                            )
                            Text(
                                text = "$currency${"%,.2f".format(summary.netSavings)}",
                                fontSize = 32.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (summary.netSavings >= 0) SlateCharcoal else CrimsonLoss
                            )

                            Spacer(modifier = Modifier.height(14.dp))

                            // Daily Inflow vs Burn
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .clip(Squircle16)
                                        .background(SageGreenContainer.copy(alpha = 0.5f))
                                        .padding(12.dp)
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            imageVector = Icons.Default.ArrowUpward,
                                            contentDescription = "Income",
                                            tint = SageGreen,
                                            modifier = Modifier.size(15.dp)
                                        )
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Column {
                                            Text(text = "Daily Inflow", fontSize = 10.sp, color = SageGreenText)
                                            Text(
                                                text = "$currency${"%,.0f".format(summary.dailyIncome)}",
                                                fontSize = 14.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = SageGreenText
                                            )
                                        }
                                    }
                                }

                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .clip(Squircle16)
                                        .background(Color(0xFFFEE2E2).copy(alpha = 0.5f))
                                        .padding(12.dp)
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            imageVector = Icons.Default.ArrowDownward,
                                            contentDescription = "Expense",
                                            tint = CrimsonLoss,
                                            modifier = Modifier.size(15.dp)
                                        )
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Column {
                                            Text(text = "Daily Burn", fontSize = 10.sp, color = CrimsonLoss)
                                            Text(
                                                text = "$currency${"%,.0f".format(summary.dailyExpense)}",
                                                fontSize = 14.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = CrimsonLoss
                                            )
                                        }
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            // Log Cashflow Button with Squircle and Spring Physics
                            Button(
                                onClick = onOpenAddTransaction,
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = TrustBlue,
                                    contentColor = Color.White
                                ),
                                shape = Squircle16,
                                elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(48.dp)
                                    .appleSpringPress(onClick = onOpenAddTransaction)
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.Add,
                                        contentDescription = null,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = "Log Cashflow Transaction",
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }
                            }
                        }
                    }
                }

                // Recent Cashflow Transactions header
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Cashflow History (${transactions.size})",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = SlateCharcoal
                        )
                        Text(
                            text = "Ledger Records",
                            fontSize = 12.sp,
                            color = SlateMuted
                        )
                    }
                }

                if (transactions.isEmpty()) {
                    item {
                        AppleGlassCard(
                            modifier = Modifier.fillMaxWidth(),
                            shape = Squircle20,
                            backgroundColor = WarmBeigeCard.copy(alpha = 0.95f),
                            borderColor = WarmBeigeBorder,
                            elevation = 2.dp
                        ) {
                            Column(
                                modifier = Modifier.padding(24.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Savings,
                                    contentDescription = null,
                                    tint = SlateSubtle,
                                    modifier = Modifier.size(32.dp)
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "Your Capital Vault is Ready",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    color = SlateCharcoal
                                )
                                Text(
                                    text = "Log your first income or expense to activate Stage 1.",
                                    fontSize = 12.sp,
                                    color = SlateMuted
                                )
                            }
                        }
                    }
                } else {
                    items(transactions.take(8)) { tx ->
                        AppleGlassCard(
                            modifier = Modifier.fillMaxWidth(),
                            shape = Squircle16,
                            backgroundColor = WarmBeigeCard.copy(alpha = 0.96f),
                            borderColor = WarmBeigeBorder,
                            elevation = 1.dp
                        ) {
                            Row(
                                modifier = Modifier.padding(14.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Box(
                                        modifier = Modifier
                                            .size(36.dp)
                                            .clip(Squircle12)
                                            .background(
                                                if (tx.type == TransactionType.INCOME) SageGreenContainer else Color(0xFFFEE2E2)
                                            ),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            imageVector = if (tx.type == TransactionType.INCOME) Icons.Default.ArrowUpward else Icons.Default.ArrowDownward,
                                            contentDescription = tx.type.name,
                                            tint = if (tx.type == TransactionType.INCOME) SageGreen else CrimsonLoss,
                                            modifier = Modifier.size(16.dp)
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Column {
                                        Text(
                                            text = tx.title,
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.SemiBold,
                                            color = SlateCharcoal
                                        )
                                        Text(
                                            text = "${tx.category} • ${SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(Date(tx.timestamp))}",
                                            fontSize = 11.sp,
                                            color = SlateMuted
                                        )
                                    }
                                }

                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = "${if (tx.type == TransactionType.INCOME) "+" else "-"}$currency${"%,.2f".format(tx.amount)}",
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (tx.type == TransactionType.INCOME) SageGreenAccent else CrimsonLoss
                                    )
                                    IconButton(
                                        onClick = { viewModel.deleteTransaction(tx) },
                                        modifier = Modifier.appleSpringPress()
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Delete,
                                            contentDescription = "Delete",
                                            tint = SlateSubtle,
                                            modifier = Modifier.size(16.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(30.dp))
            }
        }
    }
}
