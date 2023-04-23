package org.android.go.sopt.ui.main.data

import org.android.go.sopt.R
import org.android.go.sopt.ui.main.gallery.adapter.model.Repo
import org.android.go.sopt.ui.main.home.adapter.MultiViewItem

object DataSources {
    private const val HOME_ITEM_NUM = 20
    private const val GALLERY_ITEM_NUM = 10

    fun loadHomeDataSet(): ArrayList<MultiViewItem> {
        val multiViewItems = arrayListOf<MultiViewItem>().apply {
            add(MultiViewItem.Header("하은의 레포지토리"))

            for (i in 1..HOME_ITEM_NUM) {
                add(
                    MultiViewItem.Repo(
                        R.drawable.ic_launcher_background,
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
                add(Repo(R.drawable.ic_launcher_background, "name $i", "author $i"))
            }
        }

        return repos
    }
}