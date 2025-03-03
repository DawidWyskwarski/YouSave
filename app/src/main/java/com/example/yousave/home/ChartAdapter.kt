package com.example.yousave.home

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ChartAdapter(private var categories: List<Category>,
                   private var recyclerViewWidth: Int) :
    RecyclerView.Adapter<ChartAdapter.ChartViewHolder>() {

    //sums up all of money spent to later calculate the expense percentage of each category
    private var allMoney = categories.fold(0.0) { sum, category -> sum + category.moneySpent }

    inner class ChartViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChartViewHolder {

        val view = View(parent.context).apply {
            layoutParams = ViewGroup.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT)
        }

        return ChartViewHolder(view)
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    override fun onBindViewHolder(holder: ChartViewHolder, position: Int) {
        holder.itemView.setBackgroundColor(categories[position].color)

        if (allMoney > 0 && recyclerViewWidth > 0) {

            //calculates the View width based its percentage of all expenses
            val newWidth =
                ((categories[position].moneySpent / allMoney) * recyclerViewWidth).toInt()

            holder.itemView.layoutParams = holder.itemView.layoutParams.apply {
                width = newWidth
            }

            holder.itemView.requestLayout()
        }
    }
}