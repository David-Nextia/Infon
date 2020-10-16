package com.nextia.micuentainfonavit;
/**
 * class that creates aux and repetitive methods
 */

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
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
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import com.ethanhua.skeleton.Skeleton;
import com.ethanhua.skeleton.ViewSkeletonScreen;
import com.google.gson.Gson;
import com.nextia.domain.OnFinishRequestListener;
import com.nextia.domain.models.credit_year_info.CreditYearInfoResponse;
import com.nextia.domain.models.saldo.SaldoBody;
import com.nextia.domain.models.user.Credito;
import com.nextia.domain.models.user.UserResponse;
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
        lista.add("Selecciona una cuenta");
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
    public static File createPdfFromCanvas(PdfConstanciaDownloadViewModel mViewModel, String Name, Activity activity) {
        String myFilePath;
        File myfile;
        CreditYearInfoResponse credit=mViewModel.getCreditInfo().getValue();
        String credutNum= mViewModel.getCredit().getValue();
        String creditYear= mViewModel.getYear().getValue();
        PdfDocument pdfDocument = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(340, 600, 1).create();
        PdfDocument.Page mypage = pdfDocument.startPage(pageInfo);
        mypage.getCanvas().setDensity(200);
        Paint myPaint = new Paint();
        Canvas canvas= mypage.getCanvas();

        //setting title format
        Paint paintTitle = new Paint();
        paintTitle.setTextSize(10f);
        paintTitle.setFakeBoldText(true);

        //setting body text format
        Paint paintText = new Paint();
        paintText.setTextSize(8f);

        //setting description text format
        Paint paintTextDescription = new Paint();
        paintTextDescription.setTextSize(7f);

        //setting subdirect text format
        Paint paintTitleSubdirec = new Paint();
        paintTitleSubdirec.setTextSize((float) 7.5);
        paintTitleSubdirec.setFakeBoldText(true);
        paintTitleSubdirec.setColor(Color.BLACK);

        //setting images on pdf
        Bitmap logo = null;
        Bitmap cedula = null;
        Bitmap scaleImageCedula = null;
        Bitmap scaleImageLogo = null;
        if (getContext() != null) {
            logo = BitmapFactory.decodeResource(activity.getResources(), R.drawable.logo_pdf);
            cedula = BitmapFactory.decodeResource(activity.getResources(), R.drawable.cedula_rfc);
            scaleImageLogo=Bitmap.createScaledBitmap(logo,60,43,false);
            scaleImageCedula = Bitmap.createScaledBitmap(cedula, 200, 400, true);
        }
        paintTitle.setColor(Color.BLACK);
        if (logo != null) {
            canvas.drawBitmap(resizeBitmap(logo, 150, 106), 20, 10, null);

            //canvas.drawBitmap(scaleImageLogo, 20, 10, null);
        }

        //writing title
        canvas.drawText("INSTITUTO DEL FONDO NACIONAL DE LA", 95, 20, paintTitle);
        canvas.drawText("VIVIENDA PARA LOS TRABAJADORES", 100, 30, paintTitle);

        //writing body text
        canvas.drawText("BARRANCA DEL MUERTO 280 GUADALUPE INN", 105, 40, paintText);
        canvas.drawText("DELEGACIÓN ALVARO OBREGON 01029 CDMX", 100, 50, paintText);
        canvas.drawText("BARRANCA DEL MUERTO 280 GUADALUPE INN", 105, 40, paintText);
        canvas.drawText("DELEGACIÓN ALVARO OBREGON 01029 CDMX", 100, 50, paintText);
        canvas.drawText("RFC: " + credit.getDatosGenerales().getRfc(), 210, 100, paintText);
        canvas.drawText("CRÉDITO: " + credutNum, 185, 110, paintText);
        canvas.drawText(getDate(), 20, 135, paintText);
        canvas.drawText("C. Acreditado", 20, 145, paintText);
        canvas.drawText("Esta es tu Carta Constancia de Intereses del ejercicio " + creditYear + " de tu crédito", 20, 155, paintTextDescription);
        canvas.drawText("del Infonavit. Te será de ayuda para poder deducir en tu declaración anual", 20, 165, paintTextDescription);
        canvas.drawText("los  intereses  reales  pagados  por  tu  créditoInfonavit,  en caso de que ", 20, 175, paintTextDescription);
        canvas.drawText("debas presentarla. Contiene e indica el monto de los intereses nominales", 20, 185, paintTextDescription);
        canvas.drawText("devengados,  así  como  los  intereses pagados en el ejercicio " + creditYear + " y los ", 20, 195, paintTextDescription);
        canvas.drawText("intereses reales. De esta forma cumplimos con lo dispuesto en la Ley del", 20, 205, paintTextDescription);
        canvas.drawText("Impuesto sobre la Renta y su reglamento.", 20, 215, paintTextDescription);
        canvas.drawText("DOMICILIO Y UBICACIÓN DEL INMUEBLE HIPOTECADO: ", 20, 235, paintText);
        canvas.drawText("NOMBRE DEL ACREDITADO: " + credit.getDatosGenerales().getNombre(), 20, 255, paintText);
        canvas.drawText("CALLE: " + credit.getDatosGenerales().getDomicilio().getCalleNumero(), 20, 265, paintText);
        canvas.drawText("COLONIA: " + credit.getDatosGenerales().getDomicilio().getColonia(), 20, 275, paintText);
        canvas.drawText("MUNICIPIO: " + credit.getDatosGenerales().getDomicilio().getPoblacion(), 20, 285, paintText);
        canvas.drawText("ESTADO: " + credit.getDatosGenerales().getDomicilio().getEstado(), 20, 295, paintText);
        canvas.drawText("CÓDIGO POSTAL: " + credit.getDatosGenerales().getDomicilio().getCp(), 20, 305, paintText);
        //writing description
        canvas.drawText("INTERESES DEL CRÉDITO HIPOTECARIO: ", 20, 335, paintTextDescription);
        canvas.drawText("Intereses nominales devengados: " + credit.getDatosFinancieros().getInteresDevengado(), 20, 355, paintTextDescription);
        canvas.drawText("Intereses pagados en el ejercicio: " + credit.getDatosFinancieros().getInteresPagado(), 20, 365, paintTextDescription);
        canvas.drawText("Intereses reales pagados en el ejercicio: " + credit.getDatosFinancieros().getInteresReal(), 20, 375, paintTextDescription);
        canvas.drawText("ATENTAMENTE", 20, 395, paintText);
        canvas.drawText("Subdirección General de Administración de Cartera", 20, 408, paintTitleSubdirec);
        if (scaleImageCedula != null) {
            canvas.drawBitmap(scaleImageCedula, 240, 390, null);
        }

        pdfDocument.finishPage(mypage);
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
        int day = c.get(Calendar.DAY_OF_WEEK);
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