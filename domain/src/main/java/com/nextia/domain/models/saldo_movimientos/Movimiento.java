package com.nextia.domain.models.saldo_movimientos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Movimiento {
    @SerializedName("Fecha")
    @Expose
    private String fecha;
    @SerializedName("Clave")
    @Expose
    private String clave;
    @SerializedName("Concepto")
    @Expose
    private String concepto;
    @SerializedName("Origen")
    @Expose
    private String origen;
    @SerializedName("MontoTransaccionVSM")
    @Expose
    private String montoTransaccionVSM;
    @SerializedName("MontoTransaccion")
    @Expose
    private String montoTransaccion;
    @SerializedName("PagoASeguro")
    @Expose
    private String pagoASeguro;
    @SerializedName("PagoAInteresVSM")
    @Expose
    private String pagoAInteresVSM;
    @SerializedName("PagoAIntereses")
    @Expose
    private String pagoAIntereses;
    @SerializedName("PagoAMoratoriosVSM")
    @Expose
    private String pagoAMoratoriosVSM;
    @SerializedName("PagoAMoratorios")
    @Expose
    private String pagoAMoratorios;
    @SerializedName("PagoACapitalVSM")
    @Expose
    private String pagoACapitalVSM;
    @SerializedName("PagoACapital")
    @Expose
    private String pagoACapital;
    @SerializedName("SaldoACapitalVSM")
    @Expose
    private String saldoACapitalVSM;
    @SerializedName("SaldoACapital")
    @Expose
    private String saldoACapital;

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getMontoTransaccionVSM() {
        return montoTransaccionVSM;
    }

    public void setMontoTransaccionVSM(String montoTransaccionVSM) {
        this.montoTransaccionVSM = montoTransaccionVSM;
    }

    public String getMontoTransaccion() {
        return montoTransaccion;
    }

    public void setMontoTransaccion(String montoTransaccion) {
        this.montoTransaccion = montoTransaccion;
    }

    public String getPagoASeguro() {
        return pagoASeguro;
    }

    public void setPagoASeguro(String pagoASeguro) {
        this.pagoASeguro = pagoASeguro;
    }

    public String getPagoAInteresVSM() {
        return pagoAInteresVSM;
    }

    public void setPagoAInteresVSM(String pagoAInteresVSM) {
        this.pagoAInteresVSM = pagoAInteresVSM;
    }

    public String getPagoAIntereses() {
        return pagoAIntereses;
    }

    public void setPagoAIntereses(String pagoAIntereses) {
        this.pagoAIntereses = pagoAIntereses;
    }

    public String getPagoAMoratoriosVSM() {
        return pagoAMoratoriosVSM;
    }

    public void setPagoAMoratoriosVSM(String pagoAMoratoriosVSM) {
        this.pagoAMoratoriosVSM = pagoAMoratoriosVSM;
    }

    public String getPagoAMoratorios() {
        return pagoAMoratorios;
    }

    public void setPagoAMoratorios(String pagoAMoratorios) {
        this.pagoAMoratorios = pagoAMoratorios;
    }

    public String getPagoACapitalVSM() {
        return pagoACapitalVSM;
    }

    public void setPagoACapitalVSM(String pagoACapitalVSM) {
        this.pagoACapitalVSM = pagoACapitalVSM;
    }

    public String getPagoACapital() {
        return pagoACapital;
    }

    public void setPagoACapital(String pagoACapital) {
        this.pagoACapital = pagoACapital;
    }

    public String getSaldoACapitalVSM() {
        return saldoACapitalVSM;
    }

    public void setSaldoACapitalVSM(String saldoACapitalVSM) {
        this.saldoACapitalVSM = saldoACapitalVSM;
    }

    public String getSaldoACapital() {
        return saldoACapital;
    }

    public void setSaldoACapital(String saldoACapital) {
        this.saldoACapital = saldoACapital;
    }
}
