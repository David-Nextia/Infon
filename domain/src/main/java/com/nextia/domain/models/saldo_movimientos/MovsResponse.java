package com.nextia.domain.models.saldo_movimientos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovsResponse {
    @SerializedName("Retorno")
    @Expose
    private String retorno;
    @SerializedName("Mensaje")
    @Expose
    private String mensaje;
    @SerializedName("NumeroCreditoSalida")
    @Expose
    private String numeroCreditoSalida;
    @SerializedName("NumeroMovimientos")
    @Expose
    private String numeroMovimientos;
    @SerializedName("Movimientos")
    @Expose
    private List<Movimiento> movimientos = null;



    public String getRetorno() {
        return retorno;
    }

    public void setRetorno(String retorno) {
        this.retorno = retorno;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getNumeroCreditoSalida() {
        return numeroCreditoSalida;
    }

    public void setNumeroCreditoSalida(String numeroCreditoSalida) {
        this.numeroCreditoSalida = numeroCreditoSalida;
    }

    public String getNumeroMovimientos() {
        return numeroMovimientos;
    }

    public void setNumeroMovimientos(String numeroMovimientos) {
        this.numeroMovimientos = numeroMovimientos;
    }

    public List<Movimiento> getMovimientos() {
        return movimientos;
    }

    public void setMovimientos(List<Movimiento> movimientos) {
        this.movimientos = movimientos;
    }
}
