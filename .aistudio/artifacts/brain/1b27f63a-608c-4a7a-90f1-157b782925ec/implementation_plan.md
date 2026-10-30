# AuraWealth: Luxury Black & Gold Wealth Tracker & Market Engine

An ultra-refined, motivational Android wealth management app built with Jetpack Compose. Combines precision daily/monthly/annual income and expense tracking, dynamic savings-to-stock wealth multipliers, regional macroeconomic criteria analysis (pre-tuned for India ₹ INR with multi-region support), continuous income opportunity generation, crucial stock market news & plain-English trade terminologies, daily motivational pop-ups, and a multi-turn Gemini 3.8 Flash financial advisor.

---

### User Review & Critical Decisions

> [!IMPORTANT]
> **Summary of Confirmed Choices from Phase 1:**
> - **Region & Currency**: Configured to **India (INR ₹)** by default, with dynamic switching to US ($), UK (£), Canada (CAD $), EU (€), etc.
> - **Growth Focus**: **"All of it"** — comprehensive integration of stocks, index funds, dividend investing, side income generation, high-growth active trade ideas, and compound interest maximization.
> - **Visual & Motivational Tone**: **Black and Gold Minimalist Aesthetic** with active market charts, tactile glowing surfaces, benevolent explainers for complex trade concepts, and inspiring daily wealth quotes.
> - **AI Integration**: Powered by **Firebase AI Logic (`gemini-3.8-flash`)** configured with App Check attestation and multi-turn chat memory.

---

### 1. Overview & Core Concept

**AuraWealth** is designed to transform financial management from a dry chore into an empowering daily wealth-building ritual. 

- **Target Persona**: Individuals, aspiring traders, professionals, and side-hustlers looking to master their cashflow, understand the stock market without being overwhelmed by technical jargon, and discover actionable regional income streams.
- **Key Value**: Real-time clarity over daily burn rate, projected monthly and annual net income, automated savings detection, localized investment criteria (Nifty/Sensex, RBI repo rates, inflation, tax slabs), and endless tailored income ideas driven by AI.

---

### 2. User Experience & Visual Design

#### Visual Identity & Theme
- **Theme Palette**: Deep OLED Velvet Black (`#0A0A0C` background, `#141418` surface), Imperial Gold (`#D4AF37`, `#FFDF00`, `#F59E0B` primary & highlights), Muted Gold (`#9E8238`), Deep Bronze/Charcoal borders (`#2A2721`), and High-Contrast Off-White text (`#F5F5F7`).
- **Typography & Atmosphere**: Sleek modern display typography with gold hairline dividers, glowing active chart lines, pill chips, and smooth spring-animated transitions.
- **Motivational Elevation**: 
  - Daily Wealth Mindset Popup on launch (dismissible, with "Save to Favorites" or "Reflect").
  - Animated gold streak counters and milestone badges ("First ₹50k Saved", "Investment Velocity: 32%").

#### Key User Flows & Screen Architecture
1. **Home / Wealth Dashboard**:
   - Hero Gold Balance & Savings Card (Daily Net, Monthly Income, Annual Projected, Savings Rate %).
   - Quick Transaction Logger (Income vs Expense, category tags, quick amount buttons).
   - "Quote of the Day" motivational banner with quote refresh.
   - Quick-access shortcuts to Stock Multiplier and Gemini Advisor.
2. **Analytics & Cashflow Studio**:
   - Interactive gold Canvas cashflow bezier chart (Daily vs Monthly vs Annual trends).
   - Category spend donut chart & breakdown.
   - Compound Interest & Stock Multiplier Simulator: Sliders for monthly investment, expected CAGR (e.g., 12% Nifty index vs 4% savings account), visual 10-year wealth projection comparing traditional bank interest vs equity compounding.
3. **Market Intelligence & Regional Radar**:
   - Region selector (India ₹, US $, UK £, Canada $, Europe €).
   - Macroeconomic Criteria Panel: Current Inflation rates, Central Bank Repo rates, GDP outlook, and localized tax/investment vehicles (e.g. PPF, SGB, SIP/Mutual Funds, Nifty 50 vs S&P 500).
   - Crucial Market News Feed: Official & timely market catalysts (earnings, interest rate decisions, global trade updates).
   - "Benevolent Market Glossary": Searchable, bite-sized plain-English cards demystifying complex terms (RSI, P/E Ratio, Bull/Bear, Options, Market Cap, Dividend Yield, Liquidity, Stop-Loss).
