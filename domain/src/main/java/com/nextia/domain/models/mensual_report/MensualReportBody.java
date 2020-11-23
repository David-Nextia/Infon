package com.nextia.domain.models.mensual_report;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MensualReportBody {
    @SerializedName("numeroCredito")
    @Expose
    private String numeroCredito;
    @SerializedName("periodo")
    @Expose
    private String periodo;

    public String getNumeroCredito() {
        return numeroCredito;
    }

    public void setNumeroCredito(String numeroCredito) {
        this.numeroCredito = numeroCredito;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }
}
