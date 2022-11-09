package com.example.imagecup.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.depromeet.housekeeper.base.BaseFragment
import com.example.imagecup.R
import com.example.imagecup.databinding.FragmentAlbumBinding
import com.example.imagecup.databinding.FragmentGalleryBinding

class AlbumFragment : BaseFragment<FragmentAlbumBinding>(R.layout.fragment_album) {
    private val viewModel : MainViewModel by viewModels()
    override fun createView(binding: FragmentAlbumBinding) {
        initView()
    }

    override fun viewCreated() {
    }

    private fun initView(){
    }
}