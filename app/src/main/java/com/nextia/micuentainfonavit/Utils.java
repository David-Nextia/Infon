package com.nextia.micuentainfonavit;
/**
 * class that creates aux and repetitive methods
 */

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.service.voice.VoiceInteractionSession;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;
import androidx.lifecycle.ViewModel;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import com.ethanhua.skeleton.Skeleton;
import com.ethanhua.skeleton.ViewSkeletonScreen;
import com.google.gson.Gson;
import com.nextia.domain.OnFinishRequestListener;
import com.nextia.domain.models.aviso_suspension.Item;
import com.nextia.domain.models.credit_year_info.CreditYearInfoResponse;
import com.nextia.domain.models.saldo.SaldoBody;
import com.nextia.domain.models.user.Credito;
import com.nextia.domain.models.user.UserResponse;
import com.nextia.micuentainfonavit.ui.aviso.AvisoViewModel;
import com.nextia.micuentainfonavit.ui.constancia.pdf_download.PdfConstanciaDownloadViewModel;
import com.nextia.micuentainfonavit.usecases.SaldosUseCase;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static java.security.AccessController.getContext;


public class Utils {
    //Patterns date string
    public static String PATTERN_YYYYMMDD = "yyyyMMdd";

    //To get the user data saved on shared preferences
    public static UserResponse getSharedPreferencesUserData(Context context) {
        SharedPreferences mPrefs = context.getSharedPreferences("pref", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mPrefs.getString("UsuarioData", "");
        return gson.fromJson(json, UserResponse.class);
    }

    //To get the email on login screen, saved on shared preferences
    public static String getSharedPreferencesEmail(Context context) {
        SharedPreferences mPrefs = context.getSharedPreferences("pref", Context.MODE_PRIVATE);
        return mPrefs.getString("emailUser", "");

    }

    //To get the token on app, saved on shared preferences
    public static String getSharedPreferencesToken(Context context) {
        SharedPreferences mPrefs = context.getSharedPreferences("pref", Context.MODE_PRIVATE);
        return mPrefs.getString("Token", "");

    }
    //To format String as money double
    public static String formatMoney(double num) {
        String money;
        if (num == 0.0) {
            money = "$0.00";
        } else {
            DecimalFormat formatter = new DecimalFormat("#,###.00");
            money = "$" + formatter.format(num);
        }

        return money;
    }

    //To format String as money String
    public static String formatMoney(String stringNum) {
        String money;
        try {
            double num = Double.parseDouble(stringNum.trim());
            if (num == 0.0) {
                money = "$0.00";
            } else {
                DecimalFormat formatter = new DecimalFormat("#,###.00");
                money = "$" + formatter.format(num);
            }
        }catch (Exception ex){
            ex.printStackTrace();
            money = "$0.00";
        }
        return money;
    }

    //To save data on shared preferences
    public static void saveToSharedPreferences(Context context, String tag, String object) {
        SharedPreferences mPrefs = context.getSharedPreferences("pref", Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        prefsEditor.putString(tag, object);
        prefsEditor.commit();

    }


    private static ViewSkeletonScreen mSkeleton;

    //To create skeleton view
    public static void showLoadingSkeleton(View rootView, int viewId) {
        mSkeleton = Skeleton.bind(rootView)
                .load(viewId)
                .color(R.color.shimmer_color)
                .angle(0)
                .show();
    }

    //To hide skeleton View
    public static void hideLoadingSkeleton() {
        if (mSkeleton != null)
            mSkeleton.hide();
        mSkeleton = null;
    }

    //To get the device screen height
    public static int getScreenHeight(Activity context) {
        int height;
        DisplayMetrics displayMetrics = new DisplayMetrics();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            context.getDisplay().getRealMetrics(displayMetrics);
        } else {
            context.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        }

        height = displayMetrics.heightPixels;
        return height;
    }

    //To get the device screen width
    public static int getScreenWidth(Activity context) {
        int width;
        DisplayMetrics displayMetrics = new DisplayMetrics();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            context.getDisplay().getRealMetrics(displayMetrics);
        } else {
            context.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        }

        width = displayMetrics.widthPixels;
        return width;
    }

