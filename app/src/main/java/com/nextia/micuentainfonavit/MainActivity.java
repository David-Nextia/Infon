package com.nextia.micuentainfonavit;
/**
 * class that manages the entire app views
 */

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.core.view.MenuCompat;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.nextia.domain.models.user.UserResponse;
import com.nextia.micuentainfonavit.databinding.ActivityMainBinding;
import com.nextia.micuentainfonavit.foundations.DialogInfonavit;
import com.nextia.micuentainfonavit.ui.avisoprivacidad.AvisoPrivacidadActivity;
import com.nextia.micuentainfonavit.ui.avisoprivacidad.TermConditionsActivity;

import okhttp3.internal.Util;

public class MainActivity extends AppCompatActivity  {

    private AppBarConfiguration mAppBarConfiguration;
    private NavController navController;
    private View header;
    private ImageView ivCloseMenu;
    private Toolbar toolbar;
    private Intent intent;

    private ActivityMainBinding binding;
    private static String FACEBOOK_URL = "https://m.facebook.com/ComunidadInfonavit";
    private static String FACEBOOK_PAGE_ID = "ComunidadInfonavit";
    private static String TWITTER_URL = "https://twitter.com/Infonavit";
    private static String TWITTER_ID = "85345126";
    private static String YOUTUBE_URL = "https://www.youtube.com/user/ComunidadInfonavit";
    private static String YOUTUBE_PAGE_ID = "UCaDujTMEnq8clcqgbuko8DA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setContentView(binding.getRoot());

        checkPermissions();
        setNavigation();
        setLogoutMethod();
        setActions();
        setPermisionMenu();
    }

    //check for storage permissions
    public void checkPermissions(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},200);
        }
    }

    //set navigation methods
    public void setNavigation(){
        toolbar= findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //drawer = findViewById(R.id.drawer_layout);
        //navigationView = findViewById(R.id.nav_view);
        binding.navView.setItemIconTintList(null);
        header = binding.navView.getHeaderView(0);
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_new_welcome, R.id.nav_savings, R.id.nav_movements, R.id.nav_aviso_suspension,R.id.nav_constancia_interes)
                .setDrawerLayout(binding.drawerLayout)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }

    //setting the logout method
    private void setLogoutMethod() {
        binding.navView.getMenu().findItem(R.id.nav_logout).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
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
        binding.navView.getMenu().findItem(R.id.nav_separate).setEnabled(false);

    }

    private void setActions() {
        ivCloseMenu = header.findViewById(R.id.iv_close_menu);

        binding.tvTermAndCond.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), TermConditionsActivity.class);
            startActivity(intent);
        });

        binding.tvNoticePrivacy.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), AvisoPrivacidadActivity.class);
            startActivity(intent);
        });

        ivCloseMenu.setOnClickListener(view -> binding.drawerLayout.closeDrawer(GravityCompat.START));

        binding.ivFacebook.setOnClickListener(view -> clickEventCallFB());

        binding.ivTwitter.setOnClickListener(view -> clickEventCallTWITTER());

        binding.ivYoutube.setOnClickListener(view -> clickEventCallYOUTUBE());
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

    public void clickEventCallFB() {
        try{
            intent = new Intent(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            String facebookUrl = getFacebookPageURL(this);
            intent.setData(Uri.parse(facebookUrl));
            startActivity(intent);
        }
        catch (Exception e){}
    }

    private String getFacebookPageURL(Context context) {
        PackageManager packageManager = context.getPackageManager();
        try
        {
            int versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode;
            if (versionCode >= 3002850)
            { //newer versions of fb app
                return "fb://facewebmodal/f?href=" + FACEBOOK_URL;
            }
            else
            { //older versions of fb app
                return "fb://page/" + FACEBOOK_PAGE_ID;
            }
        }
        catch (PackageManager.NameNotFoundException e)
        {
            return FACEBOOK_URL; //normal web url
        }
    }

    public void clickEventCallTWITTER() {
        intent = null;
        try
        {
            // get the Twitter app if possible
            this.getPackageManager().getPackageInfo("com.twitter.android", 0);
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?user_id=" + TWITTER_ID));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        catch (Exception e)
        {
            // no Twitter app, revert to browser
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(TWITTER_URL));
        }
        this.startActivity(intent);
    }

    public void clickEventCallYOUTUBE() {
        try {
            intent =new Intent(Intent.ACTION_VIEW);
            intent.setPackage("com.google.android.youtube");
            intent.setData(Uri.parse(YOUTUBE_URL));
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(YOUTUBE_URL));
            startActivity(intent);
        }
    }

    public void setPermisionMenu() {
        UserResponse user = Utils.getSharedPreferencesUserData(getApplicationContext());
        String idPermision = user.getIdPerfilglobal();

        //Menu
        Menu menu = binding.navView.getMenu();
        switch (idPermision) {
            case "1.1":
            case "1.2":
            case "3.1":
                menu.findItem(R.id.nav_savings).setVisible(true);
                menu.findItem(R.id.nav_movements).setVisible(false);
                menu.findItem(R.id.nav_aviso_suspension).setVisible(false);
                menu.findItem(R.id.nav_constancia_interes).setVisible(false);
                break;
            case "1.3":
            case "2.1":
            case "3.2":
            case "3.3":
                menu.findItem(R.id.nav_savings).setVisible(true);
                menu.findItem(R.id.nav_movements).setVisible(true);
                menu.findItem(R.id.nav_aviso_suspension).setVisible(true);
                menu.findItem(R.id.nav_constancia_interes).setVisible(true);
                break;
            case "1.4":
            case "2.3":
            case "3.4":
                menu.findItem(R.id.nav_savings).setVisible(true);
                menu.findItem(R.id.nav_movements).setVisible(false);
                menu.findItem(R.id.nav_aviso_suspension).setVisible(true);
                menu.findItem(R.id.nav_constancia_interes).setVisible(false);
                break;
        }
    }

}