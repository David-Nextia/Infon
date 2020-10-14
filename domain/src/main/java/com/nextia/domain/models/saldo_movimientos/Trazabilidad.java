
package com.nextia.domain.models.saldo_movimientos;
/**
 * Secondary class to get the SaldoMovimientosBody class
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Trazabilidad {
    @SerializedName("codigo")
    @Expose
    private String codigo;

    @SerializedName("mensaje")
    @Expose
    private String mensaje;

    @SerializedName("ordenServicio")
    @Expose
    private String ordenServicio;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getOrdenServicio() {
        return ordenServicio;
    }

    public void setOrdenServicio(String ordenServicio) {
        this.ordenServicio = ordenServicio;
    }
}
