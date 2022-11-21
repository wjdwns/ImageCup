package com.example.imagecup.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.imagecup.databinding.ItemAlbumBinding
import com.example.imagecup.model.AlbumResponse

class AlbumAdapter(private val albumResponse: List<AlbumResponse>) :
    RecyclerView.Adapter<AlbumAdapter.ItemViewHolder>() {
    interface OnItemClickListener {
        fun onClick(v: View, label: String, position: Int)
    }

    private var itemClickListener: OnItemClickListener? = null
    fun setItemClickListener(itemClickListener: OnItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            ItemAlbumBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(albumResponse[position])
    }

    override fun getItemCount(): Int = albumResponse.size

    inner class ItemViewHolder(private val binding: ItemAlbumBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(albumResponse: AlbumResponse) {
            Glide.with(binding.root)
                .load(albumResponse.photos.first())
                .into(binding.ivAlbumImage)
            binding.tvAlbumLabel.text = albumResponse.label
        }
    }
}