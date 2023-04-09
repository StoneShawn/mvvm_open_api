package com.shawn.mvvm_cathybk.main.home

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.shawn.mvvm_cathybk.R
import com.shawn.mvvm_cathybk.databinding.ItemAttractionBinding
import com.shawn.network.model.Attraction


class HomeViewHolder(private val binding: ItemAttractionBinding, private val context: Context, val listener: HomeAdapter.ClickListener) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(data: Attraction) {
        binding.apply {
            tvTitle.text = data.name
            tvDescription.text = data.introduction

            Glide.with(context)
                .load(if (data.images.isNotEmpty()) data.images[0].src else context.getDrawable(R.drawable.one))
                .transform(CenterCrop(), RoundedCorners(50))
                .into(imageView);

            itemView.setOnClickListener { listener.onClick(data) }
        }
    }
}