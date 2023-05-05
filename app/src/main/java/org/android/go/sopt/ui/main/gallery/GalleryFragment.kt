package org.android.go.sopt.ui.main.gallery

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import org.android.go.sopt.R
import org.android.go.sopt.binding.BindingFragment
import org.android.go.sopt.databinding.FragmentGalleryBinding
import org.android.go.sopt.ui.main.gallery.adapter.MyListAdapter
import org.android.go.sopt.ui.main.gallery.adapter.MyItemTouchHelperCallback
import org.android.go.sopt.data.DataSources

/** DiffUtil + ListAdapter 사용해서 리사이클러뷰 성능 개선하기 */
class GalleryFragment : BindingFragment<FragmentGalleryBinding>(R.layout.fragment_gallery) {
    private val listAdapter by lazy { MyListAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        initFABClickListener()
        initItemTouchHelper()
    }

    private fun initRecyclerView() {
        binding.rvGallery.adapter = listAdapter

        val dataSet = DataSources.loadGalleryDataSet()
        listAdapter.submitList(dataSet)
    }

    private fun initFABClickListener() {
        binding.fabShuffle.setOnClickListener {
            val currentList = listAdapter.currentList
            listAdapter.submitList(currentList.shuffled())
        }
    }

    private fun initItemTouchHelper() {
        val itemTouchHelper = ItemTouchHelper(MyItemTouchHelperCallback(listAdapter))
        itemTouchHelper.attachToRecyclerView(binding.rvGallery)
    }

    fun scrollToTop() {
        binding.rvGallery.scrollToPosition(0)
    }
}
