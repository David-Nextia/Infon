
package com.nextia.domain.models.credit_year_info;
/**
 * Secondary class to get the CreditYearInfoResponse class
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DatosFinancieros {

    @SerializedName("interesDevengado")
    @Expose
    private Double interesDevengado;
    @SerializedName("interesPagado")
    @Expose
    private Double interesPagado;
    @SerializedName("interesReal")
    @Expose
    private Double interesReal;
    @SerializedName("fechaOriginacionCredito")
    @Expose
    private Integer fechaOriginacionCredito;
    @SerializedName("fechaTerminacionCredito")
    @Expose
    private Integer fechaTerminacionCredito;
    @SerializedName("indicador")
    @Expose
    private String indicador;
    @SerializedName("pool")
    @Expose
    private Integer pool;

    public Double getInteresDevengado() {
        return interesDevengado;
    }

    public void setInteresDevengado(Double interesDevengado) {
        this.interesDevengado = interesDevengado;
    }

    public Double getInteresPagado() {
        return interesPagado;
    }

    public void setInteresPagado(Double interesPagado) {
        this.interesPagado = interesPagado;
    }

    public Double getInteresReal() {
        return interesReal;
    }

    public void setInteresReal(Double interesReal) {
        this.interesReal = interesReal;
    }

    public Integer getFechaOriginacionCredito() {
        return fechaOriginacionCredito;
    }

    public void setFechaOriginacionCredito(Integer fechaOriginacionCredito) {
        this.fechaOriginacionCredito = fechaOriginacionCredito;
    }

    public Integer getFechaTerminacionCredito() {
        return fechaTerminacionCredito;
    }

    public void setFechaTerminacionCredito(Integer fechaTerminacionCredito) {
        this.fechaTerminacionCredito = fechaTerminacionCredito;
    }

    public String getIndicador() {
        return indicador;
    }

    public void setIndicador(String indicador) {
        this.indicador = indicador;
    }

    public Integer getPool() {
        return pool;
    }

    public void setPool(Integer pool) {
        this.pool = pool;
    }

}
