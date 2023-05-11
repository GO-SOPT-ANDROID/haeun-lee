package org.android.go.sopt.util.binding

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

object BindingAdapter {
    @JvmStatic
    @BindingAdapter("app:imageUrl", "app:placeholder", "app:error")
    fun ImageView.loadImage(
        url: String,
        loadingImg: Drawable,
        errorImg: Drawable
    ) {
        Glide.with(this.context)
            .load(url)
            .placeholder(loadingImg)
            .error(errorImg)
            .apply(RequestOptions().fitCenter())
            .into(this)
    }
}