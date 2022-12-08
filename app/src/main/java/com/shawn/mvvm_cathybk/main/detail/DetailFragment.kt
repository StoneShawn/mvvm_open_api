package com.shawn.mvvm_cathybk.main.detail

import android.graphics.Color
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.shawn.common.fragment.BaseFragment
import com.shawn.mvvm_cathybk.databinding.FragmentDetailBinding
import com.shawn.mvvm_cathybk.main.HomeActivity
import com.shawn.network.model.Attraction


class DetailFragment(private val data: Attraction) : BaseFragment<FragmentDetailBinding>(),DetailHandler {
    companion object {
        fun newInstance(data: Attraction): DetailFragment = DetailFragment(data)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        binding.apply {
            tvTitle.text = data.name
            tvDescription.text = data.introduction.replace("&nbsp;","")
            tvLink.text = data.url
//            tvLink.movementMethod = LinkMovementMethod.getInstance();
//            tvLink.setLinkTextColor(Color.BLUE)
//
            tvLink.setOnClickListener {
                goWebView(data.url)
            }
            context?.let {
                Glide.with(it)
                    .load(if (data.images.isNotEmpty()) data.images[0].src else null)
                    .centerCrop()
                    .into(imageView);
            }
        }

    }

    override fun bindingCallback(): (LayoutInflater, ViewGroup?) -> FragmentDetailBinding = { layoutInflater, viewGroup ->
        FragmentDetailBinding.inflate(LayoutInflater.from(context), viewGroup, false)
    }

    override fun goWebView(url: String) {
        getActivityHandler(HomeActivity::class.java)?.goWebView(url)
    }
}