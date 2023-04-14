package org.android.go.sopt.ui.main.home

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import org.android.go.sopt.binding.BindingFragment
import org.android.go.sopt.databinding.FragmentHomeBinding
import org.android.go.sopt.ui.main.home.adapter.MultiViewAdapter
import org.android.go.sopt.ui.main.home.data.DataSource

class HomeFragment : BindingFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dataSet = DataSource.loadDataSources()
        val recyclerView = binding.rvRepo
        recyclerView.adapter = MultiViewAdapter(dataSet)
        recyclerView.layoutManager = LinearLayoutManager(context)
    }
}