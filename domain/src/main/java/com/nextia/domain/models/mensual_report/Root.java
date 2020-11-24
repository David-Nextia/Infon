package com.nextia.domain.models.mensual_report;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Root {
    @SerializedName("codigo")
    @Expose
    private String codigo;
    @SerializedName("mensaje")
    @Expose
    private String mensaje;
    @SerializedName("periodo")
    @Expose
    private String periodo;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }
}
