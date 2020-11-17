
package com.nextia.domain.models.saldo_movimientos;
/**
 * class of the response of get saldo and movements post
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.nextia.domain.models.StatusServicio;

public class SaldoMovimientosResponse {
    public ReturnData getReturnData() {
        return returnData;
    }

    public void setReturnData(ReturnData returnData) {
        this.returnData = returnData;
    }

    @SerializedName("return")
    @Expose
    private ReturnData returnData;
}
