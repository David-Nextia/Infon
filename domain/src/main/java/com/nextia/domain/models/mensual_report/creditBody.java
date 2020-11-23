package com.nextia.domain.models.mensual_report;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class creditBody {
    @SerializedName("credito")
    @Expose
    private String credito;

    public String getCredito() {
        return credito;
    }

    public void setCredito(String credito) {
        this.credito = credito;
    }
}
