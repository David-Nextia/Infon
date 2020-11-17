package com.nextia.micuentainfonavit.ui.movements.views.pay_options.pay_usa;
/**
 * view of USA inside pay options
 */
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nextia.micuentainfonavit.R;
import com.nextia.micuentainfonavit.Utils;

public class PayUsaFragment extends Fragment {

    TextView tx_bank;
    private View rootView;
    //creating view
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_pay_usa, container, false);
        rootView = root.findViewById(R.id.rootView);
        tx_bank = (TextView) root.findViewById(R.id.tx_bank);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            tx_bank.setText(Html.fromHtml("<p style=\"font-size:5px\">La cantidad máxima  por operación al pagar con tarjeta es de  <b>$30,0000.00</b> y puedes hacer hasta 5 pagos en el mes, los cuales no deben exceder un total de <b>$90,000.00,</b>con cualquier tarjeta Visa o MasterCard </p> <p style=\"font-size:5px\"> <br> </br>Tienes 90 días naturales para aclarar tus pagos. Si tienes dudas, llámanos al <u>9991715050</u> en la Ciudad de México o al <u color=\"#c5c5c5\">01800 008 3900</u> desde el interior del país</p>", Html.FROM_HTML_MODE_COMPACT));
        }else {
            tx_bank.setText(Html.fromHtml("<p style=\"font-size:5px\">La cantidad máxima  por operación al pagar con tarjeta es de  <b>$30,0000.00</b> y puedes hacer hasta 5 pagos en el mes, los cuales no deben exceder un total de <b>$90,000.00,</b>con cualquier tarjeta Visa o MasterCard </p> <p style=\"font-size:5px\"><br> </br>Tienes 90 días naturales para aclarar tus pagos. Si tienes dudas, llámanos al <u>9991715050</u> en la Ciudad de México o al <u color=\"#c5c5c5\">01800 008 3900</u> desde el interior del país</p>"));
        }


        root.findViewById(R.id.bt_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        return root;
    }
    //function before initial view to show and stop skeleton
    @Override
    public void onStart() {
        super.onStart();
        Utils.showLoadingSkeleton(rootView,R.layout.skeleton_pay_usa);
        new CountDownTimer(1500, 1000) {
            public void onFinish() {
                Utils.hideLoadingSkeleton();
            }

            public void onTick(long millisUntilFinished) {

            }
        }.start();
    }
}