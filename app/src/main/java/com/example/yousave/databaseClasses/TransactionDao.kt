package com.example.yousave.databaseClasses

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import java.util.Date

@Dao
interface TransactionDao {
    @Query("SELECT * FROM 'transaction'")
    fun getAll(): List<Transaction>

    @Insert
    fun insert(vararg transaction: Transaction)

    @Delete
    fun remove(transaction: Transaction)

    @Query("SELECT SUM(money) as money, COUNT(*) as transactions FROM 'transaction' WHERE strftime('%Y-%m', date) = strftime('%Y-%m', :date) AND category = :categoryName")
    fun getMoneyTransactionsMonth(categoryName: String, date: Date): MoneyTransactions


}