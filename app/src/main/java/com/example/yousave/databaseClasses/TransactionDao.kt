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

    @Query("SELECT SUM(money) as money, COUNT(*) as transactions FROM `transaction` WHERE strftime('%Y-%m', date / 1000, 'unixepoch') = strftime('%Y-%m', 'now') AND category = :categoryName")
    suspend fun getMoneyTransactionsMonth(categoryName: String): MoneyTransactions

    @Query("SELECT SUM(money) as money, COUNT(*) as transactions FROM `transaction` WHERE strftime('%Y', date / 1000, 'unixepoch') = strftime('%Y', 'now') AND category = :categoryName")
    suspend fun getMoneyTransactionsYear(categoryName: String): MoneyTransactions

    @Query("SELECT SUM(money) as money, COUNT(*) as transactions FROM `transaction` WHERE category = :categoryName")
    suspend fun getMoneyTransactionsAll(categoryName: String): MoneyTransactions

    @Query("SELECT * FROM `transaction` WHERE category = :categoryName ORDER BY date DESC LIMIT :limit")
    suspend fun getCategoryTransactions(categoryName: String, limit:Int):List<Transaction>

}