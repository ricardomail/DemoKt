package com.oasis.mydemokt.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.oasis.app_common.base.BaseActivity
import com.oasis.mydemokt.R
import com.oasis.mydemokt.databinding.ActivityTestBinding

class TestActivity : BaseActivity<ActivityTestBinding>() {
    override fun getLayoutID(): Int {
        return R.layout.activity_test
    }

    override fun init() {
        mBind.button.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

}