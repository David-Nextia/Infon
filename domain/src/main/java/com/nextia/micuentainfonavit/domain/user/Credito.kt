package com.nextia.micuentainfonavit.domain.user


import com.google.gson.annotations.SerializedName

data class Credito(
    @SerializedName("estatusCredito")
    val estatusCredito: String,
    @SerializedName("numeroCredito")
    val numeroCredito: Long,
    @SerializedName("tipoCredito")
    val tipoCredito: String
)