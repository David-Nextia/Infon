
package com.nextia.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StatusServicio {

    @SerializedName("codigo")
    @Expose
    private String codigo;
    @SerializedName("mensaje")
    @Expose
    private String mensaje;

    public StatusServicio(String s, String s1) {

    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

}
