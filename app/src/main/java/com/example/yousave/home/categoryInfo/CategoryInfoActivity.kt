package com.example.yousave.home.categoryInfo

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toDrawable
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.yousave.R
import com.example.yousave.databaseClasses.AppDatabase
import com.example.yousave.databaseClasses.Transaction
import com.example.yousave.databaseClasses.TransactionDao
import com.example.yousave.home.Category
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import kotlinx.coroutines.launch
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.google.protobuf.Value
import kotlinx.coroutines.awaitAll
import java.text.SimpleDateFormat
import java.util.Date

class CategoryInfoActivity : AppCompatActivity() {

    private lateinit var category: Category
    private lateinit var db: AppDatabase
    private lateinit var dao: TransactionDao

    private lateinit var infoAdapter: InfoAdapter

    private var transactions: List<Transaction> = listOf()

    private lateinit var info: RecyclerView
    private lateinit var chart: LineChart

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

        db = AppDatabase.getDatabase(this)
        dao = db.transactionDao()

        category = intent.getParcelableExtra("DATA")
            ?: throw IllegalStateException("Category data missing!")
        initializeViews()

        lifecycleScope.launch {
            transactions = dao.getCategoryTransactions(category.name, category.transactions )

            info.adapter = InfoAdapter(transactions)

            setUpChart()
        }
    }

    private fun initializeViews() {
        val name = findViewById<TextView>(R.id.name)
        name.text = category.name
        name.setTextColor(category.color)

        val icon = findViewById<ImageView>(R.id.icon)
        icon.setImageResource(category.image)

        val money = findViewById<TextView>(R.id.money)
        val tmp = "${category.moneySpent} zł in ${category.transactions} transactions"
        money.text = tmp
        money.setTextColor(category.color)

        val colorBar = findViewById<View>(R.id.color_bar)
        colorBar.background = category.color.toDrawable()

        info = findViewById(R.id.transaction_info)
        info.layoutManager = object : LinearLayoutManager(this) {
            override fun canScrollVertically() = true
        }
    }

    private fun setUpChart() {

        chart = findViewById(R.id.chart)

        val dateMoneyPairs = transactions.map { transaction ->
            (transaction.date to transaction.money)
        }

        val dataPoints = dateMoneyPairs.groupBy({ it.first }, { it.second })
            .mapValues { (_, values) -> values.sum() }

        val chartEntries = dataPoints.map { (date, money) ->

            Entry(date.time.toFloat(), money.toFloat())
        }.reversed()


        //data set
        val dataSet = LineDataSet(chartEntries, "").apply {
            color = category.color

            valueTextColor = resources.getColor(R.color.white)
            valueTextSize = 10f

            lineWidth = 3.25f

            setCircleColor(color)
            setDrawCircleHole(false)
            circleRadius = 2.5f

            mode = LineDataSet.Mode.CUBIC_BEZIER
        }

        //chart x axis
        chart.xAxis.apply {
            position = XAxis.XAxisPosition.BOTTOM
            granularity = 1f

            setLabelCount(chartEntries.size, true)

            setDrawGridLines(false)

            valueFormatter = @SuppressLint("SimpleDateFormat")
            object : ValueFormatter() {
                private val sdf = SimpleDateFormat("dd-MM")
                override fun getFormattedValue(value: Float): String {
                    return sdf.format(Date(value.toLong()))
                }
            }

            textColor = resources.getColor(R.color.white)


            axisLineColor = textColor
            axisLineWidth = 1f
        }

        //chart y axis
        chart.axisLeft.apply {
            setDrawGridLines(false)

            textColor = resources.getColor(R.color.white)

            axisLineColor = textColor
            axisLineWidth = 1f
        }

        //chart
        chart.apply {
            description.isEnabled = false
            legend.isEnabled = false

            setDrawGridBackground(false)

            axisRight.isEnabled = false
        }

        chart.data = LineData(dataSet)
        chart.invalidate()

    }
}