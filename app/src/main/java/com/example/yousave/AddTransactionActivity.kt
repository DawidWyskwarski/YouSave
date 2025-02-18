package com.example.yousave

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager

class AddTransactionActivity : AppCompatActivity() {
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

        val expense:RadioButton = findViewById(R.id.expense)
        val income:RadioButton = findViewById(R.id.income)

        val oneTime:RadioButton = findViewById(R.id.one_time)
        val repetitive:RadioButton = findViewById(R.id.repetitive)

        val categories:Spinner = findViewById(R.id.category_selector)
        val perMonth: LinearLayout = findViewById(R.id.per_month)

        expense.setOnClickListener { categories.visibility = View.VISIBLE }
        income.setOnClickListener { categories.visibility = View.GONE }

        oneTime.setOnClickListener { perMonth.visibility = View.GONE }
        repetitive.setOnClickListener {perMonth.visibility = View.VISIBLE}

        findViewById<Button>(R.id.add).setOnClickListener{
            add()
        }
    }

    private fun add(){
        //TODO get all the data and from editTexts and add to data base

        finish()
    }

}