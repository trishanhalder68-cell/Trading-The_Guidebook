package com.example.data.model

data class MarketNewsItem(
    val id: String,
    val title: String,
    val source: String,
    val timeAgo: String,
    val sentiment: MarketSentiment,
    val summary: String,
    val keyTakeawayForTraders: String,
    val category: String // "Macro", "Earnings", "Monetary Policy", "Indices"
)

enum class MarketSentiment {
    BULLISH,
    BEARISH,
    NEUTRAL
}

data class MarketTerm(
    val term: String,
    val category: String, // "Basics", "Technical Analysis", "Valuation", "Derivatives"
    val simpleDefinition: String,
    val realWorldExample: String,
    val whyItMattersToYou: String
)

data class MotivationalQuote(
    val id: Int,
    val quote: String,
    val author: String,
    val category: String // "Mindset", "Discipline", "Compounding", "Patience"
)

object MarketGuidebookRepository {
    val OFFICIAL_MARKET_NEWS = listOf(
        MarketNewsItem(
            id = "news_1",
            title = "Central Bank Holds Benchmark Rates Steady; Signals Data-Dependent Pivot",
            source = "Monetary Policy Committee",
            timeAgo = "2h ago",
            sentiment = MarketSentiment.BULLISH,
            summary = "Central bank maintains repo rate at 6.50% while noting easing food supply pressures and steady core inflation trajectory.",
            keyTakeawayForTraders = "Bullish for rate-sensitive sectors including Banking, Real Estate, and Auto stocks. Lower probability of immediate rate hikes reduces corporate debt cost.",
            category = "Monetary Policy"
        ),
        MarketNewsItem(
            id = "news_2",
            title = "Foreign & Domestic Institutional Inflows Hit 4-Month High in Index Equities",
            source = "Exchange Trading Data",
            timeAgo = "4h ago",
            sentiment = MarketSentiment.BULLISH,
            summary = "DII net purchases exceeded ₹3,200 Crores alongside steady FII participation, absorbing local retail profit booking seamlessly.",
            keyTakeawayForTraders = "Strong institutional floor beneath benchmark support levels. Dips near the 50-day moving average are being aggressively bought by institutions.",
            category = "Indices"
        ),
        MarketNewsItem(
            id = "news_3",
            title = "Tech & Semiconductor Giants Report Resilient Margin Expansion Driven by Enterprise Demand",
            source = "Financial Markets Desk",
            timeAgo = "6h ago",
            sentiment = MarketSentiment.BULLISH,
            summary = "Leading tech bellwethers beat quarterly operating margin forecasts by 140 bps, citing accelerated software automation and cloud adoption.",
            keyTakeawayForTraders = "Momentum breakout confirmed in mid-cap tech and IT services. Watch for volume confirmation before initiating long swing positions.",
            category = "Earnings"
        ),
        MarketNewsItem(
            id = "news_4",
            title = "Global Crude Oil Consolidates Below $76/Barrel on Inventory Replenishment",
            source = "Commodities Bureau",
            timeAgo = "8h ago",
            sentiment = MarketSentiment.NEUTRAL,
            summary = "Brent crude benchmark stabilized after inventory builds in major importing economies, dampening imported inflation concerns.",
            keyTakeawayForTraders = "Margin relief for paint, aviation, tire, and specialty chemical manufacturers whose raw material inputs track petroleum derivatives.",
            category = "Macro"
        )
    )

