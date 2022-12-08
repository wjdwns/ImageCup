package com.example.imagecup.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil.setContentView
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
        setListener()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun viewCreated() {
        binding.tvLabel.text = arguments?.getString("label").toString()
    }

    private fun setListener() {
        binding.ivStar6.setOnClickListener {
            binding.ivStar6.setImageResource(R.drawable.ic_fullstar)
            binding.ivStar7.setImageResource(R.drawable.ic_emptystar)
            binding.ivStar8.setImageResource(R.drawable.ic_emptystar)
            binding.ivStar9.setImageResource(R.drawable.ic_emptystar)
            binding.ivStar10.setImageResource(R.drawable.ic_emptystar)
            Toast.makeText(requireContext(), "1점", Toast.LENGTH_LONG).show()
            }
        binding.ivStar7.setOnClickListener {
            binding.ivStar6.setImageResource(R.drawable.ic_fullstar)
            binding.ivStar7.setImageResource(R.drawable.ic_fullstar)
            binding.ivStar8.setImageResource(R.drawable.ic_emptystar)
            binding.ivStar9.setImageResource(R.drawable.ic_emptystar)
            binding.ivStar10.setImageResource(R.drawable.ic_emptystar)
            Toast.makeText(requireContext(), "2점", Toast.LENGTH_SHORT).show()
        }
        binding.ivStar8.setOnClickListener {
            binding.ivStar6.setImageResource(R.drawable.ic_fullstar)
            binding.ivStar7.setImageResource(R.drawable.ic_fullstar)
            binding.ivStar8.setImageResource(R.drawable.ic_fullstar)
            binding.ivStar9.setImageResource(R.drawable.ic_emptystar)
            binding.ivStar10.setImageResource(R.drawable.ic_emptystar)
            Toast.makeText(requireContext(), "3점", Toast.LENGTH_SHORT).show()
        }
        binding.ivStar9.setOnClickListener {
            binding.ivStar6.setImageResource(R.drawable.ic_fullstar)
            binding.ivStar7.setImageResource(R.drawable.ic_fullstar)
            binding.ivStar8.setImageResource(R.drawable.ic_fullstar)
            binding.ivStar9.setImageResource(R.drawable.ic_fullstar)
            binding.ivStar10.setImageResource(R.drawable.ic_emptystar)
            Toast.makeText(requireContext(), "4점", Toast.LENGTH_SHORT).show()
        }
        binding.ivStar10.setOnClickListener {
            binding.ivStar6.setImageResource(R.drawable.ic_fullstar)
            binding.ivStar7.setImageResource(R.drawable.ic_fullstar)
            binding.ivStar8.setImageResource(R.drawable.ic_fullstar)
            binding.ivStar9.setImageResource(R.drawable.ic_fullstar)
            binding.ivStar10.setImageResource(R.drawable.ic_fullstar)
            Toast.makeText(requireContext(), "5점", Toast.LENGTH_SHORT).show()
        }
        binding.tvRate.setOnClickListener {
            binding.ivStar6.setImageResource(R.drawable.ic_emptystar)
            binding.ivStar7.setImageResource(R.drawable.ic_emptystar)
            binding.ivStar8.setImageResource(R.drawable.ic_emptystar)
            binding.ivStar9.setImageResource(R.drawable.ic_emptystar)
            binding.ivStar10.setImageResource(R.drawable.ic_emptystar)
            Toast.makeText(requireContext(), "평가완료", Toast.LENGTH_SHORT).show()

        }
        }
}