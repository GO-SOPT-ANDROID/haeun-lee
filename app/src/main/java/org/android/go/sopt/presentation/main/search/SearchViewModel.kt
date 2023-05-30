package org.android.go.sopt.presentation.main.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.android.go.sopt.data.api.AuthFactory.ServicePool.imageService
import org.android.go.sopt.util.ContentUriRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class SearchViewModel : ViewModel() {
    private val _image = MutableLiveData<ContentUriRequestBody>()
    val image: LiveData<ContentUriRequestBody>
        get() = _image

    fun setRequestBody(requestBody: ContentUriRequestBody) {
        _image.value = requestBody
    }

    fun uploadImage() {
        if (image.value == null) {
            Timber.e("request body is null!!!")
        } else {
            imageService.postImage(image.value!!.toFormData()).enqueue(
                object : Callback<Unit> {
                    override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                        if (response.isSuccessful) {
                            Timber.d("${response.code()} ${response.message()}")
                        } else {
                            Timber.e("${response.code()} ${response.message()}")
                        }
                    }

                    override fun onFailure(call: Call<Unit>, t: Throwable) {
                        Timber.e("${t.message}")
                    }
                }
            )
        }
    }
}