
package com.nextia.domain.models.credit_year_info;
/**
 * Secondary class to get the CreditYearInfoResponse class
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DatosTecnicos {

    @SerializedName("codigoRespuesta")
    @Expose
    private String codigoRespuesta;
    @SerializedName("descripcionRespuesta")
    @Expose
    private String descripcionRespuesta;
    @SerializedName("numeroFormato")
    @Expose
    private String numeroFormato;

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

    public String getNumeroFormato() {
        return numeroFormato;
    }

    public void setNumeroFormato(String numeroFormato) {
        this.numeroFormato = numeroFormato;
    }

}
