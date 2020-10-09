package com.nextia.domain.models.credit_info;
/**
 * Secondary class to get the CreditInfoResponse class
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RespuestUm {
    @SerializedName("codigoRespuesta")
    @Expose
    private String codigoRespuesta;
    @SerializedName("descripcionRespuesta")
    @Expose
    private String descripcionRespuesta;
    @SerializedName("numeroCredito")
    @Expose
    private String numeroCredito;
    @SerializedName("ejercicioFiscal")
    @Expose
    private String ejercicioFiscal;

    public String getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public void setCodigoRespuesta(String codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }

    public String getDescripcionRespuesta() {
        return descripcionRespuesta;
    }

    public void setDescripcionRespuesta(String descripcionRespuesta) {
        this.descripcionRespuesta = descripcionRespuesta;
    }

    public String getNumeroCredito() {
        return numeroCredito;
    }

    public void setNumeroCredito(String numeroCredito) {
        this.numeroCredito = numeroCredito;
    }

    public String getEjercicioFiscal() {
        return ejercicioFiscal;
    }

    public void setEjercicioFiscal(String ejercicioFiscal) {
        this.ejercicioFiscal = ejercicioFiscal;
    }
}
