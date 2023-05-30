package org.android.go.sopt.util.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load
import coil.transform.CircleCropTransformation
import coil.transform.RoundedCornersTransformation
import org.android.go.sopt.R

object BindingAdapter {
    @JvmStatic
    @BindingAdapter("setRoundedCornersImage")
    fun ImageView.setRoundedCornersImage(imgUrl: String?) {
        load(imgUrl) {
            placeholder(R.drawable.ic_img_loading)
            error(R.drawable.ic_img_loading_error)
            fallback(R.drawable.ic_img_loading_error)
            transformations(RoundedCornersTransformation(30f))
        }
    }

    @JvmStatic
    @BindingAdapter("setCircleImage")
    fun ImageView.setCircleImage(imgUrl: String?) {
        load(imgUrl) {
            placeholder(R.drawable.ic_img_loading)
            error(R.drawable.ic_img_loading_error)
            fallback(R.drawable.ic_img_loading_error)
            transformations(CircleCropTransformation())
        }
    }
}