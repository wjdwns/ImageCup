package com.example.imagecup.ui.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.imagecup.databinding.ItemGalleryBinding

class GalleryAdapter(private val uriArr: List<Uri>) :
    RecyclerView.Adapter<GalleryAdapter.ItemViewHolder>() {

    interface OnItemClickListener {
        fun onClick(v: View, photo_uri: Uri, position: Int)
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

    override fun onBindViewHolder(holder:ItemViewHolder, position: Int) {
        holder.bind(uriArr[position])
    }

    override fun getItemCount(): Int = uriArr.size

    inner class ItemViewHolder(private val binding: ItemGalleryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(uri: Uri) {
            binding.ivImage.scaleType = ImageView.ScaleType.CENTER_CROP
            Glide.with(binding.root)
                .load(uri.toString())
                .into(binding.ivImage)
            val pos = adapterPosition
            if (pos != RecyclerView.NO_POSITION) {
                binding.ivImage.setOnClickListener {
                    itemClickListener.onClick(itemView, uri, pos)
                }
            }
        }
    }
}