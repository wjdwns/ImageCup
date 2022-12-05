package com.example.imagecup.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.depromeet.housekeeper.base.BaseFragment
import com.example.imagecup.R
import com.example.imagecup.databinding.FragmentResultBinding
import com.example.imagecup.ui.adapter.AlbumAdapter
import com.example.imagecup.ui.adapter.ResultAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber

@AndroidEntryPoint
class ResultFragment : BaseFragment<FragmentResultBinding>(R.layout.fragment_result) {
    private val viewModel : MainViewModel by viewModels()
    private lateinit var myAdapter: ResultAdapter
    val labels = listOf("강아지","고양이","자동차","사람","이동수단","도로","주방도구","가방","도구","식품","운동기구","가구","전자기기","동물","새","곰","기타")
    override fun createView(binding: FragmentResultBinding) {
        initView()
        setAdapter()
        bindingVm()
    }

    override fun viewCreated() {
        viewModel.getAllLabels()
    }
    private fun initView(){

    }

    private fun bindingVm(){
        lifecycleScope.launchWhenCreated {
            viewModel.labels.collectLatest {
                Timber.d("$it")
                myAdapter = ResultAdapter(labels)
                binding.rvRanking.adapter = myAdapter
                myAdapter.notifyDataSetChanged()
                myAdapter.setItemClickListener(object :ResultAdapter.OnItemClickListener{
                    override fun onClick(v: View, label: String, position: Int) {
                        Timber.d("Click $label")
                        var fragment = ResultLabelFragment()
                        var bundle = Bundle()
                        bundle.putString("label", label)
                        fragment.arguments = bundle
                        (activity as MainActivity).replaceFragment(fragment)
                    }
                })
            }
        }
    }

    private fun setAdapter(){
        val gridLayoutManager = GridLayoutManager(requireContext(), 1)
        binding.rvRanking.layoutManager = gridLayoutManager
        myAdapter = ResultAdapter(labels)
        binding.rvRanking.adapter = myAdapter

    }
}