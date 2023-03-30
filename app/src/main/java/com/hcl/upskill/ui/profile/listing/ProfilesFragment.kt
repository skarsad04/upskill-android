package com.hcl.upskill.ui.profile.listing

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.hcl.upskill.base.BaseFragment
import com.hcl.upskill.databinding.FragmentProfileBinding
import com.hcl.upskill.model.response.ProfilesResponseModel
import com.hcl.upskill.model.response.SignupResponseModel
import com.hcl.upskill.ui.auth.AuthActivity
import com.hcl.upskill.util.CustomLoader
import com.hcl.upskill.util.CustomLoader.Companion.hideLoader
import com.hcl.upskill.util.CustomLoader.Companion.showLoader
import com.hcl.upskill.util.NetworkResult
import com.hcl.upskill.util.PrefUtils
import com.hcl.upskill.util.PrefUtils.BEARER
import com.hcl.upskill.util.PrefUtils.PREF_NAME
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfilesFragment : BaseFragment<FragmentProfileBinding>(), View.OnClickListener {

    private val viewModel : ProfilesViewModel by viewModels()

    override fun binding(inflater: LayoutInflater, container: ViewGroup?) = FragmentProfileBinding.inflate(inflater,container,false)

    override fun init() {
        viewModel.profile(token = "$BEARER ${requireActivity().getSharedPreferences(PREF_NAME, MODE_PRIVATE)
                                                   .getString(PrefUtils.ACCESS_TOKEN,"")}")

    }

    override fun initCtrl() {
        binding.ivLogout.setOnClickListener(this)
    }

    override fun observer() {
        lifecycleScope.launch(dispatcher.main) {
            viewModel.profile.collectLatest {
                hideLoader()
                when(it){
                    is NetworkResult.Default -> { }
                    is NetworkResult.Loading -> {
                        showLoader(requireActivity())
                    }
                    is NetworkResult.Success -> {
                        val data = it.data as? ProfilesResponseModel
                        binding.rvList.adapter = ProfileAdapter(requireActivity(),data?.data)
                    }
                    is NetworkResult.Error ->{ Toast.makeText(requireActivity(),it.error, Toast.LENGTH_LONG).show() }
                }
            }
        }
    }

    override fun onClick(v: View?) {
        requireActivity().getSharedPreferences(PREF_NAME, MODE_PRIVATE).edit().apply {
            putString(PrefUtils.ACCESS_TOKEN,"")
            putString(PrefUtils.REFRESH_TOKEN,"")
            putBoolean(PrefUtils.LOGIN,false)
        }.apply()
        startActivity(Intent(requireActivity(),AuthActivity::class.java))
        requireActivity().finish()
    }
}