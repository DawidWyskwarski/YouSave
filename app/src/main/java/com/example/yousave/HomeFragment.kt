package com.example.yousave

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HomeFragment(private var data:List<Category>) : Fragment(), CategoryInterface{

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {

        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val chart: RecyclerView = view.findViewById(R.id.chart)

        chart.post {
            val width = chart.width
            chart.adapter = ChartAdapter(data, width)
        }

        chart.layoutManager = object: LinearLayoutManager(requireContext(), HORIZONTAL, false) {
            override fun canScrollVertically() = false
        }

        val categoriesRecycler:RecyclerView = view.findViewById(R.id.categories)
        categoriesRecycler.adapter = CategoriesAdapter(data,this)
        categoriesRecycler.layoutManager = object : LinearLayoutManager(requireContext()) {
            override fun canScrollVertically() = false
        }

    }

    override fun onCategoryClick(position: Int) {
        val category = data[position]

        val intent = Intent(requireContext(), CategoryInfoActivity::class.java )
        intent.putExtra("DATA", category)

        startActivity(intent)
    }

}