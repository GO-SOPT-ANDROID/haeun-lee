package org.android.go.sopt.ui.main.home

import android.os.Bundle
import android.view.View
import org.android.go.sopt.R
import org.android.go.sopt.data.local.source.RepoDataSource
import org.android.go.sopt.util.binding.BindingFragment
import org.android.go.sopt.databinding.FragmentHomeBinding
import org.android.go.sopt.domain.model.MultiViewItem
import org.android.go.sopt.ui.main.home.adapter.MultiViewAdapter
import org.android.go.sopt.domain.model.MultiViewItem.*
import timber.log.Timber

/** MultiView Adapter + ListAdapter 적용 */
class HomeFragment : BindingFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView(getMultiViewItems())
    }

    private fun getMultiViewItems(): MutableList<MultiViewItem> {
        val multiViewItems = mutableListOf<MultiViewItem>()
        multiViewItems.add(Header(getString(R.string.header_text)))

        RepoDataSource(requireContext()).getRepoList()
            .onSuccess { repoList ->
                multiViewItems.addAll(repoList)
                Timber.d("GET REPO LIST SUCCESS : $repoList")
            }
            .onFailure { t ->
                Timber.e("GET REPO LIST FAIL : ${t.message}")
            }

        return multiViewItems
    }

    private fun initRecyclerView(dataSet: MutableList<MultiViewItem>) {
        binding.rvHome.apply {
            adapter = MultiViewAdapter(dataSet)
            setHasFixedSize(true)
        }
    }

    fun scrollToTop() {
        binding.rvHome.scrollToPosition(0)
    }
}