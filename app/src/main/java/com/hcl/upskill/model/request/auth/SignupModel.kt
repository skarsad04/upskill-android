package com.hcl.upskill.model.request.auth

data class SignupModel(var first_name: String?,var last_name: String?,var email: String?,
                       var username: String?,var password: String?,var profile_icon: String?)