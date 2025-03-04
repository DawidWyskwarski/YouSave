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

class HistoryFragment : Fragment() {

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {

        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val pastList: RecyclerView = view.findViewById(R.id.past_list)

        (activity as? MainActivity)?.loadHistoryData { list ->
            pastList.adapter = PastMonthsAdapter(list)
            pastList.layoutManager = object: LinearLayoutManager(requireContext()){
                override fun canScrollVertically() = false
            }
        }
    }
}