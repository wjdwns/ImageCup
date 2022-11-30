package com.example.imagecup.ui

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.MediaStore
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import com.depromeet.housekeeper.base.BaseFragment
import com.example.imagecup.R
import com.example.imagecup.databinding.FragmentAlbumBinding
import com.example.imagecup.databinding.FragmentAlbumLabelBinding
import com.example.imagecup.ui.adapter.GalleryAdapter
import timber.log.Timber

class AlbumLabelFragment : BaseFragment<FragmentAlbumLabelBinding>(R.layout.fragment_album_label){
    private lateinit var myAdapter : GalleryAdapter
    override fun createView(binding: FragmentAlbumLabelBinding) {

    }

    override fun viewCreated() {
        getGalleryPermission()
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
        myAdapter.setItemClickListener(object : GalleryAdapter.OnItemClickListener{
            override fun onClick(v: View, photo_uri: Uri, position: Int) {
                v.isSelected = !v.isSelected
            }
        })
    }

}