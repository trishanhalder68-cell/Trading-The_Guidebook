package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

enum class TransactionType {
    INCOME,
    EXPENSE
}

enum class RecurrencePeriod {
    DAILY,
    MONTHLY,
    ANNUAL,
    ONE_TIME
}

@Entity(tableName = "transactions")
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val amount: Double,
    val type: TransactionType,
    val category: String,
    val timestamp: Long = System.currentTimeMillis(),
    val note: String = "",
    val recurrence: RecurrencePeriod = RecurrencePeriod.DAILY
)

data class CashflowSummary(
    val dailyIncome: Double,
    val dailyExpense: Double,
    val monthlyIncome: Double,
    val monthlyExpense: Double,
    val annualIncome: Double,
    val annualExpense: Double,
    val netSavings: Double,
    val savingsRatePercentage: Double
)
