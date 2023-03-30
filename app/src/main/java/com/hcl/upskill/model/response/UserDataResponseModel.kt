package com.hcl.upskill.model.response

data class UserDataResponseModel(
    var email: String?,
    var first_name: String?,
    var last_name: String?,
    var profile_icon: String?,
    var user_id: Int?,
    var username: String?
)