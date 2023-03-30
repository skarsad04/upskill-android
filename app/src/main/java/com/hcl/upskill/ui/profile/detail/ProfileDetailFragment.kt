package com.hcl.upskill.ui.profile.detail

import android.content.Context.MODE_PRIVATE
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.hcl.upskill.R
import com.hcl.upskill.base.BaseFragment
import com.hcl.upskill.databinding.FragmentProfileDetailBinding
import com.hcl.upskill.model.response.SignupResponseModel
import com.hcl.upskill.util.CustomLoader.Companion.hideLoader
import com.hcl.upskill.util.CustomLoader.Companion.showLoader
import com.hcl.upskill.util.NetworkResult
import com.hcl.upskill.util.PrefUtils.ACCESS_TOKEN
import com.hcl.upskill.util.PrefUtils.BEARER
import com.hcl.upskill.util.PrefUtils.PREF_NAME
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileDetailFragment : BaseFragment<FragmentProfileDetailBinding>(), View.OnClickListener {

    private val viewModel: ProfileDetailViewModel by viewModels()

    override fun binding(inflater: LayoutInflater, container: ViewGroup?) = FragmentProfileDetailBinding.inflate(inflater,container,false)

    override fun init() {
        viewModel.profileDetail(token = "$BEARER ${requireActivity().getSharedPreferences(PREF_NAME, MODE_PRIVATE)
                                                   .getString(ACCESS_TOKEN,"")}")

        binding.clToolbar.ivBack.setOnClickListener(this)
    }

    override fun initCtrl() {
    }

    override fun observer() {
        lifecycleScope.launch(dispatcher.main) {
            viewModel.profileDetail.collectLatest {
                hideLoader()
                when(it){
                    is NetworkResult.Default -> { }
                    is NetworkResult.Loading ->{ showLoader(requireActivity())}
                    is NetworkResult.Success ->{ binding.model = it.data as? SignupResponseModel }
                    is NetworkResult.Error ->{ Toast.makeText(requireActivity(),it.error,Toast.LENGTH_LONG).show() }
                }
            }
        }
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.ivBack -> { requireActivity().onBackPressed() }
        }
    }
}