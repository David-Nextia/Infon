
package com.nextia.domain.models.credit_year_info;
/**
 * class of the response of get credit year post
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.nextia.domain.models.StatusServicio;

public class CreditYearInfoResponse {

    @SerializedName("datosTecnicos")
    @Expose
    private DatosTecnicos datosTecnicos;
    @SerializedName("datosGenerales")
    @Expose
    private DatosGenerales datosGenerales;
    @SerializedName("datosFinancieros")
    @Expose
    private DatosFinancieros datosFinancieros;
    @SerializedName("datosSectorFinanciero")
    @Expose
    private DatosSectorFinanciero datosSectorFinanciero;
    @SerializedName("datoCFDI")
    @Expose
    private DatoCFDI datoCFDI;
    @SerializedName("StatusServicio")
    @Expose
    private StatusServicio statusServicio;

    public DatosTecnicos getDatosTecnicos() {
        return datosTecnicos;
    }

    public void setDatosTecnicos(DatosTecnicos datosTecnicos) {
        this.datosTecnicos = datosTecnicos;
    }

    public DatosGenerales getDatosGenerales() {
        return datosGenerales;
    }

    public void setDatosGenerales(DatosGenerales datosGenerales) {
        this.datosGenerales = datosGenerales;
    }

    public DatosFinancieros getDatosFinancieros() {
        return datosFinancieros;
    }

    public void setDatosFinancieros(DatosFinancieros datosFinancieros) {
        this.datosFinancieros = datosFinancieros;
    }

    public DatosSectorFinanciero getDatosSectorFinanciero() {
        return datosSectorFinanciero;
    }

    public void setDatosSectorFinanciero(DatosSectorFinanciero datosSectorFinanciero) {
        this.datosSectorFinanciero = datosSectorFinanciero;
    }

    public DatoCFDI getDatoCFDI() {
        return datoCFDI;
    }

    public void setDatoCFDI(DatoCFDI datoCFDI) {
        this.datoCFDI = datoCFDI;
    }

    public StatusServicio getStatusServicio() {
        return statusServicio;
    }

    public void setStatusServicio(StatusServicio statusServicio) {
        this.statusServicio = statusServicio;
    }

}
