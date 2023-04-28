package org.android.go.sopt.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter

object BindingAdapter {
    @JvmStatic
    @BindingAdapter("setImageBinding")
    fun ImageView.setImageBinding(resId: Int) {
        this.setImageResource(resId)
    }
}