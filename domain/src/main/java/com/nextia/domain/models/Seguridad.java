
package com.nextia.domain.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Seguridad {

    @SerializedName("movilID")
    @Expose
    private String movilID;

    @SerializedName("sessionToken")
    @Expose
    private String sessionToken;


    public String getSessionToken() {
        return sessionToken;
    }
    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }
    public Seguridad(String movilId) {
this.movilID=movilId;
    }
    public Seguridad(String movilId, String sessionToken) {
        this.movilID=movilId;
        this.sessionToken=sessionToken;
    }

    public String getMovilID() {
        return movilID;
    }

    public void setMovilID(String movilID) {
        this.movilID = movilID;
    }

}
