package org.android.go.sopt.ui.main.gallery

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import org.android.go.sopt.R
import org.android.go.sopt.data.remote.ReqResApiFactory
import org.android.go.sopt.data.remote.model.ResFollowerDto
import org.android.go.sopt.util.binding.BindingFragment
import org.android.go.sopt.databinding.FragmentGalleryBinding
import org.android.go.sopt.ui.main.gallery.adapter.FollowerAdapter
import retrofit2.Call
import retrofit2.Response
import timber.log.Timber

/** ReqRes API Retrofit + ListAdapter */
class GalleryFragment : BindingFragment<FragmentGalleryBinding>(R.layout.fragment_gallery) {
    private val followerAdapter by lazy { FollowerAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.rvGallery.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = followerAdapter
        }

        ReqResApiFactory.ServicePool.followerService.getFollowerList(PAGE, PER_PAGE)
            .enqueue(object : retrofit2.Callback<ResFollowerDto> {
                override fun onResponse(
                    call: Call<ResFollowerDto>,
                    response: Response<ResFollowerDto>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            followerAdapter.submitList(it.followers)
                        }
                    } else {
                        Timber.e(response.code().toString())
                    }
                }

                override fun onFailure(call: Call<ResFollowerDto>, t: Throwable) {
                    Timber.e(t)
                }
            })
    }

    fun scrollToTop() {
        binding.rvGallery.scrollToPosition(0)
    }

    companion object {
        private const val PAGE = 1
        private const val PER_PAGE = 10
    }
}
