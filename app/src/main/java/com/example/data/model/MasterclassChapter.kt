package com.example.data.model

data class MasterclassChapter(
    val id: String,
    val chapterNumber: Int,
    val era: String,
    val title: String,
    val subtitle: String,
    val estimatedReadMinutes: Int,
    val isFree: Boolean,
    val unlockPriceInr: Int = 125,
    val hookStatement: String,
    val agenda: List<String>,
    val coreContentSections: List<ChapterSection>,
    val goldenRulesDo: List<String>,
    val cardinalSinsAvoid: List<String>,
    val aiTutorStarterPrompt: String,
    val nextLevelId: String? = null
)

data class ChapterSection(
    val sectionTitle: String,
    val body: String,
    val keyTakeaway: String
)

object MasterclassRepository {
    val CHAPTERS = listOf(
        MasterclassChapter(
            id = "ch_1",
            chapterNumber = 1,
            era = "Foundation Level (Free All-Access)",
            title = "The Grand Initiation: The Complete Map of World Trading",
            subtitle = "A comprehensive, high-yield briefing of everything that happens in global markets",
            estimatedReadMinutes = 9,
            isFree = true,
            unlockPriceInr = 0,
            hookStatement = "Everything you need to master trading starts here. In this master initiation, you will discover the foundational laws of how money moves, why prices fluctuate, and how ordinary individuals build immense wealth through market participation.",
            agenda = listOf(
                "The Universal Law of Markets: Buyer vs Seller Auction Mechanics",
                "The 1602 Genesis: How Shares turned everyday people into business owners",
                "How Money Flows: Central Banks, Liquidity Injections, and Inflation",
                "The 4 Primary Asset Classes: Equities (Stocks), Debt (Bonds), Commodities (Gold), and Decentralized Assets",
                "The Institutional Edge: How Smart Money extracts profit from retail fear and greed",
                "The Compound Growth Multiplier: How 14% CAGR turns small daily savings into a multi-million fortune"
            ),
            coreContentSections = listOf(
                ChapterSection(
                    sectionTitle = "1. How a Trade Actually Works (The Continuous Double Auction)",
                    body = "At its core, a financial exchange is an electronic auction house. Every second, millions of participants place 'Bids' (maximum price a buyer will pay) and 'Asks' (minimum price a seller will accept). When a bid and ask match, a trade executes. Prices rise not because of mysterious magic, but because eager aggressive buyers agree to pay higher ask prices. Prices fall when fearful sellers dump shares at lower bids.",
                    keyTakeaway = "Trading is the science of supply, demand, and human emotion manifested through price and volume."
                ),
                ChapterSection(
                    sectionTitle = "2. The 1602 Breakthrough: Owning Productive Enterprises",
                    body = "Before the 1602 Amsterdam Stock Exchange, getting rich required owning land or being royalty. When the Dutch East India Company issued the first public 'shares', they allowed ordinary citizens to own pieces of global trade expeditions. Today, buying a stock gives you legally protected ownership of the world's most profitable companies (Apple, Microsoft, Reliance, TCS). Their factories, employees, patents, and cash flow work for you 24 hours a day.",
                    keyTakeaway = "Do not labor endlessly for paper money. Use paper money to buy equity in productive assets that compound forever."
                ),
                ChapterSection(
                    sectionTitle = "3. Central Banks, Money Printing & Inflation",
                    body = "Paper money is not permanent wealth—it is a depreciating medium of exchange. When central banks print currency, the purchasing power of your savings drops by 5% to 7% each year through inflation. To keep your wealth intact and expand it, you MUST park your surplus savings in assets that grow faster than inflation—primarily equity index funds, dividend stocks, and sovereign gold.",
                    keyTakeaway = "Inflation is an invisible tax on savers who hold cash. It is an economic tailwind for investors who own productive assets."
                ),
                ChapterSection(
                    sectionTitle = "4. The Math of Freedom: 1:3 Risk-to-Reward and Compounding",
                    body = "Amateurs believe profitable traders have crystal balls that predict the future 95% of the time. In reality, elite traders make fortunes with just a 45% win rate because of asymmetric risk-to-reward. When they are wrong, they cut their loss at 1 unit with a stop-loss. When they are right, they ride the trend for 3 units of profit. Over 100 trades, the mathematics guarantees substantial net profit.",
                    keyTakeaway = "Mastering two simple disciplines—cutting losses short and letting winners compound—puts you ahead of 95% of the population."
                )
            ),
            goldenRulesDo = listOf(
                "Never invest money before knowing your exact maximum risk per trade.",
                "Direct automated surplus from your daily income into broad equity index SIPs.",
                "Treat every stock as fractional ownership of a living, breathing corporation."
            ),
            cardinalSinsAvoid = listOf(
                "Never trade on emotional revenge or social media tips.",
                "Never leave cash uninvested to be eroded by annual inflation."
            ),
            aiTutorStarterPrompt = "Teach me Session 1: Give me a deep-dive breakdown of how the global market auction works and how I can start investing safely.",
            nextLevelId = "ch_2"
        ),
        MasterclassChapter(
            id = "ch_2",
            chapterNumber = 2,
            era = "Level 2 (Rs 125)",
            title = "The Mechanics of Money: Central Banks, Fiat & Inflation",
            subtitle = "Why cash guarantees poverty and how liquidity cycles dictate market booms",
            estimatedReadMinutes = 8,
            isFree = false,
            unlockPriceInr = 125,
            hookStatement = "Central banks print money by the trillions. If you do not understand interest rate cycles and repo rate pivots, you are flying blind in the market.",
            agenda = listOf(
                "The 1971 Gold Standard Collapse: Why all modern currency is fiat",
                "Central Bank Levers: Repo rates, quantitative easing, and bank reserves",
                "The Liquidity Cycle: Predicting market expansions and contractions",
                "Hedging Currency Debasement: Sovereign Gold and Equity Indexing"
            ),
            coreContentSections = listOf(
                ChapterSection(
                    sectionTitle = "1. Fiat Currency & The Infinite Printing Press",
                    body = "When money was unpegged from physical gold in 1971, central banks gained the power to expand money supply at will. When trillions of new currency units enter circulation, the prices of finite assets (real estate, prime equities, commodities) surge. Savers holding fixed-deposit cash lose purchasing power while asset owners see their net worth multiply.",
                    keyTakeaway = "Always hold your wealth in finite, productive assets rather than unbacked fiat cash."
                ),
                ChapterSection(
                    sectionTitle = "2. The Interest Rate Engine",
                    body = "When central banks lower the repo rate, borrowing costs plummet. Corporations borrow cheaply to expand, consumers take home loans, and liquidity gushes into equity markets. When central banks hike rates to fight inflation, liquidity dries up, causing speculative valuations to reset.",
                    keyTakeaway = "Align your trading with macro liquidity. Ride bull markets when central banks ease; prioritize capital preservation when they tighten."
                )
            ),
            goldenRulesDo = listOf(
                "Monitor monthly CPI inflation and Central Bank policy statements.",
                "Increase your equity allocation during early liquidity easing cycles."
            ),
            cardinalSinsAvoid = listOf(
                "Never fight the central bank's liquidity stance.",
                "Never keep more than 6 months of emergency living expenses in cash."
            ),
            aiTutorStarterPrompt = "Teach me Level 2: How do central bank interest rates drive stock market bull runs?",
            nextLevelId = "ch_3"
        ),
        MasterclassChapter(
            id = "ch_3",
            chapterNumber = 3,
            era = "Level 3 (Rs 175)",
            title = "The Asset Architecture: Equities, Bonds & Compound Dividends",
            subtitle = "Mastering corporate valuations, fixed income yields, and passive cashflow machines",
            estimatedReadMinutes = 9,
            isFree = false,
            unlockPriceInr = 175,
            hookStatement = "The world's wealthiest dynasties do not rely on active wage labor. They build diversified asset fortresses that generate compounding yield 365 days a year.",
            agenda = listOf(
                "Equities Under the Microscope: P/E ratios, earnings yield, and free cash flow",
                "Bonds & Fixed Income: Sovereign debt, corporate credit, and yield curves",
                "Dividend Aristocrats: Generating passive cashflow directly to your bank account",
                "Asset Allocation Strategy: Tailoring equity vs bond ratios for explosive compounding"
            ),
            coreContentSections = listOf(
                ChapterSection(
                    sectionTitle = "1. Reading Corporate Value in 5 Minutes",
                    body = "To value a company, inspect three metrics: P/E Ratio (how much you pay per rupee of profit), Free Cash Flow (cold hard cash left after capital expenses), and Debt-to-Equity (financial leverage). A company with growing free cash flow, low debt, and a fair P/E ratio is an asymmetric wealth compounder.",
                    keyTakeaway = "Price is what you pay; value is what you get. Focus on companies that mint genuine surplus cash."
                ),
                ChapterSection(
                    sectionTitle = "2. The Power of Dividend Compounding",
                    body = "Companies that consistently return 5–8% cash yields through dividends offer dual upside: your principal grows as the stock appreciates, while cash deposits hit your account every quarter. Reinvesting these payouts automatically accelerates the exponential compounding curve.",
                    keyTakeaway = "Dividend reinvestment turns simple savings into an unstoppable snowball of compounding capital."
                )
            ),
            goldenRulesDo = listOf(
                "Filter for companies with consistent 5-year free cash flow growth.",
                "Set dividend payouts to automatically reinvest into new shares."
            ),
            cardinalSinsAvoid = listOf(
                "Never chase high dividend yield in debt-ridden zombie companies.",
                "Never buy stocks based purely on promotional hype without checking financials."
            ),
            aiTutorStarterPrompt = "Teach me Level 3: How do I evaluate a company's free cash flow and build a dividend income portfolio?",
            nextLevelId = "ch_4"
        ),
        MasterclassChapter(
            id = "ch_4",
            chapterNumber = 4,
            era = "Level 4 (Rs 200)",
            title = "Institutional Liquidity: Order Flow & Smart Money Traps",
            subtitle = "How hedge funds manipulate retail sentiment and how to trade alongside institutional giants",
            estimatedReadMinutes = 10,
            isFree = false,
            unlockPriceInr = 200,
            hookStatement = "Retail traders look at candlestick charts with hope. Institutional algorithms look at order books with precision. Learn how smart money accumulates positions without tipping their hand.",
            agenda = listOf(
                "Why 90% of retail traders fail: Emotional bias, over-leveraging, and lack of edge",
                "Liquidity Sweeps & Stop Hunts: Where retail orders cluster and why institutions target them",
                "Volume Price Analysis (VPA): Detecting stealth institutional accumulation",
                "The 1:3 Asymmetric Setup: Protecting your trading capital with surgical stop-losses"
            ),
            coreContentSections = listOf(
                ChapterSection(
                    sectionTitle = "1. The Stop-Loss Hunt Explained",
                    body = "When retail traders buy near resistance, they place their stop-loss orders just below key support levels. Institutional market makers know this. Before driving a massive rally, institutions push prices downward to trigger retail stop-loss sells, absorbing thousands of shares at bargain prices before initiating the real breakout.",
                    keyTakeaway = "Do not place your stop-loss where the entire crowd does. Give your thesis breathing room below obvious swing pivots."
                ),
                ChapterSection(
                    sectionTitle = "2. The Mathematical Fortress of Asymmetry",
                    body = "A trader who risks ₹500 to make ₹1,500 needs only a 35% win rate to be consistently profitable. Over 20 trades: 7 wins = +₹10,500. 13 losses = -₹6,500. Net Profit = +₹4,000. Stop stressing over being right every time; master your risk-to-reward ratio.",
                    keyTakeaway = "Consistency in risk management is the true hallmark of institutional trading."
                )
            ),
            goldenRulesDo = listOf(
                "Always calculate your position size based on risking no more than 1–2% of total capital.",
                "Wait for volume confirmation when trading breakout setups."
            ),
            cardinalSinsAvoid = listOf(
                "Never move your stop-loss further away when a trade goes against you.",
                "Never trade with high leverage without a proven statistical edge."
            ),
            aiTutorStarterPrompt = "Teach me Level 4: How do institutional liquidity sweeps work, and how do I trade with 1:3 risk-to-reward?",
            nextLevelId = "ch_5"
        ),
        MasterclassChapter(
            id = "ch_5",
            chapterNumber = 5,
            era = "Level 5 (Rs 225)",
            title = "Decentralized vs. Centralized: Settlement & Digital Sovereignty",
            subtitle = "From TradFi clearing houses to decentralized blockchains, smart contracts, and crypto",
            estimatedReadMinutes = 10,
            isFree = false,
            unlockPriceInr = 225,
            hookStatement = "For 400 years, every transaction required a bank or clearing house. In 2009, decentralized mathematics proved we could settle trillions with zero intermediaries.",
            agenda = listOf(
                "The TradFi Plumbing: How brokers, custodians (CDSL/NSDL), and clearing houses operate",
                "The DeFi Revolution: Trustless ledgers, cryptographic consensus, and Bitcoin/Ethereum",
                "Central Bank Digital Currencies (CBDC) vs Sovereign Crypto Assets",
                "The All-Weather Digital Balance: Blending TradFi compounding with decentralized security"
            ),
            coreContentSections = listOf(
                ChapterSection(
                    sectionTitle = "1. Centralized Finance (TradFi) Strengths & Vulnerabilities",
                    body = "TradFi provides legal protections, SIP automation, and deposit insurance. However, it operates within strict geographic borders, enforces high cross-border wire fees, and accounts can be frozen during political or banking instability.",
                    keyTakeaway = "Use TradFi for tax-advantaged equity index compounding and local regulatory safety."
                ),
                ChapterSection(
                    sectionTitle = "2. Decentralized Finance (DeFi) & Absolute Ownership",
                    body = "Decentralized assets exist on public cryptographic ledgers. No single government can print more Bitcoin; no broker can lend out your assets without your private keys. Smart contracts automate lending and liquidity pools 24/7 with mathematical transparency.",
                    keyTakeaway = "Decentralization provides financial sovereignty and an unalterable hedge against fiat debasement."
                )
            ),
            goldenRulesDo = listOf(
                "Keep core equity compounding in regulated, low-cost domestic brokerages.",
                "Maintain cold storage self-custody for long-term decentralized holdings."
            ),
            cardinalSinsAvoid = listOf(
                "Never leave large capital sums on unregulated offshore crypto exchanges.",
                "Never share your private recovery seed phrase with anyone."
            ),
            aiTutorStarterPrompt = "Teach me Level 5: Explain the real difference between Centralized and Decentralized finance and how to position my portfolio.",
            nextLevelId = "ch_6"
        ),
        MasterclassChapter(
            id = "ch_6",
            chapterNumber = 6,
            era = "Level 6 (Rs 250)",
            title = "The Sovereign Trader: 7-Day Wealth Compounding Blueprint",
            subtitle = "The exact operational plan to transform daily savings into an unstoppable multi-million portfolio",
            estimatedReadMinutes = 11,
            isFree = false,
            unlockPriceInr = 250,
            hookStatement = "Knowledge without structured execution is meaningless. Here is the unshakeable 7-day master execution plan to build lasting financial independence.",
            agenda = listOf(
                "Day 1–2: The Cashflow Velocity Audit—Automating your savings allocation",
                "Day 3–4: The Core Equity Engine—Configuring Nifty 50 and S&P 500 low-cost index SIPs",
                "Day 5: The Tactical Swing Desk—Executing high-probability momentum setups",
                "Day 6–7: The Sovereign Wealth Fortress—Quarterly rebalancing and generational wealth protection"
            ),
            coreContentSections = listOf(
                ChapterSection(
                    sectionTitle = "1. The 80/20 Core-Satellite Architecture",
                    body = "Allocate 80% of your investment capital into low-cost broad index funds (Nifty 50 / S&P 500) that automatically capture human technological advancement. Allocate the remaining 20% into high-conviction swing trades, individual growth leaders, and dividend cashflow generators.",
                    keyTakeaway = "Your Core guarantees you beat inflation and never lag behind the global economy; your Satellite captures explosive outsized alpha."
                ),
                ChapterSection(
                    sectionTitle = "2. The Automated Dollar-Cost Averaging Flywheel",
                    body = "Never attempt to time the exact market bottom. Schedule automatic bank deductions 24 hours after your income credit. In bear market crashes, your fixed investment buys more shares at fire-sale discounts; in bull runs, your accumulated holdings surge.",
                    keyTakeaway = "Relentless automated consistency always triumphs over emotional market timing."
                )
            ),
            goldenRulesDo = listOf(
                "Increase your monthly investment contribution by 10% every time your income grows.",
                "Review your financial ledger monthly; ignore intraday panic news."
            ),
            cardinalSinsAvoid = listOf(
                "Never interrupt compound interest unnecessarily.",
                "Never borrow money to trade in speculative derivatives."
            ),
            aiTutorStarterPrompt = "Teach me Level 6: Walk me through the complete 7-day action blueprint to automate my wealth building.",
            nextLevelId = null
        )
    )

    fun getChapterById(id: String): MasterclassChapter? {
        return CHAPTERS.find { it.id == id }
    }
}
