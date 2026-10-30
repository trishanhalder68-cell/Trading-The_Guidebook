package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AutoGraph
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.InsertChart
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import com.example.ui.components.appleSpringPress
import com.example.data.ai.configureAppCheck
import com.example.ui.components.AddTransactionSheet
import com.example.ui.components.MotivationalQuoteDialog
import com.example.ui.components.PaymentCheckoutModal
import com.example.ui.screens.AiMentorScreen
import com.example.ui.screens.AnalyticsScreen
import com.example.ui.screens.GlossaryScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.MarketRadarScreen
import com.example.ui.screens.MasterclassScreen
import com.example.ui.screens.SimulatedExecutionScreen
import com.example.ui.theme.BrushedGold
import com.example.ui.theme.CreamBackground
import com.example.ui.theme.CreamBorder
import com.example.ui.theme.CreamContainer
import com.example.ui.theme.CreamSurface
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.theme.SlateCharcoal
import com.example.ui.theme.SlateMuted
import com.example.ui.theme.SlateSubtle
import com.example.ui.theme.TrustBlue
import com.example.ui.theme.TrustBlueContainer
import com.example.viewmodel.WealthViewModel

class MainActivity : ComponentActivity() {
    private val viewModel: WealthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try {
            // Configure App Check with launch intent before any UI or Firebase operations
            configureAppCheck(this, intent)
        } catch (t: Throwable) {
            android.util.Log.e("MainActivity", "App Check initialization non-fatal error", t)
        }

        enableEdgeToEdge()

        setContent {
            MyApplicationTheme {
                MainAppScreen(viewModel = viewModel)
            }
        }
    }
}

enum class NavigationTab(val label: String, val icon: ImageVector) {
    HOME("The Path", Icons.Default.AccountBalanceWallet),
    MASTERCLASS("Basics", Icons.AutoMirrored.Filled.MenuBook),
    ANALYTICS("Analysis", Icons.Default.InsertChart),
    SIMULATION("Execution", Icons.Default.AutoGraph),
    MENTOR("AI Mentor", Icons.Default.SmartToy)
}

