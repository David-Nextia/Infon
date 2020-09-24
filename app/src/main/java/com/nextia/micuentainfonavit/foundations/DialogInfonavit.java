package com.nextia.micuentainfonavit.foundations;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.nextia.micuentainfonavit.R;

public class DialogInfonavit extends AlertDialog implements View.OnClickListener{
    String title;
    String message;
    OnButtonClickListener listener;
    Context context;
    public DialogInfonavit(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(false);
        setContentView(R.layout.alert_dialog);
        findViewById(R.id.buttonacept).setOnClickListener(this);
        findViewById(R.id.buttoncancel).setOnClickListener(this);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    @Override
    public void onClick(View v) {
        if (listener!=null){
            if (v.getId() == R.id.buttonacept)
                listener.onAcceptClickListener((Button) v, this);

        }else {
            dismiss();
        }
    }

    public interface OnButtonClickListener{
        void onAcceptClickListener(Button button,AlertDialog dialog);
    }
}
