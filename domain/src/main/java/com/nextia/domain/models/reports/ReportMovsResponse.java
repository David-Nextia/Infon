package com.nextia.domain.models.reports;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.nextia.domain.models.StatusServicio;

public class ReportMovsResponse {
    @SerializedName("return")
    @Expose
    private Return _return;

    public Return getReturn() {
        return _return;
    }

    public void setReturn(Return _return) {
        this._return = _return;
    }

}
