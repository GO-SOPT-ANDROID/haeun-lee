package org.android.go.sopt.presentation.main.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import org.android.go.sopt.R
import org.android.go.sopt.databinding.FragmentHomeBinding
import org.android.go.sopt.data.entity.MultiViewItem
import org.android.go.sopt.data.entity.MultiViewItem.Header
import org.android.go.sopt.presentation.main.home.adapter.MultiViewAdapter
import org.android.go.sopt.util.LoadingDialogFragment
import org.android.go.sopt.util.LoadingDialogFragment.Companion.TAG_LOADING_DIALOG
import org.android.go.sopt.util.binding.BindingFragment
import org.android.go.sopt.util.extension.showSnackbar
import org.android.go.sopt.util.state.LocalUiState.*

/** MockRepoList + MultiView Adapter */
class HomeFragment : BindingFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    private val viewModel by viewModels<HomeViewModel>()
    private lateinit var multiViewItems: MutableList<MultiViewItem>
    private var multiViewAdapter: MultiViewAdapter? = null
    private val loadingDialog by lazy { LoadingDialogFragment() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = viewModel

        initMultiViewItems()
        initRepoListStateObserver()
        initMultiViewAdapter()
    }

    private fun initMultiViewItems() {
        multiViewItems = mutableListOf()
        multiViewItems.add(Header(getString(R.string.home_repo_header_text)))
    }

    private fun initRepoListStateObserver() {
        viewModel.getRepoListState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is Loading -> loadingDialog.show(parentFragmentManager, TAG_LOADING_DIALOG)
                is Success -> {
                    loadingDialog.dismiss()
                    addRepoList()
                }
                is Failure -> {
                    loadingDialog.dismiss()
                    requireContext().showSnackbar(
                        binding.root,
                        getString(R.string.home_empty_repo_list_error_msg)
                    )
                }
            }
        }
    }

    private fun addRepoList() {
        viewModel.repoList?.let { repoList ->
            for (element in repoList) {
                multiViewItems.add(element)
            }
        }
    }

    private fun initMultiViewAdapter() {
        multiViewAdapter = MultiViewAdapter(multiViewItems)
        binding.rvHome.adapter = multiViewAdapter
    }

    fun scrollToTop() {
        binding.rvHome.scrollToPosition(0)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        multiViewAdapter = null
    }
}