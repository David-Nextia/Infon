package com.nextia.micuentainfonavit.ui.movements.logic_views;

import java.util.ArrayList;
import java.util.List;

public class ViewsConfig {
    String tipoCaso;
    String nombreCaso;

    //0 mensualidades, 1 opciones de pago, 2 movimientos, 3 Datos de mi crédito
    List<Boolean> modulos= new ArrayList<>();
    String mensaje;


    public ViewsConfig(String tipoCaso, String nombreCaso, List<Boolean> modulos, String mensaje) {
        this.tipoCaso = tipoCaso;
        this.nombreCaso = nombreCaso;
        this.modulos = modulos;
        this.mensaje = mensaje;
    }
}
