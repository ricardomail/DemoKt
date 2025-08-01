package com.oasis.app_user.ui

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.alibaba.android.arouter.facade.annotation.Route
import com.oasis.app_common.base.BaseActivity
import com.oasis.app_common.util.Constants
import com.oasis.app_common.util.KVUtil
import com.oasis.app_common.util.ToastUtil
import com.oasis.app_common.base.BaseStateObserver
import com.oasis.app_user.R
import com.oasis.app_user.bean.LoginBean
import com.oasis.app_user.databinding.ActivityLoginBinding
import com.oasis.app_user.viewmodel.LoginViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

@Route(path = Constants.PATH_LOGIN)
class LoginActivity : BaseActivity<ActivityLoginBinding>() {
    override fun getLayoutID(): Int = R.layout.activity_login
    private val viewModel: LoginViewModel by viewModel()

    override fun init() {
        viewModel.data.observe(this, object : BaseStateObserver<LoginBean>() {
            override fun getRespDataSuccess(data: LoginBean) {
                KVUtil.put(Constants.USER_NAME, data.username)
                ToastUtil.showMsg(getString(R.string.login_success))
                finish()
            }
        })

        lifecycleScope.launch {
            launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.loginEventFlow.collect {
                        it.onFailure { throwable ->
                            ToastUtil.showMsg(throwable.message!!)
                        }.onSuccess { message ->
                            ToastUtil.showMsg(message)
                        }
                    }
                }
            }

            launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.loginStateFlow.collect {
                        KVUtil.put(Constants.USER_NAME, it.username)
                        finish()
                    }
                }
            }

            launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.loadingState.collect {
                        if (it) {
                            showLoadingDialog()
                        } else {
                            dismissLoadingDialog()
                        }
                    }
                }
            }

        }

        mBind.txLogin.setOnClickListener {
            if (mBind.edit1.text.isNotEmpty() && mBind.edit2.text.isNotEmpty()) {
                viewModel.loginByFlow(mBind.edit1.text.toString(), mBind.edit2.text.toString())
            } else {
                ToastUtil.showMsg(getString(R.string.login_input_failed))
            }
        }
        mBind.ivBack.setOnClickListener { finish() }
    }

}