package org.android.go.sopt.presentation.main.gallery

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import org.android.go.sopt.R
import org.android.go.sopt.util.binding.BindingFragment
import org.android.go.sopt.databinding.FragmentGalleryBinding
import org.android.go.sopt.presentation.main.gallery.adapter.FollowerAdapter
import org.android.go.sopt.util.LoadingDialogFragment
import org.android.go.sopt.util.LoadingDialogFragment.Companion.TAG_LOADING_DIALOG
import org.android.go.sopt.util.extension.showSnackbar
import org.android.go.sopt.util.state.RemoteUiState.*

/** ReqRes API Retrofit + ListAdapter */
class GalleryFragment : BindingFragment<FragmentGalleryBinding>(R.layout.fragment_gallery) {
    private val viewModel by viewModels<GalleryViewModel>()

    private var _followerAdapter: FollowerAdapter? = null
    private val followerAdapter
        get() = requireNotNull(_followerAdapter) { getString(R.string.adapter_not_initialized_error_msg) }

    private var _loadingDialog: LoadingDialogFragment? = null
    private val loadingDialog
        get() = requireNotNull(_loadingDialog) { getString(R.string.loading_dialog_not_initialized_error_msg) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = viewModel

        initFollowerAdapter()
        initLoadingDialogFragment()
        initRecyclerViewLayoutManager()
        initFollowerListStateObserver()
    }

    private fun initFollowerAdapter() {
        _followerAdapter = FollowerAdapter()
        binding.rvGallery.adapter = followerAdapter
    }

    private fun initLoadingDialogFragment() {
        _loadingDialog = LoadingDialogFragment()
    }

    private fun initRecyclerViewLayoutManager() {
        binding.rvGallery.layoutManager = GridLayoutManager(requireContext(), 2)
    }

    private fun initFollowerListStateObserver() {
        viewModel.getFollowerListState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is Loading -> loadingDialog.show(parentFragmentManager, TAG_LOADING_DIALOG)
                is Success -> {
                    loadingDialog.dismiss()
                    followerAdapter.submitList(viewModel.followerList)
                }
                is Failure -> {
                    loadingDialog.dismiss()
                    requireContext().showSnackbar(
                        binding.root,
                        getString(R.string.gallery_empty_follower_list_error_msg)
                    )
                }
                is Error -> {
                    loadingDialog.dismiss()
                    requireContext().showSnackbar(
                        binding.root,
                        getString(R.string.network_error_msg)
                    )
                }
            }
        }
    }

    fun scrollToTop() {
        binding.rvGallery.scrollToPosition(0)
    }

    // 뷰에 종속되는 요소들에 명시적으로 null을 할당하여 메모리 누수 방지
    override fun onDestroyView() {
        super.onDestroyView()
        _followerAdapter = null
        _loadingDialog = null
    }
}
