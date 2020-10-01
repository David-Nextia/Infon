
package com.nextia.domain.models.credit_year_info;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DatoCFDI {

    @SerializedName("xml")
    @Expose
    private String xml;
    @SerializedName("pdf")
    @Expose
    private String pdf;

    public String getXml() {
        return xml;
    }

    public void setXml(String xml) {
        this.xml = xml;
    }

    public String getPdf() {
        return pdf;
    }

    public void setPdf(String pdf) {
        this.pdf = pdf;
    }

}
