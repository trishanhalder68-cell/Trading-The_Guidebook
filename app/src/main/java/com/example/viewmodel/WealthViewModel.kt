package com.example.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.ai.ChatMessage
import com.example.data.ai.GeminiAdvisorService
import com.example.data.ai.MessageSender
import com.example.data.local.AppDatabase
import com.example.data.local.UserPreferences
import com.example.data.model.CashflowSummary
import com.example.data.model.IncomeOpportunity
import com.example.data.model.MarketGuidebookRepository
import com.example.data.model.MarketNewsItem
import com.example.data.model.MarketTerm
import com.example.data.model.MotivationalQuote
import com.example.data.model.RecurrencePeriod
import com.example.data.model.RegionCriteria
import com.example.data.model.RegionRepository
import com.example.data.model.FirestoreTransaction
import com.example.data.model.FirestoreUserProfile
import com.example.data.repository.FirestoreRepository
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.example.data.model.TransactionEntity
import com.example.data.model.TransactionType
import com.example.ui.components.CategorySlice
import com.example.ui.components.ChartPoint
import com.example.ui.theme.CyanAccent
import com.example.ui.theme.EmeraldProfit
import com.example.ui.theme.GoldGleam
import com.example.ui.theme.ImperialGold
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.math.pow

data class CompoundInvestmentResult(
    val monthlyInvestment: Double,
    val years: Int,
    val annualReturnRate: Double,
    val totalInvested: Double,
    val totalFutureValue: Double,
    val totalInterestEarned: Double,
    val bankSavingsValue: Double, // at 3.5% baseline
    val wealthMultiplier: Double
)

class WealthViewModel(application: Application) : AndroidViewModel(application) {
    private val database = AppDatabase.getDatabase(application, viewModelScope)
    private val dao = database.transactionDao()
    private val prefs = UserPreferences(application)
    private val aiService = GeminiAdvisorService()
    val firestoreRepository = FirestoreRepository(application)

    // The Trader's Path 4-Stage Sequential Roadmap State
    private val _activeStage = MutableStateFlow(prefs.activeStage)
    val activeStage: StateFlow<Int> = _activeStage.asStateFlow()

    private val _completedStages = MutableStateFlow(prefs.completedStages)
    val completedStages: StateFlow<Set<Int>> = _completedStages.asStateFlow()

    // Interactive Learning Flowchart Tree & Badges State
    private val _completedLessons = MutableStateFlow(prefs.completedLessonIds)
    val completedLessons: StateFlow<Set<String>> = _completedLessons.asStateFlow()

    fun completeLesson(lessonId: String) {
        prefs.markLessonCompleted(lessonId)
        _completedLessons.value = prefs.completedLessonIds
    }

    fun selectRoadmapStage(stage: Int) {
        prefs.activeStage = stage
        _activeStage.value = stage
    }

    fun completeStage(stage: Int) {
        prefs.markStageCompleted(stage)
        _completedStages.value = prefs.completedStages
        if (stage < 4 && prefs.activeStage <= stage) {
            prefs.activeStage = stage + 1
            _activeStage.value = stage + 1
        }
        // If user is authenticated, sync profile to Firestore
        val user = try { Firebase.auth.currentUser } catch (_: Throwable) { null }
        if (user != null) {
            viewModelScope.launch {
                firestoreRepository.saveUserProfile(
                    FirestoreUserProfile(
                        userId = user.uid,
                        displayName = user.displayName ?: "Trader",
                        email = user.email,
                        isVip = prefs.isVipPro,
                        unlockedLevels = _completedStages.value.toList()
                    )
                )
            }
        }
    }

    // Active Region State
    private val _currentRegion = MutableStateFlow(RegionRepository.getRegionByCode(prefs.selectedRegionCode))
    val currentRegion: StateFlow<RegionCriteria> = _currentRegion.asStateFlow()

    // VIP Pro Membership State
    private val _isVipPro = MutableStateFlow(prefs.isVipPro)
    val isVipPro: StateFlow<Boolean> = _isVipPro.asStateFlow()

    private val _appliedPromoCode = MutableStateFlow(prefs.appliedPromoCode)
    val appliedPromoCode: StateFlow<String> = _appliedPromoCode.asStateFlow()

