package com.example.imagecup.ui

import android.widget.ImageView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.depromeet.housekeeper.base.BaseFragment
import com.example.imagecup.R
import com.example.imagecup.databinding.FragmentResultLabelBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class ResultLabelFragment : BaseFragment<FragmentResultLabelBinding>(R.layout.fragment_result_label) {
    private val viewModel: MainViewModel by viewModels()
    private lateinit var label :String


    override fun createView(binding: FragmentResultLabelBinding) {
        initView()
        bindingVm()
    }
    private fun initView() {
        label = arguments?.getString("label").toString()
        binding.tvLabel.text = label
    }

    private fun bindingVm(){

        lifecycleScope.launchWhenCreated {
            viewModel.rankResponse.collectLatest {
                if (it != null) {
                    when (it.size) {
                        1 -> {
                            setImageView(it[0].path, binding.ivImage1)
                            val format = String.format(getString(R.string.iv_rating), it[0].avgScore)
                            binding.tvRating1.text = format
                            binding.rate1 = (it[0].avgScore).toInt()

                        }

                        2 -> {
                            setImageView(it[0].path, binding.ivImage1)
                            val format =
                                String.format(getString(R.string.iv_rating), it[0].avgScore)
                            binding.tvRating1.text = format
                            binding.rate1 = (it[0].avgScore).toInt()
                            setImageView(it[1].path, binding.ivImage2)
                            binding.tvRating2.text =
                                String.format(getString(R.string.iv_rating), it[1].avgScore)
                            binding.rate2 = (it[1].avgScore).toInt()
                        }
                        3 -> {
                            setImageView(it[0].path, binding.ivImage1)
                            val format =
                                String.format(getString(R.string.iv_rating), it[0].avgScore)
                            binding.tvRating1.text = format
                            binding.rate1 = (it[0].avgScore).toInt()
                            setImageView(it[1].path, binding.ivImage2)
                            binding.tvRating2.text =
                                String.format(getString(R.string.iv_rating), it[1].avgScore)
                            binding.rate2 = (it[1].avgScore).toInt()
                            setImageView(it[2].path, binding.ivImage3)
                            binding.tvRating3.text =
                                String.format(getString(R.string.iv_rating), it[2].avgScore)
                            binding.rate3 = (it[2].avgScore).toInt()
                        }
                    }


                }
            }
        }
    }

    private fun setImageView(uri : String, view :ImageView){
        Glide.with(binding.root)
            .load(uri)
            .into(view)
    }


    override fun viewCreated() {
        viewModel.getRankPhoto(label)
        }
    }

