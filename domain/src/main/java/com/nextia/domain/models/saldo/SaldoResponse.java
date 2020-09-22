
package com.nextia.domain.models.saldo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.nextia.domain.models.StatusServicio;

public class SaldoResponse {

    @SerializedName("saldoSARTotal")
    @Expose
    private Double saldoSARTotal;
    @SerializedName("saldoAnterior")
    @Expose
    private Double saldoAnterior;
    @SerializedName("saldoSAR92")
    @Expose
    private String saldoSAR92;
    @SerializedName("saldoSAR97")
    @Expose
    private String saldoSAR97;
    @SerializedName("NSS")
    @Expose
    private String nSS;
    @SerializedName("bimestre")
    @Expose
    private Integer bimestre;
    @SerializedName("fechaPago")
    @Expose
    private String fechaPago;
    @SerializedName("nombreEmpresa")
    @Expose
    private String nombreEmpresa;
    @SerializedName("aportacion")
    @Expose
    private Double aportacion;
    @SerializedName("StatusServicio")
    @Expose
    private StatusServicio statusServicio;

    public Double getSaldoSARTotal() {
        return saldoSARTotal;
    }

    public void setSaldoSARTotal(Double saldoSARTotal) {
        this.saldoSARTotal = saldoSARTotal;
    }

    public Double getSaldoAnterior() {
        return saldoAnterior;
    }

    public void setSaldoAnterior(Double saldoAnterior) {
        this.saldoAnterior = saldoAnterior;
    }

    public String getSaldoSAR92() {
        return saldoSAR92;
    }

    public void setSaldoSAR92(String saldoSAR92) {
        this.saldoSAR92 = saldoSAR92;
    }

    public String getSaldoSAR97() {
        return saldoSAR97;
    }

    public void setSaldoSAR97(String saldoSAR97) {
        this.saldoSAR97 = saldoSAR97;
    }

    public String getNSS() {
        return nSS;
    }

    public void setNSS(String nSS) {
        this.nSS = nSS;
    }

    public Integer getBimestre() {
        return bimestre;
    }

    public void setBimestre(Integer bimestre) {
        this.bimestre = bimestre;
    }

    public String getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(String fechaPago) {
        this.fechaPago = fechaPago;
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    public Double getAportacion() {
        return aportacion;
    }

    public void setAportacion(Double aportacion) {
        this.aportacion = aportacion;
    }

    public StatusServicio getStatusServicio() {
        return statusServicio;
    }

    public void setStatusServicio(StatusServicio statusServicio) {
        this.statusServicio = statusServicio;
    }

}
