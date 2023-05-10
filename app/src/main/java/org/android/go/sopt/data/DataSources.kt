package org.android.go.sopt.data

import android.content.Context
import org.android.go.sopt.R
import org.android.go.sopt.ui.main.home.adapter.model.MultiViewItem
import org.android.go.sopt.ui.main.home.adapter.model.MultiViewItem.*

object DataSources {
    private const val HOME_ITEM_NUM = 20
    private const val GALLERY_ITEM_NUM = 10

    fun loadHomeDataSet(context: Context): ArrayList<MultiViewItem> {
        val multiViewItems = arrayListOf<MultiViewItem>().apply {
            add(Header(context.getString(R.string.header_text)))

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