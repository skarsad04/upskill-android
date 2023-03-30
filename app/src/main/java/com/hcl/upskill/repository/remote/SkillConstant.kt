package com.hcl.upskill.repository.remote

object SkillConstant {
//    internal const val BASE_URL ="http://127.0.0.1:8000"
    internal const val BASE_URL ="http://13.233.102.144:8000"

    // AUTH
    internal const val LOGIN ="/login/"
    internal const val SIGNUP ="/account/create_user/"

    // PROFILE
    internal const val PROFILES ="/account/list_users/"
    internal const val PROFILE_DETAIL ="/account/details_users/"

    // REFRESH TOKEN
    internal const val REFRESH_TOKEN="/refresh_token/"

}