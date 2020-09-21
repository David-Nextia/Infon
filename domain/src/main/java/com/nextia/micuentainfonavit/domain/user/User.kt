package com.nextia.micuentainfonavit.domain.user


import com.google.gson.annotations.SerializedName

data class User(
        @SerializedName("apMaterno")
    val apMaterno: String,
        @SerializedName("apPaterno")
    val apPaterno: String,
        @SerializedName("Biometrico")
    val biometrico: Boolean,
        @SerializedName("Credito")
    val credito: List<Credito>,
        @SerializedName("emailPersonal")
    val emailPersonal: String,
        @SerializedName("idPerfilglobal")
    val idPerfilglobal: String,
        @SerializedName("nombre")
    val nombre: String,
        @SerializedName("Notificacion")
    val notificacion: Boolean,
        @SerializedName("nss")
    val nss: Long,
        @SerializedName("rfc")
    val rfc: String,
        @SerializedName("scurp")
    val scurp: String,
        @SerializedName("Seguridad")
    val seguridad: Seguridad,
        @SerializedName("StatusServicio")
    val statusServicio: StatusServicio,
        @SerializedName("telefonoCelular")
    val telefonoCelular: String
)