package com.example.imagecup.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.depromeet.housekeeper.base.BaseFragment
import com.example.imagecup.R
import com.example.imagecup.databinding.FragmentRatingBinding
import com.example.imagecup.model.Photo
import com.example.imagecup.ui.adapter.RatingAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import timber.log.Timber
import java.io.File

@AndroidEntryPoint
class RatingFragment : BaseFragment<FragmentRatingBinding>(R.layout.fragment_rating){
    private lateinit var myAdapter: RatingAdapter
    private val viewModel: MainViewModel by viewModels()

    override fun createView(binding: FragmentRatingBinding) {

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun viewCreated() {
        binding.tvLabel.text = arguments?.getString("label").toString()
    }
}