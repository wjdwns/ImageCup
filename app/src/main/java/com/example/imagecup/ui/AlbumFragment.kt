package com.example.imagecup.ui

import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.depromeet.housekeeper.base.BaseFragment
import com.example.imagecup.R
import com.example.imagecup.databinding.FragmentAlbumBinding
import com.example.imagecup.model.AlbumResponse
import com.example.imagecup.ui.adapter.AlbumAdapter

class AlbumFragment : BaseFragment<FragmentAlbumBinding>(R.layout.fragment_album) {
    private val viewModel : MainViewModel by viewModels()
    private lateinit var myAdapter: AlbumAdapter
    override fun createView(binding: FragmentAlbumBinding) {
        initView()
        setAdapter()
    }

    override fun viewCreated() {
    }

    private fun initView(){
    }
    private fun setAdapter(){
        val gridLayoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvAlbum.layoutManager = gridLayoutManager
        myAdapter = AlbumAdapter(listOf(
            AlbumResponse("강아지", listOf("https://img.freepik.com/free-psd/portrait-of-an-adorable-golden-retriever-puppy_53876-73976.jpg")) ,
            AlbumResponse("고양이", listOf("https://post-phinf.pstatic.net/MjAyMDAyMjlfMjY4/MDAxNTgyOTU0Nzg3MjQ4.PBMFV4WrSJmeSUJ56c4C7Vkz_SsQlJ1SByKU18kkJh0g.T7mQnadCWVtEZ448AGk_9kG1HFBAzdztXZcBjvSbduwg.JPEG/고양이_나이1.jpg?type=w1200"))
        ))//AlbumAdapter(emptyList())
        binding.rvAlbum.adapter = myAdapter
        myAdapter.setItemClickListener(object :AlbumAdapter.OnItemClickListener{
            override fun onClick(v: View, label: String, position: Int) {
            }
        })
    }

}