package com.example.yousave

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.yousave.databaseClasses.AppDatabase
import com.example.yousave.databaseClasses.Transaction
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.ZoneId
import java.util.Calendar
import java.util.Date

class AddTransactionActivity : AppCompatActivity() {

    private lateinit var categories:Spinner
    private lateinit var expense:RadioButton
    private lateinit var income:RadioButton
    private lateinit var oneTime:RadioButton
    private lateinit var repetitive:RadioButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_transaction)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        window.navigationBarColor = ContextCompat.getColor(this, R.color.dark_gray)

        expense = findViewById(R.id.expense)
        income = findViewById(R.id.income)
        oneTime= findViewById(R.id.one_time)
        repetitive = findViewById(R.id.repetitive)

        categories = findViewById(R.id.category_selector)
        ArrayAdapter.createFromResource(
            this,
            R.array.category_names,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            categories.adapter = adapter
        }

        val perMonth: LinearLayout = findViewById(R.id.per_month)

        expense.setOnClickListener { categories.visibility = View.VISIBLE }
        income.setOnClickListener { categories.visibility = View.GONE }

        oneTime.setOnClickListener { perMonth.visibility = View.GONE }
        repetitive.setOnClickListener {perMonth.visibility = View.VISIBLE}

        findViewById<Button>(R.id.add).setOnClickListener{
            addTransaction()
        }
    }

    private fun addTransaction(){

        val db = AppDatabase.getDatabase(this)
        val dao = db.transactionDao()

        // running it on a background thread to not block the main one
        lifecycleScope.launch {
            dao.insert(
                Transaction(
                    0,
                    today(),
                    if (expense.isChecked) {
                        categories.selectedItem.toString()
                    } else "Income",
                    findViewById<EditText>(R.id.money_input).text.toString().toDouble(),
                    findViewById<EditText>(R.id.title).text.toString(),
                    findViewById<EditText>(R.id.description).text.toString(),
                )
            )

            finish()
        }
    }

    private fun today():Date{
        //for the newer API levels (> 26)
        //return Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant())

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY,12)
        calendar.set(Calendar.MINUTE,0)
        calendar.set(Calendar.SECOND,0)
        calendar.set(Calendar.MILLISECOND,0)

        return calendar.time
    }

}