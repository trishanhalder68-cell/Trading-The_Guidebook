package com.example.data.model

data class RegionCriteria(
    val code: String,
    val name: String,
    val currencyCode: String,
    val currencySymbol: String,
    val inflationRate: String,
    val centralBankRepoRate: String,
    val primaryStockIndex: String,
    val indexChangePercent: String,
    val isIndexBullish: Boolean,
    val taxSlabsSummary: String,
    val economicOutlook: String,
    val keyCriteriaAffectingIncome: List<String>,
    val regionalIncomeMethods: List<IncomeOpportunity>,
    val recommendedStockVehicles: List<StockVehicle>
)

data class IncomeOpportunity(
    val id: String,
    val title: String,
    val category: String,
    val estimatedEarnings: String,
    val difficulty: String, // Beginner, Intermediate, Pro
    val description: String,
    val actionSteps: List<String>,
    val isRegionalHighlight: Boolean = false
)

data class StockVehicle(
    val name: String,
    val expectedCagr: String,
    val riskLevel: String,
    val rationale: String,
    val idealFor: String
)

object RegionRepository {
    val REGION_INDIA = RegionCriteria(
        code = "IN",
        name = "India",
        currencyCode = "INR",
        currencySymbol = "₹",
        inflationRate = "5.1%",
        centralBankRepoRate = "6.50% (RBI)",
        primaryStockIndex = "NIFTY 50 (24,850)",
        indexChangePercent = "+0.82%",
        isIndexBullish = true,
        taxSlabsSummary = "New Tax Regime: 0% up to ₹3L, 5% (₹3L-₹7L), 10% (₹7L-₹10L), 15% (₹10L-₹12L), 20% (₹12L-₹15L), 30% above ₹15L. Section 87A rebate up to ₹7L.",
        economicOutlook = "Robust 7.0%+ GDP expansion driven by domestic manufacturing, digital public infra, and record retail investor participation in equity mutual funds.",
        keyCriteriaAffectingIncome = listOf(
            "RBI Repo Rate (6.5%) maintains high FD yields but raises home/auto loan EMI burdens.",
            "Urban CPI Food Inflation dictates non-discretionary monthly household burn rate.",
            "Long-Term Capital Gains (LTCG) on Equity taxed at 12.5% beyond ₹1.25 Lakh exemption limit.",
            "Short-Term Capital Gains (STCG) on Equity taxed at 20% flat.",
            "Massive shift from unorganized cash savings into SIPs (Systematic Investment Plans) & Demat accounts."
        ),
        regionalIncomeMethods = listOf(
            IncomeOpportunity(
                id = "in_freelance_tech",
                title = "Global Tech & Design Export (Remote INR/USD)",
                category = "Remote Skill Arbitrage",
                estimatedEarnings = "₹60,000 – ₹2,50,000 / month",
                difficulty = "Intermediate",
                description = "Provide software, UI/UX, or technical copywriting to international clients via Upwork, Fiverr, or direct outreach, leveraging currency conversion arbitrage.",
                actionSteps = listOf(
                    "Set up PayPal or Wise business account with inward remittance certificate (FIRC).",
                    "Target small-medium businesses in US/UK/Australia.",
                    "File ITR-4 under presumptive taxation Section 44ADA (50% deemed profit)."
                ),
                isRegionalHighlight = true
            ),
            IncomeOpportunity(
                id = "in_nifty_sip",
                title = "Automated Nifty 50 & Midcap Equity SIPs",
                category = "Equity Wealth Compounding",
                estimatedEarnings = "12% - 15% CAGR compounding",
                difficulty = "Beginner",
                description = "Invest daily or monthly savings automatically into low-cost Nifty 50 and Nifty Next 50 index funds (TER < 0.15%) to beat inflation by 7-9%.",
                actionSteps = listOf(
                    "Complete zero-brokerage Demat KYC on Zerodha, Groww, or Angel One.",
                    "Set up auto-debit SIP scheduled 1 day after primary income credit.",
                    "Step up SIP contribution by 10% annually with every income hike."
                ),
                isRegionalHighlight = true
            ),
            IncomeOpportunity(
                id = "in_swing_breakout",
                title = "Support-Resistance Equity Swing Trading",
                category = "Active Stock Trading",
                estimatedEarnings = "3% – 8% per swing trade (Risk Managed)",
                difficulty = "Pro",
                description = "Trade high-momentum large and mid-cap stocks using 20/50 EMA crossovers, Volume breakout confirmations, and strict 1:2 risk-to-reward stop-losses.",
                actionSteps = listOf(
                    "Screen stocks near 52-week highs with rising delivery volumes on NSE.",
                    "Never risk more than 1.5% of total trading capital per trade.",
                    "Maintain strict journal of entry, exit, stop loss, and market sentiment."
                )
            ),
            IncomeOpportunity(
                id = "in_dividend_yield",
                title = "PSU & Blue-Chip High Dividend Cashflow Portfolio",
                category = "Passive Cashflow",
                estimatedEarnings = "6% – 8.5% annual cash dividend yield",
                difficulty = "Beginner",
                description = "Build a stable dividend stream by investing in cash-rich blue-chips (Coal India, Power Grid, ITC, Vedanta) with regular quarterly dividend payouts.",
                actionSteps = listOf(
                    "Identify companies with dividend payout consistency over past 5 years.",
                    "Reinvest dividends automatically into fractional shares or new lots.",
                    "Track ex-dividend dates to optimize cash timing."
                )
            ),
            IncomeOpportunity(
                id = "in_quick_commerce",
                title = "D2C Micro-Brand & Quick Commerce Retail",
                category = "Local E-Commerce",
                estimatedEarnings = "₹40,000 – ₹1,80,000 / month",
                difficulty = "Intermediate",
                description = "Curate localized niche FMCG, health snacks, or ethnic lifestyle items for listing on Amazon India, Meesho, or Blinkit/Instamart seller hubs.",
                actionSteps = listOf(
                    "Register GSTIN and trademark under Class 30/35.",
                    "Source directly from manufacturing clusters (Surat, Ludhiana, Tirupur).",
                    "Optimize listing imagery with high-contrast luxury product photos."
                )
            )
        ),
        recommendedStockVehicles = listOf(
            StockVehicle("Nifty 50 Index Fund / ETF", "12.8% Historical CAGR", "Low-Moderate", "Tracks India's top 50 corporate giants; zero stock-picking risk.", "Long-term core foundation"),
            StockVehicle("Nifty Midcap 150 Index", "16.4% Historical CAGR", "Moderate-High", "Captures India's fastest expanding mid-tier industrial and tech leaders.", "Accelerated growth pool"),
            StockVehicle("Sovereign Gold Bonds (SGB) & Gold ETFs", "10.2% + 2.5% Fixed", "Low", "Hedges rupee depreciation and global macroeconomic volatility.", "Capital preservation & hedge"),
            StockVehicle("High-Dividend PSU Basket", "7.5% Dividend + Capital", "Moderate", "Quarterly income payouts directly deposited into your savings bank account.", "Income replacement")
        )
    )

