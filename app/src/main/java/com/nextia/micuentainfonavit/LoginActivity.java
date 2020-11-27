package com.nextia.micuentainfonavit;
/**
 * class of the login view and functions
 */

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import com.google.gson.Gson;
import com.nextia.domain.OnFinishRequestListener;
import com.nextia.domain.models.user.UserResponse;
import com.nextia.micuentainfonavit.foundations.DialogInfonavit;
import com.nextia.micuentainfonavit.ui.avisoprivacidad.AvisoPrivacidadActivity;
import com.nextia.micuentainfonavit.usecases.UserUseCase;


public class LoginActivity extends AppCompatActivity implements OnFinishRequestListener<UserResponse> {
    UserUseCase user;
    Switch rememberUser;
    View auxView,email_view;
    ConstraintLayout layout,layout2;
    LinearLayout form;
    EditText password, email;
    Button loginbtn;
    TextView aviso, title, register,registerlogin;
    ConstraintSet set;
    Animation anim;
    ProgressBar progress;
    MotionLayout motionLayoutLogin;
    ImageView emailClear,passwordClear,redLogo,whiteLogo;
    TextView passwordMessage;

    Boolean hasShowedKeyboard=false, LogoIntercepted=false;
    int screenHeight;
    View view_email,view_password;
    //location to determine visibility's description view
    int[] registerLocation, formLocation;

