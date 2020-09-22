package com.nextia.micuentainfonavit;

import android.annotation.SuppressLint;
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
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.nextia.data.Database;
import com.nextia.data.OnLoginFinished;
import com.nextia.domain.login.UserResponse;


public class LoginActivity extends AppCompatActivity implements OnLoginFinished {
    Database database = new Database();
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        setButton(this);
    }


    @SuppressLint("ClickableViewAccessibility")
    void setButton(OnLoginFinished context) {
        EditText email = findViewById(R.id.email_edit);
        EditText password = findViewById(R.id.password_edit);
        Button loginbtn = findViewById(R.id.buttonlogin);
        TextView register = findViewById(R.id.registerlink);
        TextView avisopriv = findViewById(R.id.avisolink);
        register.setMovementMethod(LinkMovementMethod.getInstance());
        avisopriv.setMovementMethod(LinkMovementMethod.getInstance());

        //loginbtn.setEnabled(false);
        email.setText("aclara106@yopmail.com");
        password.setText("ContrasenaQa01");
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0 && email.getText().toString().isEmpty() == false) {
                    loginbtn.setEnabled(true);
                } else {
                    loginbtn.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0 && password.getText().toString().isEmpty() == false) {
                    loginbtn.setEnabled(true);
                } else {
                    loginbtn.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        loginbtn.setOnClickListener(new View.OnClickListener() {
            ProgressBar progress= findViewById(R.id.progressBar);
            @Override
            public void onClick(View v) {
                progress.setAlpha(1.0f);
                database.DoLogin(email.getText().toString(), password.getText().toString(), context);
            }
        });

        password.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;
                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (password.getRight() - password.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        if(password.getTransformationMethod()==null){
                            password.setTransformationMethod(new PasswordTransformationMethod());
                        }
                        else{password.setTransformationMethod(null);}

                    }
                }
                return false;
            }
        });
    }


    @Override
    public void OnError(String error) {
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
    public void OnSuccess(UserResponse mciUserResponse) {
        SharedPreferences mPrefs = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(mciUserResponse);
        prefsEditor.putString("UsuarioData", json);
        prefsEditor.commit();
        ProgressBar progress= findViewById(R.id.progressBar);
        progress.setAlpha(0.0f);
        Intent i = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(i);

    }
}