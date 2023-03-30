package com.hcl.upskill.model.response

data class SignupResponseModel(
    var data: UserDataResponseModel?,
    val message: String?,
    val status: Int?,
    val success: Boolean?
)