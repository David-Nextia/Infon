package com.nextia.micuentainfonavit;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import com.google.gson.Gson;
import com.nextia.domain.OnFinishRequestListener;
import com.nextia.domain.models.user.UserResponse;
import com.nextia.micuentainfonavit.foundations.DialogInfonavit;
import com.nextia.micuentainfonavit.usecases.UserUseCase;


public class LoginActivity extends AppCompatActivity implements OnFinishRequestListener<UserResponse> {
    UserUseCase user;
    Switch rememberUser;
    View auxView;
    ConstraintLayout layout;
    LinearLayout form;
    EditText password, email;
    Button loginbtn;
    TextView aviso, title, register;
    ConstraintSet set;
    ProgressBar progress;
    int screenHeight;
    int[] registerLocation, formLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instanceActivity(); //iniciar vista y variables
        auxView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if ((oldTop - top) > (screenHeight / 5)) //Keyboard is OnScreen
                {
                    setOnKeyboardView();
                } else if ((oldTop - top) < -screenHeight / 7)//Keyboard is OffScreen
                {
                    setOffKeyboardView();
                }
            }
        });
        setButton(this);//condicionales del botton y funciones de Onclick
    }

    public void instanceActivity() {
        setContentView(R.layout.activity_login);//Layout

        //finding elements
        auxView = findViewById(R.id.AuxView);
        form = findViewById(R.id.register_form);
        register = findViewById(R.id.registerlink);
        title = findViewById(R.id.titletext);
        layout = (ConstraintLayout) findViewById(R.id.viewLogin);
        aviso = findViewById(R.id.avisolink);
        password = findViewById(R.id.password_edit);
        loginbtn = findViewById(R.id.buttonlogin);
        email = findViewById(R.id.email_edit);
        rememberUser = findViewById(R.id.reminduser);
        progress = findViewById(R.id.progressBar);
        //initiate variables
        user = new UserUseCase();
        screenHeight = Utils.getScreenHeight(getApplicationContext());
        set = new ConstraintSet();
        registerLocation = new int[2];
        formLocation = new int[2];
        //setting links
        register.setMovementMethod(LinkMovementMethod.getInstance());
        aviso.setMovementMethod(LinkMovementMethod.getInstance());
    }

    public void setOnKeyboardView() {
        register.getLocationOnScreen(registerLocation);
        form.getLocationOnScreen(formLocation);
        if (formLocation[1] - registerLocation[1] < (screenHeight / 14))//condition if form is over description
        {
            register.animate().alpha(0.0f).setDuration(300);
            title.animate().alpha(0.0f);
        }
        set.clone(layout);
        set.clear(R.id.register_form, ConstraintSet.TOP);
        aviso.animate().alpha(0.0f);
        aviso.setText("");
        set.applyTo(layout);
    }

    public void setOffKeyboardView() {
        register.animate().alpha(1.0f);
        title.animate().alpha(1.0f);
        set.clone(layout);
        aviso.setText(R.string.hyperlinkap);
        aviso.animate().alpha(1.0f);
        set.connect(R.id.register_form, ConstraintSet.TOP, R.id.registerlink, ConstraintSet.BOTTOM, 0);
        set.applyTo(layout);

    }

    @SuppressLint("ClickableViewAccessibility")
    void setButton(OnFinishRequestListener context) {
        //loginbtn.setEnabled(false);
        if (Utils.getSharedPreferencesEmail(getApplicationContext()).isEmpty() == false) {
            rememberUser.setChecked(true);
            email.setText(Utils.getSharedPreferencesEmail(getApplicationContext()));
        } else {
            rememberUser.setChecked(false);
        }
        password.setText("ContrasenaQa01");
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

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
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }

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

            @Override
            public void onClick(View v) {
                progress.setAlpha(1.0f);
                user.doLogin(email.getText().toString(), password.getText().toString(), context);

            }
        });

    }

    @Override
    public void onFailureRequest(String message) {
        DialogInfonavit alertdialog = new DialogInfonavit(this, "Aviso", "Los datos introducidos no son correctos", DialogInfonavit.ONE_BUTTON_DIALOG);
        alertdialog.show();
        ProgressBar progress = findViewById(R.id.progressBar);
        progress.setAlpha(0.0f);

    }

    @Override
    public void onSuccesRequest(UserResponse object) {
        if (rememberUser.isChecked()) {
            Utils.saveToSharedPreferences(getApplicationContext(), "emailUser", email.getText().toString());
        } else {
            SharedPreferences mPrefs = getSharedPreferences("pref", Context.MODE_PRIVATE);
            mPrefs.edit().remove("emailUser").commit();
        }

        Gson gson = new Gson();
        String json = gson.toJson(object);
        Utils.saveToSharedPreferences(getApplicationContext(), "UsuarioData", json);
        ProgressBar progress = findViewById(R.id.progressBar);
        Intent i = new Intent(LoginActivity.this, MainActivity.class);
        progress.setAlpha(0.0f);
        startActivity(i);
    }


}