    //To create the view and initial declarations
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setBackgroundDrawable(getDrawable(R.drawable.back));
        }
        instanceActivity(); //iniciar vista y variables
        auxView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if ((oldTop - top) > (screenHeight / 7)) //Keyboard is OnScreen
                {
                    setOnKeyboardView();
                } else if ((oldTop - top) < -screenHeight/ 7 )//Keyboard is OffScreen
                {
                    setOffKeyboardView();
                }
            }
        });
        motionLayoutLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // es para poder sacarlo del focus y activar las funciones del focus de los edit text
            }
        });
        email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus && password.hasFocus()==false){
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }else if(hasFocus && !password.hasFocus()){
                    view_email.setVisibility(View.VISIBLE);
                    view_email.startAnimation(anim);
                }
                if(!hasFocus){
                    view_email.setVisibility(View.GONE);
                }
            }
        });
        password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus && email.hasFocus()==false){

                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }else if(hasFocus && !email.hasFocus()){
                    view_password.setVisibility(View.VISIBLE);
                    view_password.startAnimation(anim);
                }
                if(!hasFocus){
                    view_password.setVisibility(View.GONE);
                }
            }
        });
        setFunctions(this);//condicionales del botón y funciones de Onclick
        anim = AnimationUtils.loadAnimation(LoginActivity.this, R.anim.input_text);
        email_view.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if((oldTop-oldBottom)<(top-bottom)){

                    LogoIntercepted=true;
                }
            }
        });

    }

    //Methods before the view starts
    @Override
    protected void onPostResume() {
        super.onPostResume();
        if (Build.VERSION.SDK_INT < 21) {
            MotionLayout login=findViewById(R.id.motionLayoutLogin);
            login.setTransitionDuration(500);
            //login.transitionToEnd();

        }else{}

        //login.transitionToEnd();
    }

    //To declare variables and find views
    public void instanceActivity() {
        setContentView(R.layout.activity_login);//Layout

        //finding elements
        motionLayoutLogin = findViewById(R.id.motionLayoutLogin);
        auxView = findViewById(R.id.AuxView);
        form = findViewById(R.id.register_form);
        register = findViewById(R.id.registerlink);
        registerlogin= findViewById(R.id.registerlink2);
        title = findViewById(R.id.titletext);
        layout = (ConstraintLayout) findViewById(R.id.viewLogin);
        layout2 = (ConstraintLayout) findViewById(R.id.motionLayoutLogin);
        aviso = findViewById(R.id.avisolink);
        password = findViewById(R.id.password_edit);
        loginbtn = findViewById(R.id.buttonlogin);
        email = findViewById(R.id.email_edit);
        email_view=findViewById(R.id.emailView);
        rememberUser = findViewById(R.id.reminduser);
        progress = findViewById(R.id.progressBar);
        emailClear = findViewById(R.id.email_clear);
        passwordClear = findViewById(R.id.password_clear);
        passwordMessage = findViewById(R.id.password_message);
        redLogo= findViewById(R.id.logoimg);
        whiteLogo=findViewById(R.id.logoimgwhite);
        //initiate variables
        user = new UserUseCase();
        screenHeight = Utils.getScreenHeight(LoginActivity.this);
        set = new ConstraintSet();
        registerLocation = new int[2];
        formLocation = new int[2];
        //setting links
        registerlogin.setMovementMethod(LinkMovementMethod.getInstance());
        register.setMovementMethod(LinkMovementMethod.getInstance());
        screenHeight=Utils.getScreenHeight(this);
        view_password=findViewById(R.id.password_line);
        view_email=findViewById(R.id.email_line);
        //aviso.setMovementMethod(LinkMovementMethod.getInstance());
    }

    //To manage functions on soft keyboard
    public void setOnKeyboardView() {
        register.getLocationOnScreen(registerLocation);
        form.getLocationOnScreen(formLocation);
        set.clone(layout);
        int bottomRegister=registerLocation[1]+register.getHeight();
        if ((formLocation[1] -bottomRegister)< 0)//condition if form is over description
        {
            register.animate().alpha(0.0f).setDuration(300);
            title.animate().alpha(0.0f);
        }
        if(LogoIntercepted){
            motionLayoutLogin.transitionToState(R.id.hidetopWithIcon);
        }
        else{
            motionLayoutLogin.transitionToState(R.id.hidetop);

        }
        registerlogin.animate().alpha(0.0f).setDuration(300);
        registerlogin.setVisibility(View.GONE);
        set.clear(R.id.register_form, ConstraintSet.TOP);
        set.clear(R.id.register_form, ConstraintSet.BOTTOM);
        set.connect(R.id.register_form, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, 0);
        aviso.animate().alpha(0.0f);
        aviso.setText("");
        aviso.setVisibility(View.GONE);
        set.applyTo(layout);
        hasShowedKeyboard=true;
        redLogo.animate().setDuration(1000).alpha(1);
        whiteLogo.animate().setDuration(1000).alpha(0);
        whiteLogo.setVisibility(View.GONE);

    }

    //To manage functions off soft keyboard
    public void setOffKeyboardView() {
        if(hasShowedKeyboard)
        {
            motionLayoutLogin.transitionToState(R.id.showtop);

            redLogo.animate().setDuration(1000).alpha(0);
            whiteLogo.setVisibility(View.VISIBLE);
            whiteLogo.animate().setDuration(1000).alpha(1);

        }
        registerlogin.setVisibility(View.VISIBLE);
        registerlogin.animate().alpha(1.0f).setDuration(300);
        register.animate().alpha(1.0f);
        title.animate().alpha(1.0f);
        set.clone(layout);
        aviso.setText(R.string.hyperlinkap);
        aviso.animate().alpha(1.0f);

        set.connect(R.id.register_form, ConstraintSet.TOP, R.id.registerlink, ConstraintSet.BOTTOM, 0);
        set.connect(R.id.register_form, ConstraintSet.BOTTOM, R.id.registerlink2, ConstraintSet.BOTTOM, 0);
        set.applyTo(layout);

    }

   //To create conditions of view and trigger methods, for buttons and textviews
    @SuppressLint("ClickableViewAccessibility")
    void setFunctions(OnFinishRequestListener context) {
        password.setText("Contrasena01");
        email.setText("saldmos34@yopmail.com");
        if(email.getText().toString().trim().length()==0 || password.getText().toString().trim().length()==0) {
            loginbtn.setEnabled(false);
        } else {
            loginbtn.setEnabled(true);
        }

        if (Utils.getSharedPreferencesEmail(getApplicationContext()).isEmpty() == false) {
            rememberUser.setChecked(true);
            email.setText(Utils.getSharedPreferencesEmail(getApplicationContext()));
        } else {
            rememberUser.setChecked(false);
        }

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                passwordMessage.setVisibility(View.GONE);
                if (s.toString().trim().length() != 0 && email.getText().toString().trim().length()!=0 ) {
                    loginbtn.setEnabled(true);
                } else {
                    loginbtn.setEnabled(false);
                }

                if(s.length() != 0){
                    passwordClear.setVisibility(View.VISIBLE);
                } else {
                    passwordClear.setVisibility(View.GONE);
                }
            }
        });

        password.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                ProgressBar progress= findViewById(R.id.progressBar);
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    String mailInput = email.getText().toString().trim();
                    String passwordInput = password.getText().toString().trim();

                    if(!mailInput.isEmpty() && !passwordInput.isEmpty()){
                        // database.doLogin(email.getText().toString(), password.getText().toString(), context);
                        if(Utils.isNetworkAvailable(LoginActivity.this)){
                            doLogin(context);
                        }
                        else{
                            DialogInfonavit alertdialog = new DialogInfonavit(LoginActivity.this, "Aviso",getString(R.string.no_internet) , DialogInfonavit.ONE_BUTTON_DIALOG);
                            alertdialog.show();

                        }
                        return true;
                    }
                }
                return false;
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
                passwordMessage.setVisibility(View.GONE);
                if (s.toString().trim().length() != 0 && password.getText().toString().trim().length()!=0) {
                    loginbtn.setEnabled(true);
                } else {
                    loginbtn.setEnabled(false);
                }

                if(s.length() != 0){
                    emailClear.setVisibility(View.VISIBLE);
                } else {
                    emailClear.setVisibility(View.GONE);
                }
            }
        });

        loginbtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(Utils.isNetworkAvailable(LoginActivity.this)){
                    doLogin(context);
                    }
                else{
                    DialogInfonavit alertdialog = new DialogInfonavit(LoginActivity.this, "Aviso",getString(R.string.no_internet), DialogInfonavit.ONE_BUTTON_DIALOG);
                    alertdialog.show();

                }


            }
        });

        aviso.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, AvisoPrivacidadActivity.class);
            startActivity(intent);
        });

        emailClear.setOnClickListener(view -> email.setText(""));

        passwordClear.setOnClickListener(view -> password.setText(""));
    }

    //To manage request login
    private void doLogin(OnFinishRequestListener context){
        if(password.getText().length() >= 8) {
            progress.setAlpha(1.0f);
            user.doLogin(email.getText().toString(), password.getText().toString(), context);
        }else{
            passwordMessage.setText(getString(R.string.password_min));
            passwordMessage.setVisibility(View.VISIBLE);
        }
    }

    //To manage fail request response
    @Override
    public void onFailureRequest(String message) {
        DialogInfonavit alertdialog = new DialogInfonavit(this, "Aviso", message, DialogInfonavit.ONE_BUTTON_DIALOG);
        alertdialog.show();

        ProgressBar progress = findViewById(R.id.progressBar);
        progress.setAlpha(0.0f);

    }

    @Override
    public void onTokenExpired() {

    }

    //To manage success request response
    @Override
    public void onSuccesRequest(UserResponse object, String token) {
        if (rememberUser.isChecked()) {
            Utils.saveToSharedPreferences(getApplicationContext(), "emailUser", email.getText().toString());
        } else {
            SharedPreferences mPrefs = getSharedPreferences("pref", Context.MODE_PRIVATE);
            mPrefs.edit().remove("emailUser").commit();
        }

        Gson gson = new Gson();
        String json = gson.toJson(object);
        Utils.saveToSharedPreferences(getApplicationContext(), "UsuarioData", json);
        Utils.saveToSharedPreferences(getApplicationContext(), "Token", token);
        ProgressBar progress = findViewById(R.id.progressBar);
        Intent i = new Intent(LoginActivity.this, MainActivity.class);
        progress.setAlpha(0.0f);
        startActivity(i);
        finish();
    }


}