package com.example.yousave

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toDrawable
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class CategoryInfoActivity : AppCompatActivity() {

    private lateinit var name:TextView
    private lateinit var icon:ImageView
    private lateinit var money:TextView
    private lateinit var colorBar:View

    private lateinit var category:Category

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_category_info)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        window.navigationBarColor = ContextCompat.getColor(this, R.color.dark_gray)

        category = intent.getParcelableExtra("DATA") ?: throw IllegalStateException("Category data missing!")

        initializeViews()

    }

    fun initializeViews(){
        name = findViewById<TextView>(R.id.name)
        name.text = category.name
        name.setTextColor(category.color)

        icon = findViewById<ImageView>(R.id.icon)
        icon.setImageResource(category.image)

        money = findViewById<TextView>(R.id.money)
        val tmp = "${category.moneySpent} zł in ${category.transactions} transactions"
        money.text = tmp
        money.setTextColor(category.color)

        colorBar = findViewById<View>(R.id.color_bar)
        colorBar.background = category.color.toDrawable()
    }


}