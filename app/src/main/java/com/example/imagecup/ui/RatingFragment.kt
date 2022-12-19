package com.example.imagecup.ui

import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.depromeet.housekeeper.base.BaseFragment
import com.example.imagecup.R
import com.example.imagecup.databinding.FragmentRatingBinding
import com.example.imagecup.model.GetPhotosResponse
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class RatingFragment : BaseFragment<FragmentRatingBinding>(R.layout.fragment_rating) {
    private val viewModel: MainViewModel by viewModels()
    private var rateScore: Float = 0.0F
    private var pageNum: Int = 0
    private lateinit var label: String
    private var photo: GetPhotosResponse? = null

    override fun createView(binding: FragmentRatingBinding) {
        setListener()
        initView()
        bindingVm()
    }

    private fun bindingVm() {

        lifecycleScope.launchWhenCreated {
            viewModel.photoResponse.collectLatest {
                if (it != null) {
                    photo = it
                    setImageView(it.path)
                    setAverageScore(it.avgScore)
                }
            }
        }
    }

    private fun initView() {
        label = arguments?.getString("label").toString()
        binding.tvLabel.text = label
        lifecycleScope.launchWhenCreated {
            viewModel.pageRandomNum.collectLatest {
                if (it.isNotEmpty()) {
                    if (it.size > pageNum) {
                        viewModel.getPhoto(label, pageNum)
                    } else {
                        Toast.makeText(requireContext(), "더 이상 사진이 없습니다", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    override fun viewCreated() {
        viewModel.initGetPhoto(label)
        pageNum = 0
    }

    private fun setListener() {
        binding.ivImage.scaleType = ImageView.ScaleType.CENTER_CROP
        binding.ivRate1.setOnClickListener {
            binding.ivRate1.setImageResource(R.drawable.ic_fullstar)
            binding.ivRate2.setImageResource(R.drawable.ic_emptystar)
            binding.ivRate3.setImageResource(R.drawable.ic_emptystar)
            binding.ivRate4.setImageResource(R.drawable.ic_emptystar)
            binding.ivRate5.setImageResource(R.drawable.ic_emptystar)
            Toast.makeText(requireContext(), "1점", Toast.LENGTH_LONG).show()
            rateScore = 1.0F
        }
        binding.ivRate2.setOnClickListener {
            binding.ivRate1.setImageResource(R.drawable.ic_fullstar)
            binding.ivRate2.setImageResource(R.drawable.ic_fullstar)
            binding.ivRate3.setImageResource(R.drawable.ic_emptystar)
            binding.ivRate4.setImageResource(R.drawable.ic_emptystar)
            binding.ivRate5.setImageResource(R.drawable.ic_emptystar)
            Toast.makeText(requireContext(), "2점", Toast.LENGTH_SHORT).show()
            rateScore = 2.0F
        }
        binding.ivRate3.setOnClickListener {
            binding.ivRate1.setImageResource(R.drawable.ic_fullstar)
            binding.ivRate2.setImageResource(R.drawable.ic_fullstar)
            binding.ivRate3.setImageResource(R.drawable.ic_fullstar)
            binding.ivRate4.setImageResource(R.drawable.ic_emptystar)
            binding.ivRate5.setImageResource(R.drawable.ic_emptystar)
            Toast.makeText(requireContext(), "3점", Toast.LENGTH_SHORT).show()
            rateScore = 3.0F
        }
        binding.ivRate4.setOnClickListener {
            binding.ivRate1.setImageResource(R.drawable.ic_fullstar)
            binding.ivRate2.setImageResource(R.drawable.ic_fullstar)
            binding.ivRate3.setImageResource(R.drawable.ic_fullstar)
            binding.ivRate4.setImageResource(R.drawable.ic_fullstar)
            binding.ivRate5.setImageResource(R.drawable.ic_emptystar)
            Toast.makeText(requireContext(), "4점", Toast.LENGTH_SHORT).show()
            rateScore = 4.0F
        }
        binding.ivRate5.setOnClickListener {
            binding.ivRate1.setImageResource(R.drawable.ic_fullstar)
            binding.ivRate2.setImageResource(R.drawable.ic_fullstar)
            binding.ivRate3.setImageResource(R.drawable.ic_fullstar)
            binding.ivRate4.setImageResource(R.drawable.ic_fullstar)
            binding.ivRate5.setImageResource(R.drawable.ic_fullstar)
            Toast.makeText(requireContext(), "5점", Toast.LENGTH_SHORT).show()
            rateScore = 5.0F
        }
        binding.tvRate.setOnClickListener {
            binding.ivRate1.setImageResource(R.drawable.ic_emptystar)
            binding.ivRate2.setImageResource(R.drawable.ic_emptystar)
            binding.ivRate3.setImageResource(R.drawable.ic_emptystar)
            binding.ivRate4.setImageResource(R.drawable.ic_emptystar)
            binding.ivRate5.setImageResource(R.drawable.ic_emptystar)
            Toast.makeText(requireContext(), "평가완료", Toast.LENGTH_SHORT).show()
            if (viewModel.pageRandomNum.value.size > pageNum){
                photo?.let { it -> viewModel.ratePhoto(it, rateScore) }
            }
            if (viewModel.pageRandomNum.value.isNotEmpty()) {
                pageNum++
                if (viewModel.pageRandomNum.value.size > pageNum) {
                    viewModel.getPhoto(label, pageNum)
                } else {
                    binding.ivImage.setImageResource(R.drawable.ic_image)
                    binding.ivStar1.setImageResource(R.drawable.ic_emptystar)
                    binding.ivStar2.setImageResource(R.drawable.ic_emptystar)
                    binding.ivStar3.setImageResource(R.drawable.ic_emptystar)
                    binding.ivStar4.setImageResource(R.drawable.ic_emptystar)
                    binding.ivStar5.setImageResource(R.drawable.ic_emptystar)
                    Toast.makeText(requireContext(), "더 이상 사진이 없습니다", Toast.LENGTH_LONG).show()
                }
            }
        }
        binding.tvNotRate.setOnClickListener {
            pageNum++
            if (viewModel.pageRandomNum.value.isNotEmpty()) {
                if (viewModel.pageRandomNum.value.size > pageNum) {
                    viewModel.getPhoto(label, pageNum)
                } else {
                    binding.ivImage.setImageResource(R.drawable.ic_image)
                    binding.ivStar1.setImageResource(R.drawable.ic_emptystar)
                    binding.ivStar2.setImageResource(R.drawable.ic_emptystar)
                    binding.ivStar3.setImageResource(R.drawable.ic_emptystar)
                    binding.ivStar4.setImageResource(R.drawable.ic_emptystar)
                    binding.ivStar5.setImageResource(R.drawable.ic_emptystar)
                    Toast.makeText(requireContext(), "더 이상 사진이 없습니다", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun setImageView(uri: String) {
        Glide.with(binding.root)
            .load(uri)
            .into(binding.ivImage)
    }

    private fun setAverageScore(score: Double) {
        val format = String.format(getString(R.string.iv_rating), score)
        binding.tvRating.text = format
        when (score) {
            in 0.0..0.9 -> {
                binding.ivStar1.setImageResource(R.drawable.ic_emptystar)
                binding.ivStar2.setImageResource(R.drawable.ic_emptystar)
                binding.ivStar3.setImageResource(R.drawable.ic_emptystar)
                binding.ivStar4.setImageResource(R.drawable.ic_emptystar)
                binding.ivStar5.setImageResource(R.drawable.ic_emptystar)
            }
            in 1.0..1.9 -> {
                binding.ivStar1.setImageResource(R.drawable.ic_fullstar)
                binding.ivStar2.setImageResource(R.drawable.ic_emptystar)
                binding.ivStar3.setImageResource(R.drawable.ic_emptystar)
                binding.ivStar4.setImageResource(R.drawable.ic_emptystar)
                binding.ivStar5.setImageResource(R.drawable.ic_emptystar)
            }
            in 2.0..2.9 -> {
                binding.ivStar1.setImageResource(R.drawable.ic_fullstar)
                binding.ivStar2.setImageResource(R.drawable.ic_fullstar)
                binding.ivStar3.setImageResource(R.drawable.ic_emptystar)
                binding.ivStar4.setImageResource(R.drawable.ic_emptystar)
                binding.ivStar5.setImageResource(R.drawable.ic_emptystar)
            }
            in 3.0..3.9 -> {
                binding.ivStar1.setImageResource(R.drawable.ic_fullstar)
                binding.ivStar2.setImageResource(R.drawable.ic_fullstar)
                binding.ivStar3.setImageResource(R.drawable.ic_fullstar)
                binding.ivStar4.setImageResource(R.drawable.ic_emptystar)
                binding.ivStar5.setImageResource(R.drawable.ic_emptystar)
            }
            in 4.0..4.9 -> {
                binding.ivStar1.setImageResource(R.drawable.ic_fullstar)
                binding.ivStar2.setImageResource(R.drawable.ic_fullstar)
                binding.ivStar3.setImageResource(R.drawable.ic_fullstar)
                binding.ivStar4.setImageResource(R.drawable.ic_fullstar)
                binding.ivStar5.setImageResource(R.drawable.ic_emptystar)
            }
            else -> {
                binding.ivStar1.setImageResource(R.drawable.ic_fullstar)
                binding.ivStar2.setImageResource(R.drawable.ic_fullstar)
                binding.ivStar3.setImageResource(R.drawable.ic_fullstar)
                binding.ivStar4.setImageResource(R.drawable.ic_fullstar)
                binding.ivStar5.setImageResource(R.drawable.ic_fullstar)
            }
        }
    }

}