package com.nextia.micuentainfonavit.ui.movements;
/**
 * class that handles the saldos y moviemientos view,it contents the 4 views
 */

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.nextia.micuentainfonavit.R;

public class MovementsFragment extends Fragment {

    //creating view
    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_movements, container, false);
        return root;
    }

    //Setting navigation on view
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_mensualidades, R.id.nav_pay_options, R.id.nav_inner_movements,R.id.nav_credit_data)
                .build();
        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_movements);
        BottomNavigationView navView = view.findViewById(R.id.navbarmovements);
        NavigationUI.setupWithNavController(navView, navController);
    }
}