
package com.nextia.domain.models.credit_year_info;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DatosGenerales {

    @SerializedName("rfc")
    @Expose
    private String rfc;
    @SerializedName("nombre")
    @Expose
    private String nombre;
    @SerializedName("domicilio")
    @Expose
    private Domicilio domicilio;

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Domicilio getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(Domicilio domicilio) {
        this.domicilio = domicilio;
    }

}
