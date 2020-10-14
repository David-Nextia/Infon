
package com.nextia.domain.models.saldo_movimientos;
/**
 * Secondary class to get the RespuestasDoMovs class
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OpcionesPago {

    @SerializedName("v11Sdoliqpes")
    @Expose
    private String v11Sdoliqpes;

    @SerializedName("v12Sdoliqvsm")
    @Expose
    private String v12Sdoliqvsm;

    @SerializedName("v13SdoliqpesProymesCondesc")
    @Expose
    private String v13SdoliqpesProymesCondesc;

    @SerializedName("v14SdoliqvsmProymesCondesc")
    @Expose
    private String v14SdoliqvsmProymesCondesc;

    @SerializedName("v15PgocorrienteDiaconsulta")
    @Expose
    private String v15PgocorrienteDiaconsulta;

    @SerializedName("v16PgomensualidadDiaconsulta")
    @Expose
    private String v16PgomensualidadDiaconsulta;

    public String getV11Sdoliqpes() {
        return v11Sdoliqpes;
    }

    public void setV11Sdoliqpes(String v11Sdoliqpes) {
        this.v11Sdoliqpes = v11Sdoliqpes;
    }

    public String getV12Sdoliqvsm() {
        return v12Sdoliqvsm;
    }

    public void setV12Sdoliqvsm(String v12Sdoliqvsm) {
        this.v12Sdoliqvsm = v12Sdoliqvsm;
    }

    public String getV13SdoliqpesProymesCondesc() {
        return v13SdoliqpesProymesCondesc;
    }

    public void setV13SdoliqpesProymesCondesc(String v13SdoliqpesProymesCondesc) {
        this.v13SdoliqpesProymesCondesc = v13SdoliqpesProymesCondesc;
    }

    public String getV14SdoliqvsmProymesCondesc() {
        return v14SdoliqvsmProymesCondesc;
    }

    public void setV14SdoliqvsmProymesCondesc(String v14SdoliqvsmProymesCondesc) {
        this.v14SdoliqvsmProymesCondesc = v14SdoliqvsmProymesCondesc;
    }

    public String getV15PgocorrienteDiaconsulta() {
        return v15PgocorrienteDiaconsulta;
    }

    public void setV15PgocorrienteDiaconsulta(String v15PgocorrienteDiaconsulta) {
        this.v15PgocorrienteDiaconsulta = v15PgocorrienteDiaconsulta;
    }

    public String getV16PgomensualidadDiaconsulta() {
        return v16PgomensualidadDiaconsulta;
    }

    public void setV16PgomensualidadDiaconsulta(String v16PgomensualidadDiaconsulta) {
        this.v16PgomensualidadDiaconsulta = v16PgomensualidadDiaconsulta;
    }
}
