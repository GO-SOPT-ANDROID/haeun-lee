package org.android.go.sopt.ui.main.home.data

import org.android.go.sopt.ui.main.home.adapter.MultiViewItem
import org.android.go.sopt.R

object DataSource {
    fun loadDataSources(): List<MultiViewItem> {
        val dataSet: MutableList<MultiViewItem> = mutableListOf()
        dataSet.add(MultiViewItem.TextItem("하은의 레포지토리"))
        for (i in 1..10) {
            dataSet.add(
                MultiViewItem.ImageItem(
                    R.drawable.ic_launcher_background,
                    "name $i",
                    "author $i"
                )
            )
        }
        return dataSet
    }
}