package com.example.yousave

import android.graphics.Color
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView

class CategoriesAdapter(var categories: List<Category>): RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder>() {

    inner class CategoryViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val color: View = itemView.findViewById(R.id.color)
        val name: TextView = itemView.findViewById(R.id.name)
        val icon: ImageView = itemView.findViewById(R.id.icon)
        val money: TextView = itemView.findViewById(R.id.money_transactions)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.categories_layout,parent,false)

        return CategoryViewHolder(view)
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.apply {
            name.text = categories[position].name
            color.setBackgroundColor(categories[position].color.toArgb())
            icon.setImageResource(categories[position].image)

            val desc:String = "${categories[position].moneySpent} zł in ${categories[position].transactions} transactions"

            money.text = desc
        }

    }
}
