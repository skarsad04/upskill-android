package com.hcl.upskill.util

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.os.CountDownTimer
import com.hcl.upskill.model.request.auth.SessionRequestModel
import com.hcl.upskill.ui.auth.SessionViewModel
import com.hcl.upskill.util.PrefUtils.BEARER
import com.hcl.upskill.util.PrefUtils.PREF_NAME
import com.hcl.upskill.util.PrefUtils.REFRESH_TOKEN

object SessionUtils {

    fun startSession(context: Context?, viewModel : SessionViewModel) {
        object : CountDownTimer(20*1000, 1000) {
            override fun onTick(millisUntilFinished: Long) { }
            override fun onFinish() {
                viewModel.makeSession("$BEARER ${context?.getSharedPreferences(PREF_NAME, MODE_PRIVATE)?.getString(PrefUtils.ACCESS_TOKEN,"")}",
                                      SessionRequestModel(refresh = context?.getSharedPreferences(PREF_NAME, MODE_PRIVATE)?.getString(REFRESH_TOKEN,"")))
            }
        }.start()
    }
}