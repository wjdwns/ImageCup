package com.example.imagecup.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.imagecup.databinding.ItemRankingBinding
import com.example.imagecup.model.Photo


class RankAdapter(private val labels: List<String>) :
    RecyclerView.Adapter<RankAdapter.ItemViewHolder>() {
    interface OnItemClickListener {
        fun onClick(v: View, label: String, position: Int)
    }

    private lateinit var itemClickListener: RankAdapter.OnItemClickListener
    fun setItemClickListener(itemClickListener: RankAdapter.OnItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            ItemRankingBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(labels[position])
    }

    override fun getItemCount(): Int = labels.size

    inner class ItemViewHolder(private val binding: ItemRankingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(label: String) {
            binding.tvLabel.text = label

            val pos = adapterPosition
            if (pos != RecyclerView.NO_POSITION) {
                binding.ivJoin.setOnClickListener {
                    itemClickListener!!.onClick(itemView, label, pos)
                }
            }
        }

    }
}