package org.android.go.sopt.presentation.main.search

import android.os.Bundle
import android.view.View
import org.android.go.sopt.R
import org.android.go.sopt.util.binding.BindingFragment
import org.android.go.sopt.databinding.FragmentSearchBinding
import org.android.go.sopt.presentation.main.search.adapter.SearchAdapter

class SearchFragment : BindingFragment<FragmentSearchBinding>(R.layout.fragment_search) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vpSearch.adapter = SearchAdapter(
            listOf(
                R.drawable.profile_image,
                R.drawable.profile_image,
                R.drawable.profile_image,
                R.drawable.profile_image,
            )
        )
    }
}