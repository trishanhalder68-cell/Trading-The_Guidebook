package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.data.model.RecurrencePeriod
import com.example.data.model.TransactionEntity
import com.example.data.model.TransactionType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [TransactionEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun transactionDao(): TransactionDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "trading_guidebook_db"
                )
                    .addCallback(AppDatabaseCallback(scope))
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class AppDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch(Dispatchers.IO) {
                    populateInitialData(database.transactionDao())
                }
            }
        }

        suspend fun populateInitialData(dao: TransactionDao) {
            val now = System.currentTimeMillis()
            val dayMs = 86_400_000L

            val initialTransactions = listOf(
                TransactionEntity(
                    title = "Primary Tech Salary / Contract",
                    amount = 120000.0,
                    type = TransactionType.INCOME,
                    category = "Salary",
                    timestamp = now - (dayMs * 2),
                    note = "Monthly primary professional inflow",
                    recurrence = RecurrencePeriod.MONTHLY
                ),
                TransactionEntity(
                    title = "Nifty 50 Index Fund SIP",
                    amount = 35000.0,
                    type = TransactionType.EXPENSE,
                    category = "Investment",
                    timestamp = now - (dayMs * 1),
                    note = "Automated wealth compounding into broad equity index",
                    recurrence = RecurrencePeriod.MONTHLY
                ),
                TransactionEntity(
                    title = "Freelance UI Consulting (US Client)",
                    amount = 45000.0,
                    type = TransactionType.INCOME,
                    category = "Freelance",
                    timestamp = now - (dayMs * 4),
                    note = "Remote international invoice payment",
                    recurrence = RecurrencePeriod.ONE_TIME
                ),
                TransactionEntity(
                    title = "Apartment Rent & Society Maintenance",
                    amount = 26000.0,
                    type = TransactionType.EXPENSE,
                    category = "Housing",
                    timestamp = now - (dayMs * 5),
                    note = "Fixed living overhead",
                    recurrence = RecurrencePeriod.MONTHLY
                ),
                TransactionEntity(
                    title = "Stock Dividend Yield (Coal India & ITC)",
                    amount = 4800.0,
                    type = TransactionType.INCOME,
                    category = "Dividends",
                    timestamp = now - (dayMs * 6),
                    note = "Quarterly passive dividend payout deposited to account",
                    recurrence = RecurrencePeriod.ONE_TIME
                ),
                TransactionEntity(
                    title = "Daily Grocery & Organic Pantry",
                    amount = 1450.0,
                    type = TransactionType.EXPENSE,
                    category = "Food",
                    timestamp = now - 3600_000L,
                    note = "Daily subsistence",
                    recurrence = RecurrencePeriod.DAILY
                ),
                TransactionEntity(
                    title = "Fast Internet & Cloud Server Subscription",
                    amount = 1899.0,
                    type = TransactionType.EXPENSE,
                    category = "Utilities",
                    timestamp = now - (dayMs * 8),
                    note = "Workstation infrastructure",
                    recurrence = RecurrencePeriod.MONTHLY
                ),
                TransactionEntity(
                    title = "Swing Trade Profit (Breakout Strategy)",
                    amount = 12500.0,
                    type = TransactionType.INCOME,
                    category = "Trading",
                    timestamp = now - (dayMs * 10),
                    note = "Target hit on 20 EMA bounce with 1:2.5 risk-reward",
                    recurrence = RecurrencePeriod.ONE_TIME
                )
            )
            dao.insertAll(initialTransactions)
        }
    }
}
