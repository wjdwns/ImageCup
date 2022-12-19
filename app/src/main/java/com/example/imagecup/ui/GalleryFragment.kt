package com.example.imagecup.ui

import android.Manifest
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.depromeet.housekeeper.base.BaseFragment
import com.example.imagecup.R
import com.example.imagecup.databinding.FragmentGalleryBinding
import com.example.imagecup.ui.adapter.GalleryAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import timber.log.Timber
import java.io.File

@AndroidEntryPoint
class GalleryFragment : BaseFragment<FragmentGalleryBinding>(R.layout.fragment_gallery) {
    private val viewModel: MainViewModel by viewModels()
    private lateinit var myAdapter: GalleryAdapter

    override fun createView(binding: FragmentGalleryBinding) {
        initView()
        setAdapter()
        setListener()
        bindingVm()
    }

    override fun viewCreated() {
        getGalleryPermission()
    }

    private fun initView() {
    }

    private fun setListener() {
        binding.ivSelectComplete.setOnClickListener {
            if(viewModel.photoUri.value.isNotEmpty())
                imageToFile(viewModel.photoUri.value)
        }
    }
    private fun setAdapter(){
        val gridLayoutManager = GridLayoutManager(requireContext(), 3)
        binding.rvGallery.layoutManager = gridLayoutManager
        myAdapter = GalleryAdapter(emptyList())
        binding.rvGallery.adapter = myAdapter
    }

    private fun getGalleryPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            val isPermission = ActivityCompat.checkSelfPermission(requireContext(),android.Manifest.permission.READ_EXTERNAL_STORAGE)
            if(isPermission != PackageManager.PERMISSION_GRANTED){
                // 권한 허용 안됨
                if(ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), android.Manifest.permission.READ_EXTERNAL_STORAGE)){
                    val permissionAlert = AlertDialog.Builder(requireContext())
                    permissionAlert.setMessage("이미지를 등록하기위해선 저장소 읽기 권한이 필요합니다. 허용하시겠습니까?")
                    permissionAlert.setPositiveButton("yes") { _: DialogInterface?, _: Int ->
                        activity?.requestPermissions(
                            arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                            0x01
                        )
                    }
                    permissionAlert.setNegativeButton("no") { _: DialogInterface?, _: Int -> activity?.finish() }
                    permissionAlert.show()
                } else {
                    ActivityCompat.requestPermissions(requireActivity(), arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                        0x01)
                }
            } else {
                // 권한이 이미 허용됨
                getAllPhotos()
                Timber.d("퍼미션 허용됨")
            }
        }
        else{
            val isPermission = ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_MEDIA_IMAGES)
            if(isPermission != PackageManager.PERMISSION_GRANTED){
                // 권한 허용 안됨
                if(ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), Manifest.permission.READ_MEDIA_IMAGES)){
                    val permissionAlert = AlertDialog.Builder(requireContext())
                    permissionAlert.setMessage("이미지를 등록하기위해선 저장소 읽기 권한이 필요합니다. 허용하시겠습니까?")
                    permissionAlert.setPositiveButton("yes") { _: DialogInterface?, _: Int ->
                        activity?.requestPermissions(
                            arrayOf(Manifest.permission.READ_MEDIA_IMAGES),
                            0x01
                        )
                    }
                    permissionAlert.setNegativeButton("no") { _: DialogInterface?, _: Int -> activity?.finish() }
                    permissionAlert.show()
                } else {
                    ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.READ_MEDIA_IMAGES),
                        0x01)
                }
            } else {
                // 권한이 이미 허용됨
                getAllPhotos()
                Timber.d("퍼미션 허용됨")
            }
        }

    }
    private fun bindingVm(){
        lifecycleScope.launchWhenCreated {
            viewModel.photoUri.collectLatest {
                Timber.d("size : ${it.size}")
                val format = String.format(getString(R.string.selected), it.size)
                binding.tvSelectedPhoto.text = format
            }
        }
        lifecycleScope.launchWhenCreated {
            viewModel.loading.collectLatest {
                if(it){
                    binding.progressBar.visibility = View.VISIBLE
                }
                else{
                    binding.progressBar.visibility = View.INVISIBLE
                }
            }
        }

    }
    private fun getAllPhotos(){
        val cursor = requireContext().contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            null,
            null,
            null,
            MediaStore.Images.ImageColumns.DATE_TAKEN + " DESC")
        val uriArr = ArrayList<Uri>()
        if(cursor!=null){
            while(cursor.moveToNext()){
                // 사진 경로 Uri 가져오기
                val uri = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA))
                uriArr.add(uri.toUri())
            }
            cursor.close()
        }
        myAdapter = GalleryAdapter(uriArr)
        Timber.d("uriArr : $uriArr")
        binding.rvGallery.adapter = myAdapter
        myAdapter.setItemClickListener(object :GalleryAdapter.OnItemClickListener{
            override fun onClick(v: View, photo_uri: Uri, position: Int) {
                if(viewModel.photoUri.value.size<10){
                    v.isSelected = !v.isSelected
                    viewModel.updatePhoto(photo_uri, v.isSelected)
                    Timber.d("photo uri : $photo_uri")
                }
                else if(viewModel.photoUri.value.size==10 && v.isSelected)
                {
                    v.isSelected = !v.isSelected
                    viewModel.updatePhoto(photo_uri, v.isSelected)
                }
            }
        })
    }
    private fun imageToFile(imageList : List<Uri>) {
        var imageFiles : List<MultipartBody.Part> = listOf()
        for(element in imageList){
            val file =  File(element.toString())
            val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
            val body = MultipartBody.Part.createFormData("file", file.name, requestFile)
            imageFiles = imageFiles.plus(body)
        }
        viewModel.objectDetect(imageFiles)
        Toast.makeText(requireContext(),"사진을 판별 중입니다. 잠시만 기다려주세요.",Toast.LENGTH_LONG)
        myAdapter.dataChanged()
        Timber.d("file : $imageFiles")
    }
}