package com.example.yousave

import com.example.yousave.history.HistoryFragment
import android.content.Intent
import android.content.res.TypedArray
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.yousave.databaseClasses.AppDatabase
import com.example.yousave.databaseClasses.IncomeExpense
import com.example.yousave.databaseClasses.MoneyTransactions
import com.example.yousave.databaseClasses.TransactionDao
import com.example.yousave.home.Category
import com.example.yousave.home.HomeFragment
import com.example.yousave.recurring.RecurringFragment
import com.example.yousave.settings.SettingsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity(){

    private lateinit var db:AppDatabase
    private lateinit var transactionDao: TransactionDao

    //Fragments
    private val home = HomeFragment()
    private val history = HistoryFragment()
    private val recurring = RecurringFragment()
    private val settings = SettingsFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        window.navigationBarColor = resources.getColor(R.color.black)

        db = AppDatabase.getDatabase(this)
        transactionDao = db.transactionDao()

        replaceFragment( home )

        val addButton:FloatingActionButton = findViewById(R.id.add_transaction)

        //switching fragments
        findViewById<BottomNavigationView>(R.id.bottom_bar).setOnItemSelectedListener {

            when(it.itemId){
                R.id.home_bar -> { replaceFragment( home ); addButton.visibility = View.VISIBLE; }
                R.id.history_bar -> { replaceFragment( history ); addButton.visibility = View.GONE; }
                R.id.recurring_bar -> { replaceFragment(recurring); addButton.visibility = View.GONE; }
                R.id.settings_bar -> { replaceFragment( settings ); addButton.visibility = View.GONE; }
            }
            true
        }

        //activity to add transactions to database starts
        addButton.setOnClickListener {
            startActivity( Intent(this, AddTransactionActivity::class.java) )
        }
    }

    private fun replaceFragment(fragment: Fragment){

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragments, fragment)
            commit()
        }
    }

    //load data for a home fragment
    fun loadHomeData(onDataLoaded: (List<Category>,Double,Double) -> Unit){
        val names:Array<String> = resources.getStringArray(R.array.category_names)
        val colors:IntArray = resources.getIntArray(R.array.category_colors)
        val images: TypedArray = resources.obtainTypedArray(R.array.category_images)

        val categories = ArrayList<Category>()

        lifecycleScope.launch {
            val totalIncome = transactionDao.getMoneyTransactionsMonth("Income").money
            var totalExpense = 0.0

            for (i in names.indices) {
                val mt: MoneyTransactions = transactionDao.getMoneyTransactionsMonth(names[i])

                totalExpense += mt.money

                categories.add(Category(names[i], mt.money, mt.transactions, colors[i], images.getResourceId(i, 0)))
            }
            images.recycle()

            //pass data back to UI thread
            withContext(Dispatchers.Main) {
                onDataLoaded(categories,totalIncome,totalExpense)
            }
        }
    }

    fun loadHistoryData(onDataLoaded: (List<IncomeExpense>) -> Unit){

        lifecycleScope.launch {
            val list:List<IncomeExpense> = transactionDao.getIncomesExpenses()

            withContext(Dispatchers.Main){
                onDataLoaded(list)
            }
        }
    }
}