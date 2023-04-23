package org.android.go.sopt.ui.main.gallery

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import org.android.go.sopt.R
import org.android.go.sopt.binding.BindingFragment
import org.android.go.sopt.databinding.FragmentGalleryBinding
import org.android.go.sopt.ui.main.gallery.adapter.MyListAdapter
import org.android.go.sopt.ui.main.gallery.adapter.MyItemTouchHelperCallback
import org.android.go.sopt.ui.main.data.DataSources

/** DiffUtil + ListAdapter 사용해서 리사이클러뷰 성능 개선하기 */
class GalleryFragment : BindingFragment<FragmentGalleryBinding>(R.layout.fragment_gallery) {
    private val myListAdapter: MyListAdapter by lazy {
        MyListAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        initFABClickListener()
        initItemTouchHelper()
    }

    private fun initRecyclerView() {
        binding.rvGallery.apply {
            adapter = myListAdapter
            setHasFixedSize(true)
        }

        val dataSet = DataSources.loadGalleryDataSet()
        myListAdapter.submitList(dataSet)
        Log.d("ListAdapter", "submitList called...")
    }

    private fun initFABClickListener() {
        binding.fabShuffle.setOnClickListener {
            val currentList = myListAdapter.currentList
            myListAdapter.submitList(currentList.shuffled())
            Log.d("ListAdapter", "submitList called...")
        }
    }

    private fun initItemTouchHelper() {
        val recyclerView = binding.rvGallery
        val itemTouchHelper = ItemTouchHelper(MyItemTouchHelperCallback(recyclerView))
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

}
