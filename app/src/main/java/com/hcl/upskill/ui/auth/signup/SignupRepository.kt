package com.hcl.upskill.ui.auth.signup

import com.hcl.upskill.model.request.auth.SignupModel
import com.hcl.upskill.repository.remote.Skill
import com.hcl.upskill.util.NetworkResult
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject


class SignupRepository @Inject constructor (private val apiSkill: Skill) {

   suspend fun makeSignup(model: SignupModel?) = flow<NetworkResult<Any?>> {
       emit(NetworkResult.Loading)

       val mimeText = "text/plain".toMediaTypeOrNull()
       val mimeImage = "image/*".toMediaTypeOrNull()

       val data: MutableMap<String?, RequestBody?> = HashMap()

       data["first_name"] = model?.first_name?.toRequestBody(mimeText)
       data["last_name"] = model?.last_name?.toRequestBody(mimeText)
       data["email"] = model?.email?.toRequestBody(mimeText)
       data["username"] = model?.username?.toRequestBody(mimeText)
       data["password"] = model?.password?.toRequestBody(mimeText)

       val imgPart : MultipartBody.Part =
               if(model?.profile_icon?.isEmpty() == false) {
               val file = File(model.profile_icon?:"")
               val requestBody = file.asRequestBody(mimeImage)
               MultipartBody.Part.createFormData("profile_icon",file.name,requestBody)
           } else {
               MultipartBody.Part.createFormData("profile_icon","")
           }

        val response = apiSkill.signup(imgPart,data)
        emit(NetworkResult.Success(response))
   }.catch {
        emit(NetworkResult.Error(it.message?:"Unknown"))
    }
}