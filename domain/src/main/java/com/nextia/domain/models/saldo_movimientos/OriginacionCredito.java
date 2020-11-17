
package com.nextia.domain.models.saldo_movimientos;
/**
 * Secondary class to get the RespuestasDoMovs class
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OriginacionCredito {

    @SerializedName("v17FechaOtorgamiento")
    @Expose
    private String v17FechaOtorgamiento;

    @SerializedName("v18Moneda")
    @Expose
    private String v18Moneda;

    @SerializedName("v19MtoOtorgaVsm")
    @Expose
    private String v19MtoOtorgaVsm;

    @SerializedName("v20MtoOtorgaPes")
    @Expose
    private String v20MtoOtorgaPes;

    @SerializedName("v21TasaInteres")
    @Expose
    private String v21TasaInteres;

    @SerializedName("v22TipoTasaInteres")
    @Expose
    private String v22TipoTasaInteres;

    @SerializedName("v23DescTipPago")
    @Expose
    private String v23DescTipPago;

    @SerializedName("v24Delegacion")
    @Expose
    private String v24Delegacion;

    @SerializedName("v25Plazo")
    @Expose
    private String v25Plazo;

    public String getV17FechaOtorgamiento() {
        return v17FechaOtorgamiento;
    }

    public void setV17FechaOtorgamiento(String v17FechaOtorgamiento) {
        this.v17FechaOtorgamiento = v17FechaOtorgamiento;
    }

    public String getV18Moneda() {
        return v18Moneda;
    }

    public void setV18Moneda(String v18Moneda) {
        this.v18Moneda = v18Moneda;
    }

    public String getV19MtoOtorgaVsm() {
        return v19MtoOtorgaVsm;
    }

    public void setV19MtoOtorgaVsm(String v19MtoOtorgaVsm) {
        this.v19MtoOtorgaVsm = v19MtoOtorgaVsm;
    }

    public String getV20MtoOtorgaPes() {
        return v20MtoOtorgaPes;
    }

    public void setV20MtoOtorgaPes(String v20MtoOtorgaPes) {
        this.v20MtoOtorgaPes = v20MtoOtorgaPes;
    }

    public String getV21TasaInteres() {
        return v21TasaInteres;
    }

    public String getV21TasaInteresPercent() {
        return v21TasaInteres.trim() + " %";
    }

    public void setV21TasaInteres(String v21TasaInteres) {
        this.v21TasaInteres = v21TasaInteres;
    }

    public String getV22TipoTasaInteres() {
        return v22TipoTasaInteres;
    }

    public void setV22TipoTasaInteres(String v22TipoTasaInteres) {
        this.v22TipoTasaInteres = v22TipoTasaInteres;
    }

    public String getV23DescTipPago() {
        return v23DescTipPago;
    }

    public void setV23DescTipPago(String v23DescTipPago) {
        this.v23DescTipPago = v23DescTipPago;
    }

    public String getV24Delegacion() {
        return v24Delegacion;
    }

    public void setV24Delegacion(String v24Delegacion) {
        this.v24Delegacion = v24Delegacion;
    }

    public String getV25Plazo() {
        return v25Plazo;
    }

    public void setV25Plazo(String v25Plazo) {
        this.v25Plazo = v25Plazo;
    }
}
