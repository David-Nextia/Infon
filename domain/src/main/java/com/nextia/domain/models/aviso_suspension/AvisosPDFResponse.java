package com.nextia.domain.models.aviso_suspension;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.nextia.domain.models.StatusServicio;

public class AvisosPDFResponse {

    @SerializedName("DatosAvisos")
    @Expose
    private DatosAvisos datosAvisos;

    @SerializedName("StatusServicio")
    @Expose
    private StatusServicio statusServicio;

    public DatosAvisos getDatosAvisos() {
        return datosAvisos;
    }

    public void setDatosAvisos(DatosAvisos datosAvisos) {
        this.datosAvisos = datosAvisos;
    }

    public StatusServicio getStatusServicio() {
        return statusServicio;
    }

    public void setStatusServicio(StatusServicio statusServicio) {
        this.statusServicio = statusServicio;
    }
}
