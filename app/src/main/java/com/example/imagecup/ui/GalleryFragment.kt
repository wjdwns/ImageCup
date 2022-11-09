package com.example.imagecup.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.imagecup.R
import com.example.imagecup.databinding.FragmentGalleryBinding
import timber.log.Timber

class GalleryFragment : Fragment() {
    lateinit var binding : FragmentGalleryBinding
    private val viewModel : MainViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_gallery, container, false)
        binding.lifecycleOwner = this.viewLifecycleOwner
        return binding.root
    }
}