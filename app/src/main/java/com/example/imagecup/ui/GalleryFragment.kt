package com.example.imagecup.ui

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.provider.MediaStore
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.depromeet.housekeeper.base.BaseFragment
import com.example.imagecup.R
import com.example.imagecup.databinding.FragmentGalleryBinding
import com.example.imagecup.ui.adapter.GalleryAdapter
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber

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

        }
    }
    private fun setAdapter(){
        val gridLayoutManager = GridLayoutManager(requireContext(), 3)
        binding.rvGallery.layoutManager = gridLayoutManager
        myAdapter = GalleryAdapter(emptyList())
        binding.rvGallery.adapter = myAdapter
    }

    private fun getGalleryPermission() {
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
                false
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
    private fun bindingVm(){
        lifecycleScope.launchWhenCreated {
            viewModel.photoUri.collectLatest {
                Timber.d("size : ${it.size}")
                val format = String.format(getString(R.string.selected), it.size)
                binding.tvSelectedPhoto.text = format
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
        val uriArr = ArrayList<String>()
        if(cursor!=null){
            while(cursor.moveToNext()){
                // 사진 경로 Uri 가져오기
                val uri = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA))
                uriArr.add(uri)
            }
            cursor.close()
        }
        myAdapter = GalleryAdapter(uriArr)
        Timber.d("uriArr : $uriArr")
        binding.rvGallery.adapter = myAdapter
        myAdapter.setItemClickListener(object :GalleryAdapter.OnItemClickListener{
            override fun onClick(v: View, photo_uri: String, position: Int) {
                v.isSelected = !v.isSelected
                viewModel.updatePhoto(photo_uri, v.isSelected)
            }
        })
    }
}