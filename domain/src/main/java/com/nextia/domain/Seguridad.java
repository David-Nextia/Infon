
package com.nextia.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Seguridad {

    @SerializedName("movilID")
    @Expose
    private String movilID;

    public Seguridad(String movilId) {
this.movilID=movilId;
    }

    public String getMovilID() {
        return movilID;
    }

    public void setMovilID(String movilID) {
        this.movilID = movilID;
    }

}
