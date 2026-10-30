package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
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
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.InsertChart
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Public
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
import com.example.ui.theme.GoldBorder
import com.example.ui.theme.GoldGleam
import com.example.ui.theme.ImperialGold
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.theme.ObsidianBlack
import com.example.ui.theme.ObsidianCardElevated
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.TextTertiary
import com.example.viewmodel.WealthViewModel

class MainActivity : ComponentActivity() {
    private val viewModel: WealthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // STEP 1: Configure App Check with launch intent before any UI or Firebase operations
        configureAppCheck(this, intent)

        enableEdgeToEdge()

        setContent {
            MyApplicationTheme {
                MainAppScreen(viewModel = viewModel)
            }
        }
    }
}

enum class NavigationTab(val label: String, val icon: ImageVector) {
    HOME("Home", Icons.Default.AccountBalanceWallet),
    ANALYTICS("Analytics", Icons.Default.InsertChart),
    RADAR("Radar", Icons.Default.Public),
    GUIDEBOOK("Guidebook", Icons.Default.MenuBook),
    MENTOR("AI Mentor", Icons.Default.SmartToy)
}

@Composable
fun MainAppScreen(viewModel: WealthViewModel) {
    var selectedTab by remember { mutableIntStateOf(0) }
    var guidebookSubTab by remember { mutableIntStateOf(0) } // 0: Masterclass, 1: Glossary Dictionary

    var showAddTransactionSheet by remember { mutableStateOf(false) }
    var showCheckoutModal by remember { mutableStateOf(false) }
    var showQuoteDialog by remember { mutableStateOf(false) }

    val region by viewModel.currentRegion.collectAsState()
    val isVip by viewModel.isVipPro.collectAsState()
    val currentQuote by viewModel.currentQuote.collectAsState()

    // Show motivational quote pop-up once on launch
    LaunchedEffect(Unit) {
        showQuoteDialog = true
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = ObsidianBlack,
        bottomBar = {
            NavigationBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .windowInsetsPadding(WindowInsets.navigationBars)
                    .border(1.dp, GoldBorder.copy(alpha = 0.5f)),
                containerColor = ObsidianBlack,
                tonalElevation = 8.dp
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
                            selectedIconColor = ObsidianBlack,
                            selectedTextColor = ImperialGold,
                            unselectedIconColor = TextTertiary,
                            unselectedTextColor = TextTertiary,
                            indicatorColor = ImperialGold
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
                    containerColor = ImperialGold,
                    contentColor = ObsidianBlack,
                    shape = CircleShape,
                    modifier = Modifier
                        .size(56.dp)
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
            when (NavigationTab.entries[selectedTab]) {
                NavigationTab.HOME -> {
                    HomeScreen(
                        viewModel = viewModel,
                        onOpenAddTransaction = { showAddTransactionSheet = true },
                        onOpenCheckout = { showCheckoutModal = true },
                        onOpenQuoteDialog = { showQuoteDialog = true },
                        onNavigateToAnalytics = { selectedTab = 1 },
                        onNavigateToAiMentor = { selectedTab = 4 },
                        onInitiateMasterclass = {
                            selectedTab = 3
                            guidebookSubTab = 0
                        }
                    )
                }
                NavigationTab.ANALYTICS -> {
                    AnalyticsScreen(viewModel = viewModel)
                }
                NavigationTab.RADAR -> {
                    MarketRadarScreen(viewModel = viewModel)
                }
                NavigationTab.GUIDEBOOK -> {
                    // Unified Guidebook Screen with Sub-Tab Selector
                    Column(modifier = Modifier.fillMaxSize()) {
                        // Kang's Citadel Sub-Nav Header
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 18.dp, vertical = 10.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .background(ObsidianCardElevated)
                                .border(1.dp, GoldBorder.copy(alpha = 0.6f), RoundedCornerShape(16.dp))
                                .padding(4.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(if (guidebookSubTab == 0) ImperialGold else Color.Transparent)
                                    .clickable { guidebookSubTab = 0 }
                                    .padding(vertical = 10.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.School,
                                        contentDescription = null,
                                        tint = if (guidebookSubTab == 0) ObsidianBlack else ImperialGold,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = "Masterclass (1602–Now)",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (guidebookSubTab == 0) ObsidianBlack else TextPrimary
                                    )
                                }
                            }

                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(if (guidebookSubTab == 1) ImperialGold else Color.Transparent)
                                    .clickable { guidebookSubTab = 1 }
                                    .padding(vertical = 10.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.Book,
                                        contentDescription = null,
                                        tint = if (guidebookSubTab == 1) ObsidianBlack else ImperialGold,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = "Benevolent Glossary",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (guidebookSubTab == 1) ObsidianBlack else TextPrimary
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
                NavigationTab.MENTOR -> {
                    AiMentorScreen(viewModel = viewModel)
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
