package com.example.imagecup.ui

import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.depromeet.housekeeper.base.BaseFragment
import com.example.imagecup.R
import com.example.imagecup.databinding.FragmentRatingBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class RatingFragment : BaseFragment<FragmentRatingBinding>(R.layout.fragment_rating){
    private val viewModel: MainViewModel by viewModels()
    private var ratescore: Int = 0
    private var pageNum : Int = 0
    private lateinit var label :String

    override fun createView(binding: FragmentRatingBinding) {
        setListener()
        initView()
        bindingVm()
    }
    private fun bindingVm(){

        lifecycleScope.launchWhenCreated {
            viewModel.photoResponse.collectLatest {
                if (it != null) {
                    setImageView(it.path)
                }
            }
        }
    }

    private fun initView(){
        label = arguments?.getString("label").toString()
        binding.tvLabel.text = label
        lifecycleScope.launchWhenCreated {
            viewModel.pageRandomNum.collectLatest {
                if(it.isNotEmpty()){
                    viewModel.getPhoto(label,it[0])
                    pageNum++
                }
            }
        }
    }

    override fun viewCreated() {
        viewModel.initGetPhoto(label)

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
            ratescore = 1
            }
        binding.ivRate2.setOnClickListener {
            binding.ivRate1.setImageResource(R.drawable.ic_fullstar)
            binding.ivRate2.setImageResource(R.drawable.ic_fullstar)
            binding.ivRate3.setImageResource(R.drawable.ic_emptystar)
            binding.ivRate4.setImageResource(R.drawable.ic_emptystar)
            binding.ivRate5.setImageResource(R.drawable.ic_emptystar)
            Toast.makeText(requireContext(), "2점", Toast.LENGTH_SHORT).show()
            ratescore = 2
        }
        binding.ivRate3.setOnClickListener {
            binding.ivRate1.setImageResource(R.drawable.ic_fullstar)
            binding.ivRate2.setImageResource(R.drawable.ic_fullstar)
            binding.ivRate3.setImageResource(R.drawable.ic_fullstar)
            binding.ivRate4.setImageResource(R.drawable.ic_emptystar)
            binding.ivRate5.setImageResource(R.drawable.ic_emptystar)
            Toast.makeText(requireContext(), "3점", Toast.LENGTH_SHORT).show()
            ratescore = 3
        }
        binding.ivRate4.setOnClickListener {
            binding.ivRate1.setImageResource(R.drawable.ic_fullstar)
            binding.ivRate2.setImageResource(R.drawable.ic_fullstar)
            binding.ivRate3.setImageResource(R.drawable.ic_fullstar)
            binding.ivRate4.setImageResource(R.drawable.ic_fullstar)
            binding.ivRate5.setImageResource(R.drawable.ic_emptystar)
            Toast.makeText(requireContext(), "4점", Toast.LENGTH_SHORT).show()
            ratescore = 4
        }
        binding.ivRate5.setOnClickListener {
            binding.ivRate1.setImageResource(R.drawable.ic_fullstar)
            binding.ivRate2.setImageResource(R.drawable.ic_fullstar)
            binding.ivRate3.setImageResource(R.drawable.ic_fullstar)
            binding.ivRate4.setImageResource(R.drawable.ic_fullstar)
            binding.ivRate5.setImageResource(R.drawable.ic_fullstar)
            Toast.makeText(requireContext(), "5점", Toast.LENGTH_SHORT).show()
            ratescore = 5
        }
        binding.tvRate.setOnClickListener {
            binding.ivRate1.setImageResource(R.drawable.ic_emptystar)
            binding.ivRate2.setImageResource(R.drawable.ic_emptystar)
            binding.ivRate3.setImageResource(R.drawable.ic_emptystar)
            binding.ivRate4.setImageResource(R.drawable.ic_emptystar)
            binding.ivRate5.setImageResource(R.drawable.ic_emptystar)
            Toast.makeText(requireContext(), "평가완료", Toast.LENGTH_SHORT).show()

        }
        }
    private fun setImageView(uri : String){
        Glide.with(binding.root)
            .load(uri)
            .into(binding.ivImage)
    }

}