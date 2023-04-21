package org.android.go.sopt.ui.main.home.adapter.data

import org.android.go.sopt.ui.main.home.adapter.MultiViewItem
import org.android.go.sopt.R

object DataSource {
    fun loadDataSources(): ArrayList<MultiViewItem> {
        val dataSet = arrayListOf<MultiViewItem>().apply {
            add(MultiViewItem.TextItem("하은의 레포지토리"))
            for (i in 1..20) {
                add(
                    MultiViewItem.ImageItem(
                        R.drawable.ic_launcher_background,
                        "name $i",
                        "author $i"
                    )
                )
            }
        }
        return dataSet
    }
}