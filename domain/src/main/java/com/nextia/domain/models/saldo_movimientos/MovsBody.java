package com.nextia.domain.models.saldo_movimientos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MovsBody {
    @SerializedName("NumeroCreditoEntrada")
    @Expose
    private String numeroCreditoEntrada;
    @SerializedName("FechaInicioEntrada")
    @Expose
    private String fechaInicioEntrada;
    @SerializedName("FechaFinEntrada")
    @Expose
    private String fechaFinEntrada;
    @SerializedName("TipoTransaccion")
    @Expose
    private String tipoTransaccion;

    public String getNumeroCreditoEntrada() {
        return numeroCreditoEntrada;
    }

    public void setNumeroCreditoEntrada(String numeroCreditoEntrada) {
        this.numeroCreditoEntrada = numeroCreditoEntrada;
    }

    public String getFechaInicioEntrada() {
        return fechaInicioEntrada;
    }

    public void setFechaInicioEntrada(String fechaInicioEntrada) {
        this.fechaInicioEntrada = fechaInicioEntrada;
    }

    public String getFechaFinEntrada() {
        return fechaFinEntrada;
    }

    public void setFechaFinEntrada(String fechaFinEntrada) {
        this.fechaFinEntrada = fechaFinEntrada;
    }

    public String getTipoTransaccion() {
        return tipoTransaccion;
    }

    public void setTipoTransaccion(String tipoTransaccion) {
        this.tipoTransaccion = tipoTransaccion;
    }
}
