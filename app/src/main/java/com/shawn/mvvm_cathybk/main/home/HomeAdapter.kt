package com.shawn.mvvm_cathybk.main.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.shawn.mvvm_cathybk.databinding.ItemAttractionBinding
import com.shawn.network.model.Attraction

class HomeAdapter(private val handler : HomeFragmentHandler) : PagingDataAdapter<Attraction, HomeViewHolder>(HomeDiffListener) {

    companion object{
        val HomeDiffListener = object : DiffUtil.ItemCallback<Attraction>(){
            override fun areItemsTheSame(oldItem: Attraction, newItem: Attraction): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Attraction, newItem: Attraction): Boolean {
                return oldItem == newItem
            }
        }
    }
    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        return HomeViewHolder(ItemAttractionBinding.inflate(LayoutInflater.from(parent.context), parent, false),parent.context,handler)
    }
}