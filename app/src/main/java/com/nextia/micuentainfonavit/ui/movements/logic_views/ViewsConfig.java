package com.nextia.micuentainfonavit.ui.movements.logic_views;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ViewsConfig {
    public static final String MENSUALIDADES="mensualidades";
   public  static final String PAY_OPTIONS="opciones de pago";
   public  static final String MOVEMENTS="movimientos";
   public  static final String CREDIT_DATA="datos de mi credito";


    String tipoCaso;
    String nombreCaso;
    HashMap<String, Boolean> modulos = new HashMap<>();
    String mensaje;

    public String getTipoCaso() {
        return tipoCaso;
    }

    public void setTipoCaso(String tipoCaso) {
        this.tipoCaso = tipoCaso;
    }

    public String getNombreCaso() {
        return nombreCaso;
    }

    public void setNombreCaso(String nombreCaso) {
        this.nombreCaso = nombreCaso;
    }

    public HashMap<String, Boolean> getModulos() {
        return modulos;
    }

    public void setModulos(HashMap<String, Boolean> modulos) {
        this.modulos = modulos;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public ViewsConfig(String tipoCaso, String mensaje) {
        this.tipoCaso = tipoCaso;
        this.mensaje = mensaje;
        getNombreAndConditions(tipoCaso);
    }

    private void getNombreAndConditions(String tipoCaso){

        if(tipoCaso.equals("00010")){
            nombreCaso="Liquidado por Pagos";
            modulos.put(MENSUALIDADES,false);
            modulos.put(PAY_OPTIONS,false);
            modulos.put(MOVEMENTS,true);
            modulos.put(CREDIT_DATA,true);
        }
        else if(tipoCaso.equals("00020")){
           nombreCaso="Liquidado por: defunción, incapacidad, etc.";
            modulos.put(MENSUALIDADES,false);
            modulos.put(PAY_OPTIONS,false);
            modulos.put(MOVEMENTS,true);
            modulos.put(CREDIT_DATA,true);

        }

        else if(tipoCaso.equals("00030")){
            nombreCaso="NP. Poder Notarial\n" +
                    "EF. Entidad Financiera\n" +
                    "DA. Dación";
            modulos.put(MENSUALIDADES,false);
            modulos.put(PAY_OPTIONS,false);
            modulos.put(MOVEMENTS,false);
            modulos.put(CREDIT_DATA,true);

        }
        else if(tipoCaso.equals("00040")){
            nombreCaso="PRÓRROGAS ESPECIALES (3,4,7 y 8)";
            modulos.put(MENSUALIDADES,false);
            modulos.put(PAY_OPTIONS,true);
            modulos.put(MOVEMENTS,true);
            modulos.put(CREDIT_DATA,true);

        }
        else if(tipoCaso.equals("00045")){
            nombreCaso="PRÓRROGAS ESPECIALES (5,6 y 9)";
            modulos.put(MENSUALIDADES,true);
            modulos.put(PAY_OPTIONS,true);
            modulos.put(MOVEMENTS,true);
            modulos.put(CREDIT_DATA,true);

        }
        else if(tipoCaso.equals("00050")){
            nombreCaso="RENEWAL";
            modulos.put(MENSUALIDADES,false);
            modulos.put(PAY_OPTIONS,true);
            modulos.put(MOVEMENTS,false);
            modulos.put(CREDIT_DATA,true);

        }
        else if(tipoCaso.equals("00100")){
            nombreCaso="DEMANDADO, CONVENIO JUDICIAL, VENCIDO";
            modulos.put(MENSUALIDADES,true);
            modulos.put(PAY_OPTIONS,true);
            modulos.put(MOVEMENTS,true);
            modulos.put(CREDIT_DATA,true);

        }
        else if(tipoCaso.equals("00150")){
            nombreCaso="DEMANDADO, CONVENIO JUDICIAL, VIGENTE, CON OMISOS";
            modulos.put(MENSUALIDADES,true);
            modulos.put(PAY_OPTIONS,true);
            modulos.put(MOVEMENTS,true);
            modulos.put(CREDIT_DATA,true);

        }
        else if(tipoCaso.equals("00160")){
            nombreCaso="DEMANDADO, CONVENIO JUDICIAL, VIGENTE, SIN OMISOS";
            modulos.put(MENSUALIDADES,true);
            modulos.put(PAY_OPTIONS,true);
            modulos.put(MOVEMENTS,true);
            modulos.put(CREDIT_DATA,true);

        }
        else if(tipoCaso.equals("00200")){
            nombreCaso="DEMANDADO, SIN CONVENIO JUDICIAL";
            modulos.put(MENSUALIDADES,false);
            modulos.put(PAY_OPTIONS,true);
            modulos.put(MOVEMENTS,false);
            modulos.put(CREDIT_DATA,true);

        }
        else if(tipoCaso.equals("00220")){
            nombreCaso="RECUPERACIÓN ESPECIALIZADA SENTENCIA";
            modulos.put(MENSUALIDADES,false);
            modulos.put(PAY_OPTIONS,false);
            modulos.put(MOVEMENTS,false);
            modulos.put(CREDIT_DATA,true);

        }

        else if(tipoCaso.equals("00310")){
            nombreCaso="CON REESTRUCTURA, PRÓRROGA 1 Y 2";
            modulos.put(MENSUALIDADES,true);
            modulos.put(PAY_OPTIONS,true);
            modulos.put(MOVEMENTS,true);
            modulos.put(CREDIT_DATA,true);

        }
        else if(tipoCaso.equals("00350")){
            nombreCaso="CON REESTRUCTURA, SIN PRÓRROGA, CON OMISOS";
            modulos.put(MENSUALIDADES,true);
            modulos.put(PAY_OPTIONS,true);
            modulos.put(MOVEMENTS,true);
            modulos.put(CREDIT_DATA,true);

        }
        else if(tipoCaso.equals("00360")){
            nombreCaso="CON REESTRUCTURA, SIN PRÓRROGA, SIN OMISOS";
            modulos.put(MENSUALIDADES,true);
            modulos.put(PAY_OPTIONS,true);
            modulos.put(MOVEMENTS,true);
            modulos.put(CREDIT_DATA,true);

        }
        else if(tipoCaso.equals("00450")){
            nombreCaso="SIN REESTRUCTURA, CON FPP, PRÓRROGA 1 Y 2, CON OMISOS";
            modulos.put(MENSUALIDADES,true);
            modulos.put(PAY_OPTIONS,true);
            modulos.put(MOVEMENTS,true);
            modulos.put(CREDIT_DATA,true);

        }
        else if(tipoCaso.equals("00460")){
            nombreCaso="SIN REESTRUCTURA, CON FPP, PRÓRROGA 1 Y 2, SIN OMISOS";
            modulos.put(MENSUALIDADES,true);
            modulos.put(PAY_OPTIONS,true);
            modulos.put(MOVEMENTS,true);
            modulos.put(CREDIT_DATA,true);

        }
        else if(tipoCaso.equals("00550")){
            nombreCaso="SIN REESTRUCTURA, CON FPP, CON ROA O REA, CON OMISOS";
            modulos.put(MENSUALIDADES,true);
            modulos.put(PAY_OPTIONS,true);
            modulos.put(MOVEMENTS,true);
            modulos.put(CREDIT_DATA,true);
        }
        else if(tipoCaso.equals("00560")){
            nombreCaso="SIN REESTRUCTURA, CON FPP, CON ROA O REA, SIN OMISOS";
            modulos.put(MENSUALIDADES,true);
            modulos.put(PAY_OPTIONS,true);
            modulos.put(MOVEMENTS,true);
            modulos.put(CREDIT_DATA,true);

        }
        else if(tipoCaso.equals("00600")){
            nombreCaso="SIN REESTRUCTURA, SIN FPP, PRÓRROGA 1 Y 2";
            modulos.put(MENSUALIDADES,true);
            modulos.put(PAY_OPTIONS,true);
            modulos.put(MOVEMENTS,true);
            modulos.put(CREDIT_DATA,true);

        }
        else if(tipoCaso.equals("00650")){
            nombreCaso="SIN REESTRUCTURA, SIN FPP, CON ROA O REA, CON OMISOS";
            modulos.put(MENSUALIDADES,true);
            modulos.put(PAY_OPTIONS,true);
            modulos.put(MOVEMENTS,true);
            modulos.put(CREDIT_DATA,true);

        }
        else if(tipoCaso.equals("00660")){
            nombreCaso="SIN REESTRUCTURA, SIN FPP, CON ROA O REA, SIN OMISOS";
            modulos.put(MENSUALIDADES,true);
            modulos.put(PAY_OPTIONS,true);
            modulos.put(MOVEMENTS,true);
            modulos.put(CREDIT_DATA,true);

        }
        else{
            nombreCaso="otro caso :(";
            modulos.put(MENSUALIDADES,true);
            modulos.put(PAY_OPTIONS,true);
            modulos.put(MOVEMENTS,true);
            modulos.put(CREDIT_DATA,true);
        }



    }


}
