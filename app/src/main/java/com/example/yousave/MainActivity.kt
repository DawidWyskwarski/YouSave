package com.example.yousave

import android.annotation.SuppressLint
import android.content.res.TypedArray
import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.toColor
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val categoriesRecycler:RecyclerView = findViewById(R.id.categories)
        categoriesRecycler.adapter = CategoriesAdapter(getData())
        categoriesRecycler.layoutManager = object : LinearLayoutManager(this) {
            override fun canScrollVertically() = false
        }

    }

    //load data into the list of categories
    @RequiresApi(Build.VERSION_CODES.O)
    private fun getData():List<Category>{
        val names:Array<String> = resources.getStringArray(R.array.category_names)
        val colors:IntArray = resources.getIntArray(R.array.category_colors)
        val images: TypedArray = resources.obtainTypedArray(R.array.category_images)

        //temporary solution before i add database or something
        val money:Array<Double> = Array(names.size) { 0.0 }
        val transactions:Array<Int> = Array(names.size) { 0 }

        val categories = ArrayList<Category>()

        for (i in names.indices){
            categories.add(Category(names[i],money[i],transactions[i],colors[i].toColor(),images.getResourceId(i,0)))
        }

        return categories
    }
}