@Composable
fun MainAppScreen(viewModel: WealthViewModel) {
    var selectedTab by remember { mutableIntStateOf(0) }
    var guidebookSubTab by remember { mutableIntStateOf(0) } // 0: Masterclass, 1: Glossary Dictionary
    var analyticsSubTab by remember { mutableIntStateOf(0) } // 0: Charts & Multiplier, 1: Macro Radar

    var showAddTransactionSheet by remember { mutableStateOf(false) }
    var showCheckoutModal by remember { mutableStateOf(false) }
    var showQuoteDialog by remember { mutableStateOf(false) }

    val region by viewModel.currentRegion.collectAsState()
    val isVip by viewModel.isVipPro.collectAsState()
    val currentQuote by viewModel.currentQuote.collectAsState()


    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = CreamBackground,
        bottomBar = {
            NavigationBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .windowInsetsPadding(WindowInsets.navigationBars)
                    .border(BorderStroke(1.dp, CreamBorder)),
                containerColor = CreamSurface,
                tonalElevation = 6.dp
            ) {
                NavigationTab.entries.forEachIndexed { index, tab ->
                    val isSelected = selectedTab == index
                    NavigationBarItem(
                        selected = isSelected,
                        onClick = { selectedTab = index },
                        icon = {
                            Icon(
                                imageVector = tab.icon,
                                contentDescription = tab.label,
                                modifier = Modifier.size(22.dp)
                            )
                        },
                        label = {
                            Text(
                                text = tab.label,
                                fontSize = 10.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = TrustBlue,
                            selectedTextColor = TrustBlue,
                            unselectedIconColor = SlateSubtle,
                            unselectedTextColor = SlateMuted,
                            indicatorColor = TrustBlueContainer
                        ),
                        modifier = Modifier.testTag("nav_tab_${tab.name.lowercase()}")
                    )
                }
            }
        },
        floatingActionButton = {
            if (selectedTab == 0) {
                FloatingActionButton(
                    onClick = { showAddTransactionSheet = true },
                    containerColor = TrustBlue,
                    contentColor = Color.White,
                    shape = CircleShape,
                    modifier = Modifier
                        .size(56.dp)
                        .appleSpringPress(onClick = { showAddTransactionSheet = true })
                        .testTag("fab_add_cashflow")
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Add Transaction")
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            AnimatedContent(
                targetState = selectedTab,
                transitionSpec = {
                    fadeIn(tween(240)) togetherWith fadeOut(tween(180))
                },
                label = "screen_transition"
            ) { tabIdx ->
                when (NavigationTab.entries[tabIdx]) {
                    NavigationTab.HOME -> {
                        HomeScreen(
                            viewModel = viewModel,
                            onOpenAddTransaction = { showAddTransactionSheet = true },
                            onOpenCheckout = { showCheckoutModal = true },
                            onOpenQuoteDialog = { showQuoteDialog = true },
                            onNavigateToStage = { target ->
                                when (target) {
                                    "home" -> selectedTab = 0
                                    "masterclass" -> selectedTab = 1
                                    "analytics" -> selectedTab = 2
                                    "simulation" -> selectedTab = 3
                                    "mentor" -> selectedTab = 4
                                    else -> selectedTab = 0
                                }
                            }
                        )
                    }
                NavigationTab.MASTERCLASS -> {
                    // Stage 2: Market Basics & Glossary
                    Column(modifier = Modifier.fillMaxSize()) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(CreamContainer)
                                .border(BorderStroke(1.dp, CreamBorder), RoundedCornerShape(12.dp))
                                .padding(4.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(if (guidebookSubTab == 0) TrustBlue else Color.Transparent)
                                    .clickable { guidebookSubTab = 0 }
                                    .padding(vertical = 8.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.School,
                                        contentDescription = null,
                                        tint = if (guidebookSubTab == 0) Color.White else SlateCharcoal,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = "Stage 2: Masterclass",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (guidebookSubTab == 0) Color.White else SlateCharcoal
                                    )
                                }
                            }

                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(if (guidebookSubTab == 1) TrustBlue else Color.Transparent)
                                    .clickable { guidebookSubTab = 1 }
                                    .padding(vertical = 8.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.Book,
                                        contentDescription = null,
                                        tint = if (guidebookSubTab == 1) Color.White else SlateCharcoal,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = "Benevolent Glossary",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (guidebookSubTab == 1) Color.White else SlateCharcoal
                                    )
                                }
                            }
                        }

                        if (guidebookSubTab == 0) {
                            MasterclassScreen(
                                viewModel = viewModel,
                                onNavigateToAiChatWithPrompt = { prompt ->
                                    selectedTab = 4
                                    viewModel.sendChatMessage(prompt)
                                },
                                onOpenCheckout = { showCheckoutModal = true }
                            )
                        } else {
                            GlossaryScreen(viewModel = viewModel)
                        }
                    }
                }
                NavigationTab.ANALYTICS -> {
                    // Stage 3: Analysis & Charts + Macro Radar
                    Column(modifier = Modifier.fillMaxSize()) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(CreamContainer)
                                .border(BorderStroke(1.dp, CreamBorder), RoundedCornerShape(12.dp))
                                .padding(4.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(if (analyticsSubTab == 0) TrustBlue else Color.Transparent)
                                    .clickable { analyticsSubTab = 0 }
                                    .padding(vertical = 8.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "Stage 3: Wealth Multiplier",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (analyticsSubTab == 0) Color.White else SlateCharcoal
                                )
                            }

                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(if (analyticsSubTab == 1) TrustBlue else Color.Transparent)
                                    .clickable { analyticsSubTab = 1 }
                                    .padding(vertical = 8.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "Regional Macro Radar",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (analyticsSubTab == 1) Color.White else SlateCharcoal
                                )
                            }
                        }

                        if (analyticsSubTab == 0) {
                            AnalyticsScreen(viewModel = viewModel)
                        } else {
                            MarketRadarScreen(viewModel = viewModel)
                        }
                    }
                }
                NavigationTab.SIMULATION -> {
                    // Stage 4: Simulated Execution
                    SimulatedExecutionScreen(
                        onConsultAiMentor = { prompt ->
                            selectedTab = 4
                            viewModel.sendChatMessage(prompt)
                        },
                        onCompleteStage4 = {
                            viewModel.completeStage(4)
                        }
                    )
                }
                NavigationTab.MENTOR -> {
                    AiMentorScreen(viewModel = viewModel)
                }
            }
        }

            // Modal Sheets
            if (showAddTransactionSheet) {
                AddTransactionSheet(
                    currencySymbol = region.currencySymbol,
                    onDismiss = { showAddTransactionSheet = false },
                    onSaveTransaction = { tx ->
                        viewModel.addTransaction(tx)
                    }
                )
            }

            if (showCheckoutModal) {
                PaymentCheckoutModal(
                    isAlreadyVip = isVip,
                    currencySymbol = region.currencySymbol,
                    onDismiss = { showCheckoutModal = false },
                    onUnlockWithCode = { code ->
                        viewModel.unlockVipWithCode(code)
                    },
                    onPayWithMethod = { method ->
                        viewModel.unlockVipPaid(method)
                    },
                    onResetVip = {
                        viewModel.resetVip()
                    }
                )
            }

            if (showQuoteDialog) {
                MotivationalQuoteDialog(
                    quote = currentQuote,
                    onDismiss = { showQuoteDialog = false },
                    onNextQuote = { viewModel.nextMotivationalQuote() }
                )
            }
        }
    }
}
