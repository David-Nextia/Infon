package com.nextia.domain.models.saldo_movimientos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Salidagrals {

    @SerializedName("mensaje")
    @Expose
    private String mensaje;
    @SerializedName("tipoCaso")
    @Expose
    private String tipoCaso;

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getTipoCaso() {
        return tipoCaso;
    }

    public void setTipoCaso(String tipoCaso) {
        this.tipoCaso = tipoCaso;
    }
}
