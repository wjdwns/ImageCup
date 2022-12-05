package com.example.imagecup.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.imagecup.databinding.ItemGalleryBinding
import com.example.imagecup.model.Photo

class AlbumLabelAdapter (private val albumPhotos: List<Photo>) :
    RecyclerView.Adapter<AlbumLabelAdapter.ItemViewHolder>() {

    interface OnItemClickListener {
        fun onClick(v: View, photo: Photo, position: Int)
    }
    private lateinit var itemClickListener: OnItemClickListener
    fun setItemClickListener(itemClickListener: OnItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            ItemGalleryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onBindViewHolder(holder:ItemViewHolder, position: Int) {
        holder.bind(albumPhotos[position])
    }

    override fun getItemCount(): Int = albumPhotos.size

    inner class ItemViewHolder(private val binding: ItemGalleryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(albumPhotos: Photo) {
            binding.ivImage.scaleType = ImageView.ScaleType.CENTER_CROP
            Glide.with(binding.root)
                .load(albumPhotos.title)
                .into(binding.ivImage)
            val pos = adapterPosition
            if (pos != RecyclerView.NO_POSITION) {
                binding.ivImage.setOnClickListener {
                    itemClickListener.onClick(itemView, albumPhotos, pos)
                }
            }
        }
    }
}
