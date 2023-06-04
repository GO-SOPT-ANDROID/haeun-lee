package org.android.go.sopt.util

import android.app.Dialog
import android.content.Context
import org.android.go.sopt.R

class LoadingDialog(context: Context): Dialog(context) {
    init {
        setCanceledOnTouchOutside(false)
        setContentView(R.layout.progressbar_loading)
    }
}