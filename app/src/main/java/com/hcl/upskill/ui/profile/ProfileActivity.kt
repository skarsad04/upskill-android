package com.hcl.upskill.ui.profile

import android.os.CountDownTimer
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.hcl.upskill.base.BaseActivity
import com.hcl.upskill.databinding.ActivityProfileBinding
import com.hcl.upskill.model.request.auth.SessionRequestModel
import com.hcl.upskill.model.response.SessionResponseModel
import com.hcl.upskill.ui.auth.SessionViewModel
import com.hcl.upskill.ui.auth.login.LoginViewModel
import com.hcl.upskill.util.CustomLoader.Companion.hideLoader
import com.hcl.upskill.util.CustomLoader.Companion.showLoader
import com.hcl.upskill.util.NetworkResult
import com.hcl.upskill.util.PrefUtils.ACCESS_TOKEN
import com.hcl.upskill.util.PrefUtils.PREF_NAME
import com.hcl.upskill.util.SessionUtils.startSession
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.lang.String
import kotlin.Long


@AndroidEntryPoint
class ProfileActivity : BaseActivity<ActivityProfileBinding>() {

    private val viewModel : SessionViewModel by viewModels()

    override fun binding() = ActivityProfileBinding.inflate(layoutInflater)

    override fun init() {
        startSession(this,viewModel)
    }



    override fun initCtrl() {
    }

    override fun observer() {
        lifecycleScope.launch(dispatcher.main) {
            viewModel.session.collectLatest {
                hideLoader()
                when(it){
                    is NetworkResult.Default -> { }
                    is NetworkResult.Loading ->{ showLoader(this@ProfileActivity) }
                    is NetworkResult.Success -> {
                       val response = it.data as? SessionResponseModel
                       getSharedPreferences(PREF_NAME, MODE_PRIVATE).edit().putString(ACCESS_TOKEN,response?.access).apply()
                       startSession(this@ProfileActivity,viewModel)
                    }
                    is NetworkResult.Error ->{ Toast.makeText(this@ProfileActivity,it.error,Toast.LENGTH_LONG).show() }
                }
            }
        }
    }

}