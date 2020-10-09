package com.nextia.micuentainfonavit;
/**
 * class that manages the entire app views
 */

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.core.view.MenuCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.nextia.micuentainfonavit.foundations.DialogInfonavit;
import com.nextia.micuentainfonavit.ui.avisoprivacidad.AvisoPrivacidadActivity;

public class MainActivity extends AppCompatActivity  {

    private AppBarConfiguration mAppBarConfiguration;
    NavController navController;
    NavigationView navigationView;
    DrawerLayout drawer;
    View header;
    TextView tvTermAndCond;
    TextView tvNoticePrivacy;
    ImageView ivCloseMenu;
    Toolbar toolbar;
    //create of the view, and instance of variables
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        checkPermissions();
        setNavigation();
        setLogoutMethod();
        setActions();
    }

    //check for storage permissions
    public void checkPermissions(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }
    }

    //set navigation methods
    public void setNavigation(){
        toolbar= findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        header = navigationView.getHeaderView(0);
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_savings, R.id.nav_movements, R.id.nav_aviso_suspension,R.id.nav_constancia_interes, R.id.nav_profile)
                .setDrawerLayout(drawer)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    //setting the logout method
    private void setLogoutMethod() {
        navigationView.getMenu().findItem(R.id.nav_logout).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                DialogInfonavit alertdialog = new DialogInfonavit(MainActivity.this, "Cerrar sesión","¿Seguro que deseas cerrar sesión?", DialogInfonavit.TWO_BUTTON_DIALOG, new DialogInfonavit.OnButtonClickListener() {
                    @Override
                    public void onAcceptClickListener(Button button, AlertDialog dialog) {
                        Intent i = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(i);
                        finish();
                    }
                });
                alertdialog.show();

                return  true;
            }
        });

    }

    private void setActions() {
        tvTermAndCond = findViewById(R.id.tv_term_and_cond);
        tvNoticePrivacy = findViewById(R.id.tv_notice_privacy);
        ivCloseMenu = header.findViewById(R.id.iv_close_menu);

        tvTermAndCond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AvisoPrivacidadActivity.class);
                startActivity(intent);
            }
        });

        tvNoticePrivacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AvisoPrivacidadActivity.class);
                startActivity(intent);
            }
        });

        ivCloseMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.closeDrawer(GravityCompat.START);
            }
        });
    }


    //to create the menu options profile
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity_menu, menu);
        menu.getItem(0).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                if(findViewById(R.id.imageView3)==null)
                {
                    int z= item.getOrder() ;
                    int y=Menu.CATEGORY_SECONDARY;
                    int finan= z & y;
                    NavigationUI.onNavDestinationSelected(item, navController);
                    //Utils.NavigateWhitBackStack(MainActivity.this,menu.getItem(0).getItemId());
                }
                return true;
            }
        });
        return true;
    }

   //to create return functions on navigation
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

}