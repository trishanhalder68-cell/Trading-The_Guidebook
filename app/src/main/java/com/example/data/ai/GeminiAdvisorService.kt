package com.example.data.ai

import android.util.Log
import com.example.data.model.CashflowSummary
import com.example.data.model.IncomeOpportunity
import com.example.data.model.RegionCriteria
import com.google.firebase.Firebase
import com.google.firebase.ai.Chat
import com.google.firebase.ai.GenerativeModel
import com.google.firebase.ai.ai
import com.google.firebase.ai.type.Content
import com.google.firebase.ai.type.content
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withTimeout

private const val TAG = "GeminiAdvisorService"

data class ChatMessage(
    val id: String,
    val sender: MessageSender,
    val text: String,
    val timestamp: Long = System.currentTimeMillis()
)

enum class MessageSender {
    USER,
    AI
}

class GeminiAdvisorService {
    // GenerativeModel initialized lazily as required by Firebase AI Logic skill
    private val model: GenerativeModel by lazy {
        Firebase.ai.generativeModel(
            modelName = "gemini-3.8-flash"
        )
    }

    private val conversationHistory = mutableListOf<Content>()

    fun resetConversation() {
        conversationHistory.clear()
    }

    fun streamChatResponse(
        userMessage: String,
        region: RegionCriteria,
        summary: CashflowSummary
    ): Flow<String> = flow {
        // Build rich contextual system instruction into prompt if conversation is starting
        val systemContext = if (conversationHistory.isEmpty()) {
            """
            You are the Chief Market Strategist and Benevolent Wealth Mentor at 'Trading: The Guidebook' by Apex Trading Technologies.
            The user is managing their wealth in ${region.name} (${region.currencyCode} ${region.currencySymbol}).
            Current User Financial Snapshot:
            - Daily Income: ${region.currencySymbol}${String.format("%.0f", summary.dailyIncome)} | Daily Expense: ${region.currencySymbol}${String.format("%.0f", summary.dailyExpense)}
            - Monthly Inflow: ${region.currencySymbol}${String.format("%.0f", summary.monthlyIncome)} | Monthly Burn: ${region.currencySymbol}${String.format("%.0f", summary.monthlyExpense)}
            - Projected Annual Income: ${region.currencySymbol}${String.format("%.0f", summary.annualIncome)}
            - Net Savings: ${region.currencySymbol}${String.format("%.0f", summary.netSavings)} (Savings Rate: ${String.format("%.1f", summary.savingsRatePercentage)}%)
            - Regional Inflation: ${region.inflationRate} | Central Bank Repo Rate: ${region.centralBankRepoRate} | Prime Index: ${region.primaryStockIndex}
            
            Your Mission:
            1. Provide empowering, precise, and highly practical financial & stock trading guidance.
            2. Explain trading concepts benevolently with simple, lucrative examples.
            3. Show how shifting savings into index funds (like Nifty 50) beats inflation through compound interest.
            4. Suggest actionable income streams valid for ${region.name}.
            Keep answers motivational, structured with bullet points, and easy to read.
            """.trimIndent()
        } else ""

        val promptToSend = if (systemContext.isNotEmpty()) {
            "$systemContext\n\nUser Question: $userMessage"
        } else {
            userMessage
        }

        val userContent = content("user") { text(promptToSend) }
        conversationHistory.add(userContent)

        val fullResponseBuilder = StringBuilder()

        try {
            val historyToSend = if (conversationHistory.size > 1) {
                conversationHistory.subList(0, conversationHistory.size - 1)
            } else {
                emptyList()
            }
            val chatSession: Chat = model.startChat(history = historyToSend)

            withTimeout(60_000L) {
                val streamFlow = chatSession.sendMessageStream(promptToSend)
                streamFlow.collect { chunk ->
                    val chunkText = chunk.text.orEmpty()
                    fullResponseBuilder.append(chunkText)
                    emit(chunkText)
                }
            }
            // Record assistant response in history
            val assistantContent = content("model") { text(fullResponseBuilder.toString()) }
            conversationHistory.add(assistantContent)
        } catch (e: TimeoutCancellationException) {
            Log.e(TAG, "Chat response timed out", e)
            emit("\n[Advisor timed out. The markets move fast, but our connection hit a delay. Please try again!]")
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Log.e(TAG, "Chat generation error", e)
            emit("\n[Market Advisor note: Unable to connect to AI Logic: ${e.localizedMessage ?: "Unknown error"}. You can still use the built-in Guidebook & Market Radar!]")
        }
    }

