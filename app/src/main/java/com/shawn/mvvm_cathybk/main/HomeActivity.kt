package com.shawn.mvvm_cathybk.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.startup.AppInitializer
import app.rive.runtime.kotlin.RiveInitializer
import app.rive.runtime.kotlin.core.Rive
import com.shawn.common.activity.BaseActivity
import com.shawn.common.utils.LanguageUtils
import com.shawn.mvvm_cathybk.R
import com.shawn.mvvm_cathybk.databinding.ActivityHomeBinding
import com.shawn.mvvm_cathybk.main.detail.DetailFragment
import com.shawn.mvvm_cathybk.main.home.HomeFragment
import com.shawn.mvvm_cathybk.main.web.WebViewActivity
import com.shawn.mvvm_cathybk.utils.ActivityUtils
import com.shawn.network.model.Attraction

class HomeActivity : BaseActivity<ActivityHomeBinding>(), HomeHandler {

    companion object {
        fun startActivity(context: Context) {
            val intent = Intent(context, HomeActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        setToolBar()
        AppInitializer.getInstance(applicationContext)
            .initializeComponent(RiveInitializer::class.java)

        Rive.init(this)
    }

    private fun initView() {
        goHome()
    }

    private fun getCurrentFragment(): Fragment? {
        val manager = supportFragmentManager
        return manager.findFragmentById(R.id.frame_layout) as Fragment?
    }

    private fun setToolBar() {

        binding.apply {
            topAppBar.title = "台北旅遊"
            topAppBar.navigationIcon = null
            topAppBar.inflateMenu(R.menu.home_menu)

            topAppBar.setNavigationOnClickListener {
                when (getCurrentFragment()) {
                    is HomeFragment -> {
                    }
                    is DetailFragment -> {
                        onBackPressed()
                    }
                    else -> {
                    }
                }
            }

            topAppBar.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.menu_language -> {
                        true
                    }
                    R.id.zh_tw -> {
                        changeHomeLanguage(LanguageUtils.lang.ZH_TW.lang)
                        true
                    }
                    R.id.zh_cn -> {
                        changeHomeLanguage(LanguageUtils.lang.ZH_CN.lang)
                        true
                    }
                    R.id.en -> {
                        changeHomeLanguage(LanguageUtils.lang.EN.lang)
                        true
                    }
                    R.id.ja -> {
                        changeHomeLanguage(LanguageUtils.lang.JA.lang)
                        true
                    }
                    R.id.ko -> {
                        changeHomeLanguage(LanguageUtils.lang.KO.lang)
                        true
                    }
                    R.id.es -> {
                        changeHomeLanguage(LanguageUtils.lang.ES.lang)
                        true
                    }
                    R.id.id -> {
                        changeHomeLanguage(LanguageUtils.lang.ID.lang)
                        true
                    }
                    R.id.th -> {
                        changeHomeLanguage(LanguageUtils.lang.TH.lang)
                        true
                    }
                    R.id.vi -> {
                        changeHomeLanguage(LanguageUtils.lang.VI.lang)
                        true
                    }
                    else -> false
                }
            }
        }
    }

    private fun changeHomeLanguage(language: String) {
        val fragment = getCurrentFragment()
        when (fragment) {
            is HomeFragment -> {
                fragment.changeLanguage(language)
            }
            else -> {

            }
        }
    }

    override fun getViewBinding(): ActivityHomeBinding = ActivityHomeBinding.inflate(layoutInflater)

    override fun goHome() {
        binding.apply {
            topAppBar.title = "台北旅遊"
            topAppBar.navigationIcon = null
        }
//        ActivityUtils.replaceFragment(
//            supportFragmentManager,
//            R.id.frame_layout,
//            HomeFragment.newInstance(),
//            HomeFragment::class.simpleName
//        )

        ActivityUtils.addFragmentToActivity(
            supportFragmentManager,
            HomeFragment.newInstance(),
            R.id.frame_layout,
            false
        )
    }

    override fun goDetail(data: Attraction) {
        binding.apply {
            topAppBar.title = data.name
            topAppBar.navigationIcon = resources.getDrawable(R.drawable.ic_baseline_arrow_back_24)
            topAppBar.menu.clear()
        }

//        ActivityUtils.replaceFragment(
//            supportFragmentManager,
//            R.id.frame_layout,
//            DetailFragment.newInstance(data),
//            DetailFragment::class.simpleName
//        )

        ActivityUtils.addFragmentToActivity(
            supportFragmentManager,
            DetailFragment.newInstance(data),
            R.id.frame_layout,
            true
        )
    }

    override fun goWebView(url: String) {
        WebViewActivity.start(this, url)
    }

    override fun onBack() {

    }

    override fun onBackPressed() {
        when(getCurrentFragment()){
            is DetailFragment -> {
                super.onBackPressed()
                binding.apply {
                    topAppBar.title = "台北旅遊"
                    topAppBar.navigationIcon = null
                    topAppBar.inflateMenu(R.menu.home_menu)
                }
            }
            is HomeFragment -> {
                super.onBackPressed()
                finish()
            }
            else -> {
                super.onBackPressed()
            }
        }
    }
}