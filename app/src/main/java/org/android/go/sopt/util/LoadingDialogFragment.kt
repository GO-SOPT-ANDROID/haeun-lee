package org.android.go.sopt.util

import android.os.Bundle
import android.view.View
import org.android.go.sopt.R
import org.android.go.sopt.databinding.FragmentLoadingDialogBinding
import org.android.go.sopt.util.binding.BindingDialogFragment

class LoadingDialogFragment :
    BindingDialogFragment<FragmentLoadingDialogBinding>(R.layout.fragment_loading_dialog) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialog?.setCanceledOnTouchOutside(false)
    }

    override fun dismiss() {
        if(isAdded) super.dismiss()
    }

    companion object {
        const val TAG_LOADING_DIALOG = "LOADING_DIALOG"
    }
}