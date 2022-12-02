package com.example.imagecup.ui

import android.view.View
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.depromeet.housekeeper.base.BaseFragment
import com.example.imagecup.R
import com.example.imagecup.databinding.FragmentAlbumBinding
import com.example.imagecup.model.AlbumResponse
import com.example.imagecup.model.Photo
import com.example.imagecup.ui.adapter.AlbumAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber


@AndroidEntryPoint
class AlbumFragment : BaseFragment<FragmentAlbumBinding>(R.layout.fragment_album) {
    private val viewModel : MainViewModel by viewModels()
    private lateinit var myAdapter: AlbumAdapter
    override fun createView(binding: FragmentAlbumBinding) {
        initView()
        setAdapter()
        bindingVm()
    }
    private fun bindingVm(){
        lifecycleScope.launchWhenCreated {
            viewModel.labels.collectLatest {
                Timber.d("$it")
                myAdapter = AlbumAdapter(it)
                binding.rvAlbum.adapter = myAdapter
                myAdapter.notifyDataSetChanged()
                myAdapter.setItemClickListener(object :AlbumAdapter.OnItemClickListener{
                    override fun onClick(v: View, label: String, position: Int) {

                    }
                })
            }
        }
    }

    override fun viewCreated() {
        viewModel.getAllLabels()
    }

    private fun initView(){

    }
    private fun setAdapter(){

        val gridLayoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvAlbum.layoutManager = gridLayoutManager
        myAdapter = AlbumAdapter(emptyList())
        binding.rvAlbum.adapter = myAdapter

    }

}