    val BENEVOLENT_TERMS = listOf(
        MarketTerm(
            term = "P/E Ratio (Price-to-Earnings)",
            category = "Valuation",
            simpleDefinition = "How many rupees (or dollars) you are paying for every single rupee of annual profit the company earns.",
            realWorldExample = "If a company earns ₹10 per share and the stock trades at ₹200, its P/E is 20x. It means investors pay 20 times its current profit.",
            whyItMattersToYou = "Helps you avoid overpaying for hype. A P/E of 80x means expectations are sky-high, while a P/E of 15x might be an undervalued bargain."
        ),
        MarketTerm(
            term = "Compound Interest & CAGR",
            category = "Basics",
            simpleDefinition = "Earning interest not just on your initial money, but on the accumulated interest from all previous years.",
            realWorldExample = "Investing ₹10,000 monthly at 14% CAGR produces ₹72+ Lakhs in 15 years, with more than ₹54 Lakhs coming purely from compounding!",
            whyItMattersToYou = "Time in the market beats timing the market. The longer your money compounds, the faster it doubles without extra physical labor."
        ),
        MarketTerm(
            term = "Stop-Loss Order",
            category = "Risk Management",
            simpleDefinition = "An automatic safety switch that immediately sells your stock if the price drops to a specific floor you chose in advance.",
            realWorldExample = "You buy a stock at ₹500 and set a stop-loss at ₹480. If bad news hits and the stock plunges to ₹350, you are safely kicked out at ₹480, preserving your wealth.",
            whyItMattersToYou = "The #1 rule of billionaire traders: Cut losses small and let your winners run. A stop-loss prevents a single mistake from wiping out your account."
        ),
        MarketTerm(
            term = "Market Capitalization",
            category = "Basics",
            simpleDefinition = "The total price tag of an entire company if you bought every single share in existence.",
            realWorldExample = "Number of shares × Current share price. A company with 1 Crore shares at ₹500 has a Market Cap of ₹500 Crores (Small-cap).",
            whyItMattersToYou = "Large-cap stocks (TCS, Reliance, Apple) offer stability and peace of mind. Small-cap stocks offer explosive growth but higher volatility."
        ),
        MarketTerm(
            term = "RSI (Relative Strength Index)",
            category = "Technical Analysis",
            simpleDefinition = "A speedometer from 0 to 100 measuring whether buyers or sellers are currently running at exhaustion.",
            realWorldExample = "RSI above 70 means the stock is 'Overbought' (too many eager buyers; pullback likely). RSI below 30 means 'Oversold' (heavy panic selling; bounce likely).",
            whyItMattersToYou = "Prevents you from FOMO buying at the exact peak when everyone is euphoric, and alerts you to bargain entry points during market panic."
        ),
        MarketTerm(
            term = "Dividend Yield",
            category = "Income",
            simpleDefinition = "The percentage of cash payout a company deposits straight into your bank account each year relative to its stock price.",
            realWorldExample = "If a stock costs ₹100 and pays an annual dividend of ₹7, its dividend yield is 7%—higher than a traditional fixed deposit, plus you own the shares!",
            whyItMattersToYou = "Allows you to create passive cashflow that pays your living expenses without needing to sell your underlying shares."
        ),
        MarketTerm(
            term = "Call & Put Options",
            category = "Derivatives",
            simpleDefinition = "Contracts that give you the right to buy (Call) or sell (Put) a stock at a fixed price within a certain time window.",
            realWorldExample = "A Call option is like putting down a non-refundable token deposit to lock in a house's price before the neighborhood booms.",
            whyItMattersToYou = "Used by institutions for insurance (hedging). Beginners should master spot equity before trading options to avoid rapid theta decay."
        ),
        MarketTerm(
            term = "Liquidity",
            category = "Basics",
            simpleDefinition = "How effortlessly and quickly you can turn your investment back into cold, hard cash without crashing the price.",
            realWorldExample = "Shares of large index stocks trade in seconds. A physical plot of land can take 8 months to find a buyer at fair value.",
            whyItMattersToYou = "Always ensure your emergency fund and trading capital are parked in high-liquidity assets so you are never trapped."
        )
    )

    val MOTIVATIONAL_QUOTES = listOf(
        MotivationalQuote(
            id = 1,
            quote = "Do not save what is left after spending, but spend what is left after saving.",
            author = "Warren Buffett",
            category = "Discipline"
        ),
        MotivationalQuote(
            id = 2,
            quote = "The stock market is a device for transferring money from the impatient to the patient.",
            author = "Warren Buffett",
            category = "Patience"
        ),
        MotivationalQuote(
            id = 3,
            quote = "Earn with your mind, not your time. Wealth is having assets that earn while you sleep.",
            author = "Naval Ravikant",
            category = "Wealth Mindset"
        ),
        MotivationalQuote(
            id = 4,
            quote = "Invest in the market for the long term. If you don't stay in the game, you can't win.",
            author = "Rakesh Jhunjhunwala",
            category = "Compounding"
        ),
        MotivationalQuote(
            id = 5,
            quote = "Spending money to show people how much money you have is the fastest way to have less money.",
            author = "Morgan Housel",
            category = "Mindset"
        ),
        MotivationalQuote(
            id = 6,
            quote = "Compound interest is the eighth wonder of the world. He who understands it, earns it; he who doesn't, pays it.",
            author = "Albert Einstein",
            category = "Compounding"
        )
    )
}
