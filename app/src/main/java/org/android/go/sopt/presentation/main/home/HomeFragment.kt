package org.android.go.sopt.presentation.main.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import org.android.go.sopt.R
import org.android.go.sopt.databinding.FragmentHomeBinding
import org.android.go.sopt.data.entity.MultiViewItem
import org.android.go.sopt.data.entity.MultiViewItem.Header
import org.android.go.sopt.presentation.main.home.adapter.MultiViewAdapter
import org.android.go.sopt.util.LoadingDialog
import org.android.go.sopt.util.binding.BindingFragment
import org.android.go.sopt.util.extension.showSnackbar
import org.android.go.sopt.util.state.LocalUiState.Failure
import org.android.go.sopt.util.state.LocalUiState.Success

/** MockRepoList + MultiView Adapter */
class HomeFragment : BindingFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    private val viewModel by viewModels<HomeViewModel>()
    private lateinit var multiViewItems: MutableList<MultiViewItem>
    private lateinit var loadingDialog: LoadingDialog
    private var multiViewAdapter: MultiViewAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = viewModel

        showLoadingDialog()
        initMultiViewItems()
        initRepoListStateObserver()
        initMultiViewAdapter()
    }

    private fun showLoadingDialog() {
        loadingDialog = LoadingDialog(requireContext())
        loadingDialog.show()
    }

    private fun initMultiViewItems() {
        multiViewItems = mutableListOf()
        multiViewItems.add(Header(getString(R.string.header_text)))
    }

    private fun initRepoListStateObserver() {
        viewModel.getRepoListState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is Success -> {
                    addRepoList()
                    if(loadingDialog.isShowing) loadingDialog.dismiss()
                }
                is Failure -> requireContext().showSnackbar(
                    binding.root,
                    getString(R.string.home_get_repo_list_fail_msg)
                )
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