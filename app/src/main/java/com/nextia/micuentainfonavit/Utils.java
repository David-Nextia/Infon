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
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
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
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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
    public static File createPdfFromCanvas(ViewModel mViewModel, String Name, Activity activity, int mode, boolean download) {
        String myFilePath;
        File myfile = null;
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
            try {
                if(download)
                {
                    myFilePath = activity.getExternalFilesDir(null).getAbsolutePath() + "/" + Name + ".pdf";}
                else{
                    myFilePath=Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath()+ "/" + Name + ".pdf";
                }
            } catch (Exception e) {
                e.printStackTrace();
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
        else{
            AvisoViewModel viewModel=(AvisoViewModel)mViewModel;
            List<Item> item=viewModel.getAviso().getValue().getDatosAvisos().getItem();


            String calleAviso = item.get(0).getCALLE_Y_NUMERO_NRP();
            String coloAviso = item.get(0).getCOLONIA_NRP();
            String localAviso = item.get(0).getLOCALIDAD_NRP();
            String entAviso = item.get(0).getENT_FED_DESCRIP_NRP();
            String TipDesc =item.get(0).getTIPDESC();
            String TipDescAnt = item.get(0).getTIPDESC_ANTERIOR();
            String Factor = item.get(0).getFACTOR();
            String FactorAnt = item.get(0).getFACTOR_ANTERIOR();
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
                NuevoFactorDesc = item.get(0).getMONTDESC();
            } else {
                NuevoFactorDesc = Factor;
            }

            if (FactorAnt.equals("0") || FactorAnt.equals("0.0")) {
                DescAnterior = item.get(0).getMONTDESCANTERIOR();
            } else {
                DescAnterior = FactorAnt;
            }

            if (TipDesc.equals("1") || TipDesc.equals("01")) {
                Porcen = item.get(0).getMONTDESC() + " %";
                Pesos = "0";
                NuevoFactorDesc = NuevoFactorDesc + " %";
            } else if (TipDesc.equals("2") || TipDesc.equals("02")) {
                Porcen = "0";
                Pesos = "$ " + item.get(0).getMONTDESC();
                NuevoFactorDesc = "$ " + NuevoFactorDesc;
            } else {
                Porcen = item.get(0).getMONTDESC() + " %";
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

            if(mode==2) {
                // Se inician variables de guardado
                File root = null;
                File myDir =  null;
                String name = "";
                String fname = "";
                OutputStream fos = null;
                PdfReader pdfReader = null;
                PdfStamper pdfStamper = null;
                root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                myDir = new File(root + "/AvisosInfonavit");
                if (!myDir.exists()) {
                    myDir.mkdirs();
                }

                //Se define el tipo de aviso y el nombre del archivo
                name = "Aviso_retención_" + item.get(0).getNUMCRED();
                fname = name + ".pdf";
               myfile = new File(myDir, fname);

                //Función para agregar numeros si se repite el archivo
                for (int num = 1; myfile.exists(); num++) {
                    fname = name + "(" + num + ")" + ".pdf";
                    myfile = new File(myDir, fname);
                }

                //Se define la plantilla que se usara
                try {
                    fos = new FileOutputStream(myfile);
                    pdfReader = new PdfReader(activity.getResources().openRawResource(R.raw.retencion_descuentos));
                    pdfStamper = new PdfStamper(pdfReader, fos);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (DocumentException e) {
                    e.printStackTrace();
                }

                //Se toma el total de las paginas
                for (int a = 1; a <= pdfReader.getNumberOfPages(); a++) {
                    PdfContentByte pdfContentByte = pdfStamper.getOverContent(a);

                    //se comienza el archivo y se define el tamaño y letra
                    pdfContentByte.beginText();
                    try {
                        pdfContentByte.setFontAndSize(BaseFont.createFont(
                                BaseFont.HELVETICA,
                                BaseFont.CP1252,
                                BaseFont.EMBEDDED
                        ),9);
                    } catch (DocumentException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    // Número de aviso
                    pdfContentByte.setTextMatrix(470, (float) 704.5);
                    pdfContentByte.showText(item.get(0).getNROAVIS());

//
//                    // Fecha
                    String date=item.get(0).getFCREAVIS();
                    SimpleDateFormat spf=new SimpleDateFormat("yyyy-MM-dd");
                    Date newDate= null;
                    try {
                        newDate = spf.parse(date);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    spf= new SimpleDateFormat("dd.MM.yyyy");
                    date = spf.format(newDate);

                    pdfContentByte.setTextMatrix(470,693);
                    pdfContentByte.showText(date);

                    // N.R.P
                    pdfContentByte.setTextMatrix(456,670);
                    pdfContentByte.showText(item.get(0).getNRP());

                    // R.F.C (NRP)
                    pdfContentByte.setTextMatrix(456,650);
                    pdfContentByte.showText(item.get(0).getRFC_NRP());

                    // Nombre empresa retenedora
                    pdfContentByte.setTextMatrix(36, (float) 663.5);
                    pdfContentByte.showText(item.get(0).getNOMBRE_NRP());

                    // Dirección
                    pdfContentByte.setTextMatrix(36, (float) 655.5);
                    pdfContentByte.showText(fDireccion);

                    // Localidad
                    pdfContentByte.setTextMatrix(36,647);
                    pdfContentByte.showText(localAviso);

                    // CP
                    pdfContentByte.setTextMatrix(36,638);
                    pdfContentByte.showText(item.get(0).getCP_NRP() + ",");

                    // Entidad
                    pdfContentByte.setTextMatrix(66,638);
                    pdfContentByte.showText(entAviso);

                    // Nombre del Trabajador
                    pdfContentByte.setTextMatrix(340,210);
                    pdfContentByte.showText(item.get(0).getNOMBRE_NSS());

                    // Número de seguridad social
                    pdfContentByte.setTextMatrix(55,224);
                    pdfContentByte.showText(item.get(0).getNSS());

                    // Número de crédito
                    pdfContentByte.setTextMatrix(150,224);
                    pdfContentByte.showText(item.get(0).getNUMCRED());

                    // RFC o CURP
                    pdfContentByte.setTextMatrix(235,224);
                    pdfContentByte.showText(item.get(0).getRFC_NSS());

                    // Porcentaje
                    pdfContentByte.setTextMatrix(65,191);
                    pdfContentByte.showText(Porcen);

                    // Pesos
                    pdfContentByte.setTextMatrix(157,191);
                    pdfContentByte.showText(Pesos);

                    // Factor de descuento
                    pdfContentByte.setTextMatrix(245,191);
                    pdfContentByte.showText(item.get(0).getFACTOR());

                    // Se termina de escribir en el PDF

                    pdfContentByte.endText();


                }

                try {
                    pdfStamper.close();
                } catch (DocumentException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

//                File uriFileRetencion = file;
//                Intent sendIntent = new Intent(Intent.ACTION_VIEW);
//
//                Uri uri = null;
//                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
//                    uri = Uri.fromFile(uriFileRetencion);
//                } else {
//                    if (activity != null)
//                        uri = FileProvider.getUriForFile(activity, activity.getPackageName() + ".provider", uriFileRetencion);
//                }
            }
            else if(mode==3){
                File root = null;
                File myDir =  null;
                String name = "";
                String fname = "";
                OutputStream fos = null;
                PdfReader pdfReader = null;
                PdfStamper pdfStamper = null;
                root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                myDir = new File(root + "/AvisosInfonavit");
                if (!myDir.exists()) {
                    myDir.mkdirs();
                }

                //Se define el tipo de aviso y el nombre del archivo
                name = "Aviso_suspensión_" + item.get(0).getNUMCRED();
                fname = name + ".pdf";
                myfile = new File(myDir, fname);

                //Función para agregar numeros si se repite el archivo
                for (int num = 1; myfile.exists(); num++) {
                    fname = name + "(" + num + ")" + ".pdf";
                    myfile = new File(myDir, fname);
                }

                //Se define la plantilla que se usara
                try {
                    fos = new FileOutputStream(myfile);
                    pdfReader = new PdfReader(activity.getResources().openRawResource(R.raw.suspension_descuentos));
                    pdfStamper = new PdfStamper(pdfReader, fos);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (DocumentException e) {
                    e.printStackTrace();
                }

                //Se toma el total de las paginas
                for (int a = 1; a <= pdfReader.getNumberOfPages(); a++) {
                    PdfContentByte pdfContentByte = pdfStamper.getOverContent(a);

                    //se comienza el archivo y se define el tamaño y letra
                    pdfContentByte.beginText();
                    try {
                        pdfContentByte.setFontAndSize(BaseFont.createFont(
                                BaseFont.HELVETICA,
                                BaseFont.CP1252,
                                BaseFont.EMBEDDED
                        ),9);
                    } catch (DocumentException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    // Número de aviso
                    pdfContentByte.setTextMatrix(475, 659);
                    pdfContentByte.showText(item.get(0).getNROAVIS());

                    // Fecha
                    String date=item.get(0).getFCREAVIS();
                    SimpleDateFormat spf=new SimpleDateFormat("yyyy-MM-dd");
                    Date newDate= null;
                    try {
                        newDate = spf.parse(date);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    spf= new SimpleDateFormat("dd.MM.yyyy");
                    date = spf.format(newDate);
                    pdfContentByte.setTextMatrix(475, (float) 639.5);
                    pdfContentByte.showText(date);

                    // N.R.P
                    pdfContentByte.setTextMatrix(455, (float) 605.5);
                    pdfContentByte.showText(item.get(0).getNRP());

                    // R.F.C (NRP)
                    pdfContentByte.setTextMatrix(455, (float) 563);
                    pdfContentByte.showText(item.get(0).getRFC_NRP());

                    // Nombre empresa retenedora
                    pdfContentByte.setTextMatrix(45,588 );
                    pdfContentByte.showText(item.get(0).getNOMBRE_NRP());

                    // Dirección
                    pdfContentByte.setTextMatrix(45, 578);
                    pdfContentByte.showText(fDireccion);

                    // Localidad
                    pdfContentByte.setTextMatrix(45,568);
                    pdfContentByte.showText(localAviso);

                    // CP
                    pdfContentByte.setTextMatrix(45,558);
                    pdfContentByte.showText(item.get(0).getCP_NRP() + ",");

                    // Entidad
                    pdfContentByte.setTextMatrix(75,558);
                    pdfContentByte.showText(entAviso);

                    // Nombre del Trabajador
                    pdfContentByte.setTextMatrix(245,300 );
                    pdfContentByte.showText(item.get(0).getNOMBRE_NSS());

                    // Número de crédito
                    pdfContentByte.setTextMatrix(115,320 );
                    pdfContentByte.showText(item.get(0).getNUMCRED());

                    // Número de seguridad social
                    pdfContentByte.setTextMatrix(60,275 );
                    pdfContentByte.showText(item.get(0).getNSS());

                    // RFC o CURP
                    pdfContentByte.setTextMatrix(150,275 );
                    pdfContentByte.showText(item.get(0).getRFC_NSS());

                    // Se termina de escribir en el PDF
                    pdfContentByte.endText();
                }

                try {
                    pdfStamper.close();
                } catch (DocumentException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            else if(mode==4){
                File root = null;
                File myDir =  null;
                String name = "";
                String fname = "";
                OutputStream fos = null;
                PdfReader pdfReader = null;
                PdfStamper pdfStamper = null;
                root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                myDir = new File(root + "/AvisosInfonavit");
                if (!myDir.exists()) {
                    myDir.mkdirs();
                }

                //Se define el tipo de aviso y el nombre del archivo
                name = "Aviso_suspensión_" + item.get(0).getNUMCRED();
                fname = name + ".pdf";
                myfile = new File(myDir, fname);

                //Función para agregar numeros si se repite el archivo
                for (int num = 1; myfile.exists(); num++) {
                    fname = name + "(" + num + ")" + ".pdf";
                    myfile = new File(myDir, fname);
                }

                //Se define la plantilla que se usara
                try {
                    fos = new FileOutputStream(myfile);
                    pdfReader = new PdfReader(activity.getResources().openRawResource(R.raw.suspension_pal));
                    pdfStamper = new PdfStamper(pdfReader, fos);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (DocumentException e) {
                    e.printStackTrace();
                }

                //Se toma el total de las paginas
                for (int a = 1; a <= pdfReader.getNumberOfPages(); a++) {
                    PdfContentByte pdfContentByte = pdfStamper.getOverContent(a);

                    //se comienza el archivo y se define el tamaño y letra
                    pdfContentByte.beginText();
                    try {
                        pdfContentByte.setFontAndSize(BaseFont.createFont(
                                BaseFont.HELVETICA,
                                BaseFont.CP1252,
                                BaseFont.EMBEDDED
                        ),9);
                    } catch (DocumentException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    // Número de aviso
                    pdfContentByte.setTextMatrix(475, (float) 687.5);
                    pdfContentByte.showText(item.get(0).getNROAVIS());

                    // Fecha
                    String date=item.get(0).getFCREAVIS();
                    SimpleDateFormat spf=new SimpleDateFormat("yyyy-MM-dd");
                    Date newDate= null;
                    try {
                        newDate = spf.parse(date);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    spf= new SimpleDateFormat("dd.MM.yyyy");
                    date = spf.format(newDate);
                    pdfContentByte.setTextMatrix(475, (float) 667.5);
                    pdfContentByte.showText(date);

                    // N.R.P
                    pdfContentByte.setTextMatrix(455, 643);
                    pdfContentByte.showText(item.get(0).getNRP());

                    // R.F.C (NRP)
                    pdfContentByte.setTextMatrix(455, (float) 600.5);
                    pdfContentByte.showText(item.get(0).getRFC_NRP());

                    // Nombre empresa retenedora
                    pdfContentByte.setTextMatrix(45,625);
                    pdfContentByte.showText(item.get(0).getNOMBRE_NRP());

                    // Dirección
                    pdfContentByte.setTextMatrix(45, 615);
                    pdfContentByte.showText(fDireccion);

                    // Localidad
                    pdfContentByte.setTextMatrix(45,605);
                    pdfContentByte.showText(localAviso);

                    // CP
                    pdfContentByte.setTextMatrix(45,595);
                    pdfContentByte.showText(item.get(0).getCP_NRP() + ",");

                    // Entidad
                    pdfContentByte.setTextMatrix(75,595);
                    pdfContentByte.showText(entAviso);

                    // Nombre del Trabajador
                    pdfContentByte.setTextMatrix(245,285);
                    pdfContentByte.showText(item.get(0).getNOMBRE_NSS());

                    // Número de crédito
                    pdfContentByte.setTextMatrix(115,305);
                    pdfContentByte.showText(item.get(0).getNUMCRED());

                    // Número de seguridad social
                    pdfContentByte.setTextMatrix(60,260);
                    pdfContentByte.showText(item.get(0).getNSS());

                    // RFC o CURP
                    pdfContentByte.setTextMatrix(150,260);
                    pdfContentByte.showText(item.get(0).getRFC_NSS());

                    // Se termina de escribir en el PDF
                    pdfContentByte.endText();
                }

                try {
                    pdfStamper.close();
                } catch (DocumentException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
            else if(mode==5){
                File root = null;
                File myDir =  null;
                String name = "";
                String fname = "";
                OutputStream fos = null;
                PdfReader pdfReader = null;
                PdfStamper pdfStamper = null;
                // PLANTILLA 4 (Aviso de Modificación)

                //Se crea la dirección de guardado
                root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                myDir = new File(root + "/AvisosInfonavit");
                if (!myDir.exists()) {
                    myDir.mkdirs();
                }

                //Se define el tipo de aviso y el nombre del archivo
                name = "Aviso_modificación_" + item.get(0).getNUMCRED();
                fname = name + ".pdf";
                myfile = new File(myDir, fname);

                //Función para agregar numeros si se repite el archivo
                for (int num = 1; myfile.exists(); num++) {
                    fname = name + "(" + num + ")" + ".pdf";
                    myfile = new File(myDir, fname);
                }

                //Se define la plantilla que se usara
                try {
                    fos = new FileOutputStream(myfile);
                    pdfReader = new PdfReader(activity.getResources().openRawResource(R.raw.modificacion_factor_descuentos));
                    pdfStamper = new PdfStamper(pdfReader, fos);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (DocumentException e) {
                    e.printStackTrace();
                }

                //Se toma el total de las paginas
                for (int i = 1; i <= pdfReader.getNumberOfPages(); i++) {
                    PdfContentByte pdfContentByte = pdfStamper.getOverContent(i);

                    //se comienza el archivo y se define el tamaño y letra
                    pdfContentByte.beginText();
                    try {
                        pdfContentByte.setFontAndSize(BaseFont.createFont(
                                BaseFont.HELVETICA,
                                BaseFont.CP1252,
                                BaseFont.EMBEDDED
                        ),9);
                    } catch (DocumentException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    // Número de aviso
                    pdfContentByte.setTextMatrix(475, 705);
                    pdfContentByte.showText(item.get(0).getNROAVIS());

                    // Fecha
                    String date=item.get(0).getFCREAVIS();
                    SimpleDateFormat spf=new SimpleDateFormat("yyyy-MM-dd");
                    Date newDate= null;
                    try {
                        newDate = spf.parse(date);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    spf= new SimpleDateFormat("dd.MM.yyyy");
                    date = spf.format(newDate);
                    pdfContentByte.setTextMatrix(475,688 );
                    pdfContentByte.showText(date);

                    // N.R.P
                    pdfContentByte.setTextMatrix(455,663 );
                    pdfContentByte.showText(item.get(0).getNRP());

                    // R.F.C (NRP)
                    pdfContentByte.setTextMatrix(455, (float) 637.5);
                    pdfContentByte.showText(item.get(0).getRFC_NRP());

                    // Nombre empresa retenedora
                    pdfContentByte.setTextMatrix(31,656 );
                    pdfContentByte.showText(item.get(0).getNOMBRE_NRP());

                    // Dirección
                    pdfContentByte.setTextMatrix(31, 647);
                    pdfContentByte.showText(fDireccion);

                    // Localidad
                    pdfContentByte.setTextMatrix(31, 638);
                    pdfContentByte.showText(localAviso);

                    // CP
                    pdfContentByte.setTextMatrix(31,629);
                    pdfContentByte.showText(item.get(0).getCP_NRP() + ",");

                    // Entidad
                    pdfContentByte.setTextMatrix(60,629);
                    pdfContentByte.showText(entAviso);

                    // Nombre del Trabajador
                    pdfContentByte.setTextMatrix(303,272);
                    pdfContentByte.showText(item.get(0).getNOMBRE_NSS());

                    // Número de crédito
                    pdfContentByte.setTextMatrix(130,272);
                    pdfContentByte.showText(item.get(0).getNUMCRED());

                    // Número de seguridad social
                    pdfContentByte.setTextMatrix(65,223);
                    pdfContentByte.showText(item.get(0).getNSS());

                    // RFC o CURP
                    pdfContentByte.setTextMatrix(180,223);
                    pdfContentByte.showText(item.get(0).getRFC_NSS());

                    // Descuento Anterior
                    pdfContentByte.setTextMatrix(330,223);
                    pdfContentByte.showText(DescAnterior);

                    // Nuevo Factor de Descuento
                    pdfContentByte.setTextMatrix(490,223);
                    pdfContentByte.showText(NuevoFactorDesc);

                    // Se termina de escribir en el PDF
                    pdfContentByte.endText();
                }

                try {
                    pdfStamper.close();
                } catch (DocumentException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else if(mode==6){
                File root = null;
                File myDir =  null;
                String name = "";
                String fname = "";
                OutputStream fos = null;
                PdfReader pdfReader = null;
                PdfStamper pdfStamper = null;
                // PLANTILLA 1 (Aviso de Retención)

                //Se crea la dirección de guardado
                root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                myDir = new File(root + "/AvisosInfonavit");
                if (!myDir.exists()) {
                    myDir.mkdirs();
                }

                //Se define el tipo de aviso y el nombre del archivo
                name = "Aviso_retención_suspensión_" + item.get(0).getNUMCRED();
                fname = name + ".pdf";
                myfile = new File(myDir, fname);

                //Función para agregar numeros si se repite el archivo
                for (int num = 1; myfile.exists(); num++) {
                    fname = name + "(" + num + ")" + ".pdf";
                    myfile = new File(myDir, fname);
                }

                //Se define las plantillas que se usaran
                try {
                    fos = new FileOutputStream(myfile);
                    pdfReader = new PdfReader(activity.getResources().openRawResource(R.raw.retencion_y_suspension));
                    pdfStamper = new PdfStamper(pdfReader, fos);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (DocumentException e) {
                    e.printStackTrace();
                }

                PdfContentByte pdfContentByte = pdfStamper.getOverContent(1);

                //se comienza el archivo y se define el tamaño y letra
                pdfContentByte.beginText();
                try {
                    pdfContentByte.setFontAndSize(BaseFont.createFont(
                            BaseFont.HELVETICA,
                            BaseFont.CP1252,
                            BaseFont.EMBEDDED
                    ),9);
                } catch (DocumentException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // Número de aviso
                pdfContentByte.setTextMatrix(470, (float) 704.5);
                pdfContentByte.showText(item.get(0).getNROAVIS());

                // Fecha
                String date=item.get(0).getFCREAVIS();
                SimpleDateFormat spf=new SimpleDateFormat("yyyy-MM-dd");
                Date newDate= null;
                try {
                    newDate = spf.parse(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                spf= new SimpleDateFormat("dd.MM.yyyy");
                date = spf.format(newDate);
                pdfContentByte.setTextMatrix(470,693);
                pdfContentByte.showText(date);

                // N.R.P
                pdfContentByte.setTextMatrix(456,670);
                pdfContentByte.showText(item.get(0).getNRP());

                // R.F.C (NRP)
                pdfContentByte.setTextMatrix(456,650);
                pdfContentByte.showText(item.get(0).getRFC_NRP());

                // Nombre empresa retenedora
                pdfContentByte.setTextMatrix(36,(float) 663.5);
                pdfContentByte.showText(item.get(0).getNOMBRE_NRP());

                // Dirección
                pdfContentByte.setTextMatrix(36, (float) 655.5);
                pdfContentByte.showText(fDireccion);

                // Localidad
                pdfContentByte.setTextMatrix(36,647);
                pdfContentByte.showText(localAviso);

                // CP
                pdfContentByte.setTextMatrix(36,638);
                pdfContentByte.showText(item.get(0).getCP_NRP()+ ",");

                // Entidad
                pdfContentByte.setTextMatrix(66,638);
                pdfContentByte.showText(entAviso);

                // Nombre del Trabajador
                pdfContentByte.setTextMatrix(340,210);
                pdfContentByte.showText(item.get(0).getNOMBRE_NSS());

                // Número de seguridad social
                pdfContentByte.setTextMatrix(55,224);
                pdfContentByte.showText(item.get(0).getNSS());

                // Número de crédito
                pdfContentByte.setTextMatrix(150,224);
                pdfContentByte.showText(item.get(0).getNUMCRED());

                // RFC o CURP
                pdfContentByte.setTextMatrix(235,224);
                pdfContentByte.showText(item.get(0).getRFC_NSS());

                // Porcentaje
                pdfContentByte.setTextMatrix(65,191);
                pdfContentByte.showText(Porcen);

                // Pesos
                pdfContentByte.setTextMatrix(157,191);
                pdfContentByte.showText(Pesos);

                // Factor de descuento
                pdfContentByte.setTextMatrix(245,191);
                pdfContentByte.showText(item.get(0).getFACTOR());

                // Se termina de escribir en el PDF
                pdfContentByte.endText();


                //Plantilla 2 (Avisos de suspensión)

                String fFecha2 = "";
                String calleAviso2 = "";
                String coloAviso2 = "";
                String localAviso2 = "";
                String entAviso2 =  "";
                fFecha2 =  item.get(1).getFCREAVIS();
                calleAviso2 = item.get(1).getCALLE_Y_NUMERO_NRP();
                coloAviso2 = item.get(1).getCOLONIA_NRP();
                localAviso2 = item.get(1).getLOCALIDAD_NRP();
                entAviso2 = item.get(1).getENT_FED_DESCRIP_NRP();

                String fDireccion2 = "";

                String year = fFecha2.substring(0,4);
                String month = fFecha2.substring(5,7);
                String day = fFecha2.substring(8,10);

                fFecha2 = day + "." + month + "." + year;

                if (calleAviso2.equals("") && !coloAviso2.equals("")) {
                    fDireccion2 = coloAviso2;
                } else if (!calleAviso2.equals("") && coloAviso2.equals("")) {
                    fDireccion2 = calleAviso2;
                } else if (!calleAviso2.equals("") && !coloAviso2.equals("")) {
                    fDireccion2 = calleAviso2 + ",   " + coloAviso2;
                }

                if (localAviso2.startsWith("PSCD")) {
                    localAviso2 = "";
                }

                if (entAviso2.startsWith("Oficinas")) {
                    entAviso2 = "";
                }else {
                    entAviso2 = entAviso2.toUpperCase();
                }

                //se comienza el archivo y se define el tamaño y letra
                PdfContentByte pdfContentByte2 = pdfStamper.getOverContent(2);

                //se comienza el archivo y se define el tamaño y letra
                pdfContentByte2.beginText();
                try {
                    pdfContentByte2.setFontAndSize(BaseFont.createFont(
                            BaseFont.HELVETICA,
                            BaseFont.CP1252,
                            BaseFont.EMBEDDED
                    ),9);
                } catch (DocumentException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // Número de aviso
                pdfContentByte2.setTextMatrix(470, 680);
                pdfContentByte2.showText(item.get(1).getNROAVIS());

                // Fecha
                pdfContentByte2.setTextMatrix(470, (float) 660.5);
                pdfContentByte2.showText(fFecha2);

                // N.R.P
                pdfContentByte2.setTextMatrix(445, 626);
                pdfContentByte2.showText(item.get(1).getNRP());

                // R.F.C (NRP)
                pdfContentByte2.setTextMatrix(445, 584);
                pdfContentByte2.showText(item.get(1).getRFC_NRP());

                // Nombre empresa retenedora
                pdfContentByte2.setTextMatrix(35, 608 );
                pdfContentByte2.showText(item.get(1).getNOMBRE_NRP());

                // Dirección
                pdfContentByte2.setTextMatrix(35, 598);
                pdfContentByte2.showText(fDireccion2);

                // Localidad
                pdfContentByte2.setTextMatrix(35,588);
                pdfContentByte2.showText(localAviso2);

                // CP
                pdfContentByte2.setTextMatrix(35,578);
                pdfContentByte2.showText(item.get(1).getCP_NRP() + ",");

                // Entidad
                pdfContentByte2.setTextMatrix(65,578);
                pdfContentByte2.showText(entAviso2);

                // Nombre del Trabajador
                pdfContentByte2.setTextMatrix(240,320 );
                pdfContentByte2.showText(item.get(1).getNOMBRE_NSS());

                // Número de crédito
                pdfContentByte2.setTextMatrix(100,340 );
                pdfContentByte2.showText(item.get(1).getNUMCRED());

                // Número de seguridad social
                pdfContentByte2.setTextMatrix(55,295 );
                pdfContentByte2.showText(item.get(1).getNSS());

                // RFC o CURP
                pdfContentByte2.setTextMatrix(145,295 );
                pdfContentByte2.showText(item.get(1).getRFC_NSS());

                // Se termina de escribir en el PDF
                pdfContentByte2.endText();

                try {
                    pdfStamper.close();
                } catch (DocumentException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            return myfile;
        }



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
    public static File createPdfFromBase64(String pdfUrlBase64, String name, Activity context, boolean downloads) throws FileNotFoundException {
        String myFilePath;
        File myfile;
        try {
            if(downloads)
            {
                myFilePath = context.getExternalFilesDir(null).getAbsolutePath() + "/" + name + ".pdf";}
            else{
                myFilePath=Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath()+ "/" + name + ".pdf";
            }
        } catch (Exception e) {
            e.printStackTrace();
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

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}