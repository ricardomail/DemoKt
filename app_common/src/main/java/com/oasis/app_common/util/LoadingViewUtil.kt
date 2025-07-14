package com.oasis.app_common.util

import android.app.Activity
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.oasis.app_common.R

object LoadingViewUtil {

    private var dialog: AlertDialog? = null

    fun showLoadingDialog(context: Context, isCancel: Boolean) {
        (context as Activity).takeIf { it.isFinishing }?.let { return }
        dialog = dialog ?: AlertDialog.Builder(context).run {
            setView(LayoutInflater.from(context).inflate(R.layout.common_loading_dialog, null))
            setCancelable(isCancel)
            create()
        }.takeIf { it.window != null }?.apply {
            window?.setBackgroundDrawable(ColorDrawable(0))
        }
        // TODO: bug 闪退
        dialog?.takeIf { !it.isShowing }?.show()
    }

    fun dismissLoadingDialog() {
        dialog?.takeIf { it.isShowing }?.dismiss()
    }
}