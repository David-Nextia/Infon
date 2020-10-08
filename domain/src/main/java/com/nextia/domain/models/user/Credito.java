
package com.nextia.domain.models.user;
/**
 * Secondary class to get the UserResponse class
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Credito {

    @SerializedName("numeroCredito")
    @Expose
    private String numeroCredito;
    @SerializedName("tipoCredito")
    @Expose
    private String tipoCredito;
    @SerializedName("estatusCredito")
    @Expose
    private String estatusCredito;

    public String getNumeroCredito() {
        return numeroCredito;
    }

    public void setNumeroCredito(String numeroCredito) {
        this.numeroCredito = numeroCredito;
    }

    public String getTipoCredito() {
        return tipoCredito;
    }

    public void setTipoCredito(String tipoCredito) {
        this.tipoCredito = tipoCredito;
    }

    public String getEstatusCredito() {
        return estatusCredito;
    }

    public void setEstatusCredito(String estatusCredito) {
        this.estatusCredito = estatusCredito;
    }

}
