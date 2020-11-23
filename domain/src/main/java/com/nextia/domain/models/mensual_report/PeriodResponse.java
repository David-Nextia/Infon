package com.nextia.domain.models.mensual_report;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PeriodResponse {
    @SerializedName("root")
    @Expose
    private Root root;

    public Root getRoot() {
        return root;
    }

    public void setRoot(Root root) {
        this.root = root;
    }
}