    // Transactions Flow
    val allTransactions: StateFlow<List<TransactionEntity>> = dao.getAllTransactions()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Cashflow & Totals Summary
    val cashflowSummary: StateFlow<CashflowSummary> = allTransactions.combine(_currentRegion) { txs, _ ->
        computeSummary(txs)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), defaultSummary())

    // Stock Investment Multiplier State
    var multiplierMonthlyAmount = MutableStateFlow(10000.0)
    var multiplierYears = MutableStateFlow(15)
    var multiplierReturnRate = MutableStateFlow(14.0) // 14% Nifty 50 average

    val compoundResult: StateFlow<CompoundInvestmentResult> = combine(
        multiplierMonthlyAmount,
        multiplierYears,
        multiplierReturnRate
    ) { amount, years, rate ->
        calculateCompoundGrowth(amount, years, rate)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), calculateCompoundGrowth(10000.0, 15, 14.0))

    // Endless Dynamic Income Ideas
    private val _incomeOpportunities = MutableStateFlow<List<IncomeOpportunity>>(RegionRepository.REGION_INDIA.regionalIncomeMethods)
    val incomeOpportunities: StateFlow<List<IncomeOpportunity>> = _incomeOpportunities.asStateFlow()

    private val _isGeneratingIdeas = MutableStateFlow(false)
    val isGeneratingIdeas: StateFlow<Boolean> = _isGeneratingIdeas.asStateFlow()

    private var ideaGenerationCount = 0

    // Masterclass Chapters State
    val chapters = com.example.data.model.MasterclassRepository.CHAPTERS
    private val _unlockedChapterIds = MutableStateFlow(prefs.unlockedChapterIds)
    val unlockedChapterIds: StateFlow<Set<String>> = _unlockedChapterIds.asStateFlow()

    private val _selectedChapter = MutableStateFlow<com.example.data.model.MasterclassChapter?>(chapters.first())
    val selectedChapter: StateFlow<com.example.data.model.MasterclassChapter?> = _selectedChapter.asStateFlow()

    fun isChapterUnlocked(chapterId: String): Boolean {
        return prefs.isChapterUnlocked(chapterId)
    }

    fun selectChapter(chapter: com.example.data.model.MasterclassChapter) {
        _selectedChapter.value = chapter
    }

    fun unlockChapter(chapterId: String) {
        prefs.unlockChapter(chapterId)
        _unlockedChapterIds.value = prefs.unlockedChapterIds
    }

    // Market News & Glossary
    val marketNews: StateFlow<List<MarketNewsItem>> = MutableStateFlow(MarketGuidebookRepository.OFFICIAL_MARKET_NEWS)
    val marketTerms: StateFlow<List<MarketTerm>> = MutableStateFlow(MarketGuidebookRepository.BENEVOLENT_TERMS)

    // Motivational Quote State
    private var quoteIndex = 0
    private val _currentQuote = MutableStateFlow(MarketGuidebookRepository.MOTIVATIONAL_QUOTES.first())
    val currentQuote: StateFlow<MotivationalQuote> = _currentQuote.asStateFlow()

    // AI Chat Messages
    private val _chatMessages = MutableStateFlow<List<ChatMessage>>(
        listOf(
            ChatMessage(
                id = "welcome",
                sender = MessageSender.AI,
                text = "Welcome to Trading: The Guidebook! I am your Market Strategist and Wealth Advisor. Ask me anything about stock investments, trading strategies, regional criteria, or analyzing your daily cashflow."
            )
        )
    )
    val chatMessages: StateFlow<List<ChatMessage>> = _chatMessages.asStateFlow()

    private val _isAiThinking = MutableStateFlow(false)
    val isAiThinking: StateFlow<Boolean> = _isAiThinking.asStateFlow()

    init {
        // Pre-fill opportunities for active region
        _incomeOpportunities.value = _currentRegion.value.regionalIncomeMethods
    }

    fun selectRegion(regionCode: String) {
        prefs.selectedRegionCode = regionCode
        val newRegion = RegionRepository.getRegionByCode(regionCode)
        _currentRegion.value = newRegion
        _incomeOpportunities.value = newRegion.regionalIncomeMethods
    }

    fun addTransaction(tx: TransactionEntity) {
        viewModelScope.launch {
            dao.insertTransaction(tx)
            completeStage(1) // Completes Stage 1: Money Foundations & Income Tracker!
            val user = try { Firebase.auth.currentUser } catch (_: Throwable) { null }
            if (user != null) {
                firestoreRepository.addTransaction(
                    userId = user.uid,
                    transaction = FirestoreTransaction(
                        title = tx.title,
                        amount = tx.amount,
                        type = tx.type.name,
                        category = tx.category,
                        date = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault()).format(java.util.Date(tx.timestamp))
                    )
                )
            }
        }
    }

    fun deleteTransaction(tx: TransactionEntity) {
        viewModelScope.launch {
            dao.deleteTransaction(tx)
        }
    }

    fun nextMotivationalQuote() {
        val quotes = MarketGuidebookRepository.MOTIVATIONAL_QUOTES
        quoteIndex = (quoteIndex + 1) % quotes.size
        _currentQuote.value = quotes[quoteIndex]
    }

    fun unlockVipWithCode(code: String): Boolean {
        val success = prefs.unlockVipWithCode(code)
        if (success) {
            _isVipPro.value = true
            _appliedPromoCode.value = prefs.appliedPromoCode
            _unlockedChapterIds.value = prefs.unlockedChapterIds
            _completedStages.value = prefs.completedStages
        }
        return success
    }

    fun unlockVipPaid(method: String) {
        prefs.unlockVipPaid(method)
        _isVipPro.value = true
        _appliedPromoCode.value = prefs.appliedPromoCode
        _unlockedChapterIds.value = prefs.unlockedChapterIds
        _completedStages.value = prefs.completedStages
    }

    fun resetVip() {
        prefs.resetVipStatus()
        _isVipPro.value = false
        _appliedPromoCode.value = ""
        _unlockedChapterIds.value = prefs.unlockedChapterIds
        _completedStages.value = prefs.completedStages
    }

    fun generateMoreIncomeIdeas() {
        viewModelScope.launch {
            _isGeneratingIdeas.value = true
            ideaGenerationCount++
            val fresh = aiService.generateFreshIncomeIdeas(_currentRegion.value, ideaGenerationCount)
            val combined = (fresh + _incomeOpportunities.value).distinctBy { it.title }
            _incomeOpportunities.value = combined
            _isGeneratingIdeas.value = false
        }
    }

    fun sendChatMessage(text: String) {
        val userMsg = ChatMessage(
            id = "user_${System.currentTimeMillis()}",
            sender = MessageSender.USER,
            text = text
        )
        _chatMessages.value = _chatMessages.value + userMsg
        _isAiThinking.value = true

        val aiMsgId = "ai_${System.currentTimeMillis()}"
        val initialAiMsg = ChatMessage(
            id = aiMsgId,
            sender = MessageSender.AI,
            text = "Thinking..."
        )
        _chatMessages.value = _chatMessages.value + initialAiMsg

        viewModelScope.launch {
            val responseBuilder = StringBuilder()
            aiService.streamChatResponse(
                userMessage = text,
                region = _currentRegion.value,
                summary = cashflowSummary.value
            ).collect { chunk ->
                _isAiThinking.value = false
                responseBuilder.append(chunk)
                _chatMessages.value = _chatMessages.value.map { msg ->
                    if (msg.id == aiMsgId) {
                        msg.copy(text = responseBuilder.toString())
                    } else msg
                }
            }
            _isAiThinking.value = false
        }
    }

    fun getCategorySlices(txs: List<TransactionEntity>): List<CategorySlice> {
        val expenseTxs = txs.filter { it.type == TransactionType.EXPENSE }
        val categoryTotals = expenseTxs.groupBy { it.category }
            .mapValues { entry -> entry.value.sumOf { it.amount } }

        val palette = listOf(
            ImperialGold,
            EmeraldProfit,
            CyanAccent,
            GoldGleam,
            androidx.compose.ui.graphics.Color(0xFFF97316),
            androidx.compose.ui.graphics.Color(0xFFA855F7),
            androidx.compose.ui.graphics.Color(0xFFEC4899)
        )

        return categoryTotals.entries.mapIndexed { index, (cat, amt) ->
            CategorySlice(
                name = cat,
                amount = amt,
                color = palette[index % palette.size]
            )
        }
    }

    fun getCashflowTrendPoints(txs: List<TransactionEntity>): List<ChartPoint> {
        if (txs.isEmpty()) {
            return listOf(
                ChartPoint("Day 1", 2000f),
                ChartPoint("Day 2", 3400f),
                ChartPoint("Day 3", 5100f),
                ChartPoint("Day 4", 8200f),
                ChartPoint("Today", 12500f)
            )
        }
        val days = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
        val dailyNet = cashflowSummary.value.dailyIncome - cashflowSummary.value.dailyExpense
        val base = (dailyNet * 0.8).toFloat().coerceAtLeast(1000f)

        return days.mapIndexed { i, day ->
            val variance = (i * 0.15f * base) + (if (i % 2 == 0) base else base * 1.3f)
            ChartPoint(day, variance)
        }
    }

    private fun computeSummary(txs: List<TransactionEntity>): CashflowSummary {
        var dailyInc = 0.0
        var dailyExp = 0.0
        var monthlyInc = 0.0
        var monthlyExp = 0.0

        txs.forEach { tx ->
            val amount = tx.amount
            if (tx.type == TransactionType.INCOME) {
                when (tx.recurrence) {
                    RecurrencePeriod.DAILY -> {
                        dailyInc += amount
                        monthlyInc += amount * 30.0
                    }
                    RecurrencePeriod.MONTHLY -> {
                        dailyInc += amount / 30.0
                        monthlyInc += amount
                    }
                    RecurrencePeriod.ANNUAL -> {
                        dailyInc += amount / 365.0
                        monthlyInc += amount / 12.0
                    }
                    RecurrencePeriod.ONE_TIME -> {
                        monthlyInc += amount
                        dailyInc += amount / 30.0
                    }
                }
            } else {
                when (tx.recurrence) {
                    RecurrencePeriod.DAILY -> {
                        dailyExp += amount
                        monthlyExp += amount * 30.0
                    }
                    RecurrencePeriod.MONTHLY -> {
                        dailyExp += amount / 30.0
                        monthlyExp += amount
                    }
                    RecurrencePeriod.ANNUAL -> {
                        dailyExp += amount / 365.0
                        monthlyExp += amount / 12.0
                    }
                    RecurrencePeriod.ONE_TIME -> {
                        monthlyExp += amount
                        dailyExp += amount / 30.0
                    }
                }
            }
        }

        val annualInc = monthlyInc * 12.0
        val annualExp = monthlyExp * 12.0
        val netSavings = (monthlyInc - monthlyExp).coerceAtLeast(0.0)
        val savingsRate = if (monthlyInc > 0) (netSavings / monthlyInc) * 100.0 else 0.0

        return CashflowSummary(
            dailyIncome = dailyInc,
            dailyExpense = dailyExp,
            monthlyIncome = monthlyInc,
            monthlyExpense = monthlyExp,
            annualIncome = annualInc,
            annualExpense = annualExp,
            netSavings = netSavings,
            savingsRatePercentage = savingsRate
        )
    }

    private fun defaultSummary() = CashflowSummary(
        dailyIncome = 4500.0,
        dailyExpense = 1450.0,
        monthlyIncome = 135000.0,
        monthlyExpense = 43500.0,
        annualIncome = 1620000.0,
        annualExpense = 522000.0,
        netSavings = 91500.0,
        savingsRatePercentage = 67.7
    )

    private fun calculateCompoundGrowth(
        monthlyAmount: Double,
        years: Int,
        cagrPercent: Double
    ): CompoundInvestmentResult {
        val totalMonths = years * 12
        val totalInvested = monthlyAmount * totalMonths
        val monthlyRate = (cagrPercent / 100.0) / 12.0
        val bankMonthlyRate = (3.5 / 100.0) / 12.0 // standard savings bank interest

        // Future Value of monthly SIP: P * [((1 + r)^n - 1) / r] * (1 + r)
        val futureValueStock = if (monthlyRate > 0) {
            monthlyAmount * (((1.0 + monthlyRate).pow(totalMonths.toDouble()) - 1.0) / monthlyRate) * (1.0 + monthlyRate)
        } else totalInvested

        val futureValueBank = if (bankMonthlyRate > 0) {
            monthlyAmount * (((1.0 + bankMonthlyRate).pow(totalMonths.toDouble()) - 1.0) / bankMonthlyRate) * (1.0 + bankMonthlyRate)
        } else totalInvested

        val interestEarned = futureValueStock - totalInvested
        val multiplier = if (totalInvested > 0) futureValueStock / totalInvested else 1.0

        return CompoundInvestmentResult(
            monthlyInvestment = monthlyAmount,
            years = years,
            annualReturnRate = cagrPercent,
            totalInvested = totalInvested,
            totalFutureValue = futureValueStock,
            totalInterestEarned = interestEarned,
            bankSavingsValue = futureValueBank,
            wealthMultiplier = multiplier
        )
    }
}
