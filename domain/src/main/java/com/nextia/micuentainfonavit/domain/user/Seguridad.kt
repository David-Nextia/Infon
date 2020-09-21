package com.nextia.micuentainfonavit.domain.user


import com.google.gson.annotations.SerializedName

data class Seguridad(
    @SerializedName("sessionToken")
    val sessionToken: String
)