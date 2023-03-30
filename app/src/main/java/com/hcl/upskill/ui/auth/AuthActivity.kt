package com.hcl.upskill.ui.auth

import android.content.Intent
import com.hcl.upskill.base.BaseActivity
import com.hcl.upskill.databinding.ActivityAuthBinding
import com.hcl.upskill.ui.profile.ProfileActivity
import com.hcl.upskill.util.PrefUtils.LOGIN
import com.hcl.upskill.util.PrefUtils.PREF_NAME
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AuthActivity : BaseActivity<ActivityAuthBinding>() {

    override fun binding() = ActivityAuthBinding.inflate(layoutInflater)

    override fun init() {
        when(getSharedPreferences(PREF_NAME, MODE_PRIVATE).getBoolean(LOGIN,false)){
            true -> {
                startActivity(Intent(this,ProfileActivity::class.java))
                finish()
            }
            else -> {}
        }
    }

    override fun initCtrl() {
    }

    override fun observer() {
    }
}