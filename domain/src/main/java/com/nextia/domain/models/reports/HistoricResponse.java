package com.nextia.domain.models.reports;
/**
 * class of the response of get credit historyc post
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.nextia.domain.models.StatusServicio;

public class HistoricResponse {

    @SerializedName("reporte")
    @Expose
    private String reporte;
    @SerializedName("StatusServicio")
    @Expose
    private StatusServicio statusServicio;

    public String getReporte() {
        return reporte;
    }

    public void setReporte(String reporte) {
        this.reporte = reporte;
    }

    public StatusServicio getStatusServicio() {
        return statusServicio;
    }

    public void setStatusServicio(StatusServicio statusServicio) {
        this.statusServicio = statusServicio;
    }
}
