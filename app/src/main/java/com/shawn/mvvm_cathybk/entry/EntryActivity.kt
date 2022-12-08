package com.shawn.mvvm_cathybk.entry

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.shawn.common.activity.BaseActivity
import com.shawn.mvvm_cathybk.R
import com.shawn.mvvm_cathybk.databinding.ActivityEntryBinding
import com.shawn.mvvm_cathybk.main.HomeActivity

class EntryActivity : BaseActivity<ActivityEntryBinding>(),EntryHandler {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initUI()
    }

    private fun initUI() {
        binding.apply {
            btnStart.setOnClickListener {
                goHomeActivity()
            }
        }
    }
    override fun goHomeActivity() {
        HomeActivity.startActivity(this)
    }

    override fun getViewBinding(): ActivityEntryBinding = ActivityEntryBinding.inflate(layoutInflater)

}