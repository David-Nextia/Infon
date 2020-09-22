
package com.nextia.domain.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.UUID;

public class UserLogin {

    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("canal")
    @Expose
    private String canal;
    @SerializedName("correo")
    @Expose
    private String correo;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("pagina")
    @Expose
    private String pagina;
    @SerializedName("Seguridad")
    @Expose
    private Seguridad seguridad;

    public UserLogin(String username, String passsword) {
        this.username=username;
        this.password=passsword;
        this.canal="2";
        this.correo=username;
        this.url="https";
        this.pagina="www.infonavit.com";
        this.seguridad=new Seguridad(UUID.randomUUID().toString());

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCanal() {
        return canal;
    }

    public void setCanal(String canal) {
        this.canal = canal;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPagina() {
        return pagina;
    }

    public void setPagina(String pagina) {
        this.pagina = pagina;
    }

    public Seguridad getSeguridad() {
        return seguridad;
    }

    public void setSeguridad(Seguridad seguridad) {
        this.seguridad = seguridad;
    }

}
