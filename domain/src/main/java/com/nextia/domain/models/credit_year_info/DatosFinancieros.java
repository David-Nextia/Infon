
package com.nextia.domain.models.credit_year_info;
/**
 * Secondary class to get the CreditYearInfoResponse class
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DatosFinancieros {

    @SerializedName("interesDevengado")
    @Expose
    private String interesDevengado;
    @SerializedName("interesPagado")
    @Expose
    private String interesPagado;
    @SerializedName("interesReal")
    @Expose
    private String interesReal;
    @SerializedName("fechaOriginacionCredito")
    @Expose
    private String fechaOriginacionCredito;
    @SerializedName("fechaTerminacionCredito")
    @Expose
    private String fechaTerminacionCredito;
    @SerializedName("indicador")
    @Expose
    private String indicador;
    @SerializedName("pool")
    @Expose
    private String pool;



    public String getInteresDevengado() {
        return interesDevengado;
    }

    public void setInteresDevengado(String interesDevengado) {
        this.interesDevengado = interesDevengado;
    }

    public String getInteresPagado() {
        return interesPagado;
    }

    public void setInteresPagado(String interesPagado) {
        this.interesPagado = interesPagado;
    }

    public String getInteresReal() {
        return interesReal;
    }

    public void setInteresReal(String interesReal) {
        this.interesReal = interesReal;
    }

    public String getFechaOriginacionCredito() {
        return fechaOriginacionCredito;
    }

    public void setFechaOriginacionCredito(String fechaOriginacionCredito) {
        this.fechaOriginacionCredito = fechaOriginacionCredito;
    }

    public String getFechaTerminacionCredito() {
        return fechaTerminacionCredito;
    }

    public void setFechaTerminacionCredito(String fechaTerminacionCredito) {
        this.fechaTerminacionCredito = fechaTerminacionCredito;
    }

    public String getIndicador() {
        return indicador;
    }

    public void setIndicador(String indicador) {
        this.indicador = indicador;
    }

    public String getPool() {
        return pool;
    }

    public void setPool(String pool) {
        this.pool = pool;
    }

}
