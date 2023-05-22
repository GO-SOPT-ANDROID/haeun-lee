package org.android.go.sopt.presentation.main.home

import android.os.Bundle
import android.view.View
import org.android.go.sopt.R
import org.android.go.sopt.domain.repository.RepoRepository
import org.android.go.sopt.util.binding.BindingFragment
import org.android.go.sopt.databinding.FragmentHomeBinding
import org.android.go.sopt.domain.model.MultiViewItem
import org.android.go.sopt.presentation.main.home.adapter.MultiViewAdapter
import org.android.go.sopt.domain.model.MultiViewItem.*
import timber.log.Timber

/** MockRepoList + MultiView Adapter */
class HomeFragment : BindingFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView(getMultiViewItems())
    }

    private fun getMultiViewItems(): MutableList<MultiViewItem> {
        val multiViewItems = mutableListOf<MultiViewItem>()
        multiViewItems.add(Header(getString(R.string.header_text)))

        // HomeViewModel에서 RepoRepository 사용하려고 했는데,,
        // assetLoader에 context를 넘겨줘야 하기 때문에 프래그먼트에서 사용함.
        // 뷰모델에 context를 넘겨주면 메모리 누수가 발생할 수 있음.
        RepoRepository(requireContext()).getRepoList()
            .onSuccess { repoList ->
                multiViewItems.addAll(repoList)
            }
            .onFailure { t ->
                Timber.e(t)
            }

        return multiViewItems
    }

    private fun initRecyclerView(multiViewItems: MutableList<MultiViewItem>) {
        binding.rvHome.apply {
            adapter = MultiViewAdapter(multiViewItems)
            setHasFixedSize(true)
        }
    }

    fun scrollToTop() {
        binding.rvHome.scrollToPosition(0)
    }
}