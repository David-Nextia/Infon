package com.nextia.domain.models.aviso_suspension;
/**
 * Secondary class to get the AvisoPdfResponse class
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DatosAvisos {

    @SerializedName("item")
    @Expose
    public List<Item> item;

    public List<Item> getItem() {
        return item;
    }

    public void setItem(List<Item> item) {
        this.item = item;
    }
}
