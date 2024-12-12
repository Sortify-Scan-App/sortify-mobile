package com.dicoding.sortify.ui.article

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.sortify.databinding.ActivityWebViewBinding


class WebViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWebViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val url = intent.getStringExtra(EXTRA_URL) ?: return

        with(binding.webView) {
            webViewClient = object : WebViewClient() {
                override fun onPageStarted(view: WebView?, url: String?, favicon: android.graphics.Bitmap?) {
                    super.onPageStarted(view, url, favicon)
                    binding.lottieLoading.visibility = android.view.View.VISIBLE
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    binding.lottieLoading.visibility = android.view.View.GONE
                }
            }
            settings.javaScriptEnabled = true
            loadUrl(url)
        }

    }

    companion object {
        const val EXTRA_URL = "extra_url"
    }
}
