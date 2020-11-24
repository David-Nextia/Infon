package com.nextia.domain.models.reports;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Return {
    @SerializedName("codigo")
    @Expose
    private String codigo;
    @SerializedName("mensaje")
    @Expose
    private String mensaje;
    @SerializedName("reporte")
    @Expose
    private String reporte;

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

    public String getReporte() {
        return reporte;
    }

    public void setReporte(String reporte) {
        this.reporte = reporte;
    }

}
