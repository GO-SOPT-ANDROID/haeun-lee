package org.android.go.sopt.data.local

import org.android.go.sopt.R
import org.android.go.sopt.ui.main.home.adapter.model.MultiViewItem
import org.android.go.sopt.ui.main.home.adapter.model.MultiViewItem.*

object DataSources {
    private const val HOME_ITEM_NUM = 20
    private const val GALLERY_ITEM_NUM = 10

    fun loadHomeDataSet(): ArrayList<MultiViewItem> {
        val multiViewItems = arrayListOf<MultiViewItem>().apply {
            add(Header("Repository List"))

            for (i in 1..HOME_ITEM_NUM) {
                add(
                    Repo(
                        R.drawable.profile_image,
                        "name $i",
                        "author $i"
                    )
                )
            }
        }

        return multiViewItems
    }

    fun loadGalleryDataSet(): ArrayList<Repo> {
        val repos = arrayListOf<Repo>().apply {
            for (i in 1..GALLERY_ITEM_NUM) {
                add(Repo(R.drawable.profile_image, "name $i", "author $i"))
            }
        }

        return repos
    }
}