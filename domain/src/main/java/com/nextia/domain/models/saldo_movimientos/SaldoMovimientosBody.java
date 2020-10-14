
package com.nextia.domain.models.saldo_movimientos;
/**
 * Class body to post and get Saldo and movements info
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.nextia.domain.models.Seguridad;

import java.util.UUID;

public class SaldoMovimientosBody {

    @SerializedName("credito")
    @Expose
    private String credito;

    @SerializedName("legado")
    @Expose
    private String legado;

    public SaldoMovimientosBody(String credito){
        this.credito = credito;
        this.legado = "1";
    }

    public String getCredito() {
        return credito;
    }

    public void setCredito(String credito) {
        this.credito = credito;
    }

    public String getLegado() {
        return legado;
    }

    public void setLegado(String legado) {
        this.legado = legado;
    }
}
