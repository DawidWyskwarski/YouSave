package com.example.yousave.history.pastMonths

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.yousave.R
import com.example.yousave.databaseClasses.IncomeExpense
import kotlinx.coroutines.withContext

class PastMonthsAdapter(private val context: Context, private var pastInfo:List<IncomeExpense>):RecyclerView.Adapter<PastMonthsAdapter.PastMonthViewHolder>() {

    inner class PastMonthViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val timePeriod : TextView = itemView.findViewById(R.id.month)
        val income : TextView = itemView.findViewById(R.id.month_income)
        val expense : TextView = itemView.findViewById(R.id.month_expense)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PastMonthViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.history_months_layout, parent, false)

        return PastMonthViewHolder(view)
    }

    override fun getItemCount(): Int {
        return pastInfo.size
    }

    override fun onBindViewHolder(holder: PastMonthViewHolder, position: Int) {
        holder.apply {
            timePeriod.text = formatDate( pastInfo[position].formatted_date )
            income.text = pastInfo[position].income.toString()
            expense.text = pastInfo[position].expense.toString()
        }
    }

    // changes the numerical month representation to the short abbreviation
    // 01 -> Jan, 12 -> Dec
    private fun formatDate(date: String):String{

        return context.resources.getStringArray(R.array.months)[date.substring(0,2).toInt() - 1] + date.substring(2)
    }
}