package com.example.yousave

import HistoryFragment
import android.content.Intent
import android.content.res.TypedArray
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(){

    private lateinit var homeData:List<Category>

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
        homeData = getHomeData()

        //Fragments
        val home = HomeFragment( homeData )
        val history = HistoryFragment()
        val recurring = RecurringFragment()
        val settings = SettingsFragment()

        replaceFragment( home )

        val bottomBar:BottomNavigationView = findViewById(R.id.bottom_bar)

        val addButton:FloatingActionButton = findViewById(R.id.add_transaction)

        bottomBar.setOnItemSelectedListener {
            when(it.itemId){
                R.id.home_bar -> { replaceFragment( home ); addButton.alpha = 1F }
                R.id.history_bar -> { replaceFragment( history ); addButton.alpha = 0F }
                R.id.recurring_bar -> { replaceFragment(recurring); addButton.alpha = 0F }
                R.id.settings_bar -> { replaceFragment( settings ); addButton.alpha = 0F }
            }
            true
        }

        addButton.setOnClickListener {
            startActivity( Intent(this, AddTransactionActivity::class.java) )
        }
    }

    fun replaceFragment(fragment: Fragment){

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragments, fragment)
            commit()
        }
    }

    //load data into the list of categories
    private fun getHomeData():List<Category>{
        val names:Array<String> = resources.getStringArray(R.array.category_names)
        val colors:IntArray = resources.getIntArray(R.array.category_colors)
        val images: TypedArray = resources.obtainTypedArray(R.array.category_images)

        //temporary solution before i add database or something
        val money:Array<Double> = Array(names.size) { 0.0 }
        val transactions:Array<Int> = Array(names.size) { 0 }

        money[3] = 500.0
        transactions[3] = 1

        money[0] = 123.0
        transactions[0] = 3

        money[5] = 200.12
        transactions[5] = 4

        val categories = ArrayList<Category>()

        for (i in names.indices){
            categories.add(Category(names[i], money[i], transactions[i], colors[i], images.getResourceId(i,0)))
        }

        images.recycle()

        return categories
    }
}