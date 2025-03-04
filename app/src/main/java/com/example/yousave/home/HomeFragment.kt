package com.example.yousave.home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.yousave.MainActivity
import com.example.yousave.R
import com.example.yousave.home.categoryInfo.CategoryInfoActivity

class HomeFragment(
    private var data:List<Category> = listOf(),
): Fragment(), CategoryInterface {

    private lateinit var chart:RecyclerView
    private lateinit var chartAdapter: ChartAdapter

    private lateinit var categoriesAdapter: CategoriesAdapter

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val incomeT: TextView = view.findViewById(R.id.earned_num)
        val expenseT: TextView = view.findViewById(R.id.spent_num)
        val balanceT: TextView = view.findViewById(R.id.balance_num)

        val categories: RecyclerView = view.findViewById(R.id.categories)

        chart = view.findViewById(R.id.chart)

        (activity as? MainActivity)?.loadHomeData { newData, income, expense ->
            data = newData

            //need its width and need to make sure it got initialized properly to get it
            chart.post {
                chartAdapter = ChartAdapter(data, chart.width)
                chart.adapter = chartAdapter
                chart.layoutManager = object: LinearLayoutManager(requireContext(), HORIZONTAL, false) {
                    override fun canScrollVertically() = false
                }
            }

            categoriesAdapter = CategoriesAdapter(data,this)
            categories.adapter = categoriesAdapter

            categories.layoutManager = object : LinearLayoutManager(requireContext()) {
                override fun canScrollVertically() = false
            }

            incomeT.text = "$income zł"
            expenseT.text = "$expense zł"
            balanceT.text = "${income - expense} zł"
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {

        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onCategoryClick(position: Int) {
        val category = data[position]

        val intent = Intent(requireContext(), CategoryInfoActivity::class.java )
        intent.putExtra("DATA", category)

        startActivity(intent)
    }
}