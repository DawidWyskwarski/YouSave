package com.example.yousave

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.TypedArray
import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.toColor
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
@RequiresApi(Build.VERSION_CODES.O)
class MainActivity : AppCompatActivity(), CategoryInterface {

    private lateinit var data:List<Category>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        data = getData()
        window.navigationBarColor = ContextCompat.getColor(this, R.color.black)

        val chart: RecyclerView = findViewById(R.id.chart)

        chart.post {
            val width = chart.width
            chart.adapter = ChartAdapter(data, width)
        }
        chart.layoutManager = object: LinearLayoutManager(this, HORIZONTAL, false) {
            override fun canScrollVertically() = false
        }

        val categoriesRecycler:RecyclerView = findViewById(R.id.categories)
        categoriesRecycler.adapter = CategoriesAdapter(data,this)
        categoriesRecycler.layoutManager = object : LinearLayoutManager(this) {
            override fun canScrollVertically() = false
        }

    }

    //load data into the list of categories
    private fun getData():List<Category>{
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

    override fun onCategoryClick(position: Int) {
        val category = data[position]

        val intent = Intent(this, CategoryInfoActivity::class.java )
        intent.putExtra("DATA", category)

        startActivity(intent)
    }
}