package com.nextia.micuentainfonavit.foundations;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import com.nextia.micuentainfonavit.R;
public class DialogInfonavit extends AlertDialog implements View.OnClickListener{
    String title;
    String message;
    OnButtonClickListener listener;
    Context context;
    int type;
    public static int TWO_BUTTON_DIALOG=1;
    public static int ONE_BUTTON_DIALOG=2;
    public DialogInfonavit(@NonNull Context context,String title, String message,int type, OnButtonClickListener listener) {
        super(context);
        this.title = title;
        this.listener = listener;
        this.message=message;
        this.context=context;
        this.type=type;
    }
    public DialogInfonavit(@NonNull Context context,String title, String message,int type) {
        super(context);
        this.title = title;
        this.listener = listener;
        this.message=message;
        this.context=context;
        this.type=type;
    }
    public DialogInfonavit(@NonNull Context context, OnButtonClickListener listener) {
        super(context);
        this.listener = listener;
        this.title="Cerrar sesión";
        this.message="¿Seguro que sesas cerrar sesión?";
        this.context=context;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(false);
        setContentView(R.layout.alert_dialog);
        Button accept=findViewById(R.id.buttonacept);
        Button deny=findViewById(R.id.buttoncancel);
        accept.setOnClickListener(this);
        deny.setOnClickListener(this);
        try{((TextView)findViewById(R.id.tv_title_description)).setText(title);}catch (Exception e){e.printStackTrace();}
        try{((TextView)findViewById(R.id.tv_description)).setText(message);}catch (Exception e){e.printStackTrace();}
        if(type==ONE_BUTTON_DIALOG)
        {
            try{
                deny.setVisibility(View.GONE);
                accept.getBackground().setTint(ContextCompat.getColor(getContext(),R.color.colorPrimary));
                accept.setText("Aceptar");
            }catch (Exception e){e.printStackTrace();}
        }
        else if (type==TWO_BUTTON_DIALOG){
            try{
                accept.getBackground().setTint(ContextCompat.getColor(getContext(),R.color.colorskeleton));
                accept.setText("Si");
                deny.setText("No");
            }catch (Exception e){e.printStackTrace();}
        }
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }
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
    public interface OnButtonClickListener{
        void onAcceptClickListener(Button button,AlertDialog dialog);
    }
}