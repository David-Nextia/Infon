
package com.nextia.domain.models.saldo_movimientos;
/**
 * Secondary class to get the SaldoMovimientosBody class
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RespuestasDoMovs {

    @SerializedName("tabOpcionesDePago")
    @Expose
    private OpcionesPago opcionesPago;

    @SerializedName("tabPagosMensualidad")
    @Expose
    private PagosMensualidades pagosMensualidades;

    @SerializedName("tablaPagos1")
    @Expose
    private TablaPagos1 tablaPagos1;

    @SerializedName("tabOriginacionCredito")
    @Expose
    private OriginacionCredito originacionCredito;
    @SerializedName("tablaPagos2")
    @Expose
    private TablaPagos2 tablaPagos2;

    @SerializedName("movilidad")
    @Expose
    private Movilidad movilidad;
    public void setOpcionesPago(OpcionesPago opcionesPago) {
        this.opcionesPago = opcionesPago;
    }

    public PagosMensualidades getPagosMensualidades() {
        return pagosMensualidades;
    }

    public void setPagosMensualidades(PagosMensualidades pagosMensualidades) {
        this.pagosMensualidades = pagosMensualidades;
    }

    public TablaPagos1 getTablaPagos1() {
        return tablaPagos1;
    }

    public void setTablaPagos1(TablaPagos1 tablaPagos1) {
        this.tablaPagos1 = tablaPagos1;
    }

    public OriginacionCredito getOriginacionCredito() {
        return originacionCredito;
    }

    public void setOriginacionCredito(OriginacionCredito originacionCredito) {
        this.originacionCredito = originacionCredito;
    }

    public OpcionesPago getOpcionesPago() {
        return opcionesPago;
    }
    public TablaPagos2 getTablaPagos2() {
        return tablaPagos2;
    }

    public void setTablaPagos2(TablaPagos2 tablaPagos2) {
        this.tablaPagos2 = tablaPagos2;
    }
    public Movilidad getMovilidad() {
        return movilidad;
    }

    public void setMovilidad(Movilidad movilidad) {
        this.movilidad = movilidad;
    }

}
