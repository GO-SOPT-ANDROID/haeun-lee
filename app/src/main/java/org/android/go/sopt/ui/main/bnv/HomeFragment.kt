package org.android.go.sopt.ui.main.bnv

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import org.android.go.sopt.binding.BindingFragment
import org.android.go.sopt.databinding.FragmentHomeBinding
import org.android.go.sopt.ui.main.adapter.RepoAdapter
import org.android.go.sopt.ui.main.adapter.data.RepoSource

class HomeFragment : BindingFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dataSet = RepoSource.loadRepoSources()
        val recyclerView = binding.rvRepo
        recyclerView.adapter = RepoAdapter(dataSet)
        recyclerView.layoutManager = LinearLayoutManager(context)
    }
}