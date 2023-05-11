package org.android.go.sopt.ui.main.search

import android.os.Bundle
import android.view.View
import org.android.go.sopt.R
import org.android.go.sopt.util.binding.BindingFragment
import org.android.go.sopt.databinding.FragmentSearchBinding
import org.android.go.sopt.ui.main.search.adapter.SearchAdapter

/** Week2: 리사이클러뷰 Selection 라이브러리 사용해보기
 *  Week4: 뷰페이저 적용하기
 * */
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