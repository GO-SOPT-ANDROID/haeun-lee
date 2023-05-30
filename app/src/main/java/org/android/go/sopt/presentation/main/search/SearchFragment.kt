package org.android.go.sopt.presentation.main.search

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import coil.load
import org.android.go.sopt.R
import org.android.go.sopt.databinding.FragmentSearchBinding
import org.android.go.sopt.util.ContentUriRequestBody
import org.android.go.sopt.util.binding.BindingFragment
import org.android.go.sopt.util.extension.showToast

class SearchFragment : BindingFragment<FragmentSearchBinding>(R.layout.fragment_search) {
    private val viewModel by viewModels<SearchViewModel>()
    private lateinit var singleLauncher: ActivityResultLauncher<String>
    private lateinit var multiLauncher: ActivityResultLauncher<PickVisualMediaRequest>
    private lateinit var uploadLauncher: ActivityResultLauncher<PickVisualMediaRequest>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initPermissionLauncher()
        initImageLauncher()
        initButtonClickListener()
    }

    private fun initPermissionLauncher() {
        val requestPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                requireContext().showToast("퍼미션 허용")
            } else {
                requireContext().showToast("퍼미션 거부")
            }
        }

        requestPermissionLauncher.launch("android.permission.ACCESS_FINE_LOCATION")
    }

    private fun initButtonClickListener() {
        binding.btnSingleImage.setOnClickListener {
            singleLauncher.launch("image/*")
        }

        binding.btnMultiImage.setOnClickListener {
            multiLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo))
        }

        binding.btnUploadImage.setOnClickListener {
            uploadLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo))
        }
    }

    private fun initImageLauncher() {
        singleLauncher =
            registerForActivityResult(ActivityResultContracts.GetContent()) { imgUrl ->
                binding.ivSingleImage.load(imgUrl)
            }

        multiLauncher =
            registerForActivityResult(ActivityResultContracts.PickMultipleVisualMedia(maxItems = 3)) { imgUrlList ->
                with(binding) {
                    when (imgUrlList.size) {
                        0 -> {
                            Toast.makeText(requireContext(), "이미지를 선택하지 않았습니다.", Toast.LENGTH_SHORT)
                                .show()
                        }
                        1 -> {
                            ivMulti1.load(imgUrlList[0])
                        }
                        2 -> {
                            ivMulti1.load(imgUrlList[0])
                            ivMulti2.load(imgUrlList[1])
                        }
                        3 -> {
                            ivMulti1.load(imgUrlList[0])
                            ivMulti2.load(imgUrlList[1])
                            ivMulti3.load(imgUrlList[2])
                        }
                        else -> {
                            Toast.makeText(
                                requireContext(),
                                "3개까지의 이미지만 선택해주세요.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }

        uploadLauncher =
            registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { imgUrl ->
                viewModel.setRequestBody(ContentUriRequestBody(requireContext(), imgUrl!!))
                viewModel.uploadImage()
            }
    }
}