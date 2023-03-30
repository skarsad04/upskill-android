package com.hcl.upskill.repository.remote

import com.hcl.upskill.model.request.auth.LoginModel
import com.hcl.upskill.model.request.auth.SessionRequestModel
import com.hcl.upskill.model.response.LoginResponseModel
import com.hcl.upskill.model.response.ProfilesResponseModel
import com.hcl.upskill.model.response.SessionResponseModel
import com.hcl.upskill.model.response.SignupResponseModel
import com.hcl.upskill.repository.remote.SkillConstant.LOGIN
import com.hcl.upskill.repository.remote.SkillConstant.PROFILES
import com.hcl.upskill.repository.remote.SkillConstant.PROFILE_DETAIL
import com.hcl.upskill.repository.remote.SkillConstant.REFRESH_TOKEN
import com.hcl.upskill.repository.remote.SkillConstant.SIGNUP
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface Skill {

    @POST(LOGIN)
    suspend fun login(@Body model: LoginModel?) : LoginResponseModel?

    @Multipart
    @POST(SIGNUP)
    @JvmSuppressWildcards
    suspend fun signup(@Part icon : MultipartBody.Part?,
                       @PartMap map : Map<String?,RequestBody?>?) : SignupResponseModel?


    @GET(PROFILES)
    suspend fun profiles(@Header("Authorization") token: String?) : ProfilesResponseModel?

    @POST(PROFILE_DETAIL)
    suspend fun profileDetail(@Header("Authorization") token: String?) : SignupResponseModel?

    @POST(REFRESH_TOKEN)
    suspend fun refreshToken(@Header("Authorization") token: String?,
                             @Body model: SessionRequestModel?) : SessionResponseModel?

}