package com.hcl.upskill.model.response

data class ProfilesResponseModel(
    var data: MutableList<UserDataResponseModel?>?,
    val message: String?,
    val status: Int?,
    val success: Boolean?)
