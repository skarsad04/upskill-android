package com.hcl.upskill.ui.auth.login

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.hcl.upskill.R
import com.hcl.upskill.base.BaseFragment
import com.hcl.upskill.databinding.FragmentLoginBinding
import com.hcl.upskill.model.request.auth.LoginModel
import com.hcl.upskill.model.response.LoginResponseModel
import com.hcl.upskill.ui.profile.ProfileActivity
import com.hcl.upskill.util.CustomLoader.Companion.hideLoader
import com.hcl.upskill.util.CustomLoader.Companion.showLoader
import com.hcl.upskill.util.NetworkResult
import com.hcl.upskill.util.PrefUtils
import com.hcl.upskill.util.PrefUtils.PREF_NAME
import com.hcl.upskill.util.spannableSignup
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(), View.OnClickListener {

    private val viewModel : LoginViewModel by viewModels()

    override fun binding(inflater: LayoutInflater, container: ViewGroup?) = FragmentLoginBinding.inflate(inflater,container,false)

    override fun init() {
        binding.model = LoginModel(username = "", password = "")
        binding.tvSignup.spannableSignup()
    }

    override fun initCtrl() {
        binding.btnLogin.setOnClickListener(this)
    }

    override fun observer() {

        lifecycleScope.launch(dispatcher.main) {
            viewModel.validEmail.collect {
                binding.tilUsername.apply {
                    error = if(it) "" else  getString(R.string.invalid_username)
                    isErrorEnabled = !it
                }
            }
        }

        lifecycleScope.launch(dispatcher.main) {
            viewModel.validPassword.collect {
                binding.tilPassword.apply {
                    error = if(it) "" else getString(R.string.invalid_password)
                    isErrorEnabled = !it
                }
            }
        }

        lifecycleScope.launch(dispatcher.main) {
            viewModel.login.collect {
                hideLoader()
                when(it) {
                    is NetworkResult.Default -> {
                    }
                    is NetworkResult.Loading -> {
                        showLoader(requireActivity())
                    }
                    is NetworkResult.Success -> {
                        val data = it.data as? LoginResponseModel

                        requireActivity().getSharedPreferences(PREF_NAME, MODE_PRIVATE)
                            .edit().apply {
                                putString(PrefUtils.ACCESS_TOKEN,data?.access)
                                putString(PrefUtils.REFRESH_TOKEN,data?.refresh)
                                putBoolean(PrefUtils.LOGIN,true)
                            }.apply()


                        startActivity(Intent(requireActivity(),ProfileActivity::class.java))
                    }
                    is NetworkResult.Error -> {
                        Toast.makeText(requireActivity(),it.error,Toast.LENGTH_LONG).show() }
                }
            }
        }
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.btnLogin -> {
                viewModel.makeLogin(binding.model)
            }
        }
    }
}