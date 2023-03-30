package com.hcl.upskill.ui.auth.signup

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.hcl.upskill.R
import com.hcl.upskill.base.BaseFragment
import com.hcl.upskill.databinding.FragmentSignupBinding
import com.hcl.upskill.model.request.auth.SignupModel
import com.hcl.upskill.model.response.SignupResponseModel
import com.hcl.upskill.ui.profile.ProfileActivity
import com.hcl.upskill.util.CameraHelper.cameraIntent
import com.hcl.upskill.util.CameraHelper.checkCameraPermissions
import com.hcl.upskill.util.CameraHelper.requestCameraPermission
import com.hcl.upskill.util.CustomLoader
import com.hcl.upskill.util.CustomLoader.Companion.hideLoader
import com.hcl.upskill.util.CustomLoader.Companion.showLoader
import com.hcl.upskill.util.FilePath.getPath
import com.hcl.upskill.util.NetworkResult
import com.hcl.upskill.util.StorageHelper.checkStoragePermissions
import com.hcl.upskill.util.StorageHelper.requestStoragePermission
import com.hcl.upskill.util.loadProfile
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class SignupFragment : BaseFragment<FragmentSignupBinding>(), View.OnClickListener {

    private val viewModel : SignupViewModel by viewModels()
    private var path: String?=null


    override fun binding(inflater: LayoutInflater, container: ViewGroup?) = FragmentSignupBinding.inflate(inflater,container,false)

    override fun init() {
        binding.model = SignupModel(first_name = "", last_name = "", email = "", username = "", password = "", profile_icon  = null)
    }

    override fun initCtrl() {
        binding.btnSignup.setOnClickListener(this)
        binding.ivProfile.setOnClickListener(this)
    }

    override fun observer() {

        lifecycleScope.launch(dispatcher.main) {
            viewModel.validFirstName.collect {
                binding.tilFirstName.apply {
                    error = if(it) "" else  getString(R.string.invalid_first_name)
                    isErrorEnabled = !it
                }
            }
        }


        lifecycleScope.launch(dispatcher.main) {
            viewModel.validLastName.collect {
                binding.tilLastName.apply {
                    error = if(it) "" else  getString(R.string.invalid_last_name)
                    isErrorEnabled = !it
                }
            }
        }

        lifecycleScope.launch(dispatcher.main) {
            viewModel.validEmail.collect {
                binding.tilEmail.apply {
                    error = if(it) "" else  getString(R.string.invalid_email)
                    isErrorEnabled = !it
                }
            }
        }



        lifecycleScope.launch(dispatcher.main) {
            viewModel.validUsername.collect {
                binding.tilUsername.apply {
                    error = if(it) "" else  getString(R.string.invalid_username)
                    isErrorEnabled = !it
                }
            }
        }


        lifecycleScope.launch(dispatcher.main) {
            viewModel.validPassword.collect {
                binding.tilPassword.apply {
                    error = if(it) "" else  getString(R.string.invalid_password)
                    isErrorEnabled = !it
                }
            }
        }




        lifecycleScope.launch(dispatcher.main) {
            viewModel.signup.collect {
                hideLoader()
                when(it) {
                    is NetworkResult.Default -> {
                    }
                    is NetworkResult.Loading -> {
                        showLoader(requireActivity())
                    }
                    is NetworkResult.Success -> {
                      val data = it.data as? SignupResponseModel
                      when(data?.message){
                          getString(R.string.success) -> {
                              Toast.makeText(requireActivity(),"Signup successful ",Toast.LENGTH_LONG).show()
                              requireActivity().onBackPressed()
                          }
                          else -> {
                              Toast.makeText(requireActivity(),data?.message,Toast.LENGTH_LONG).show()
                          }
                      }
                    }
                    is NetworkResult.Error -> { Toast.makeText(requireActivity(),it.error, Toast.LENGTH_LONG).show() }
                }
            }
        }
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.btnSignup -> { viewModel.makeSignup(binding.model) }
            R.id.ivProfile -> {
                if (!checkCameraPermissions(requireActivity())) requestCameraPermission(onCameraLaucher)
                else if (!checkStoragePermissions(requireActivity())) requestStoragePermission(requireActivity(), onScopeResultLaucher = onScopeResultLaucher, onPermissionlaucher = onPermissionlaucher)
                else path=cameraIntent(requireActivity(),resultLauncher)
            }
        }
    }


    private var onScopeResultLaucher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if(result.resultCode == Activity.RESULT_OK){
            path=cameraIntent(requireActivity(),resultLauncher)
        }
    }

    private var onCameraLaucher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()){ permissions->
        var permission=true
        permissions.entries.forEach {
            if(!it.value){ permission=it.value }
        }
        when (permission){
            true -> {
                if (!checkStoragePermissions(requireActivity())) requestStoragePermission(requireActivity(),
                                             onScopeResultLaucher = onScopeResultLaucher, onPermissionlaucher = onPermissionlaucher)
                else path=cameraIntent(requireActivity(),resultLauncher)
            }
            else ->{ Toast.makeText(requireActivity(),"Please allow permission for camera",Toast.LENGTH_LONG).show() }
        }
    }

    private var onPermissionlaucher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
        var permission=true
        permissions.entries.forEach {
            if(!it.value){ permission=it.value }
        }
        when(permission){
            true ->{ path=cameraIntent(requireActivity(),resultLauncher) }
            else ->{ Toast.makeText(requireActivity(),"Please allow permission",Toast.LENGTH_LONG).show() }
        }
    }


    private var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if(result.resultCode == Activity.RESULT_OK) {
            if(result.data!=null){
                path = getPath(requireActivity(), Uri.parse(result?.data?.dataString))
            }
            binding.ivProfile.loadProfile(path, binding.progressBar)
            binding.model?.profile_icon = path
        }
    }


}