4. **Income Opportunity Hub (Endless Idea Engine)**:
   - Dynamic, filterable income opportunities tailored to the chosen region (Remote Skills, Micro-Agencies, Dividend Portfolios, E-commerce, Local Services).
   - "Generate New Ideas" button that continuously synthesizes new tailored opportunities via AI without dead-ends.
5. **Gemini AI Financial & Trading Mentor**:
   - Multi-turn conversation with streaming responses using `gemini-3.8-flash`.
   - Context-aware: Chatbot has access to the user's active regional settings and financial snapshot.
   - Pre-loaded prompt suggestions: *"How can I invest ₹5,000 this month in Indian stocks?"*, *"Explain how inflation is eroding my cash savings"*, *"What side hustle can I start with zero capital in my region?"*.

---

### 3. Key Product Decisions & Trade-Offs

- **Decision 1: Persistence Strategy (Room vs Remote DB)**
  - *Chosen Approach*: **Room Local Database** with Flow observables for instant offline responsiveness, zero latency, and complete privacy for financial entries.
  - *Rationale*: Financial expense and income logging must work instantly without network delays. The database persists all user entries, custom savings goals, and regional preferences across restarts.
- **Decision 2: AI Logic & App Check (`gemini-3.8-flash`)**
  - *Chosen Approach*: **Firebase AI Logic with App Check** configured in `MainActivity.onCreate()` using the official system guidelines, utilizing `gemini-3.8-flash` for high quality and rapid streaming responses.
  - *Fallback / Resilience*: If offline or during network dropouts, built-in intelligent regional engines provide instant deterministic market data and curated ideas until network connectivity resumes.
- **Decision 3: Charting Architecture**
  - *Chosen Approach*: Lightweight, custom Jetpack Compose Canvas implementations with smooth Bezier curves, glowing gradients, and interactive touch scrubbing.
  - *Rationale*: Provides 100% control over the Black and Gold aesthetic, avoids external library bloat, and guarantees high-performance 60fps rendering.

---

### 4. Technical Architecture & Data Strategy

```
┌────────────────────────────────────────────────────────────────────────┐
│                        Jetpack Compose UI Layer                        │
│  ┌───────────────┐ ┌────────────────┐ ┌───────────────┐ ┌────────────┐ │
│  │ WealthHome    │ │ AnalyticsChart │ │ MarketRadar   │ │ AiChatView │ │
│  │ & Logger Modal│ │ & Stock Sim    │ │ & Glossary    │ │ & IdeasHub │ │
│  └───────┬───────┘ └────────┬───────┘ └───────┬───────┘ └──────┬─────┘ │
└──────────┼──────────────────┼─────────────────┼────────────────┼───────┘
           │                  │                 │                │
┌──────────▼──────────────────▼─────────────────▼────────────────▼───────┐
│                           AuraViewModel                                │
│   - StateFlow<WealthUiState>       - Transaction management            │
│   - Region & Macro Criteria        - Stock Multiplier math             │
│   - Gemini AI streaming & history  - Motivational Quote rotation       │
└──────────┬────────────────────────────────────┬────────────────────────┘
           │                                    │
┌──────────▼──────────────┐          ┌──────────▼────────────────────────┐
│   Room Database Layer   │          │     Firebase AI Logic Layer       │
│  - TransactionEntity    │          │  - configureAppCheck(this, intent)│
│  - AppPreferences       │          │  - gemini-3.8-flash stream model  │
│  - TransactionDao       │          │  - Market Advisor System Prompt   │
└─────────────────────────┘          └───────────────────────────────────┘
```

#### Core Data Entities:
- `TransactionEntity`: `id`, `title`, `amount`, `type` (INCOME, EXPENSE), `category` (Salary, Business, Stocks/Dividends, Food, Housing, Utilities, Leisure, Investment), `timestamp`, `recurrence` (DAILY, MONTHLY, ONE_TIME).
- `RegionConfig`: Region identifier (`IN`, `US`, `UK`, `CA`, `EU`), Currency symbol (`₹`, `$`, `£`, `€`), GDP/Inflation/Repo rate telemetry, Local tax slabs, Prime market indices (Nifty 50, Sensex, etc.).
- `MarketNewsItem`: Title, source, impact tag (BULLISH, BEARISH, NEUTRAL), timestamp, summary, educational takeaway.
- `MarketTerm`: Term name, category, plain-English summary, practical trading example.
- `MotivationalQuote`: Quote text, author/source, wealth discipline category.
