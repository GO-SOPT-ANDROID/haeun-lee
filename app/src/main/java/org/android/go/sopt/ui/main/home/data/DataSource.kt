package org.android.go.sopt.ui.main.home.data

import org.android.go.sopt.ui.main.home.adapter.MultiViewItem
import org.android.go.sopt.R

object DataSource {
    fun loadDataSources(): ArrayList<MultiViewItem> {
        return arrayListOf(
            MultiViewItem.TextItem("하은의 레포지토리"),
            MultiViewItem.ImageItem(R.drawable.ic_launcher_background, "name", "author"),
            MultiViewItem.ImageItem(R.drawable.ic_launcher_background, "name", "author"),
            MultiViewItem.ImageItem(R.drawable.ic_launcher_background, "name", "author"),
            MultiViewItem.ImageItem(R.drawable.ic_launcher_background, "name", "author"),
            MultiViewItem.ImageItem(R.drawable.ic_launcher_background, "name", "author"),
            MultiViewItem.ImageItem(R.drawable.ic_launcher_background, "name", "author"),
            MultiViewItem.ImageItem(R.drawable.ic_launcher_background, "name", "author"),
            MultiViewItem.ImageItem(R.drawable.ic_launcher_background, "name", "author"),
            MultiViewItem.ImageItem(R.drawable.ic_launcher_background, "name", "author"),
            MultiViewItem.ImageItem(R.drawable.ic_launcher_background, "name", "author"),
        )
    }
}