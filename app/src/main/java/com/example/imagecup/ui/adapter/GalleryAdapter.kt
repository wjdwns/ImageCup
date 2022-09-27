package com.example.imagecup.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.imagecup.databinding.ItemGalleryBinding

class GalleryAdapter(private val uriArr:List<String>) : RecyclerView.Adapter<GalleryAdapter.ItemViewHolder>(){

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
        fun bind(uri : String){
            binding.ivImage.scaleType = ImageView.ScaleType.CENTER_CROP
            Glide.with(binding.root)
                .load(uri)
                .into(binding.ivImage)
        }
    }
}