    suspend fun generateFreshIncomeIdeas(
        region: RegionCriteria,
        seedCount: Int
    ): List<IncomeOpportunity> {
        val prompt = """
            Provide 3 brand new, highly lucrative, actionable income opportunities specifically valid for ${region.name} (${region.currencyCode}).
            Categorize into: 1 remote/digital arbitrage, 1 equity/stock dividend tactic, and 1 localized micro-hustle.
            Format each with:
            Title: [Title]
            Category: [Category]
            Earnings: [Estimated earnings in ${region.currencySymbol}]
            Difficulty: [Beginner / Intermediate / Pro]
            Description: [Clear 2-sentence summary]
            Step1: [Action step 1]
            Step2: [Action step 2]
            Step3: [Action step 3]
            Seed: $seedCount
        """.trimIndent()

        return try {
            val response = withTimeout(45_000L) {
                model.generateContent(prompt)
            }
            val text = response.text ?: ""
            parseIdeasFromText(text, region)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Log.e(TAG, "Error generating fresh income ideas", e)
            listOf(
                IncomeOpportunity(
                    id = "dyn_algo_${System.currentTimeMillis()}",
                    title = "Algorithmic Momentum Screener in ${region.name}",
                    category = "Quant Equity",
                    estimatedEarnings = "${region.currencySymbol}25,000 – ${region.currencySymbol}90,000 / month",
                    difficulty = "Intermediate",
                    description = "Deploy automated Python or TradingView webhook alerts to capture 15-minute breakouts on ${region.primaryStockIndex} constituents.",
                    actionSteps = listOf(
                        "Code a simple 200 EMA + VWAP trend follower on TradingView.",
                        "Backtest over 12 months with minimum 1:2 risk-to-reward ratio.",
                        "Execute via discount broker API."
                    )
                ),
                IncomeOpportunity(
                    id = "dyn_sub_${System.currentTimeMillis() + 1}",
                    title = "Regional Financial Newsletter & Market Recap",
                    category = "Digital Publishing",
                    estimatedEarnings = "${region.currencySymbol}30,000 – ${region.currencySymbol}1,20,000 / month",
                    difficulty = "Beginner",
                    description = "Curate daily pre-market briefs on local monetary policy, institutional flows, and earnings for busy retail investors.",
                    actionSteps = listOf(
                        "Launch a free Substack or WhatsApp Community.",
                        "Post 3-bullet morning summaries before market opening bell.",
                        "Introduce VIP subscriber tier with deep-dive analysis."
                    )
                )
            )
        }
    }

    private fun parseIdeasFromText(rawText: String, region: RegionCriteria): List<IncomeOpportunity> {
        val ideas = mutableListOf<IncomeOpportunity>()
        val blocks = rawText.split(Regex("Title:|Opportunity \\d+:"))
        for (block in blocks) {
            if (block.isBlank() || !block.contains("Category:")) continue
            val lines = block.lines().map { it.trim() }.filter { it.isNotEmpty() }
            val title = lines.firstOrNull()?.replace(Regex("^[*#-]+\\s*"), "")?.trim() ?: "Lucrative Market Opportunity"
            var category = "Market Strategy"
            var earnings = "${region.currencySymbol}50,000+ / month"
            var difficulty = "Intermediate"
            var desc = ""
            val steps = mutableListOf<String>()

            for (line in lines) {
                when {
                    line.startsWith("Category:", true) -> category = line.substringAfter(":").trim()
                    line.startsWith("Earnings:", true) -> earnings = line.substringAfter(":").trim()
                    line.startsWith("Difficulty:", true) -> difficulty = line.substringAfter(":").trim()
                    line.startsWith("Description:", true) -> desc = line.substringAfter(":").trim()
                    line.startsWith("Step", true) -> steps.add(line.substringAfter(":").trim())
                }
            }
            if (desc.isEmpty()) desc = "Lucrative strategy tailored to ${region.name} economic conditions."
            if (steps.isEmpty()) {
                steps.addAll(listOf("Research local regulations.", "Open dedicated high-yield account.", "Scale gradually."))
            }

            ideas.add(
                IncomeOpportunity(
                    id = "ai_idea_${System.currentTimeMillis()}_${ideas.size}",
                    title = title,
                    category = category,
                    estimatedEarnings = earnings,
                    difficulty = difficulty,
                    description = desc,
                    actionSteps = steps,
                    isRegionalHighlight = false
                )
            )
        }
        return if (ideas.isNotEmpty()) ideas else region.regionalIncomeMethods
    }
}
