package com.nextia.domain.models.credit_info;
/**
 * class of the response of get credit post
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.nextia.domain.models.StatusServicio;

import java.util.List;

public class CreditInfoResponse {
    @SerializedName("Respuesta")
    @Expose
    private List<RespuestUm> respuesta = null;
    @SerializedName("StatusServicio")
    @Expose
    private StatusServicio statusServicio;

    public List<RespuestUm> getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(List<RespuestUm> respuesta) {
        this.respuesta = respuesta;
    }

    public StatusServicio getStatusServicio() {
        return statusServicio;
    }

    public void setStatusServicio(StatusServicio statusServicio) {
        this.statusServicio = statusServicio;
    }
}
