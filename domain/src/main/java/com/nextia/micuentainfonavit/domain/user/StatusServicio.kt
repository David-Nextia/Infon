package com.nextia.micuentainfonavit.domain.user


import com.google.gson.annotations.SerializedName

data class StatusServicio(
    @SerializedName("codigo")
    val codigo: String,
    @SerializedName("mensaje")
    val mensaje: String
)