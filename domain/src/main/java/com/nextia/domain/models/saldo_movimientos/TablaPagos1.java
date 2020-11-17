
package com.nextia.domain.models.saldo_movimientos;
/**
 * Secondary class to get the RespuestasDoMovs class
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TablaPagos1 {

    @SerializedName("tp11MensPgoCorriente")
    @Expose
    private String tp11MensPgoCorriente;

    @SerializedName("tp12MensPgoMensualidad")
    @Expose
    private String tp12MensPgoMensualidad;

    @SerializedName("tp13PesPgoCorriente")
    @Expose
    private String tp13PesPgoCorriente;

    @SerializedName("tp14PesPgoMensualidad")
    @Expose
    private String tp14PesPgoMensualidad;

    @SerializedName("tp15VsmPgoCorriente")
    @Expose
    private String tp15VsmPgoCorriente;

    @SerializedName("tp16VsmPgoMensualidad")
    @Expose
    private String tp16VsmPgoMensualidad;

    @SerializedName("tp17PgoCorriente")
    @Expose
    private String tp17PgoCorriente;

    @SerializedName("tp18PgoMensualidad")
    @Expose
    private String tp18PgoMensualidad;

    public String getTp11MensPgoCorriente() {
        return tp11MensPgoCorriente;
    }

    public void setTp11MensPgoCorriente(String tp11MensPgoCorriente) {
        this.tp11MensPgoCorriente = tp11MensPgoCorriente;
    }

    public String getTp12MensPgoMensualidad() {
        return tp12MensPgoMensualidad;
    }

    public void setTp12MensPgoMensualidad(String tp12MensPgoMensualidad) {
        this.tp12MensPgoMensualidad = tp12MensPgoMensualidad;
    }

    public String getTp13PesPgoCorriente() {
        return tp13PesPgoCorriente;
    }

    public void setTp13PesPgoCorriente(String tp13PesPgoCorriente) {
        this.tp13PesPgoCorriente = tp13PesPgoCorriente;
    }

    public String getTp14PesPgoMensualidad() {
        return tp14PesPgoMensualidad;
    }

    public void setTp14PesPgoMensualidad(String tp14PesPgoMensualidad) {
        this.tp14PesPgoMensualidad = tp14PesPgoMensualidad;
    }

    public String getTp15VsmPgoCorriente() {
        return tp15VsmPgoCorriente;
    }

    public void setTp15VsmPgoCorriente(String tp15VsmPgoCorriente) {
        this.tp15VsmPgoCorriente = tp15VsmPgoCorriente;
    }

    public String getTp16VsmPgoMensualidad() {
        return tp16VsmPgoMensualidad;
    }

    public void setTp16VsmPgoMensualidad(String tp16VsmPgoMensualidad) {
        this.tp16VsmPgoMensualidad = tp16VsmPgoMensualidad;
    }

    public String getTp17PgoCorriente() {
        return tp17PgoCorriente;
    }

    public void setTp17PgoCorriente(String tp17PgoCorriente) {
        this.tp17PgoCorriente = tp17PgoCorriente;
    }

    public String getTp18PgoMensualidad() {
        return tp18PgoMensualidad;
    }

    public void setTp18PgoMensualidad(String tp18PgoMensualidad) {
        this.tp18PgoMensualidad = tp18PgoMensualidad;
    }
}
