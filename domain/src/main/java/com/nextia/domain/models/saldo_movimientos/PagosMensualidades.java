
package com.nextia.domain.models.saldo_movimientos;
/**
 * Secondary class to get the RespuestasDoMovs class
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PagosMensualidades {

    @SerializedName("v10TipoCreditoFam")
    @Expose
    private String v10TipoCreditoFam;

    @SerializedName("v1TipoCredito")
    @Expose
    private String v1TipoCredito;

    @SerializedName("v2FechaLiquidacion")
    @Expose
    private String v2FechaLiquidacion;

    @SerializedName("v3TipoLiquidacion")
    @Expose
    private String v3TipoLiquidacion;

    @SerializedName("v4DescLegalStatus")
    @Expose
    private String v4DescLegalStatus;

    @SerializedName("v5DespachoCobranza")
    @Expose
    private String v5DespachoCobranza;

    @SerializedName("v6FechaActualAls")
    @Expose
    private String v6FechaActualAls;

    @SerializedName("v7FechaReestructura")
    @Expose
    private String v7FechaReestructura;

    @SerializedName("v8NombreReestructur")
    @Expose
    private String v8NombreReestructur;

    @SerializedName("v9FechaAsignacion")
    @Expose
    private int v9FechaAsignacion;

    public String getV10TipoCreditoFam() {
        return v10TipoCreditoFam;
    }

    public void setV10TipoCreditoFam(String v10TipoCreditoFam) {
        this.v10TipoCreditoFam = v10TipoCreditoFam;
    }

    public String getV1TipoCredito() {
        return v1TipoCredito;
    }

    public void setV1TipoCredito(String v1TipoCredito) {
        this.v1TipoCredito = v1TipoCredito;
    }

    public String getV2FechaLiquidacion() {
        return v2FechaLiquidacion;
    }

    public void setV2FechaLiquidacion(String v2FechaLiquidacion) {
        this.v2FechaLiquidacion = v2FechaLiquidacion;
    }

    public String getV3TipoLiquidacion() {
        return v3TipoLiquidacion;
    }

    public void setV3TipoLiquidacion(String v3TipoLiquidacion) {
        this.v3TipoLiquidacion = v3TipoLiquidacion;
    }

    public String getV4DescLegalStatus() {
        return v4DescLegalStatus;
    }

    public void setV4DescLegalStatus(String v4DescLegalStatus) {
        this.v4DescLegalStatus = v4DescLegalStatus;
    }

    public String getV5DespachoCobranza() {
        return v5DespachoCobranza;
    }

    public void setV5DespachoCobranza(String v5DespachoCobranza) {
        this.v5DespachoCobranza = v5DespachoCobranza;
    }

    public String getV6FechaActualAls() {
        return v6FechaActualAls;
    }

    public void setV6FechaActualAls(String v6FechaActualAls) {
        this.v6FechaActualAls = v6FechaActualAls;
    }

    public String getV7FechaReestructura() {
        return v7FechaReestructura;
    }

    public void setV7FechaReestructura(String v7FechaReestructura) {
        this.v7FechaReestructura = v7FechaReestructura;
    }

    public String getV8NombreReestructur() {
        return v8NombreReestructur;
    }

    public void setV8NombreReestructur(String v8NombreReestructur) {
        this.v8NombreReestructur = v8NombreReestructur;
    }

    public int getV9FechaAsignacion() {
        return v9FechaAsignacion;
    }

    public void setV9FechaAsignacion(int v9FechaAsignacion) {
        this.v9FechaAsignacion = v9FechaAsignacion;
    }
}
