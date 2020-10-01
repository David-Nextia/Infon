package com.nextia.domain.models.credit_year_info;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreditYearInfoBody {


        @SerializedName("numeroCredito")
        @Expose
        private String numeroCredito;
        @SerializedName("Anio")
        @Expose
        private String anio;

        public String getNumeroCredito() {
            return numeroCredito;
        }

        public void setNumeroCredito(String numeroCredito) {
            this.numeroCredito = numeroCredito;
        }

        public String getAnio() {
            return anio;
        }

        public void setAnio(String anio) {
            this.anio = anio;
        }

    }


