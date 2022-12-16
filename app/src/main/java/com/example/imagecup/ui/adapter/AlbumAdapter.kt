package com.example.imagecup.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.imagecup.databinding.ItemAlbumBinding
import com.example.imagecup.model.AlbumResponse
import com.example.imagecup.model.Photo

class AlbumAdapter(private val photos: List<Photo>) :
    RecyclerView.Adapter<AlbumAdapter.ItemViewHolder>() {
    interface OnItemClickListener {
        fun onClick(v: View, label: String, position: Int)
    }

    private lateinit var itemClickListener: OnItemClickListener
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
        holder.bind(photos[position])
    }

    override fun getItemCount(): Int = photos.size

    inner class ItemViewHolder(private val binding: ItemAlbumBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(photo: Photo) {
            binding.ivAlbumImage.scaleType = ImageView.ScaleType.CENTER_CROP
            Glide.with(binding.root)
                .load(photo.title)
                .into(binding.ivAlbumImage)
            binding.tvAlbumLabel.text = photo.label

            val pos = adapterPosition
            if (pos != RecyclerView.NO_POSITION) {
                binding.ivAlbumImage.setOnClickListener {
                    itemClickListener.onClick(itemView, photo.label, pos)
                }
            }
        }


    }
}