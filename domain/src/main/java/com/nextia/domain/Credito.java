
package com.nextia.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Credito {

    @SerializedName("numeroCredito")
    @Expose
    private Integer numeroCredito;
    @SerializedName("tipoCredito")
    @Expose
    private String tipoCredito;
    @SerializedName("estatusCredito")
    @Expose
    private String estatusCredito;

    public Integer getNumeroCredito() {
        return numeroCredito;
    }

    public void setNumeroCredito(Integer numeroCredito) {
        this.numeroCredito = numeroCredito;
    }

    public String getTipoCredito() {
        return tipoCredito;
    }

    public void setTipoCredito(String tipoCredito) {
        this.tipoCredito = tipoCredito;
    }

    public String getEstatusCredito() {
        return estatusCredito;
    }

    public void setEstatusCredito(String estatusCredito) {
        this.estatusCredito = estatusCredito;
    }

}
