package org.android.go.sopt.presentation.main.gallery

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import org.android.go.sopt.R
import org.android.go.sopt.data.remote.module.ReqResFactory
import org.android.go.sopt.data.remote.entity.response.ResponseGetFollowerListDto
import org.android.go.sopt.util.binding.BindingFragment
import org.android.go.sopt.databinding.FragmentGalleryBinding
import org.android.go.sopt.presentation.main.gallery.adapter.FollowerAdapter
import retrofit2.Call
import retrofit2.Response
import timber.log.Timber

/** ReqRes API Retrofit + ListAdapter */
class GalleryFragment : BindingFragment<FragmentGalleryBinding>(R.layout.fragment_gallery) {
    private var followerAdapter: FollowerAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initFollowerAdapter()
        initRecyclerViewLayoutManager()
        initFollowerList()
    }

    private fun initFollowerAdapter() {
        followerAdapter = FollowerAdapter()
        binding.rvGallery.adapter = followerAdapter
    }

    private fun initRecyclerViewLayoutManager() {
        binding.rvGallery.layoutManager = GridLayoutManager(requireContext(), 2)
    }

    private fun initFollowerList() {
        ReqResFactory.ServicePool.followerService.getFollowerList(PAGE, PER_PAGE)
            .enqueue(object : retrofit2.Callback<ResponseGetFollowerListDto> {
                override fun onResponse(
                    call: Call<ResponseGetFollowerListDto>,
                    response: Response<ResponseGetFollowerListDto>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            followerAdapter?.submitList(it.toFollowerList())
                        }
                    } else {
                        Timber.e(response.code().toString())
                    }
                }

                override fun onFailure(call: Call<ResponseGetFollowerListDto>, t: Throwable) {
                    Timber.e(t)
                }
            })
    }

    fun scrollToTop() {
        binding.rvGallery.scrollToPosition(0)
    }

    // 뷰에 종속되는 어댑터에도 null을 할당하여 메모리 누수 방지
    override fun onDestroyView() {
        super.onDestroyView()
        followerAdapter = null
    }

    companion object {
        private const val PAGE = 1
        private const val PER_PAGE = 10
    }
}
