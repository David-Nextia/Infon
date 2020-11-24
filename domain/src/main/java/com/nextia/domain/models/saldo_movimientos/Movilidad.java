package com.nextia.domain.models.saldo_movimientos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Movilidad {
    @SerializedName("smovilDelega")
    @Expose
    private String smovilDelega;
    @SerializedName("smovilDescTas")
    @Expose
    private String smovilDescTas;
    @SerializedName("smovilDescTipag")
    @Expose
    private String smovilDescTipag;
    @SerializedName("smovilFecOri")
    @Expose
    private String smovilFecOri;
    @SerializedName("smovilInd")
    @Expose
    private String smovilInd;
    @SerializedName("smovilMonOto")
    @Expose
    private String smovilMonOto;
    @SerializedName("smovilMoneda")
    @Expose
    private String smovilMoneda;
    @SerializedName("smovilPlazo")
    @Expose
    private String smovilPlazo;
    @SerializedName("smovilTasaint")
    @Expose
    private String smovilTasaint;
    @SerializedName("smovilTipcred")
    @Expose
    private String smovilTipcred;
    @SerializedName("smovilTipmovi")
    @Expose
    private String smovilTipmovi;

    public String getSmovilDelega() {
        return smovilDelega;
    }

    public void setSmovilDelega(String smovilDelega) {
        this.smovilDelega = smovilDelega;
    }

    public String getSmovilDescTas() {
        return smovilDescTas;
    }

    public void setSmovilDescTas(String smovilDescTas) {
        this.smovilDescTas = smovilDescTas;
    }

    public String getSmovilDescTipag() {
        return smovilDescTipag;
    }

    public void setSmovilDescTipag(String smovilDescTipag) {
        this.smovilDescTipag = smovilDescTipag;
    }

    public String getSmovilFecOri() {
        return smovilFecOri;
    }

    public void setSmovilFecOri(String smovilFecOri) {
        this.smovilFecOri = smovilFecOri;
    }

    public String getSmovilInd() {
        return smovilInd;
    }

    public void setSmovilInd(String smovilInd) {
        this.smovilInd = smovilInd;
    }

    public String getSmovilMonOto() {
        return smovilMonOto;
    }

    public void setSmovilMonOto(String smovilMonOto) {
        this.smovilMonOto = smovilMonOto;
    }

    public String getSmovilMoneda() {
        return smovilMoneda;
    }

    public void setSmovilMoneda(String smovilMoneda) {
        this.smovilMoneda = smovilMoneda;
    }

    public String getSmovilPlazo() {
        return smovilPlazo;
    }

    public void setSmovilPlazo(String smovilPlazo) {
        this.smovilPlazo = smovilPlazo;
    }

    public String getSmovilTasaint() {
        return smovilTasaint;
    }

    public void setSmovilTasaint(String smovilTasaint) {
        this.smovilTasaint = smovilTasaint;
    }

    public String getSmovilTipcred() {
        return smovilTipcred;
    }

    public void setSmovilTipcred(String smovilTipcred) {
        this.smovilTipcred = smovilTipcred;
    }

    public String getSmovilTipmovi() {
        return smovilTipmovi;
    }

    public void setSmovilTipmovi(String smovilTipmovi) {
        this.smovilTipmovi = smovilTipmovi;
    }
}
