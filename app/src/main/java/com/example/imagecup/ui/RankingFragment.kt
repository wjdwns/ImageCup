package com.example.imagecup.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.depromeet.housekeeper.base.BaseFragment
import com.example.imagecup.R
import com.example.imagecup.databinding.FragmentAlbumBinding
import com.example.imagecup.databinding.FragmentGalleryBinding
import com.example.imagecup.databinding.FragmentRankingBinding
import com.example.imagecup.ui.adapter.AlbumAdapter
import com.example.imagecup.ui.adapter.RankAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber

@AndroidEntryPoint
class RankingFragment : BaseFragment<FragmentRankingBinding>(R.layout.fragment_ranking) {
    private val viewModel : MainViewModel by viewModels()
    private lateinit var myAdapter: RankAdapter
    private val labels = listOf("강아지","고양이","자동차","사람","이동수단","도로","주방도구","가방","도구","식품","운동기구","가구","전자기기","동물","새","곰","기타")
    override fun createView(binding: FragmentRankingBinding) {
        initView()
        setAdapter()
        bindingVm()
    }

    private fun bindingVm(){
        lifecycleScope.launchWhenCreated {
            viewModel.labels.collectLatest {
                Timber.d("$it")
                myAdapter = RankAdapter(labels)
                binding.rvRanking.adapter = myAdapter
                myAdapter.notifyDataSetChanged()
                myAdapter.setItemClickListener(object :RankAdapter.OnItemClickListener{
                    override fun onClick(v: View, label: String, position: Int) {
                        Timber.d("Click $label")
                        var fragment = RatingFragment()
                        var bundle = Bundle()
                        bundle.putString("label", label)
                        fragment.arguments = bundle
                        (activity as MainActivity).replaceFragment(fragment)
                    }
                })
            }
        }
    }

    private fun initView(){

    }

    private fun setAdapter(){
        val gridLayoutManager = GridLayoutManager(requireContext(), 1)
        binding.rvRanking.layoutManager = gridLayoutManager
        myAdapter = RankAdapter(labels)
        binding.rvRanking.adapter = myAdapter

    }

    override fun viewCreated() {
        viewModel.getAllLabels()
    }

}