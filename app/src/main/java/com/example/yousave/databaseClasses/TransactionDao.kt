package com.example.yousave.databaseClasses

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TransactionDao {
    @Query("SELECT * FROM 'transaction'")
    suspend fun getAll(): List<Transaction>

    @Insert
    suspend fun insert(vararg transaction: Transaction)

    @Delete
    suspend fun remove(transaction: Transaction)

    @Query("""
        SELECT 
            SUM(money) as money,
            COUNT(*) as transactions 
        FROM `transaction`
        WHERE 
            strftime('%Y-%m', date / 1000, 'unixepoch') = strftime('%Y-%m', 'now') 
            AND category = :category
        """)
    suspend fun getMoneyTransactionsMonth(category: String): MoneyTransactions

    @Query("""
        SELECT 
            SUM(money) as money,
            COUNT(*) as transactions 
        FROM `transaction`
        WHERE 
            strftime('%Y', date / 1000, 'unixepoch') = strftime('%Y', 'now') 
            AND category = :category
        """)
    suspend fun getMoneyTransactionsYear(category: String): MoneyTransactions

    @Query("""
        SELECT 
            SUM(money) as money,
            COUNT(*) as transactions 
        FROM `transaction`
        WHERE 
            category = :category
        """)
    suspend fun getMoneyTransactionsAll(category: String): MoneyTransactions

    @Query("""
        SELECT
            * 
        FROM `transaction`
        WHERE 
            category = :category
        ORDER BY date DESC 
        LIMIT :limit
        """)
    suspend fun getCategoryTransactions(category: String, limit: Int): List<Transaction>

    @Query("""
        SELECT 
            strftime('%m %Y', date / 1000.0, 'unixepoch') AS formatted_date, 
            SUM(CASE WHEN category = 'Income' THEN money ELSE 0 END) AS income, 
            SUM(CASE WHEN category != 'Income' THEN money ELSE 0 END) AS expense 
        FROM 'transaction'
        GROUP BY formatted_date
        ORDER BY formatted_date DESC
        """)
    suspend fun getIncomesExpenses():List<IncomeExpense>
}