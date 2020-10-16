package com.nextia.domain.models.aviso_suspension;
/**
 * Class body to post and get aviso pdf
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AvisosPDFBody {

    @SerializedName("numeroCredito")
    @Expose
    private String numeroCredito;

    public AvisosPDFBody() {
        this.numeroCredito = numeroCredito;
    }

    public String getNumeroCredito() {
        return numeroCredito;
    }

    public void setNumeroCredito(String numeroCredito) {
        this.numeroCredito = numeroCredito;
    }
}
