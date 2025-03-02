package com.example.yousave.home.categoryInfo

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.yousave.R
import com.example.yousave.databaseClasses.Transaction
import java.text.SimpleDateFormat

class InfoAdapter(var transactions: List<Transaction>): RecyclerView.Adapter<InfoAdapter.InfoViewHolder>() {

    inner class InfoViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val date:TextView = itemView.findViewById(R.id.date)
        val price:TextView = itemView.findViewById(R.id.price)
        val desc:TextView = itemView.findViewById(R.id.desc)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InfoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.info_adapter_layout,parent,false)

        return InfoViewHolder(view)
    }

    override fun getItemCount(): Int {
        return transactions.size
    }

    @SuppressLint("SimpleDateFormat")
    override fun onBindViewHolder(holder: InfoViewHolder, position: Int) {
        holder.apply {
            val data = transactions[position].date
            val dateFormat = SimpleDateFormat("dd/MM/yyyy")

            date.text = dateFormat.format(data)

            val pri = "${transactions[position].money} zł"

            price.text = pri

            val titleDesc = transactions[position].title + " / " + transactions[position].description

            desc.text = titleDesc
        }
    }
}