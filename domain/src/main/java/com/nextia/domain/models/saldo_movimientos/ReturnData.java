package com.nextia.domain.models.saldo_movimientos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.nextia.domain.models.StatusServicio;
/**
 * Secondary class to get the SaldoMovimientosResponse class
 */
public class ReturnData {
    @SerializedName("codigo")
    @Expose
    private String codigo;

    @SerializedName("descripcion")
    @Expose
    private String descripcion;

    @SerializedName("respuestasdomovs")
    @Expose
    private RespuestasDoMovs respuestasDoMovs;

    @SerializedName("trazabilidad")
    @Expose
    private Trazabilidad trazabilidad;

    @SerializedName("StatusServicio")
    @Expose
    private StatusServicio statusServicio;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public RespuestasDoMovs getRespuestasDoMovs() {
        return respuestasDoMovs;
    }

    public void setRespuestasDoMovs(RespuestasDoMovs respuestasDoMovs) {
        this.respuestasDoMovs = respuestasDoMovs;
    }

    public Trazabilidad getTrazabilidad() {
        return trazabilidad;
    }

    public void setTrazabilidad(Trazabilidad trazabilidad) {
        this.trazabilidad = trazabilidad;
    }

    public StatusServicio getStatusServicio() {
        return statusServicio;
    }

    public void setStatusServicio(StatusServicio statusServicio) {
        this.statusServicio = statusServicio;
    }
}
