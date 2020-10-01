
package com.nextia.domain.models.credit_year_info;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DatosSectorFinanciero {

    @SerializedName("numeroInstitucionBancaria")
    @Expose
    private String numeroInstitucionBancaria;
    @SerializedName("rfc")
    @Expose
    private String rfc;
    @SerializedName("razonSocial")
    @Expose
    private String razonSocial;
    @SerializedName("domicilioFiscal")
    @Expose
    private String domicilioFiscal;

    public String getNumeroInstitucionBancaria() {
        return numeroInstitucionBancaria;
    }

    public void setNumeroInstitucionBancaria(String numeroInstitucionBancaria) {
        this.numeroInstitucionBancaria = numeroInstitucionBancaria;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getDomicilioFiscal() {
        return domicilioFiscal;
    }

    public void setDomicilioFiscal(String domicilioFiscal) {
        this.domicilioFiscal = domicilioFiscal;
    }

}
