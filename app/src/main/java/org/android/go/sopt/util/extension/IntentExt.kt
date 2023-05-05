package org.android.go.sopt.util.extension

import android.content.Intent
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.TIRAMISU
import java.io.Serializable

/** Retrieve serializable extra data from the intent and support app compatibility */
inline fun <reified T : Serializable> Intent.getCompatibleSerializableExtra(key: String): T? = when {
    SDK_INT >= TIRAMISU -> getSerializableExtra(key, T::class.java)
    else -> getSerializableExtra(key) as? T
}