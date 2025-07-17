package com.oasis.app_web

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.alibaba.android.arouter.facade.annotation.Route
import com.oasis.app_common.base.BaseActivity
import com.oasis.app_common.util.Constants
import com.oasis.app_web.databinding.ActivityWebBinding
@Route(path = Constants.PATH_WEB)
class WebActivity : BaseActivity<ActivityWebBinding>() {

    private lateinit var web: WebView

    override fun getLayoutID(): Int = R.layout.activity_web

    @SuppressLint("SetJavaScriptEnabled")
    override fun init() {
        if (intent == null) return
        val title = intent.getStringExtra(Constants.WEB_TITLE)
        val link = intent.getStringExtra(Constants.WEB_LINK).toString()
        mBind.ivBack.setOnClickListener { finish() }
        mBind.tvTitle.text = title

        web = WebView(applicationContext).apply {
            settings.javaScriptEnabled = true
            settings.loadWithOverviewMode = true
            settings.domStorageEnabled = true
            webViewClient = object : WebViewClient() {
                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    super.onPageStarted(view, url, favicon)
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                }

                override fun shouldOverrideUrlLoading(
                    view: WebView?,
                    request: WebResourceRequest?
                ): Boolean {
                    return if (!link.equals(request?.url)) {
                        startActivity(
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse(request?.url.toString())
                            )

                        )
                        true
                    } else false
                }
            }
            webChromeClient = object : WebChromeClient() {
                override fun onProgressChanged(view: WebView?, newProgress: Int) {
                    mBind.pb.progress = newProgress
                    if (newProgress == 100) {
                        mBind.pb.visibility = View.GONE
                    }
                }
            }
        }
        mBind.webContainer.addView(web)
        web.loadUrl(link)
    }

    override fun onDestroy() {
        mBind.webContainer.removeAllViews()
        web.clearHistory()
        web.destroy()
        super.onDestroy()
    }

}