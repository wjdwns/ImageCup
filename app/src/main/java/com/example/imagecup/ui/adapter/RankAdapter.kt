package com.example.imagecup.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.imagecup.R
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
            binding.ivThunmnail.scaleType = ImageView.ScaleType.CENTER_CROP
            binding.tvLabel.text = label
            Glide.with(binding.root)
                .load(setImage(label))
                .into(binding.ivThunmnail)
            val pos = adapterPosition
            if (pos != RecyclerView.NO_POSITION) {
                binding.ivJoin.setOnClickListener {
                    itemClickListener.onClick(itemView, label, pos)
                }
            }
        }

    }
    fun setImage(label : String) : Int {
        return when (label) {
            "강아지" -> R.drawable.dog
            "고양이" -> R.drawable.cat
            "자동차" -> R.drawable.car
            "사람" -> R.drawable.person
            "이동수단" -> R.drawable.vehicle
            "도로" -> R.drawable.road
            "주방도구" -> R.drawable.kitchen_tool
            "가방" -> R.drawable.bag
            "도구" -> R.drawable.tool
            "식품" -> R.drawable.food
            "운동기구" -> R.drawable.sport_equip
            "가구" -> R.drawable.funiture
            "전자기기" -> R.drawable.electronics
            "동물" -> R.drawable.animal
            "새" -> R.drawable.bird
            "곰" -> R.drawable.bear
            else -> R.drawable.etc
        }
    }
}