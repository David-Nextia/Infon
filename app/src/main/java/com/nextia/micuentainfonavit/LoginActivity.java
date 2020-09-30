package com.nextia.micuentainfonavit;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

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
    View dumbView;
    LinearLayout form;
    TextView aviso;
    TextView textitle;
    TextView regiterlink;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_login);
        TextView register = findViewById(R.id.registerlink);
        register.animate().alpha(1.0f)
                .setDuration(300);
        TextView title = findViewById(R.id.titletext);
        title.animate().alpha(1.0f);
        ConstraintSet set = new ConstraintSet();
        ConstraintLayout layout;
        layout = (ConstraintLayout) findViewById(R.id.viewLogin);
        set.clone(layout);
        set.connect(R.id.register_form,ConstraintSet.TOP,R.id.registerlink,ConstraintSet.BOTTOM,0);
        set.applyTo(layout);
        dumbView = findViewById(R.id.theDumbViewId);
        form= findViewById(R.id.register_form);

        Point size = new Point();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getApplicationContext().getDisplay().getRealMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        dumbView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if((oldTop-top)>(height/5)){
                    TextView register = findViewById(R.id.registerlink);
                    int[] location1 = new int[2];
                    register.getLocationOnScreen(location1);
                    int[] location2 = new int[2];
                   ConstraintLayout layout;
                    LinearLayout form;


                  form= (LinearLayout) findViewById(R.id.register_form);
                    form.getLocationOnScreen(location2);


                    if(location2[1]-location1[1]<(height/14))
                    {//13.71
                        register.animate()
                                .alpha(0.0f)
                                .setDuration(300);

                       // register.setText("");
                        TextView title = findViewById(R.id.titletext);
                        title.animate().alpha(0.0f);
                        //title.setText("");
                        //Toast.makeText(getApplicationContext(),"debe desaparecer",Toast.LENGTH_SHORT).show();
                    }

                    //Toast.makeText(getApplicationContext(),"subiowe",Toast.LENGTH_SHORT).show();
                    ConstraintSet set = new ConstraintSet();



                    layout = (ConstraintLayout) findViewById(R.id.viewLogin);
                    set.clone(layout);
                    //set.connect(R.id.register_form,ConstraintSet.TOP,R.id.registerlink,ConstraintSet.BOTTOM,0);
                    set.clear(R.id.register_form, ConstraintSet.TOP);
                    //set.connect(R.id.register_form,ConstraintSet.BOTTOM,R.id.viewLogin,ConstraintSet.BOTTOM,0);
                    aviso= findViewById(R.id.avisolink);
                    aviso.setText("");
                    set.applyTo(layout);
                }
                else if((oldTop-top)<-height/7){
                    ConstraintSet set = new ConstraintSet();
                    ConstraintLayout layout;
                    aviso= findViewById(R.id.avisolink);
                    aviso.setText(R.string.hyperlinkap);
                    TextView register = findViewById(R.id.registerlink);
                    register.animate().alpha(1.0f)
                            .setDuration(300);
                    TextView title = findViewById(R.id.titletext);
                    title.animate().alpha(1.0f);
                   // title.setText(R.string.iniciar_sesi_n);
                    layout = (ConstraintLayout) findViewById(R.id.viewLogin);
                    set.clone(layout);
                    set.connect(R.id.register_form,ConstraintSet.TOP,R.id.registerlink,ConstraintSet.BOTTOM,0);
                    set.applyTo(layout);
                    register.setMovementMethod(LinkMovementMethod.getInstance());
                    aviso.setMovementMethod(LinkMovementMethod.getInstance());

                }


            }
        });
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



        //loginbtn.setEnabled(false);
        if(Utils.getSharedPreferencesEmail(getApplicationContext()).isEmpty()==false){
            rememberUser.setChecked(true);
            email.setText(Utils.getSharedPreferencesEmail(getApplicationContext()));
        }
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