package com.nextia.micuentainfonavit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.nextia.data.Database;
import com.nextia.domain.Seguridad;
import com.nextia.domain.UserLogin;
import com.nextia.micuentainfonavit.usecases.Utils;

//import com.nextia.micuentainfonavit.domain.user.SeguridadX;
//import com.nextia.micuentainfonavit.domain.user.UserLogin;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        //Utils utirl;
        Database database= new Database();
        database.DoLogin(new UserLogin("aclara106@yopmail.com","ContrasenaQa01"));
    }
}