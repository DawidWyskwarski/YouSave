package com.example.yousave.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.yousave.MainActivity
import com.example.yousave.R
import com.example.yousave.history.pastMonths.PastMonthsAdapter
import com.github.mikephil.charting.charts.CombinedChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.CombinedData

class HistoryFragment : Fragment() {

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {

        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val pastList: RecyclerView = view.findViewById(R.id.past_list)
        val chart: CombinedChart = view.findViewById(R.id.history_chart)

        (activity as? MainActivity)?.loadHistoryData { list ->
            pastList.adapter = PastMonthsAdapter(requireContext() ,list)

            pastList.layoutManager = object: LinearLayoutManager(requireContext()){
                override fun canScrollVertically() = false
            }

            // TODO change the chart style
            val incomeDate = getBarDate(list.map { (_,income,_) -> income }, resources.getColor(R.color.income_green))
            val expenseDate = getBarDate(list.map { (_,_,expense) -> expense }, resources.getColor(R.color.expense_red))

            val combinedData = CombinedData()
            combinedData.setData( BarData(incomeDate,expenseDate) )


            chart.data = combinedData
            chart.invalidate()
        }
    }

    private fun getBarDate(values: List<Double>, color:Int): BarDataSet{
        val entries = values.mapIndexed { index, value ->
            BarEntry( index.toFloat(), value.toFloat() )
        }

        return BarDataSet(entries, "").apply {
            this.color = color
            valueTextColor = resources.getColor(R.color.white)
            valueTextSize = 10f
        }
    }
}