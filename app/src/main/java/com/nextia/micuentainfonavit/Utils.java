package com.nextia.micuentainfonavit;
/**
 * class that creates aux and repetitive methods
 */

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import java.util.ArrayList;


public class Utils {
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

    //To create a pdf from canvas
    public static File createPdfFromCanvas(PdfConstanciaDownloadViewModel mViewModel, String Name, Activity activity) {
        String myFilePath;
        File myfile;
        PdfDocument pdfDocument = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(300, 600, 1).create();
        PdfDocument.Page mypage = pdfDocument.startPage(pageInfo);
        Paint myPaint = new Paint();
        mypage.getCanvas().drawText("Este es un pdf", 10, 30, myPaint);
        mypage.getCanvas().drawText("Datos del servidor:", 10, 50, myPaint);
        mypage.getCanvas().drawText("Datos técnicos:", 10, 70, myPaint);
        mypage.getCanvas().drawText(mViewModel.getCreditInfo().getValue().getDatosTecnicos().getCodigoRespuesta(), 10, 90, myPaint);
        mypage.getCanvas().drawText(mViewModel.getCreditInfo().getValue().getDatosTecnicos().getDescripcionRespuesta(), 10, 100, myPaint);
        mypage.getCanvas().drawText(mViewModel.getCreditInfo().getValue().getDatosTecnicos().getNumeroFormato().toString(), 10, 110, myPaint);

        mypage.getCanvas().drawText("Datos generales:", 10, 130, myPaint);
        mypage.getCanvas().drawText(mViewModel.getCreditInfo().getValue().getDatosGenerales().getNombre(), 10, 150, myPaint);
        mypage.getCanvas().drawText(mViewModel.getCreditInfo().getValue().getDatosGenerales().getRfc(), 10, 160, myPaint);
        mypage.getCanvas().drawText("Domicilio:", 10, 175, myPaint);
        mypage.getCanvas().drawText(mViewModel.getCreditInfo().getValue().getDatosGenerales().getDomicilio().getCalleNumero(), 10, 185, myPaint);
        mypage.getCanvas().drawText(mViewModel.getCreditInfo().getValue().getDatosGenerales().getDomicilio().getColonia(), 10, 195, myPaint);
        mypage.getCanvas().drawText(mViewModel.getCreditInfo().getValue().getDatosGenerales().getDomicilio().getEstado(), 10, 205, myPaint);
        mypage.getCanvas().drawText(mViewModel.getCreditInfo().getValue().getDatosGenerales().getDomicilio().getPoblacion(), 10, 215, myPaint);
        mypage.getCanvas().drawText(mViewModel.getCreditInfo().getValue().getDatosGenerales().getDomicilio().getCp().toString(), 10, 225, myPaint);
        mypage.getCanvas().drawText("Datos financieros:", 10, 245, myPaint);
        mypage.getCanvas().drawText(mViewModel.getCreditInfo().getValue().getDatosSectorFinanciero().getDomicilioFiscal(), 10, 255, myPaint);
        mypage.getCanvas().drawText(mViewModel.getCreditInfo().getValue().getDatosSectorFinanciero().getRfc(), 10, 265, myPaint);
        mypage.getCanvas().drawText(mViewModel.getCreditInfo().getValue().getDatosSectorFinanciero().getRazonSocial(), 10, 275, myPaint);
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

}