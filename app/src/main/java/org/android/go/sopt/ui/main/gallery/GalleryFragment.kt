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
import org.android.go.sopt.ui.main.gallery.adapter.model.Repo
import org.android.go.sopt.ui.main.home.adapter.MyItemTouchHelperCallback

class GalleryFragment : BindingFragment<FragmentGalleryBinding>(R.layout.fragment_gallery) {
    private val myListAdapter: MyListAdapter by lazy {
        MyListAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        initFABClickListener()
        applyItemTouchHelper()
    }

    private fun initRecyclerView() {
        binding.rvGallery.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = myListAdapter
        }

        val dataSet = arrayListOf<Repo>().apply {
            for (i in 1..20) {
                add(Repo(R.drawable.ic_launcher_background, "name $i", "author $i"))
            }
        }

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

    private fun applyItemTouchHelper() {
        val recyclerView = binding.rvGallery
        val itemTouchHelper = ItemTouchHelper(MyItemTouchHelperCallback(recyclerView))
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

}
