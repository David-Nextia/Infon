
package com.nextia.domain.models.credit_year_info;
/**
 * Secondary class to get the CreditYearInfoResponse class
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Domicilio {

    @SerializedName("calleNumero")
    @Expose
    private String calleNumero;
    @SerializedName("colonia")
    @Expose
    private String colonia;
    @SerializedName("poblacion")
    @Expose
    private String poblacion;
    @SerializedName("estado")
    @Expose
    private String estado;
    @SerializedName("cp")
    @Expose
    private Integer cp=0;

    public String getCalleNumero() {
        return calleNumero;
    }

    public void setCalleNumero(String calleNumero) {
        this.calleNumero = calleNumero;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getPoblacion() {
        return poblacion;
    }

    public void setPoblacion(String poblacion) {
        this.poblacion = poblacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Integer getCp() {
        return cp;
    }

    public void setCp(Integer cp) {
        this.cp = cp;
    }

}
