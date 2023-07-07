package org.android.go.sopt.presentation.main.search

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import org.android.go.sopt.R
import org.android.go.sopt.databinding.FragmentSearchBinding
import org.android.go.sopt.util.binding.BindingFragment

class SearchFragment : BindingFragment<FragmentSearchBinding>(R.layout.fragment_search) {
    private val viewModel by viewModels<SearchViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}