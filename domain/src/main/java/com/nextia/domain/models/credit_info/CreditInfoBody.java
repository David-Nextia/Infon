package com.nextia.domain.models.credit_info;
/**
 * Class body to post and get credit info
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreditInfoBody {
    @SerializedName("numeroCredito")
    @Expose
    private String numeroCredito;

    public String getNumeroCredito() {
        return numeroCredito;
    }

    public void setNumeroCredito(String numeroCredito) {
        this.numeroCredito = numeroCredito;
    }
}
