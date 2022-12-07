package com.example.imagecup.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.depromeet.housekeeper.base.BaseFragment
import com.example.imagecup.R
import com.example.imagecup.databinding.FragmentAlbumLabelBinding
import com.example.imagecup.model.Photo
import com.example.imagecup.ui.adapter.AlbumLabelAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import timber.log.Timber
import java.io.File

@AndroidEntryPoint
class AlbumLabelFragment : BaseFragment<FragmentAlbumLabelBinding>(R.layout.fragment_album_label) {
    private lateinit var myAdapter: AlbumLabelAdapter
    private val viewModel: MainViewModel by viewModels()
    private lateinit var label : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getString("label")?.let { viewModel.getAlbumPhotos(it) }
        label = arguments?.getString("label").toString()
    }

    override fun createView(binding: FragmentAlbumLabelBinding) {
        setListener()
        setAdapter()
        bindingVM()
    }

    override fun viewCreated() {
        binding.tvAlbumLabel.text = label
    }

    private fun setListener() {
        binding.ivAlbumUploadIcon.setOnClickListener {
            if (viewModel.uploadPhoto.value != null) {
                val imageFile = imageToFile(viewModel.uploadPhoto.value!!)
                viewModel.uploadPhoto(imageFile,label)
                Toast.makeText(requireContext(), "업로드가 완료되었습니다.", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(requireContext(), "사진을 선택해주세요.", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun setAdapter() {
        val gridLayoutManager = GridLayoutManager(requireContext(), 3)
        binding.rvGallery.layoutManager = gridLayoutManager
        myAdapter = AlbumLabelAdapter(emptyList())
        binding.rvGallery.adapter = myAdapter
    }

    private fun bindingVM() {
        lifecycleScope.launchWhenCreated {
            viewModel.albumPhotos.collectLatest {
                Timber.d("albumphotos : $it")
                if (it.isNotEmpty()) {
                    myAdapter = AlbumLabelAdapter(it)
                    binding.rvGallery.adapter = myAdapter
                    myAdapter.setItemClickListener(object : AlbumLabelAdapter.OnItemClickListener {
                        override fun onClick(v: View, photo: Photo, position: Int) {
                            if (viewModel.uploadPhoto.value == null || viewModel.uploadPhoto.value == photo) {
                                if (v.isSelected) {
                                    viewModel.setUploadPhoto(null)
                                    v.isSelected = !v.isSelected
                                } else {
                                    viewModel.setUploadPhoto(photo)
                                    v.isSelected = !v.isSelected
                                }
                            }
                        }
                    })
                }
            }
        }

    }

    private fun imageToFile(photo: Photo):MultipartBody.Part {
        val file = File(photo.title)
        val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
        val body = MultipartBody.Part.createFormData("file", file.name, requestFile)
        Timber.d("file : $body")
        return body
    }


}