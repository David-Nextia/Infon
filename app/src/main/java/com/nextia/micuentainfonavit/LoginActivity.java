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
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.nextia.data.Database;
import com.nextia.domain.OnFinishRequestListener;
import com.nextia.domain.models.user.UserResponse;
import com.nextia.micuentainfonavit.foundations.DialogInfonavit;
import com.nextia.micuentainfonavit.usecases.UserUseCase;


public class LoginActivity extends AppCompatActivity implements OnFinishRequestListener<UserResponse> {
    Database database = new Database();
    UserUseCase user = new UserUseCase();
    Switch rememberUser;
    EditText email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);


        setButton(this);

    }


    @SuppressLint("ClickableViewAccessibility")
    void setButton(OnFinishRequestListener context) {
        email= findViewById(R.id.email_edit);
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
        //password.setText("ContrasenaQa01");
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
                user.doLogin(email.getText().toString(),password.getText().toString(),context);

            }
        });

    }

    @Override
    public void onFailureRequest(String message) {
        DialogInfonavit alertdialog = new DialogInfonavit(this, "Aviso", "Los datos introducidos no son correctos",DialogInfonavit.ONE_BUTTON_DIALOG);
        alertdialog.show();
        ProgressBar progress= findViewById(R.id.progressBar);
        progress.setAlpha(0.0f);

    }
    @Override
    public void onSuccesRequest(UserResponse object) {
        if(rememberUser.isChecked()){
            Utils.saveToSharedPreferences(getApplicationContext(),"emailUser",email.getText().toString());
        }else{
            SharedPreferences mPrefs =getSharedPreferences("pref", Context.MODE_PRIVATE);
            mPrefs.edit().remove("emailUser").commit();
        }
        Gson gson = new Gson();
        String json = gson.toJson(object);
        Utils.saveToSharedPreferences(getApplicationContext(),"UsuarioData",json);
        ProgressBar progress= findViewById(R.id.progressBar);
        Intent i = new Intent(LoginActivity.this, MainActivity.class);
        progress.setAlpha(0.0f);
        startActivity(i);
    }


}