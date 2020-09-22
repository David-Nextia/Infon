
package com.nextia.domain.models.saldo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.nextia.domain.models.Seguridad;

public class SaldoBody {

    @SerializedName("NSS")
    @Expose
    private String nSS;
    @SerializedName("Canal")
    @Expose
    private String canal;
    @SerializedName("Control")
    @Expose
    private String control;
    @SerializedName("Id")
    @Expose
    private String id;
    @SerializedName("Origen")
    @Expose
    private String origen;
    @SerializedName("RFC")
    @Expose
    private String rFC;
    @SerializedName("Seguridad")
    @Expose
    private Seguridad seguridad;

    public String getNSS() {
        return nSS;
    }

    public void setNSS(String nSS) {
        this.nSS = nSS;
    }

    public String getCanal() {
        return canal;
    }

    public void setCanal(String canal) {
        this.canal = canal;
    }

    public String getControl() {
        return control;
    }

    public void setControl(String control) {
        this.control = control;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getRFC() {
        return rFC;
    }

    public void setRFC(String rFC) {
        this.rFC = rFC;
    }

    public Seguridad getSeguridad() {
        return seguridad;
    }

    public void setSeguridad(Seguridad seguridad) {
        this.seguridad = seguridad;
    }

}