    val REGION_USA = RegionCriteria(
        code = "US",
        name = "United States",
        currencyCode = "USD",
        currencySymbol = "$",
        inflationRate = "3.2%",
        centralBankRepoRate = "5.25% (Fed Funds)",
        primaryStockIndex = "S&P 500 (5,780)",
        indexChangePercent = "+0.64%",
        isIndexBullish = true,
        taxSlabsSummary = "Federal Brackets: 10%, 12%, 22%, 24%, 32%, 35%, 37%. Long-term capital gains at 0%, 15%, or 20% depending on income threshold.",
        economicOutlook = "Resilient consumer spending and enterprise AI infrastructure buildout driving mega-cap tech earnings expansion.",
        keyCriteriaAffectingIncome = listOf(
            "High Federal Funds Rate sustains 4.5%+ yields on Money Market accounts and US Treasuries.",
            "Mega-cap tech valuations heavily sensitive to 10-Year Treasury Yield fluctuations.",
            "Retirement compounding maximized through Tax-Advantaged accounts (Roth IRA, 401k match)."
        ),
        regionalIncomeMethods = listOf(
            IncomeOpportunity(
                id = "us_voo_dollar_cost",
                title = "S&P 500 Index Compounding (VOO / SPY)",
                category = "Passive Equity",
                estimatedEarnings = "10.5% Long-term CAGR",
                difficulty = "Beginner",
                description = "Dollar-cost average weekly into Vanguard S&P 500 ETF (VOO) with automatic dividend reinvestment (DRIP).",
                actionSteps = listOf("Open Robinhood, Schwab, or Fidelity account.", "Set automatic recurring deposit each payday.", "Max out Roth IRA first ($7,000/year)."),
                isRegionalHighlight = true
            ),
            IncomeOpportunity(
                id = "us_covered_call",
                title = "Covered Call Options on Blue-Chip Holdings",
                category = "Options Cashflow",
                estimatedEarnings = "1.5% – 3% Monthly Premium",
                difficulty = "Pro",
                description = "Sell out-of-the-money call options against 100-share blocks of stable dividend aristocrats (e.g. Apple, Microsoft, Coca-Cola) for weekly cash income.",
                actionSteps = listOf("Accumulate 100 shares of solid growth stock.", "Sell 30-45 DTE call option at 0.20-0.30 Delta.", "Collect instant cash premium upfront.")
            )
        ),
        recommendedStockVehicles = listOf(
            StockVehicle("Vanguard S&P 500 (VOO)", "10.5% CAGR", "Moderate", "Total market exposure with 0.03% expense ratio.", "Core portfolio"),
            StockVehicle("Invesco QQQ (Nasdaq 100)", "15.2% CAGR", "High", "Tech-heavy exposure to Apple, Nvidia, Microsoft, Amazon.", "Aggressive growth")
        )
    )

    val ALL_REGIONS = listOf(REGION_INDIA, REGION_USA)

    fun getRegionByCode(code: String): RegionCriteria {
        return ALL_REGIONS.find { it.code.equals(code, ignoreCase = true) } ?: REGION_INDIA
    }
}
