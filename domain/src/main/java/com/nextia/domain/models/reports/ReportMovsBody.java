package com.nextia.domain.models.reports;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReportMovsBody {
    @SerializedName("credito")
    @Expose
    private String credito;
    @SerializedName("desde")
    @Expose
    private String desde;
    @SerializedName("nombre_acreditado")
    @Expose
    private String nombreAcreditado;
    @SerializedName("tipo_cantidades")
    @Expose
    private String tipoCantidades;

    public String getCredito() {
        return credito;
    }

    public void setCredito(String credito) {
        this.credito = credito;
    }

    public String getDesde() {
        return desde;
    }

    public void setDesde(String desde) {
        this.desde = desde;
    }

    public String getNombreAcreditado() {
        return nombreAcreditado;
    }

    public void setNombreAcreditado(String nombreAcreditado) {
        this.nombreAcreditado = nombreAcreditado;
    }

    public String getTipoCantidades() {
        return tipoCantidades;
    }

    public void setTipoCantidades(String tipoCantidades) {
        this.tipoCantidades = tipoCantidades;
    }
}
