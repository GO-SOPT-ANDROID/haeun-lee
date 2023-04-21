package org.android.go.sopt.ui.main.gallery.adapter

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import org.android.go.sopt.ui.main.gallery.adapter.MyListAdapter

class MyItemTouchHelperCallback(private val recyclerView: RecyclerView) :
    ItemTouchHelper.SimpleCallback(
        ItemTouchHelper.UP or ItemTouchHelper.DOWN,
        ItemTouchHelper.LEFT
    ) {
    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        (recyclerView.adapter as MyListAdapter).moveItem(
            viewHolder.adapterPosition,
            target.adapterPosition
        )
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        (recyclerView.adapter as MyListAdapter).removeItem(viewHolder.layoutPosition)
    }
}