package com.example.yousave.home.categoryInfo

import android.annotation.SuppressLint
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.yousave.R
import com.example.yousave.databaseClasses.AppDatabase
import com.example.yousave.databaseClasses.Transaction
import com.example.yousave.formatMoney
import com.example.yousave.home.Category
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import kotlinx.coroutines.launch
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import java.text.SimpleDateFormat
import java.util.Date

class CategoryInfoActivity : AppCompatActivity() {

    private lateinit var category: Category

    private lateinit var transactions: List<Transaction>

    private lateinit var info: RecyclerView

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

        val db = AppDatabase.getDatabase(this)
        val dao = db.transactionDao()

        category = intent.getParcelableExtra("DATA")
            ?: throw IllegalStateException("Category data missing!")

        initializeViews()

        lifecycleScope.launch {

            transactions = dao.getCategoryTransactions(category.name, category.transactions)
            info.adapter = InfoAdapter(transactions)

            setUpChart()
        }
    }

    private fun initializeViews() {

        findViewById<TextView>(R.id.name).apply {
            text = category.name
            setTextColor(category.color)
        }

        findViewById<ImageView>(R.id.icon).apply { setImageResource(category.image) }

        findViewById<TextView>(R.id.money).apply {
            val tmp = "${formatMoney(category.moneySpent)} in ${category.transactions} transactions"
            text = tmp
            setTextColor(category.color)
        }

        findViewById<View>(R.id.color_bar).apply {
            background.setColorFilter(category.color, PorterDuff.Mode.SRC_IN)
        }

        info = findViewById(R.id.transaction_info)
        info.layoutManager = object : LinearLayoutManager(this) {
            override fun canScrollVertically() = true
        }
    }

    private fun getChartEntries(): List<Entry> {
        val dateMoneyPairs = transactions.map { transaction ->
            (transaction.date to transaction.money)
        }

        val dataPoints = dateMoneyPairs.groupBy({ it.first }, { it.second })
            .mapValues { (_, values) -> values.sum() }

        val chartEntries = dataPoints.map { (date, money) ->
            Entry(date.time.toFloat(), money.toFloat())
        }.reversed()

        return chartEntries
    }

    private fun setUpChart() {

        val chart: LineChart = findViewById(R.id.chart)

        val entries = getChartEntries()

        //data set
        val dataSet = LineDataSet(entries, "").apply {
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

            setLabelCount(entries.size, true)

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