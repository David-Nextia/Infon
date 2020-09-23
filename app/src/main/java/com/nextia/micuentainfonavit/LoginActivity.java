package com.nextia.micuentainfonavit;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.nextia.data.Database;
import com.nextia.domain.OnFinishRequestListener;
import com.nextia.domain.models.user.UserResponse;
import com.nextia.micuentainfonavit.ui.saldo.Utils;
import com.nextia.micuentainfonavit.usecases.UserUseCase;


public class LoginActivity extends AppCompatActivity implements OnFinishRequestListener<UserResponse> {
    Database database = new Database();
    UserUseCase user = new UserUseCase();
    Switch rememberUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        setButton(this);
    }


    @SuppressLint("ClickableViewAccessibility")
    void setButton(OnFinishRequestListener context) {
        EditText email = findViewById(R.id.email_edit);
        EditText password = findViewById(R.id.password_edit);
        Button loginbtn = findViewById(R.id.buttonlogin);
        TextView register = findViewById(R.id.registerlink);
        TextView avisopriv = findViewById(R.id.avisolink);
        rememberUser=findViewById(R.id.reminduser);
        register.setMovementMethod(LinkMovementMethod.getInstance());
        avisopriv.setMovementMethod(LinkMovementMethod.getInstance());
        //loginbtn.setEnabled(false);
        if(Utils.getSharedPreferencesEmail(getApplicationContext()).isEmpty()==false){
            rememberUser.setChecked(true);
            email.setText(Utils.getSharedPreferencesEmail(getApplicationContext()));
        }
        //email.setText("aclara106@yopmail.com");
        password.setText("ContrasenaQa01");
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) { }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0 && email.getText().toString().isEmpty() == false) {
                    loginbtn.setEnabled(true);
                } else {
                    loginbtn.setEnabled(false);
                }
            }

        });

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void afterTextChanged(Editable s) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0 && password.getText().toString().isEmpty() == false) {
                    loginbtn.setEnabled(true);
                } else {
                    loginbtn.setEnabled(false);
                }
            }
        });

        loginbtn.setOnClickListener(new View.OnClickListener() {
            ProgressBar progress= findViewById(R.id.progressBar);
            @Override
            public void onClick(View v) {
                progress.setAlpha(1.0f);
               // database.doLogin(email.getText().toString(), password.getText().toString(), context);
                user.doLogin(email.getText().toString(),password.getText().toString(),context);
                if(rememberUser.isChecked()){
                    SharedPreferences mPrefs =getSharedPreferences("pref", Context.MODE_PRIVATE);
                    SharedPreferences.Editor prefsEditor = mPrefs.edit();
                    prefsEditor.putString("emailUser", email.getText().toString());
                    prefsEditor.commit();
                }else{
                    SharedPreferences mPrefs =getSharedPreferences("pref", Context.MODE_PRIVATE);
                    mPrefs.edit().remove("emailUser").commit();
                }
            }
        });

    }

    @Override
    public void onFailureRequest(String message) {
        AlertDialog.Builder builder
                = new AlertDialog
                .Builder(LoginActivity.this);
        builder.setMessage("Los datos introducidos no son correctos");
        builder.setTitle("Aviso");
        builder.setCancelable(false);
        builder.setPositiveButton("Reintentar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ProgressBar progress= findViewById(R.id.progressBar);
                progress.setAlpha(0.0f);
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public void onSuccesRequest(UserResponse object) {
        SharedPreferences mPrefs =getSharedPreferences("pref", Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(object);
        prefsEditor.putString("UsuarioData", json);
        prefsEditor.commit();
        ProgressBar progress= findViewById(R.id.progressBar);
        Intent i = new Intent(LoginActivity.this, MainActivity.class);
        progress.setAlpha(0.0f);
        startActivity(i);
    }


}