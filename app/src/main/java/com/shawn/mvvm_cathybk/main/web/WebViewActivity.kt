package com.shawn.mvvm_cathybk.main.web

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebViewClient
import com.shawn.common.activity.BaseActivity
import com.shawn.mvvm_cathybk.databinding.ActivityHomeBinding
import com.shawn.mvvm_cathybk.databinding.ActivityWebViewBinding

class WebViewActivity : BaseActivity<ActivityWebViewBinding>() {

    companion object{
        private const val KEY_URL = "key_url"
        fun start(context: Context, url: String){
            val intent = Intent(context,WebViewActivity::class.java)
            intent.putExtra(KEY_URL, url)
            context.startActivity(intent)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
    }

    private fun initView(){
        binding.apply {
            val webSetting = webview.settings
            webSetting.javaScriptEnabled = true
            webview.webViewClient = WebViewClient()
            intent.getStringExtra(KEY_URL)?.let {
                webview.loadUrl(it)
            }
        }
    }

    override fun getViewBinding(): ActivityWebViewBinding = ActivityWebViewBinding.inflate(layoutInflater)

}