package com.nextia.micuentainfonavit.foundations;
/**
 * This class creates the custom dialog of the entire app
 */
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import com.nextia.micuentainfonavit.R;

public class DialogInfonavit extends AlertDialog implements View.OnClickListener{
    String title, message;
    int type;
    OnButtonClickListener listener;
    CardView card;
    Context context;

    //Options of dialog type
    public static int TWO_BUTTON_DIALOG=1,ONE_BUTTON_DIALOG=2;

    //constructor with button listener
    public DialogInfonavit(@NonNull Context context,String title, String message,int type, OnButtonClickListener listener) {

        super(context);
        this.title = title;
        this.listener = listener;
        this.message=message;
        this.context=context;
        this.type=type;
    }

    //constructor without button listener
    public DialogInfonavit(@NonNull Context context,String title, String message,int type) {

        super(context);
        this.title = title;
        this.listener = listener;
        this.message=message;
        this.context=context;
        this.type=type;
    }

    //creation of dialog
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setCancelable(false);

        setContentView(R.layout.alert_dialog);
        Button accept=findViewById(R.id.buttonacept);
        Button deny=findViewById(R.id.buttoncancel);
        if (Build.VERSION.SDK_INT < 21) {
            card=findViewById(R.id.alertlayout);
        card.setRadius(0.0f);}


        accept.setOnClickListener(this);
        deny.setOnClickListener(this);

        try{((TextView)findViewById(R.id.tv_title_description)).setText(title);}catch (Exception e){e.printStackTrace();}
        try{((TextView)findViewById(R.id.tv_description)).setText(message);}catch (Exception e){e.printStackTrace();}
        if(type==ONE_BUTTON_DIALOG)
        {
            try{
                deny.setVisibility(View.GONE);
                if (Build.VERSION.SDK_INT >= 21) {
                    accept.getBackground().setTint(ContextCompat.getColor(getContext(),R.color.colorPrimary));
                }else{
                    accept.getBackground().setColorFilter(ContextCompat.getColor(getContext(), R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
                }
//
                accept.setText("Aceptar");
        }catch (Exception e){e.printStackTrace();}

        }
        else if (type==TWO_BUTTON_DIALOG){
            try{
                if (Build.VERSION.SDK_INT >= 21) {
                    accept.getBackground().setTint(ContextCompat.getColor(getContext(),R.color.colorskeleton));
                }else{
                    accept.setBackgroundColor(R.color.color_black);
                   // accept.getBackground().setColorFilter(ContextCompat.getColor(getContext(), R.color.colorskeleton), PorterDuff.Mode.SRC_IN);
                }
                //
                accept.setText("Sí");
                deny.setText("No");
            }catch (Exception e){e.printStackTrace();}
        }
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    //manage the click of the button on dialog
    @Override
    public void onClick(View v) {
        if (listener!=null){
            if (v.getId() == R.id.buttonacept)
            {listener.onAcceptClickListener((Button) v, this);}
            else{dismiss();}

        }else{
            dismiss();
        }
    }

    //listener to trigger functions of the dialog
    public interface OnButtonClickListener{
        void onAcceptClickListener(Button button,AlertDialog dialog);
    }
}
