package org.android.go.sopt.util

import android.content.Context
import android.database.sqlite.SQLiteBindOrColumnIndexOutOfRangeException
import android.net.Uri
import android.provider.MediaStore.Images.Media.*
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okio.BufferedSink
import okio.source
import timber.log.Timber

class ContentUriRequestBody(
    context: Context,
    private val contentUri: Uri
) : RequestBody() {
    private val contentResolver = context.contentResolver
    private var fileName = ""
    private var size = -1L

    init {
        try {
            contentResolver.query(
                contentUri,
                arrayOf(SIZE, DISPLAY_NAME),
                null,
                null,
                null
            )?.use { cursor ->
                if (cursor.moveToFirst()) {
                    size = cursor.getLong(cursor.getColumnIndexOrThrow(SIZE))
                    fileName = cursor.getString(cursor.getColumnIndexOrThrow(DISPLAY_NAME))
                }
            }
        } catch (e: SQLiteBindOrColumnIndexOutOfRangeException) {
            Timber.e(e.message)
        }
    }

    override fun contentType(): MediaType? =
        contentResolver.getType(contentUri)?.toMediaTypeOrNull()

    override fun writeTo(sink: BufferedSink) {
        try {
            contentResolver.openInputStream(contentUri).use { inputStream ->
                val source = inputStream?.source()
                if (source != null) sink.writeAll(source)
            }
        } catch (e: IllegalStateException) {
            "Couldn't open content URI for reading: $contentUri"
        }
    }

    override fun contentLength(): Long = size

    fun toFormData() = MultipartBody.Part.createFormData("file", fileName, this)
}