    //To share other apps string
    public static void showShareIntent(Activity activity) {
        String image_url = "http://images.cartradeexchange.com//img//800//vehicle//Honda_Brio_562672_5995_6_1438153637072.jpg";
        String image_url2 = "https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.pdf";

        Intent shareIntent = new Intent();
        shareIntent.setType("pdf/*");
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        shareIntent.setAction(Intent.ACTION_SEND);
        //without the below line intent will show error
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, image_url2);
        // Target whatsapp:
        //shareIntent.setPackage("com.whatsapp");
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        activity.startActivity(Intent.createChooser(shareIntent, "choose one"));

    }

    //To get available hard data of markets to pay credit
    public static ArrayList<String> getItems() {
        ArrayList<String> items = new ArrayList<>();
        items.add("Famsa");
        items.add("Chedraui");
        items.add("Súperché");
        items.add("Súper Chedraui");
        items.add("7 Eleven");
        items.add("Aeroflah");
        items.add("Al súper");
        items.add("Súper Fasti");
        items.add("Farmacias Benavides");
        items.add("Súper Iberia");
        items.add("Central de estacionamientos");
        items.add("Equipate");
        items.add("Supertiendas Rico");
        items.add("Puntos recarga");
        items.add("Farmacias del Ahorro");
        items.add("Yastas");
        items.add("El Austuriano");
        items.add("El Vigilante/One");
        items.add("Circulo K");
        items.add("Maxilana");
        items.add("LDI");
        items.add("Milano");
        items.add("Muebles América");
        items.add("Multired");
        items.add("Movivendor");
        items.add("Pademobile");
        items.add("Pago Rápido");
        items.add("Prendamex");
        items.add("Red Efectiva");
        items.add("Súper kiosco");
        items.add("Extra/Circulo K");
        items.add("ISSSTEZAC");
        items.add("Akala");
        items.add("Farmacias Bazar");
        items.add("Te creemos");
        items.add("La Misión");
        items.add("Gana Mas");
        items.add("Netpay");
        items.add("Gestopago");
        items.add("Quantum");
        items.add("Fundación Donde");
        items.add("Airpack");
        items.add("Waldos");
        items.add("La Casa Abarrotera");
        items.add("Banjercito");
        items.add("Virtual Market");
        items.add("Vía Servicios");
        items.add("Administradora de Fondos y Recursos Regiomontanos");
        items.add("Oki Auto-Servicios");
        items.add("Supermercados del Occidente");
        items.add("Alamo");
        items.add("Comercial Mexicana");
        items.add("Mega");
        items.add("Bodega Comercial Mexicana");
        items.add("Al précio");
        items.add("La Comer");
        items.add("City Market");
        items.add("Sumesa");
        items.add("Fresko");
        items.add("City Club");
        items.add("Soriana");
        items.add("Soriana Mercado");
        items.add("Soriana Hiper");
        items.add("Soriana Súper");
        items.add("Soriana Express");
        items.add("Súper City");
        return items;
    }

    //To fill any spinner with credits of the user
    public static void fillSpinnerWithCredit(Context context, Spinner spinner) {
        ArrayList<Credito> creditos;
        ArrayList<String> lista = new ArrayList<>();
        creditos = getSharedPreferencesUserData(context).getCredito();
        //lista.add("Selecciona una cuenta");
        for (int i = 0; i < creditos.size(); i++) {
            lista.add(creditos.get(i).getNumeroCredito());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, lista);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    //To share  a pdf to other apps
    public static void sharePdf(File file, Activity activity) {

        Uri uri = Uri.fromFile(file);
        Uri fileUri = FileProvider.getUriForFile(activity.getApplicationContext(),
                activity.getPackageName() + ".fileprovider", file);
        Intent share = new Intent();
        share.setAction(Intent.ACTION_SEND);
        share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//      share.setType("application/pdf");

        share.setDataAndType(fileUri, "application/pdf");
        share.putExtra(Intent.EXTRA_STREAM, fileUri);

        activity.startActivity(Intent.createChooser(share, "Share it"));
    }

    //To create a pdf with canvas
    public static File createPdfFromCanvas(ViewModel mViewModel, String Name, Activity activity, int mode) {
        String myFilePath;
        File myfile;
        PdfDocument.Page mypage;
        PdfDocument.PageInfo pageInfo;
        int pageWidth;
        int pageHieght;
        int paddingTop;
        int paddingSide;
        Canvas canvas;
        PdfDocument pdfDocument = new PdfDocument();
        Typeface typefaceTitle = Typeface.createFromAsset(activity.getAssets(), "font/title.ttf");
        Typeface typefaceSubtitle = Typeface.createFromAsset(activity.getAssets(), "font/subtitle.ttf");
        Typeface typefaceBody = Typeface.createFromAsset(activity.getAssets(), "font/body.ttf");
        Paint rectPaint = new Paint();
        rectPaint.setColor(Color.rgb(255, 0, 0));
        Paint rect = new Paint();
        Paint paintTitle = new Paint();
        Paint paintTitle1 = new Paint();
        Paint paintTitle2 = new Paint();
        Paint paintTitle3 = new Paint();
        Paint paintText = new Paint();
        Paint paintText1 = new Paint();
        Paint paintTextDescription = new Paint();
        Paint paintTextDescription1 = new Paint();
        Paint paintTextDescription2 = new Paint();
        Paint paintBoldCS = new Paint();
        Paint paintBoldCS1 = new Paint();
        Paint paintBoldCS2 = new Paint();
        Paint paintBoldCS3 = new Paint();
        Paint paintBold = new Paint();
        Paint paintBold1 = new Paint();
        Paint paintBold2 = new Paint();
        Paint paintBold3 = new Paint();
        Paint line = new Paint();
        Paint blckPoint = new Paint();
        Paint whtPoint = new Paint();


        if (mode>1){
            //ATRIBUTOS EN LAS PLANTILLAS
            paintTitle.setTextSize(16);
            paintTitle.setFakeBoldText(true);
            paintTitle.setColor(Color.rgb(255, 255, 255));
            paintTitle.setLinearText(true);

            paintTitle1.setTextSize(26);
            paintTitle1.setFakeBoldText(true);
            paintTitle1.setColor(Color.rgb(255, 255, 255));
            paintTitle1.setLinearText(true);

            paintTitle2.setTextSize(22);
            paintTitle2.setFakeBoldText(true);
            paintTitle2.setColor(Color.rgb(255, 255, 255));
            paintTitle2.setLinearText(true);

            paintTitle3.setTextSize(15);
            paintTitle3.setFakeBoldText(true);
            paintTitle3.setColor(Color.rgb(255, 255, 255));
            paintTitle3.setLinearText(true);

            paintText.setTextSize(9f);
            paintText.setLinearText(true);

            paintText1.setTextSize(11f);
            paintText1.setLinearText(true);

            paintTextDescription.setTextSize(7f);
            paintTextDescription.setLinearText(true);

            paintTextDescription1.setTextSize(8f);
            paintTextDescription1.setLinearText(true);

            paintTextDescription2.setTextSize(10f);
            paintTextDescription2.setLinearText(true);

            paintBoldCS.setTextSize(7f);
            //paintBoldCS.setTypeface(Typeface.create("Arial", Typeface.BOLD_ITALIC));
            paintBoldCS.setUnderlineText(true);
            paintBoldCS.setLinearText(true);

            paintBoldCS1.setTextSize(8f);
            //paintBoldCS1.setTypeface(Typeface.create("Arial", Typeface.BOLD_ITALIC));
            paintBoldCS1.setUnderlineText(true);
            paintBoldCS1.setLinearText(true);

            paintBoldCS2.setTextSize(10f);
            //paintBoldCS2.setTypeface(Typeface.create("Arial", Typeface.BOLD_ITALIC));
            paintBoldCS2.setUnderlineText(true);
            paintBoldCS2.setLinearText(true);

            paintBoldCS3.setTextSize(10f);
            //paintBoldCS3.setTypeface(Typeface.create("Arial", Typeface.BOLD_ITALIC));
            paintBoldCS3.setLinearText(true);

            paintBold.setTextSize(9f);
            paintBold.setFakeBoldText(true);
            paintBold.setLinearText(true);

            paintBold1.setTextSize(11f);
            paintBold1.setFakeBoldText(true);
            paintBold1.setLinearText(true);

            paintBold2.setTextSize(7f);
            paintBold2.setFakeBoldText(true);
            paintBold2.setLinearText(true);

            paintBold3.setTextSize(10f);
            paintBold3.setFakeBoldText(true);
            paintBold3.setLinearText(true);

            rectPaint = new Paint();
            rectPaint.setColor(Color.rgb(255, 0, 0));

             rect = new Paint();
            rect.setStyle(Paint.Style.STROKE);
            rect.setStrokeWidth(1);

            line.setColor(Color.BLACK);

            blckPoint.setColor(Color.BLACK);

            whtPoint.setStyle(Paint.Style.STROKE);
            whtPoint.setStrokeWidth(1);
        }
        if(mode==1){
            PdfConstanciaDownloadViewModel viewmodel=(PdfConstanciaDownloadViewModel)mViewModel;
             pageWidth=1200;
             pageHieght=2100;
             paddingTop=pageWidth/20;
             paddingSide=pageWidth/20;
            CreditYearInfoResponse credit=viewmodel.getCreditInfo().getValue();
            String credutNum= viewmodel.getCredit().getValue();
            String creditYear=viewmodel.getYear().getValue();

           pageInfo = new PdfDocument.PageInfo.Builder(pageWidth, pageHieght, 1).create();
            mypage = pdfDocument.startPage(pageInfo);
            Paint myPaint = new Paint();
            canvas= mypage.getCanvas();

            //setting title format
            paintTitle.setTextSize(40f);
            paintTitle.setTypeface(typefaceTitle);


            //setting body text format
            paintText.setTextSize(30f);
            paintText.setTypeface(typefaceSubtitle);

            //setting description text format
            paintTextDescription.setTextSize(28f);
            paintTextDescription.setTypeface(typefaceBody);


            //setting subdirect text format
            Paint paintTitleSubdirec = new Paint();
            paintTitleSubdirec.setTextSize((float) 30);
            paintTitleSubdirec.setFakeBoldText(true);
            paintTitleSubdirec.setColor(Color.BLACK);
            paintTitleSubdirec.setTypeface(typefaceSubtitle);


            //setting images on pdf
            Bitmap logo = null;
            Bitmap cedula = null;
            Bitmap scaleImageCedula = null;
            Bitmap scaleImageLogo = null;
            if (getContext() != null) {
                logo = BitmapFactory.decodeResource(activity.getResources(), R.drawable.logo_pdf);
                cedula = BitmapFactory.decodeResource(activity.getResources(), R.drawable.cedula_rfc);
                scaleImageLogo=Bitmap.createScaledBitmap(logo,60,60,true);
                scaleImageCedula = Bitmap.createScaledBitmap(cedula, 90, 160, true);
            }
            paintTitle.setColor(Color.BLACK);
            if (logo != null) {
                int imageWidth= (int)(pageWidth/5.5);
                int imageHeight= (int)((imageWidth)*0.7);
                canvas.drawBitmap(resizeBitmap(logo, imageWidth, imageHeight), paddingTop, paddingTop, null);
            }

            //writing title
            int porportionalLine= pageHieght/52;

            canvas.drawText("INSTITUTO DEL FONDO NACIONAL DE LA", (pageWidth/5)+80, getPosition(paddingTop+30), paintTitle);
            canvas.drawText("VIVIENDA PARA LOS TRABAJADORES", (pageWidth/5)+110, getPosition(porportionalLine), paintTitle);

            //writing body text
            canvas.drawText("BARRANCA DEL MUERTO 280 GUADALUPE INN", (pageWidth/5)+130, getPosition(porportionalLine), paintText);
            canvas.drawText("DELEGACIÓN ALVARO OBREGON 01029 CDMX", (pageWidth/5)+110, getPosition(porportionalLine), paintText);
            Rect bounds = new Rect();
            int text_height = 0;
            int text_width = 0;
            String text="RFC: "+credit.getDatosGenerales().getRfc();
            paintText.getTextBounds(text, 0,text.length(), bounds);
            text_width =  bounds.width();
            canvas.drawText("RFC: " + credit.getDatosGenerales().getRfc(), pageWidth-text_width-paddingSide, getPosition(porportionalLine*5), paintText);
            text="CRÉDITO: "+credutNum;
            paintText.getTextBounds(text, 0,text.length(), bounds);
            text_width =  bounds.width();
            canvas.drawText("CRÉDITO: " + credutNum, pageWidth-text_width-paddingSide, getPosition(porportionalLine), paintText);
            canvas.drawText(getDate(), paddingSide, getPosition(porportionalLine*3), paintText);
            canvas.drawText("C. Acreditado", paddingSide,  getPosition(porportionalLine), paintText);
            canvas.drawText("Esta es tu Carta Constancia de Intereses del ejercicio " + creditYear + " de tu crédito", paddingSide, getPosition(porportionalLine), paintTextDescription);
            canvas.drawText("del Infonavit. Te será de ayuda para poder deducir en tu declaración anual", paddingSide, getPosition(porportionalLine), paintTextDescription);
            canvas.drawText("los  intereses  reales  pagados  por  tu  crédito Infonavit,  en caso de que ", paddingSide, getPosition(porportionalLine), paintTextDescription);
            canvas.drawText("debas presentarla. Contiene e indica el monto de los intereses nominales", paddingSide, getPosition(porportionalLine), paintTextDescription);
            canvas.drawText("devengados,  así  como  los  intereses pagados en el ejercicio " + creditYear + " y los ", paddingSide, getPosition(porportionalLine), paintTextDescription);
            canvas.drawText("intereses reales. De esta forma cumplimos con lo dispuesto en la Ley del", paddingSide, getPosition(porportionalLine), paintTextDescription);
            canvas.drawText("Impuesto sobre la Renta y su reglamento.", paddingSide,  getPosition(porportionalLine), paintTextDescription);
            canvas.drawText("DOMICILIO Y UBICACIÓN DEL INMUEBLE HIPOTECADO: ", paddingSide,  getPosition(porportionalLine*3), paintText);
            canvas.drawText("NOMBRE DEL ACREDITADO: " + credit.getDatosGenerales().getNombre(), paddingSide,  getPosition(porportionalLine*3), paintText);
            canvas.drawText("CALLE: " + credit.getDatosGenerales().getDomicilio().getCalleNumero(), paddingSide,  getPosition(porportionalLine), paintText);
            canvas.drawText("COLONIA: " + credit.getDatosGenerales().getDomicilio().getColonia(), paddingSide,  getPosition(porportionalLine), paintText);
            canvas.drawText("MUNICIPIO: " + credit.getDatosGenerales().getDomicilio().getPoblacion(), paddingSide,  getPosition(porportionalLine), paintText);
            canvas.drawText("ESTADO: " + credit.getDatosGenerales().getDomicilio().getEstado(), paddingSide,  getPosition(porportionalLine), paintText);
            canvas.drawText("CÓDIGO POSTAL: " + credit.getDatosGenerales().getDomicilio().getCp(), paddingSide,  getPosition(porportionalLine), paintText);
            //writing description
            canvas.drawText("INTERESES DEL CRÉDITO HIPOTECARIO: ", paddingSide,  getPosition(porportionalLine*5), paintTextDescription);
            canvas.drawText("Intereses nominales devengados: " + credit.getDatosFinancieros().getInteresDevengado(), paddingSide,  getPosition(porportionalLine*2), paintTextDescription);
            canvas.drawText("Intereses pagados en el ejercicio: " + credit.getDatosFinancieros().getInteresPagado(), paddingSide,  getPosition(porportionalLine), paintTextDescription);
            canvas.drawText("Intereses reales pagados en el ejercicio: " + credit.getDatosFinancieros().getInteresReal(), paddingSide,  getPosition(porportionalLine), paintTextDescription);
            canvas.drawText("ATENTAMENTE", paddingSide, getPosition(porportionalLine*7), paintText);
            canvas.drawText("Subdirección General de Administración de Cartera", paddingSide, getPosition(porportionalLine+30), paintTitleSubdirec);
            if (scaleImageCedula != null) {
                int imageHeight= (int)(pageHieght/3);
                int imageWidth= (int)((imageHeight)*0.5);
                int yPosition=pageHieght/2+240;
                int xPosition=(pageWidth/5)*3+50;
                canvas.drawBitmap(resizeBitmap(cedula,imageWidth,imageHeight), xPosition, yPosition, null);
            }
            position=0;
            pdfDocument.finishPage(mypage);
        }
        else{
            AvisoViewModel viewModel=(AvisoViewModel)mViewModel;
            Item item=viewModel.getAviso().getValue().getDatosAvisos().getItem().get(0);
            String ClasAviso = item.getCLASE_DEL_AVISO();
            String calleAviso = item.getCALLE_Y_NUMERO_NRP();
            String coloAviso = item.getCOLONIA_NRP();
            String localAviso = item.getLOCALIDAD_NRP();
            String entAviso = item.getENT_FED_DESCRIP_NRP();
            String TipDesc =item.getTIPDESC();
            String TipDescAnt = item.getTIPDESC_ANTERIOR();
            String Factor = item.getFACTOR();
            String FactorAnt = item.getFACTOR_ANTERIOR();
            String fDireccion="";
            String NuevoFactorDesc="";
            String DescAnterior="";
            String Porcen="",Pesos="";


            //condicionales
            if (calleAviso.equals("") && !coloAviso.equals("")) {
                fDireccion = coloAviso;
            } else if (!calleAviso.equals("") && coloAviso.equals("")) {
                fDireccion = calleAviso;
            } else if (!calleAviso.equals("") && !coloAviso.equals("")) {
                fDireccion = calleAviso + ",   " + coloAviso;
            }

            if (localAviso.startsWith("PSCD")) {
                localAviso = "";
            }

            if (entAviso.startsWith("Oficinas")) {
                entAviso = "";
            }

            if (Factor.equals("0") || Factor.equals("0.0")) {
                NuevoFactorDesc = item.getMONTDESC();
            } else {
                NuevoFactorDesc = Factor;
            }

            if (FactorAnt.equals("0") || FactorAnt.equals("0.0")) {
                DescAnterior = item.getMONTDESCANTERIOR();
            } else {
                DescAnterior = FactorAnt;
            }

            if (TipDesc.equals("1") || TipDesc.equals("01")) {
                Porcen = item.getMONTDESC() + " %";
                Pesos = "0";
                NuevoFactorDesc = NuevoFactorDesc + " %";
            } else if (TipDesc.equals("2") || TipDesc.equals("02")) {
                Porcen = "0";
                Pesos = "$ " + item.getMONTDESC();
                NuevoFactorDesc = "$ " + NuevoFactorDesc;
            } else {
                Porcen = item.getMONTDESC() + " %";
                Pesos = "0";
                NuevoFactorDesc = NuevoFactorDesc + " VSM";
            }

            if (TipDescAnt.equals("1") || TipDescAnt.equals("01")) {
                DescAnterior = DescAnterior + " %";
            } else if (TipDescAnt.equals("2") || TipDescAnt.equals("02")) {
                DescAnterior = "$ " + DescAnterior;
            } else {
                DescAnterior = DescAnterior + " VSM";
            }

            if(mode==2){
                pageWidth=689;
                pageHieght=892;
                Bitmap logo = BitmapFactory.decodeResource(activity.getResources(), R.drawable.logo_pdf);
                Bitmap sign = BitmapFactory.decodeResource(activity.getResources(), R.drawable.firmanotice);
                pageInfo = new PdfDocument.PageInfo.Builder(pageWidth, pageHieght, 1).create();
                mypage = pdfDocument.startPage(pageInfo);
                canvas = mypage.getCanvas();
                //LOGO
                canvas.drawBitmap(resizeBitmap(logo, 59, 45), 600, 20, null);
                //FiRMA
                canvas.drawBitmap(resizeBitmap(sign, 140, 60), 265, 707, null);

                //TITULO
                canvas.drawRect(20, 20, 579, 67, rectPaint);
                canvas.drawText("AVISO  PARA  RETENCIÓN  DE  DESCUENTOS", 35, 52, paintTitle1);
                //FOLIO Y FECHA
                canvas.drawRect(512, 77, 669, 92, rect);
                canvas.drawText("FOLIO: ", 517, 87, paintBold1);
                canvas.drawText(item.getNROAVIS(), 567, 87, paintText1);
                canvas.drawRect(512, 92, 669, 107, rect);
                canvas.drawText("FECHA: ", 517, 103, paintBold1);
                canvas.drawText(item.getFCREAVIS(), 567, 103, paintText1);


                //NOMBRE y DIRECCIÓN NRP
                canvas.drawRect(20, 112, 492, 127, rect);
                canvas.drawText("NOMBRE  Y  DOMICILIO  FISCAL  DE  LA  EMPRESA  RETENEDORA", 112, 123, paintBold1);
                canvas.drawRect(20, 127, 492, 172, rect);
                canvas.drawText(item.getNOMBRE_NRP(), 25, 137, paintText1);
                canvas.drawText(fDireccion, 25, 147, paintText1);
                canvas.drawText(localAviso, 25, 157, paintText1);
                canvas.drawText("C.P : " + item.getCP_NRP(), 337, 157, paintText1);
                canvas.drawText(entAviso, 25, 167, paintText1);
                canvas.drawText("Municipio: " + item.getEDO_MUNICIPIO_NRP(), 337, 167, paintText1);

                //NRP Y RFC
                canvas.drawRect(492, 112, 669, 142, rect);
                canvas.drawText("N.R.P: ", 497, 132, paintBold);
                canvas.drawText(item.getNRP(), 537, 132, paintText1);
                canvas.drawRect(492, 142, 669, 172, rect);
                canvas.drawText("R.F.C: ", 497, 162, paintBold);
                canvas.drawText(item.getRFC_NRP(), 537, 162, paintText1);

                //TEXTO
                canvas.drawRect(20, 177, 669, 622, rect);
                canvas.drawText("El Instituto del Fondo Nacional de la Vivienda para los Trabajadores ha otorgado un crédito de vivienda al trabajador, cuyos datos se consignan al calce, quien aceptó le sean", 25, 187, paintTextDescription1);
                canvas.drawText("efectuados descuentos a su salario, para amortizar su crédito; según se señala en el recuadro de “DESCUENTO” de este aviso, pudiendo ser:", 25, 197, paintTextDescription1);
                canvas.drawCircle(25, 205, 2, blckPoint);
                canvas.drawText("Descuento en porcentaje se calcula: Salario diario integrado por el porcentaje de descuento por los días laborados con crédito con cada salario que haya percibido el trabajador.", 40, 207, paintTextDescription1);
                canvas.drawCircle(25, 215, 2, blckPoint);
                canvas.drawText("Descuento en pesos se calcula: Cuota fija en pesos mensual por los meses del bimestre entre días del bimestre por días laborados con crédito.", 40, 217, paintTextDescription1);
                canvas.drawCircle(25, 225, 2, blckPoint);
                canvas.drawText("Factor de descuento, presenta dos variantes:", 40, 227, paintTextDescription1);
                canvas.drawCircle(65, 235, 2, whtPoint);
                canvas.drawText("Para efectos de los descuentos correspondientes a 2016 y años anteriores, se debe considerar para el cálculo la cuota fija en VSM por el SMGDF por los meses del", 80, 237, paintTextDescription1);
                canvas.drawText("bimestre entre los días del bimestre por los días laborados con crédito.", 80, 247, paintTextDescription1);
                canvas.drawCircle(65, 255, 2, whtPoint);
                canvas.drawText("Para efectos de descuentos posteriores al 1 de Enero de 2017, se debe considerar para el cálculo el factor de descuento por el valor de la Unidad Mixta INFONAVIT", 80, 257, paintTextDescription1);
                canvas.drawText("base de cálculo por los meses del bimestre entre los días del bimestre por los días laborados con crédito", 80, 267, paintTextDescription1);
                canvas.drawText("El valor de la Unidad Mixta INFONAVIT se determina de conformidad con lo dispuesto en el artículo 6º transitorio del Decreto en materia de desindexación del salario mínimo, de", 25, 277, paintTextDescription1);
                canvas.drawText("la Constitución Política de los Estados Unidos Mexicanos, publicado el 27 de enero del 2016 en el Diario Oficial de la Federación, considerando la actualización del salario mínimo,", 25, 287, paintTextDescription1);
                canvas.drawText("más el crecimiento porcentual de la inflación sustituyendo de esta manera el Salario Mínimo.", 25, 297, paintTextDescription1);
                canvas.drawCircle(25, 305, 2, blckPoint);
                canvas.drawText("Si el trabajador percibe un salario mínimo, no podrá exceder del 20% del mismo, con fundamento en los artículos 47 del Reglamento de Inscripción, Pago de Aportaciones y", 40, 307, paintTextDescription1);
                canvas.drawText("Entero de Descuentos al Instituto del Fondo Nacional de la Vivienda para los Trabajadores; y 110 fracción VII de la Ley Federal del Trabajo.", 40, 317, paintTextDescription1);
                canvas.drawCircle(25, 325, 2, blckPoint);
                canvas.drawText("Cuando se indique el factor de descuento mensual y el periodo sea menor a los días que comprende el bimestre, deberá retener y enterar la parte proporcional de los días del", 40, 327, paintTextDescription1);
                canvas.drawText("bimestre efectivamente laborados por el trabajador", 40, 337, paintTextDescription1);
                canvas.drawText("Con fundamento en los artículos 23 fracción I de la Ley del Instituto del Fondo Nacional de la Vivienda para los Trabajadores; 1, 3 fracciones XXXVII y XLV, 4 fracción IX, así como el", 25, 347, paintTextDescription1);
                canvas.drawText("artículo 13 del Reglamento Interior del Instituto del Fondo Nacional de la Vivienda para los Trabajadores publicado en el Diario Oficial de la Federación el 20 de junio de 2008, así", 25, 357, paintTextDescription1);
                canvas.drawText("como su reforma publicada en el mismo Diario el 31 de julio de 2017;97 fracción III y 110 fracción III de la Ley Federal del Trabajo;29 fracción III de la Ley del Instituto del Fondo", 23, 367, paintTextDescription1);
                canvas.drawText("Nacional de la Vivienda para los Trabajadores; 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53 y 54 del Reglamento de Inscripción, Pago de Aportaciones y Entero de Descuentos al", 25, 377, paintTextDescription1);
                canvas.drawText("Instituto del Fondo Nacional de la Vivienda para los Trabajadores, deberá usted descontar del salario diario integrado de aportación, sin límite superior salarial y con la periodicidad", 25, 387, paintTextDescription1);
                canvas.drawText("con que efectúe el pago de éste, la cantidad que resulte conforme al tipo de descuento indicado en los puntos anteriores, ", 25, 397, paintTextDescription1);
                canvas.drawText("a partir del día siguiente a aquel en que se le haya notificado", 455, 397, paintBoldCS1);
                canvas.drawText("el presente aviso,", 25, 407, paintBoldCS1);
                canvas.drawText("conforme a lo siguiente:", 88, 407, paintTextDescription1);
                canvas.drawText("El entero de los descuentos se efectuará de manera bimestral conjuntamente con las aportaciones patronales, en la entidad receptora autorizada de su preferencia, a más tardar el", 25, 417, paintTextDescription1);
                canvas.drawText("día 17: del mes siguiente al bimestre que corresponda, cuando el último día para el cumplimiento de pago sea inhábil o viernes, se estará a lo dispuesto en el artículo 12 del Código", 25, 427, paintTextDescription1);
                canvas.drawText("Fiscal de la Federación, mediante el programa de cómputo aprobado por el Instituto o utilizando las “Cédulas de Determinación de Cuotas, Aportaciones y Amortizaciones” emitidas", 25, 437, paintTextDescription1);
                canvas.drawText("conjuntamente por el IMSS y el INFONAVIT, cuando así proceda.", 25, 447, paintTextDescription1);
                canvas.drawText("Los patrones son solidariamente responsables del entero de los descuentos ante el Instituto, en términos de lo que señalan los artículos 26 fracción I del Código Fiscal de la", 25, 457, paintTextDescription1);
                canvas.drawText("Federación; 29 fracción III de la Ley del Instituto del Fondo Nacional de la Vivienda para los Trabajadores; 50, 51 y 52 del Reglamento de Inscripción antes citado, a partir del día", 25, 467, paintTextDescription1);
                canvas.drawText("siguiente a aquel en que reciban este aviso y hasta en tanto no se presente aviso de baja del trabajador en el formato Afil-04 del IMSS-INFONAVIT o el equivalente, o reciban del", 25, 477, paintTextDescription1);
                canvas.drawText("Instituto el Aviso de Suspensión de Descuentos.", 25, 487, paintTextDescription1);
                canvas.drawText("En caso de no dar cumplimiento a las obligaciones antes señaladas, se hará acreedor a la determinación y cobro de el (los) crédito(s) fiscal(es) y a las sanciones que correspondan", 25, 497, paintTextDescription1);
                canvas.drawText("por violaciones a la Ley y sus Reglamentos en los términos de los artículos 2, 4 y 6 del Código Fiscal de la Federación; y 30, 55, 56 y 57 de la Ley del Instituto del Fondo Nacional de", 25, 507, paintTextDescription1);
                canvas.drawText("la Vivienda para los Trabajadores; y 6, 7, 19, 20, 21, 22 y 25 del Reglamento para la Imposición de Multas por Incumplimiento de las Obligaciones que la Ley del Instituto del Fondo", 25, 517, paintTextDescription1);
                canvas.drawText("Nacional de la Vivienda para los Trabajadores y sus Reglamentos establecen a cargo de los Patrones.", 25, 527, paintTextDescription1);
                canvas.drawText("Hacemos de su conocimiento, que por ningún motivo deberá sellar ni firmar este documento si el trabajador a que se refiere el mismo no guarda actualmente relación laboral con", 25, 537, paintTextDescription1);
                canvas.drawText("usted, en caso contrario, será responsable del entero de las amortizaciones que correspondan, sin perjuicio de aplicar el contenido del artículo 58 de la Ley del Instituto del Fondo", 25, 547, paintTextDescription1);
                canvas.drawText("Nacional de la Vivienda para los Trabajadores.", 25, 557, paintTextDescription1);
                canvas.drawText("De conformidad con el artículo 48 del Reglamento de Inscripción, Pago de Aportaciones y Entero de Descuentos al Instituto del Fondo Nacional de la Vivienda para los Trabajadores,", 25, 567, paintTextDescription1);
                canvas.drawText("la omisión de los datos de un trabajador en la Cédula de Determinación emitida por el Instituto Mexicano del Seguro Social, no exime al patrón de la obligación de retener y enterar", 25, 577, paintTextDescription1);
                canvas.drawText("los descuentos a través del programa de cómputo autorizado.", 25, 587, paintTextDescription1);
                canvas.drawText("La recepción y firma de este Aviso, implica que acepta que la relación laboral que mantiene con el trabajador que se cita, se encuentra vigente y que no existen causas probables", 25, 597, paintTextDescription1);
                canvas.drawText("para que ésta se rescinda en términos de los artículos 47 y 51 de la Ley Federal del Trabajo o se termine por lo previsto en el artículo 53 de dicha Ley; dentro de los 90 días", 25, 607, paintTextDescription1);
                canvas.drawText("posteriores a la fecha de recepción y firma de este Aviso.", 25, 617, paintTextDescription1);
//NUMERO NSS, NUMERO DE CRÉDITO Y RFC DEL TRABAJADOR
                canvas.drawRect(20, 622, 171, 637, rect);
                canvas.drawText("NÚMERO  DE  SEGURIDAD  SOCIAL", 30, 632, paintBold);
                canvas.drawRect(20, 637, 171, 652, rect);
                canvas.drawText(item.getNSS(), 30, 648, paintText1);
                canvas.drawRect(171, 622, 306, 637, rect);
                canvas.drawText("NÚNMERO  DE  CRÉDITO", 181, 632, paintBold);
                canvas.drawRect(171, 637, 306, 652, rect);
                canvas.drawText(item.getNUMCRED(), 181, 648, paintText1);
                canvas.drawRect(306, 622, 461, 637, rect);
                canvas.drawText("RFC  O  CURP  DEL  TRABAJADOR", 316, 632, paintBold);
                canvas.drawRect(306, 637, 461, 652, rect);
                canvas.drawText(item.getRFC_NSS(), 316, 648, paintText1);

                //NOMBRE_NSS
                canvas.drawRect(461, 622, 669, 637, rect);
                canvas.drawText("NOMBRE  DEL  TRABAJADOR", 471, 633, paintBold1);
                canvas.drawRect(461, 637, 669, 697, rect);
                canvas.drawText(item.getNOMBRE_NSS(), 471, 667, paintText1);

                //DESCUENTO MENSUAL
                //PORCENTAJE, PESOS, FACTOR DE DESCUENTO
                canvas.drawRect(20, 652, 461, 667, rect);
                canvas.drawText("DESCUENTO  MENSUAL", 180, 663, paintBold1);
                canvas.drawRect(20, 667, 171, 682, rect);
                canvas.drawText("PORCENTAJE", 65, 677, paintBold);
                canvas.drawRect(20, 682, 171, 697, rect);
                canvas.drawText(Porcen, 65, 693, paintText1);
                canvas.drawRect(171, 667, 306, 682, rect);
                canvas.drawText("PESOS", 225, 677, paintBold);
                canvas.drawRect(171, 682, 306, 697, rect);
                canvas.drawText(Pesos, 225, 693, paintText1);
                canvas.drawRect(306, 667, 461, 682, rect);
                canvas.drawText("FACTOR  DE  DESCUENTO", 321, 677, paintBold);
                canvas.drawRect(306, 682, 461, 697, rect);
                canvas.drawText(item.getFACTOR(), 321, 693, paintText1);

                //Texto Firma
                canvas.drawText("A  T  E  N  T  A  M  E  N  T  E", 290, 708, paintBold);
                canvas.drawRect(237, 774, 447, 775, line);
                canvas.drawText("MTRO. FERNANDO TAPIA DÍAZ", 281, 781, paintBold);
                canvas.drawText("GERENTE  DE  FACTURACIÓN  FISCAL", 267, 790, paintBold);

                //NOMBRE FIRMA
                canvas.drawRect(20, 797, 345, 872, rect);
                canvas.drawText("PERSONA  QUE  RECIBE  EL  AVISO", 112, 807, paintBold);
                canvas.drawRect(30, 857, 172, 858, line);
                canvas.drawText("NOMBRE", 82, 867, paintBold);
                canvas.drawRect(192, 857, 334, 858, line);
                canvas.drawText("FIRMA", 247, 867, paintBold);

                //FECHA DE RECEPCIÓN Y SELLO DE LA EMPRESA
                canvas.drawRect(345, 797, 474, 812, rect);
                canvas.drawText("FECHA  DE  RECEPCIÓN", 360, 807, paintBold);
                canvas.drawRect(345, 812, 474, 857, rect);
                canvas.drawRect(345, 857, 474, 872, rect);
                canvas.drawText("DD / MM / AAAA", 375, 867, paintBold);
                canvas.drawRect(474, 797, 669, 812, rect);
                canvas.drawText("SELLO  DE  LA  EMPRESA", 520, 807, paintBold);
                canvas.drawRect(474, 812, 669, 872, rect);
                canvas.drawText("Act.07-2019/GSRyCF", 550, 882, paintBold);

                position=0;
                pdfDocument.finishPage(mypage);

            }
            else if(mode==3){
                pageWidth=612;
                pageHieght=792;
                Bitmap logo = BitmapFactory.decodeResource(activity.getResources(), R.drawable.logo_pdf);
                Bitmap sign = BitmapFactory.decodeResource(activity.getResources(), R.drawable.firmanotice);
                pageInfo = new PdfDocument.PageInfo.Builder(pageWidth, pageHieght, 1).create();
                mypage = pdfDocument.startPage(pageInfo);
                canvas = mypage.getCanvas();

                //LOGO
                canvas.drawBitmap(resizeBitmap(logo, 59, 45), 530, 20, null);

                //FiRMA
                canvas.drawBitmap(resizeBitmap(sign, 140, 60), 230, 480, null);

                //TITULO
                canvas.drawRect(20, 20, 502, 67, rectPaint);
                canvas.drawText("AVISO  DE  SUSPENSIÓN  DE  DESCUENTOS", 45, 52, paintTitle2);
                //FOLIO Y FECHA
                canvas.drawRect(435, 87, 592, 102, rect);
                canvas.drawText("FOLIO: ", 440, 97, paintBold);
                canvas.drawText(item.getNROAVIS(), 480, 97, paintText);
                canvas.drawRect(435, 102, 592, 117, rect);
                canvas.drawText("FECHA: ", 440, 112, paintBold);
                canvas.drawText(item.getFCREAVIS(), 480, 112, paintText);

                //NOMBRE y DIRECCIÓN NRP
                canvas.drawRect(20, 127, 415, 142, rect);
                canvas.drawText("NOMBRE  Y  DOMICILIO  FISCAL  DE  LA  EMPRESA  RETENEDORA", 90, 138, paintBold);
                canvas.drawRect(20, 142, 415, 187, rect);
                canvas.drawText(item.getNOMBRE_NRP(), 25, 152, paintText);
                canvas.drawText(fDireccion, 25, 162, paintText);
                canvas.drawText(localAviso, 25, 172, paintText);
                canvas.drawText("C.P : " + item.getCP_NRP(), 260, 172, paintText);
                canvas.drawText(entAviso, 25, 182, paintText);
                canvas.drawText("Municipio: " + item.getEDO_MUNICIPIO_NRP(), 260, 182, paintText);

                //NRP Y RFC
                canvas.drawRect(415, 127, 592, 157, rect);
                canvas.drawText("N.R.P: ", 420, 147, paintBold);
                canvas.drawText(item.getNRP(), 460, 147, paintText);
                canvas.drawRect(415, 157, 592, 187, rect);
                canvas.drawText("R.F.C: ", 420, 177, paintBold);
                canvas.drawText(item.getRFC_NRP(), 460, 177, paintText);

                //TEXTO
                canvas.drawRect(20, 197, 592, 347, rect);
                canvas.drawText("Con fundamento en los artículos 23 fracción I de la Ley del Instituto del Fondo Nacional de la Vivienda para los Trabajadores;1,3", 25, 207, paintTextDescription2);
                canvas.drawText("fracciones XXXVII y XLV, 4 fracción IX, así como el artículo 13 del Reglamento Interior del Instituto del Fondo Nacional de la", 25, 217, paintTextDescription2);
                canvas.drawText("Vivienda para los Trabajadores publicado en el Diario Oficial de la Federación el 20 de junio de 2008, así como su reforma", 25, 227, paintTextDescription2);
                canvas.drawText("publicada en el mismo Diario el 31 de julio de 2017; 50 segundo párrafo del Reglamento de Inscripción, Pago de Aportaciones y", 25, 237, paintTextDescription2);
                canvas.drawText("Entero de Descuentos al Instituto del Fondo Nacional de la Vivienda para los Trabajadores, le notifico que a", 25, 247, paintTextDescription2);
                canvas.drawText("partir de la fecha en", 501, 247, paintBoldCS2);
                canvas.drawText("que se reciba este aviso deberá suspender los descuentos que por concepto de amortización se vienen efectuando al trabajador,", 25, 257, paintBoldCS2);

                canvas.drawText("cuyos datos se asientan en el presente aviso.", 25, 267, paintTextDescription2);
                canvas.drawText("Los descuentos retenidos y no enterados a la fecha de recepción de este aviso, deberán devolverse al trabajador.", 25, 287, paintTextDescription2);
                canvas.drawText("Si sus pagos los realiza a través del Sistema Único de Autodeterminación (SUA), deberá proceder a dar de baja el número de", 25, 307, paintTextDescription2);
                canvas.drawText("crédito de este trabajador en el referido sistema, lo anterior sin perjuicio de seguir cubriendo en su favor las aportaciones del", 25, 317, paintTextDescription2);
                canvas.drawText("5% que establece la Ley del Instituto del Fondo Nacional de la Vivienda para los Trabajadores así como del Reglamento de", 25, 327, paintTextDescription2);
                canvas.drawText("Inscripción Pago de Aportaciones y Entero de Descuentos al Instituto del Fondo Nacional de la Vivienda para los Trabajadores.", 25, 337, paintTextDescription2);

                //NUMERO CRÉDITO NSS y NOMBRE NSS
                canvas.drawRect(20, 347, 290, 367, rect);
                canvas.drawText("NÚMERO  DE  CRÉDITO", 110, 362, paintBold);
                canvas.drawRect(20, 367, 290, 387, rect);
                canvas.drawText(item.getNUMCRED(), 110, 382, paintText);
                canvas.drawRect(290, 347, 592, 367, rect);
                canvas.drawText("NOMBRE  DEL  TRABAJADOR ", 400, 362, paintBold);
                canvas.drawRect(290, 367, 592, 427, rect);
                canvas.drawText(item.getNOMBRE_NSS(), 400, 392, paintText);

                //NÚMERO DE SEGURIDAD SOCIAL Y RFC O CURP DEL TRABAJADOR
                canvas.drawRect(20, 387, 155, 407, rect);
                canvas.drawText("NÚMERO  DE  SEGURIDAD  SOCIAL", 30, 402, paintBold2);
                canvas.drawRect(20, 407, 155, 427, rect);
                canvas.drawText(item.getNSS(), 30, 422, paintText);
                canvas.drawRect(155, 387, 290, 407, rect);
                canvas.drawText("RFC  O  CURP  DEL  TRABAJADOR", 165, 402, paintBold2);
                canvas.drawRect(155, 407, 290, 427, rect);
                canvas.drawText(item.getRFC_NSS(), 165, 422, paintText);

                //Texto Firma
                canvas.drawText("A  T  E  N  T  A  M  E  N  T  E", 260, 478, paintBold2);
                canvas.drawRect(205, 544, 395, 545, line);
                canvas.drawText("MTRO. FERNANDO TAPIA DÍAZ", 255, 551, paintBold2);
                canvas.drawText("GERENTE  DE  FACTURACIÓN  FISCAL", 245, 560, paintBold2);

                //NOMBRE FIRMA
                canvas.drawRect(20, 597, 306, 672, rect);
                canvas.drawText("PERSONA  QUE  RECIBE  EL  AVISO", 105, 607, paintBold2);
                canvas.drawRect(30, 657, 160, 658, line);
                canvas.drawText("NOMBRE", 80, 667, paintBold2);
                canvas.drawRect(170, 657, 296, 658, line);
                canvas.drawText("FIRMA", 220, 667, paintBold2);

                //FECHA DE RECEPCIÓN Y SELLO DE LA EMPRESA
                canvas.drawRect(306, 597, 420, 612, rect);
                canvas.drawText("FECHA  DE  RECEPCIÓN", 326, 607, paintBold2);
                canvas.drawRect(306, 612, 420, 657, rect);
                canvas.drawRect(306, 657, 420, 672, rect);
                canvas.drawText("DD / MM / AAAA", 336, 667, paintBold2);
                canvas.drawRect(420, 597, 592, 612, rect);
                canvas.drawText("SELLO  DE  LA  EMPRESA", 465, 607, paintBold2);
                canvas.drawRect(420, 612, 592, 672, rect);
                canvas.drawText("Act.07-2019/GSRyCF", 500, 682, paintBold2);


                position=0;
                pdfDocument.finishPage(mypage);
            }
            else if(mode==4){
                pageWidth=612;
                pageHieght=792;
                Bitmap logo = BitmapFactory.decodeResource(activity.getResources(), R.drawable.logo_pdf);
                Bitmap sign = BitmapFactory.decodeResource(activity.getResources(), R.drawable.firmanotice);
                pageInfo = new PdfDocument.PageInfo.Builder(pageWidth, pageHieght, 1).create();
                mypage = pdfDocument.startPage(pageInfo);
                canvas = mypage.getCanvas();
                //LOGO
                canvas.drawBitmap(resizeBitmap(logo, 59, 45), 530, 20, null);

                //FiRMA
                canvas.drawBitmap(resizeBitmap(sign, 140, 60), 230, 520, null);

                //TITULO
                canvas.drawRect(20, 20, 502, 67, rectPaint);
                canvas.drawText("AVISO DE SUSPENSIÓN POR PRÓXIMA LIQUIDACIÓN DE CRÉDITO", 29, 52, paintTitle3);

                //FOLIO Y FECHA
                canvas.drawRect(435, 87, 592, 102, rect);
                canvas.drawText("FOLIO: ", 440, 97, paintBold);
                canvas.drawText(item.getNROAVIS(), 480, 97, paintText);
                canvas.drawRect(435, 102, 592, 117, rect);
                canvas.drawText("FECHA: ", 440, 112, paintBold);
                canvas.drawText(item.getFCREAVIS(), 480, 112, paintText);

                //NOMBRE y DIRECCIÓN NRP
                canvas.drawRect(20, 127, 415, 142, rect);
                canvas.drawText("NOMBRE  Y  DOMICILIO  FISCAL  DE  LA  EMPRESA  RETENEDORA", 90, 138, paintBold);
                canvas.drawRect(20, 142, 415, 187, rect);
                canvas.drawText(item.getNOMBRE_NRP(), 25, 152, paintText);
                canvas.drawText(fDireccion, 25, 162, paintText);
                canvas.drawText(item.getLOCALIDAD_NRP(), 25, 172, paintText);
                canvas.drawText("C.P : " + item.getCP_NRP(), 260, 172, paintText);
                canvas.drawText(entAviso, 25, 182, paintText);
                canvas.drawText("Municipio: " + item.getEDO_MUNICIPIO_NRP(), 260, 182, paintText);

                //NRP Y RFC
                canvas.drawRect(415, 127, 592, 157, rect);
                canvas.drawText("N.R.P: ", 420, 147, paintBold);
                canvas.drawText(item.getNRP(), 460, 147, paintText);
                canvas.drawRect(415, 157, 592, 187, rect);
                canvas.drawText("R.F.C: ", 420, 177, paintBold);
                canvas.drawText(item.getRFC_NRP(), 460, 177, paintText);

                //TEXTO
                canvas.drawRect(20, 197, 592, 387, rect);
                canvas.drawText("Con fundamento en los artículos 23 fracción I de la Ley del Instituto del Fondo Nacional de la Vivienda para los Trabajadores;1,3", 25, 212, paintTextDescription2);
                canvas.drawText("fracciones XXXVII y XLV, 4 fracción IX, así como el artículo 13 del Reglamento Interior del Instituto del Fondo Nacional de la", 25, 222, paintTextDescription2);
                canvas.drawText("Vivienda para los Trabajadores publicado en el Diario Oficial de la Federación el 20 de junio de 2008, así como su reforma", 25, 232, paintTextDescription2);
                canvas.drawText("publicada en en el mismo Diario el 31 de julio de 2017;50 segundo párrafo del Reglamento de Inscripción,Pago de Aportaciones", 25, 242, paintTextDescription2);
                canvas.drawText("y Entero de Descuentos al Instituto del Fondo Nacional de la Vivienda para los Trabajadores, le notifico que a", 25, 252, paintTextDescription2);
                canvas.drawText("partir de la fecha", 510, 252, paintBoldCS2);
                canvas.drawText("en que se reciba este aviso,", 25, 262, paintBoldCS2);
                canvas.drawText("deberá suspender los descuentos, derivado a que el crédito está en la etapa final de cobro, motivo", 148, 262, paintBoldCS3);
                canvas.drawText("por el cual ya no deberá de retenerle la amortización ya que con el 5% de la aportación patronal durante un plazo establecido de", 25, 272, paintBoldCS3);
                canvas.drawText("12 meses se terminará de pagar.", 25, 282, paintBoldCS3);
                canvas.drawText("Los descuentos retenidos por el concepto de amortización durante el bimestre de la recepción de este aviso deberán ser", 25, 302, paintBoldCS3);
                canvas.drawText("enterados al Instituto para ser aplicados al crédito de su trabajador.", 25, 312, paintBoldCS3);
                canvas.drawText("Si sus pagos los realiza a través del Sistema Único de Autodeterminación (SUA), deberá proceder a dar de baja el número de", 25, 332, paintTextDescription2);
                canvas.drawText("crédito de este trabajador en el referido sistema, lo anterior sin perjuicio de seguir cubriendo en su favor las aportaciones del", 25, 342, paintTextDescription2);
                canvas.drawText("5% que establece la Ley del Instituto del Fondo Nacional de la Vivienda para los Trabajadores así como del Reglamento de", 25, 352, paintTextDescription2);
                canvas.drawText("Inscripción Pago de Aportaciones y Entero de Descuentos al Instituto del Fondo Nacional de la Vivienda para los Trabajadores.", 25, 362, paintTextDescription2);
                canvas.drawText("El presente aviso es emitido como beneficio al buen comportamiento de pago del crédito del trabajador", 25, 382, paintBold3);

                //NUMERO CRÉDITO NSS y NOMBRE NSS
                canvas.drawRect(20, 387, 290, 407, rect);
                canvas.drawText("NÚMERO  DE  CRÉDITO", 110, 402, paintBold);
                canvas.drawRect(20, 407, 290, 427, rect);
                canvas.drawText(item.getNUMCRED(), 110, 422, paintText);
                canvas.drawRect(290, 387, 592, 407, rect);
                canvas.drawText("NOMBRE  DEL  TRABAJADOR ", 400, 402, paintBold);
                canvas.drawRect(290, 407, 592, 467, rect);
                canvas.drawText(item.getNOMBRE_NSS(), 400, 432, paintText);

                //NÚMERO DE SEGURIDAD SOCIAL Y RFC O CURP DEL TRABAJADOR
                canvas.drawRect(20, 427, 155, 447, rect);
                canvas.drawText("NÚMERO  DE  SEGURIDAD  SOCIAL", 30, 442, paintBold2);
                canvas.drawRect(20, 447, 155, 467, rect);
                canvas.drawText(item.getNSS(), 30, 462, paintText);
                canvas.drawRect(155, 427, 290, 447, rect);
                canvas.drawText("RFC  O  CURP  DEL  TRABAJADOR", 165, 442, paintBold2);
                canvas.drawRect(155, 447, 290, 467, rect);
                canvas.drawText(item.getRFC_NSS(), 165, 462, paintText);

                //Texto Firma
                canvas.drawText("A  T  E  N  T  A  M  E  N  T  E", 260, 518, paintBold2);
                canvas.drawRect(205, 584, 395, 585, line);
                canvas.drawText("MTRO. FERNANDO TAPIA DÍAZ", 255, 591, paintBold2);
                canvas.drawText("GERENTE  DE  FACTURACIÓN  FISCAL", 245, 600, paintBold2);

                //NOMBRE FIRMA
                canvas.drawRect(20, 637, 306, 712, rect);
                canvas.drawText("PERSONA  QUE  RECIBE  EL  AVISO", 105, 647, paintBold2);
                canvas.drawRect(30, 697, 160, 698, line);
                canvas.drawText("NOMBRE", 80, 707, paintBold2);
                canvas.drawRect(170, 697, 296, 698, line);
                canvas.drawText("FIRMA", 220, 707, paintBold2);

                //FECHA DE RECEPCIÓN Y SELLO DE LA EMPRESA
                canvas.drawRect(306, 637, 420, 652, rect);
                canvas.drawText("FECHA  DE  RECEPCIÓN", 326, 647, paintBold2);
                canvas.drawRect(306, 652, 420, 697, rect);
                canvas.drawRect(306, 697, 420, 712, rect);
                canvas.drawText("DD / MM / AAAA", 336, 707, paintBold2);
                canvas.drawRect(420, 637, 592, 652, rect);
                canvas.drawText("SELLO  DE  LA  EMPRESA", 465, 647, paintBold2);
                canvas.drawRect(420, 652, 592, 712, rect);
                canvas.drawText("Act.07-2019/GSRyCF", 500, 722, paintBold2);


                position=0;
                pdfDocument.finishPage(mypage);
            }
            else if(mode==5){
                pageWidth=612;
                pageHieght=792;
                Bitmap logo = BitmapFactory.decodeResource(activity.getResources(), R.drawable.logo_pdf);
                Bitmap sign = BitmapFactory.decodeResource(activity.getResources(), R.drawable.firmanotice);
                pageInfo = new PdfDocument.PageInfo.Builder(pageWidth, pageHieght, 1).create();
                mypage = pdfDocument.startPage(pageInfo);
                canvas = mypage.getCanvas();

                //LOGO
                canvas.drawBitmap(resizeBitmap(logo, 59, 45), 530, 20, null);

                //FiRMA
                canvas.drawBitmap(resizeBitmap(sign, 140, 60), 230, 595, null);

                //TITULO
                canvas.drawRect(20, 20, 502, 67, rectPaint);
                canvas.drawText("AVISO  DE  MODIFICACIÓN  AL  FACTOR  DE  DESCUENTOS", 45, 52, paintTitle);

                //FOLIO Y FECHA
                canvas.drawRect(435, 77, 592, 92, rect);
                canvas.drawText("FOLIO: ", 440, 87, paintBold);
                canvas.drawText(item.getNROAVIS(), 480, 87, paintText);
                canvas.drawRect(435, 92, 592, 107, rect);
                canvas.drawText("FECHA: ", 440, 102, paintBold);
                canvas.drawText(item.getFCREAVIS(), 480, 102, paintText);

                //NOMBRE y DIRECCIÓN NRP
                canvas.drawRect(20, 112, 415, 127, rect);
                canvas.drawText("NOMBRE  Y  DOMICILIO  FISCAL  DE  LA  EMPRESA  RETENEDORA", 90, 123, paintBold);
                canvas.drawRect(20, 127, 415, 172, rect);
                canvas.drawText(item.getNOMBRE_NRP(), 25, 137, paintText);
                canvas.drawText(fDireccion, 25, 147, paintText);
                canvas.drawText(localAviso, 25, 157, paintText);
                canvas.drawText("C.P : " + item.getCP_NRP(), 260, 157, paintText);
                canvas.drawText(entAviso, 25, 167, paintText);
                canvas.drawText("Municipio: " + item.getEDO_MUNICIPIO_NRP(), 260, 167, paintText);

                //NRP Y RFC
                canvas.drawRect(415, 112, 592, 142, rect);
                canvas.drawText("N.R.P: ", 420, 132, paintBold);
                canvas.drawText(item.getNRP(), 460, 132, paintText);
                canvas.drawRect(415, 142, 592, 172, rect);
                canvas.drawText("R.F.C: ", 420, 162, paintBold);
                canvas.drawText(item.getRFC_NRP(), 460, 162, paintText);

                //TEXTO
                canvas.drawRect(20, 177, 592, 522, rect);
                canvas.drawText("Con fundamento en los artículos 23 fracción I de la Ley del Instituto del Fondo Nacional de la Vivienda para los Trabajadores; 1, 3 fracciones XXXVII y XLV, 4 fracción IX, así como el", 25, 187, paintTextDescription);
                canvas.drawText("artículo 13 del Reglamento Interior del Instituto del Fondo Nacional de la Vivienda para los Trabajadores publicado en el Diario Oficial de la Federación el 20 de junio de 2008, así", 25, 197, paintTextDescription);
                canvas.drawText("como su reforma publicada en el mismo Diario el 31 de julio de 2017, le notifico que ha sido modificado el factor de los descuentos que viene haciendo a su trabajador, cuyos datos", 25, 207, paintTextDescription);
                canvas.drawText("se consignan en el presente documento, motivo por el cual a partir", 25, 217, paintTextDescription);
                canvas.drawText("del primer día del siguiente bimestre a aquel en que haya recibido el presente,", 234, 217, paintBoldCS);
                canvas.drawText("deberá realizar los descuentos", 474, 217, paintTextDescription);
                canvas.drawText("aplicando la modalidad que se indica en el recuadro respectivo", 25, 227, paintTextDescription);
                canvas.drawCircle(25, 235, 2, blckPoint);
                canvas.drawText("Descuento en porcentaje se calcula: Salario diario integrado por el porcentaje de descuento por los días laborados con crédito con cada salario que haya percibido el trabajador.", 40, 237, paintTextDescription);
                canvas.drawCircle(25, 245, 2, blckPoint);
                canvas.drawText("Descuento en pesos se calcula: Cuota fija en pesos mensual por los meses del bimestre entre días del bimestre por días laborados con crédito.", 40, 247, paintTextDescription);
                canvas.drawCircle(25, 255, 2, blckPoint);
                canvas.drawText("Factor de descuento, presenta dos variantes:", 40, 257, paintTextDescription);
                canvas.drawCircle(65, 265, 2, whtPoint);
                canvas.drawText("Para efectos de los descuentos correspondientes a 2016 y años anteriores, se debe considerar para el cálculo la cuota fija en VSM por el SMGDF por los meses del", 80, 267, paintTextDescription);
                canvas.drawText("bimestre entre los días del bimestre por los días laborados con crédito.", 80, 277, paintTextDescription);
                canvas.drawCircle(65, 285, 2, whtPoint);
                canvas.drawText("Para efectos de descuentos posteriores al 1 de Enero de 2017, se debe considerar para el cálculo el factor de descuento por el valor de la unidad Mixta INFONAVIT", 80, 287, paintTextDescription);
                canvas.drawText("base de cálculo por los meses del bimestre entre los días del bimestre por los días laborados con crédito.", 80, 297, paintTextDescription);
                canvas.drawText("El valor de la Unidad Mixta INFONAVIT se determina de conformidad con lo dispuesto en el artículo 6º transitorio del Decreto en materia de desindexación del salario minimo de la", 25, 307, paintTextDescription);
                canvas.drawText("Constitución Política de los Estados Unidos Mexicanos, publicado el 27 de enero del 2016 en el Diario Oficial de la Federación, considerando la actualización del salario mínimo, más", 25, 317, paintTextDescription);
                canvas.drawText("el crecimiento porcentual de la inflación sustituyendo de esta manera el Salario Mínimo.", 25, 327, paintTextDescription);
                canvas.drawText("El entero de los descuentos deberá efectuarlo a través de los mismos medios y plazos en que lo venia haciendo hasta antes de la recepción del presente aviso.", 25, 337, paintTextDescription);
                canvas.drawCircle(25, 345, 2, blckPoint);
                canvas.drawText("Si el trabajador percibe un salario mínimo, no podrá exceder del 20% del mismo, con fundamento en los artículos 47 del Reglamento de Inscripción, Pago de Aportaciones y", 40, 347, paintTextDescription);
                canvas.drawText("Entero de Descuentos al Instituto del Fondo Nacional de la Vivienda para los Trabajadores; y 110 fracción VII de la Ley Federal del Trabajo.", 40, 357, paintTextDescription);
                canvas.drawCircle(25, 365, 2, blckPoint);
                canvas.drawText("Cuando se indique el Factor de Descuento mensual y el periodo sea menor a los días que comprende el bimestre, deberá retener y enterar la parte proporcional de los días del", 40, 367, paintTextDescription);
                canvas.drawText("bimestre efectivamente laborados por el trabajador.", 40, 377, paintTextDescription);
                canvas.drawText("Lo anterior, con apoyo en el Contrato de Crédito con Garantía Hipotecaria que el trabajador en cuestión celebró con este Instituto, mediante el cual aceptó ésta forma de descuento", 25, 387, paintTextDescription);
                canvas.drawText("para la amortización de su crédito, y en el Convenio de Reestructura que en su caso dicho trabajador acreditado hubiese celebrado con el Instituto del Fondo Nacional de la Vivienda", 25, 397, paintTextDescription);
                canvas.drawText("para los Trabajadores, situación que es acorde con lo señalado en los artículos 110 fracción III de la Ley Federal del Trabajo; 29 fracción III de la Ley del Instituto del Fondo Nacional", 25, 407, paintTextDescription);
                canvas.drawText("de la Vivienda para los Trabajadores; y 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53 y 54 del Reglamento de Inscripción, Pago de Aportaciones y Entero de Descuentos al Instituto del", 25, 417, paintTextDescription);
                canvas.drawText("Fondo Nacional de la Vivienda para los Trabajadores.", 25, 427, paintTextDescription);
                canvas.drawText("Hacemos de su conocimiento, que por ningún motivo deberá sellar ni firmar este documento si el trabajador a que se refiere el mismo no guarda actualmente relación laboral con", 25, 437, paintTextDescription);
                canvas.drawText("usted, ya que al hacerlo se convertirá en responsable del entero de las amortizaciones que correspondan, sin perjuicio de aplicar el contenido del artículo 58 de la Ley del Instituto", 25, 447, paintTextDescription);
                canvas.drawText("del Fondo Nacional de la Vivienda para los Trabajadores.", 25, 457, paintTextDescription);
                canvas.drawText("La omisión de los datos de un trabajador en la Cédula de Determinación emitida por el Instituto o la diferencia de factor a retener, no exime al patrón de la obligación de retener y", 25, 467, paintTextDescription);
                canvas.drawText(" enterar los descuentos conforme a este aviso modificatorio.", 25, 477, paintTextDescription);
                canvas.drawText("La falta de cumplimiento a las obligaciones señaladas en el presente aviso, motivara la determinación y cobro de los importes omitidos a través de el (los) crédito(s) fiscal(es) así ", 25, 487, paintTextDescription);
                canvas.drawText("como la imposición de las sanciones que correspondan por violaciones a la Ley y sus reglamentos en los términos de los artículos 2, 4 y 6 del Código Fiscal de la Federación; 30, 55,", 25, 497, paintTextDescription);
                canvas.drawText(" 56, y 57 de la Ley del Instituto del Fondo Nacional de la Vivienda para los Trabajadores; y 6, 7, 19, 20, 21, 22, y 25 del Reglamento para la Imposición de Multas por Incumplimiento", 25, 507, paintTextDescription);
                canvas.drawText(" de las Obligaciones que la Ley de Instituto del Fondo Nacional de la Vivienda para los Trabajadores y sus reglamentos establecen a cargo de los Patrones.", 25, 517, paintTextDescription);

                //NUMERO CRÉDITO NSS y NOMBRE NSS
                canvas.drawRect(20, 522, 290, 537, rect);
                canvas.drawText("NÚMERO  DE  CRÉDITO", 110, 532, paintBold);
                canvas.drawRect(20, 537, 290, 552, rect);
                canvas.drawText(item.getNUMCRED(), 110, 547, paintText);
                canvas.drawRect(290, 522, 592, 537, rect);
                canvas.drawText("NOMBRE  DEL  TRABAJADOR ", 400, 532, paintBold);
                canvas.drawRect(290, 537, 592, 552, rect);
                canvas.drawText(item.getNOMBRE_NSS(), 400, 547, paintText);

                //NÚMERO DE SEGURIDAD SOCIAL Y RFC O CURP DEL TRABAJADOR
                canvas.drawRect(20, 552, 155, 567, rect);
                canvas.drawText("NÚMERO  DE  SEGURIDAD  SOCIAL", 30, 562, paintBold2);
                canvas.drawRect(20, 567, 155, 582, rect);
                canvas.drawText(item.getNSS(), 30, 577, paintText);
                canvas.drawRect(155, 552, 290, 567, rect);
                canvas.drawText("RFC  O  CURP  DEL  TRABAJADOR", 165, 562, paintBold2);
                canvas.drawRect(155, 567, 290, 582, rect);
                canvas.drawText(item.getRFC_NSS(), 165, 577, paintText);

                //DESCUENTO ANTERIOR Y NUEVO FACTOR DE DESCUENTO
                canvas.drawRect(290, 552, 441, 567, rect);
                canvas.drawText("DESCUENTO  ANTERIOR", 300, 562, paintBold);
                canvas.drawRect(290, 567, 441, 582, rect);
                canvas.drawText(DescAnterior, 300, 577, paintText);
                canvas.drawRect(441, 552, 592, 567, rect);
                canvas.drawText("NUEVO  FACTOR  DE  DESCUENTO", 451, 562, paintBold);
                canvas.drawRect(441, 567, 592, 582, rect);
                canvas.drawText(NuevoFactorDesc, 451, 577, paintText);

                //Texto Firma
                canvas.drawText("A  T  E  N  T  A  M  E  N  T  E", 260, 593, paintBold2);
                canvas.drawRect(205, 659, 395, 660, line);
                canvas.drawText("MTRO. FERNANDO TAPIA DÍAZ", 255, 666, paintBold2);
                canvas.drawText("GERENTE  DE  FACTURACIÓN  FISCAL", 245, 675, paintBold2);

                //NOMBRE FIRMA
                canvas.drawRect(20, 682, 306, 757, rect);
                canvas.drawText("PERSONA  QUE  RECIBE  EL  AVISO", 105, 692, paintBold2);
                canvas.drawRect(30, 742, 160, 743, line);
                canvas.drawText("NOMBRE", 80, 752, paintBold2);
                canvas.drawRect(170, 742, 296, 743, line);
                canvas.drawText("FIRMA", 220, 752, paintBold2);

                //FECHA DE RECEPCIÓN Y SELLO DE LA EMPRESA
                canvas.drawRect(306, 682, 420, 697, rect);
                canvas.drawText("FECHA  DE  RECEPCIÓN", 326, 692, paintBold2);
                canvas.drawRect(306, 697, 420, 742, rect);
                canvas.drawRect(306, 742, 420, 757, rect);
                canvas.drawText("DD / MM / AAAA", 336, 752, paintBold2);
                canvas.drawRect(420, 682, 592, 697, rect);
                canvas.drawText("SELLO  DE  LA  EMPRESA", 465, 692, paintBold2);
                canvas.drawRect(420, 697, 592, 757, rect);
                canvas.drawText("Act.07-2019/GSRyCF", 500, 767, paintBold2);
                position=0;
                pdfDocument.finishPage(mypage);
            }
        }

        try {
            myFilePath = activity.getExternalFilesDir(null).getAbsolutePath() + "/" + Name + ".pdf";
        } catch (Exception e) {
            myFilePath = "";
        }
        myfile = new File(myFilePath);
        try {
            pdfDocument.writeTo(new FileOutputStream(myfile));
        } catch (Exception e) {
            e.printStackTrace();

        }
        pdfDocument.close();
        return myfile;
    }

    //to get next position on canvas text pdf
    public static int position=0;
    public static int getPosition( int plus){
           position=position+plus;
        return position;
    }
    //to resize mipmaps
    public static Bitmap resizeBitmap(Bitmap bitmap,int newWidth,int newHeight) {
        Bitmap scaledBitmap = Bitmap.createBitmap(newWidth, newHeight, Bitmap.Config.ARGB_8888);

        float ratioX = newWidth / (float) bitmap.getWidth();
        float ratioY = newHeight / (float) bitmap.getHeight();
        float middleX = newWidth / 2.0f;
        float middleY = newHeight / 2.0f;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bitmap, middleX - bitmap.getWidth() / 2, middleY - bitmap.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));

        return scaledBitmap;

    }

    //To create a pfd from Base64 String
    public static File createPdfFromBase64(String pdfUrlBase64, String name, Activity context) throws FileNotFoundException {
        String myFilePath;
        File myfile;
        try {
            myFilePath = context.getExternalFilesDir(null).getAbsolutePath() + "/" + name + ".pdf";
        } catch (Exception e) {
            myFilePath = "";
        }

        myfile = new File(myFilePath);
        FileOutputStream out = new FileOutputStream(myfile);

        try {
            byte[] pdfAsBytes = Base64.decode(pdfUrlBase64, 0);
            out.write(pdfAsBytes);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return myfile;
    }

    //to get actual date
    public static String getDate() {
        Calendar c = Calendar.getInstance();
        String month = c.getDisplayName(Calendar.MONTH, Calendar.LONG, new Locale("es", "ES"));
        int year = c.get(Calendar.YEAR);
        int day = c.get(Calendar.DAY_OF_MONTH);
        return month.toUpperCase() + " " + day + " DEL " + year;
    }

    //to get actual date in format dd MMMM yyy
    public static String getDateString() {
        SimpleDateFormat fmtOut = new SimpleDateFormat("dd MMMM yyy");
        return fmtOut.format(new Date());
    }

    //to get date format with pattern
    public static String formatDate(String dateString, String pattern){
        try {
            SimpleDateFormat fmt = new SimpleDateFormat(pattern);
            Date date = fmt.parse(dateString);
            Calendar c = Calendar.getInstance();
            SimpleDateFormat fmtOut = new SimpleDateFormat("dd MMMM yyy");
            return fmtOut.format(date);
        }catch (Exception ex){
            ex.printStackTrace();
            return dateString;
        }
    }
}