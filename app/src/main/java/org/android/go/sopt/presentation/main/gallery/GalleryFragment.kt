package org.android.go.sopt.presentation.main.gallery

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import org.android.go.sopt.R
import org.android.go.sopt.util.binding.BindingFragment
import org.android.go.sopt.databinding.FragmentGalleryBinding
import org.android.go.sopt.presentation.main.gallery.adapter.FollowerAdapter
import org.android.go.sopt.util.extension.showSnackbar
import org.android.go.sopt.util.state.RemoteUiState.*

/** ReqRes API Retrofit + ListAdapter */
class GalleryFragment : BindingFragment<FragmentGalleryBinding>(R.layout.fragment_gallery) {
    private val viewModel by viewModels<GalleryViewModel>()
    private var followerAdapter: FollowerAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.galleryViewModel = viewModel

        initFollowerAdapter()
        initRecyclerViewLayoutManager()
        initFollowerListStateObserver()
    }

    private fun initFollowerAdapter() {
        followerAdapter = FollowerAdapter()
        binding.rvGallery.adapter = followerAdapter
    }

    private fun initRecyclerViewLayoutManager() {
        binding.rvGallery.layoutManager = GridLayoutManager(requireContext(), 2)
    }

    private fun initFollowerListStateObserver() {
        viewModel.getFollowerListState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is Success -> followerAdapter?.submitList(viewModel.followerList)
                is Failure -> requireContext().showSnackbar(
                    binding.root,
                    getString(R.string.gallery_follower_list_null_msg)
                )
                is Error -> requireContext().showSnackbar(
                    binding.root,
                    getString(R.string.network_error_msg)
                )
            }
        }
    }

//    private fun initFollowerList() {
//        ReqResFactory.ServicePool.followerService.getFollowerList(PAGE, PER_PAGE)
//            .enqueue(object : retrofit2.Callback<ResponseGetFollowerListDto> {
//                override fun onResponse(
//                    call: Call<ResponseGetFollowerListDto>,
//                    response: Response<ResponseGetFollowerListDto>
//                ) {
//                    if (response.isSuccessful) {
//                        response.body()?.let {
//                            followerAdapter?.submitList(it.toFollowerList())
//                        }
//                    } else {
//                        Timber.e(response.code().toString())
//                    }
//                }
//
//                override fun onFailure(call: Call<ResponseGetFollowerListDto>, t: Throwable) {
//                    Timber.e(t)
//                }
//            })
//    }

    fun scrollToTop() {
        binding.rvGallery.scrollToPosition(0)
    }

    // 뷰에 종속되는 어댑터에도 null을 할당하여 메모리 누수 방지
    override fun onDestroyView() {
        super.onDestroyView()
        followerAdapter = null
    }
}
