package com.example.imagecup.ui

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.depromeet.housekeeper.base.BaseFragment
import com.example.imagecup.R
import com.example.imagecup.databinding.FragmentResultBinding
import com.example.imagecup.ui.adapter.AlbumAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber

@AndroidEntryPoint
class ResultFragment : BaseFragment<FragmentResultBinding>(R.layout.fragment_result) {
    private val viewModel : MainViewModel by viewModels()
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

    }

    private fun setAdapter(){
        val gridLayoutManager = GridLayoutManager(requireContext(), 2)

    }
}