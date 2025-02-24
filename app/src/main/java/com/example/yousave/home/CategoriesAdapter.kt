package com.example.yousave.home

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.yousave.R

class CategoriesAdapter(var categories: List<Category>, private val categoryInterface: CategoryInterface): RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder>() {

    inner class CategoryViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener{

        val color: View = itemView.findViewById(R.id.color)
        val name: TextView = itemView.findViewById(R.id.name)
        val icon: ImageView = itemView.findViewById(R.id.icon)
        val money: TextView = itemView.findViewById(R.id.money_transactions)

        init{
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            val position = adapterPosition

            if ( position != RecyclerView.NO_POSITION ) {
                categoryInterface.onCategoryClick(position)
            }
        }
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
            color.setBackgroundColor(categories[position].color)
            icon.setImageResource(categories[position].image)

            val desc = "${categories[position].moneySpent} zł in ${categories[position].transactions} transactions"

            money.text = desc
        }
    }
}
