package org.android.go.sopt.ui.main.home

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import org.android.go.sopt.R
import org.android.go.sopt.binding.BindingFragment
import org.android.go.sopt.databinding.FragmentHomeBinding
import org.android.go.sopt.ui.main.data.DataSources
import org.android.go.sopt.ui.main.home.adapter.MultiViewAdapter
import org.android.go.sopt.ui.main.home.adapter.MultiViewItem

/** 멀티 뷰 타입의 리사이클러뷰 적용 (텍스트 타입, 이미지 타입) */
class HomeFragment : BindingFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dataSet = DataSources.loadHomeDataSet()
        initRecyclerView(dataSet)
    }

    private fun initRecyclerView(dataSet: ArrayList<MultiViewItem>) {
        binding.rvHome.apply {
            adapter = MultiViewAdapter(dataSet)
            setHasFixedSize(true)
        }
    }
}