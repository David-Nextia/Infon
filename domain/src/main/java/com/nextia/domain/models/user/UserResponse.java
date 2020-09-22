
package com.nextia.domain.models.user;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.nextia.domain.models.Seguridad;
import com.nextia.domain.models.StatusServicio;

public class UserResponse {

    @SerializedName("nss")
    @Expose
    private String nss;
    @SerializedName("nombre")
    @Expose
    private String nombre;
    @SerializedName("apPaterno")
    @Expose
    private String apPaterno;
    @SerializedName("apMaterno")
    @Expose
    private String apMaterno;
    @SerializedName("rfc")
    @Expose
    private String rfc;
    @SerializedName("scurp")
    @Expose
    private String scurp;
    @SerializedName("telefonoCelular")
    @Expose
    private String telefonoCelular;
    @SerializedName("emailPersonal")
    @Expose
    private String emailPersonal;
    @SerializedName("idPerfilglobal")
    @Expose
    private String idPerfilglobal;
    @SerializedName("Biometrico")
    @Expose
    private Boolean biometrico;
    @SerializedName("Notificacion")
    @Expose
    private Boolean notificacion;
    @SerializedName("Credito")
    @Expose
    private List<Credito> credito = null;
    @SerializedName("StatusServicio")
    @Expose
    private StatusServicio statusServicio;
    @SerializedName("Seguridad")
    @Expose
    private Seguridad seguridad;

    public UserResponse(String s, String s1, boolean b, ArrayList<Credito> creditos, String s2, String s3, String s4, boolean b1, int i, String s5, String s6, Seguridad seguridad, StatusServicio statusServicio, String s7) {

    }

    public UserResponse() {

    }

    public String getNss() {
        return nss;
    }

    public void setNss(String nss) {
        this.nss = nss;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApPaterno() {
        return apPaterno;
    }

    public void setApPaterno(String apPaterno) {
        this.apPaterno = apPaterno;
    }

    public String getApMaterno() {
        return apMaterno;
    }

    public void setApMaterno(String apMaterno) {
        this.apMaterno = apMaterno;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getScurp() {
        return scurp;
    }

    public void setScurp(String scurp) {
        this.scurp = scurp;
    }

    public String getTelefonoCelular() {
        return telefonoCelular;
    }

    public void setTelefonoCelular(String telefonoCelular) {
        this.telefonoCelular = telefonoCelular;
    }

    public String getEmailPersonal() {
        return emailPersonal;
    }

    public void setEmailPersonal(String emailPersonal) {
        this.emailPersonal = emailPersonal;
    }

    public String getIdPerfilglobal() {
        return idPerfilglobal;
    }

    public void setIdPerfilglobal(String idPerfilglobal) {
        this.idPerfilglobal = idPerfilglobal;
    }

    public Boolean getBiometrico() {
        return biometrico;
    }

    public void setBiometrico(Boolean biometrico) {
        this.biometrico = biometrico;
    }

    public Boolean getNotificacion() {
        return notificacion;
    }

    public void setNotificacion(Boolean notificacion) {
        this.notificacion = notificacion;
    }

    public List<Credito> getCredito() {
        return credito;
    }

    public void setCredito(List<Credito> credito) {
        this.credito = credito;
    }

    public StatusServicio getStatusServicio() {
        return statusServicio;
    }

    public void setStatusServicio(StatusServicio statusServicio) {
        this.statusServicio = statusServicio;
    }

    public Seguridad getSeguridad() {
        return seguridad;
    }

    public void setSeguridad(Seguridad seguridad) {
        this.seguridad = seguridad;
    }

}
