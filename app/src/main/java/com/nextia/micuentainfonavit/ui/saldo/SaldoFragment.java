package com.nextia.micuentainfonavit.ui.saldo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.nextia.domain.models.saldo.SaldoResponse;
import com.nextia.micuentainfonavit.R;
import com.nextia.micuentainfonavit.Utils;
import com.nextia.micuentainfonavit.ui.saldo.tabs.ViewPagerAdapter;
import com.nextia.micuentainfonavit.ui.saldo.tabs.ViewPageFragment;

public class SaldoFragment extends Fragment {

    private SaldoViewModel saldoViewModel;
    public TextView ahorro;
    TextView patrontxt;
    TextView aportaciotxt;
    ProgressBar progres;
    LinearLayout linear;
    TabLayout tabLayout;
    ViewPager viewPager;
    TextView fechapagotxt;
    ViewPagerAdapter adapterViewpage;
    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        saldoViewModel =new ViewModelProvider(this).get(SaldoViewModel.class);
        View root = inflater.inflate(R.layout.fragment_saldos, container, false);
        ahorro=root.findViewById(R.id.txtAhorroTotal);
        adapterViewpage=new ViewPagerAdapter(getChildFragmentManager());
        tabLayout=(TabLayout)root.findViewById(R.id.tabs);
        progres= root.findViewById(R.id.progressBarSaldos);
        linear=root.findViewById(R.id.linearopaque);
        viewPager=root.findViewById(R.id.viewpager);
        patrontxt=root.findViewById(R.id.txtPatronActual);
        aportaciotxt=root.findViewById(R.id.txtAportacion);
        fechapagotxt=root.findViewById(R.id.txtFecha);
        progres.setAlpha(1.0f);
        linear.setAlpha(0.3f);
        this.getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        saldoViewModel.getSaldo(this.getContext());

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        saldoViewModel.getSaldos().observe(getViewLifecycleOwner(), new Observer<SaldoResponse>() {
            @Override
            public void onChanged(SaldoResponse saldoResponse) {
                getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                adapterViewpage.addFragment(ViewPageFragment.getIntance(Utils.formatMoney(saldoResponse.getSaldoAnterior())," Fondo de ahorro","De 1972 al 28/feb/1992"),"Año 1972");
                adapterViewpage.addFragment(ViewPageFragment.getIntance(Utils.formatMoney(saldoResponse.getSaldoSAR92()),"Subcuenta de vivienda SAR","Del 1/mar/1992 al 30/jun/1997"),"Año 1992");
                adapterViewpage.addFragment(ViewPageFragment.getIntance(Utils.formatMoney(saldoResponse.getSaldoSAR97())," Subcuenta de vivienda","Del 1/jul/1997 a la actualidad"),"Año 1997");
                patrontxt.setText(saldoResponse.getNombreEmpresa());
                aportaciotxt.setText(Utils.formatMoney(saldoResponse.getAportacion()));
                fechapagotxt.setText(saldoResponse.getFechaPago());
                adapterViewpage.notifyDataSetChanged();

              viewPager.setAdapter(adapterViewpage);
              tabLayout.setupWithViewPager(viewPager);
                progres.setAlpha(0.0f);
                linear.setAlpha(1.0f);

                tabLayout.setClickable(true);

                ahorro.setText(Utils.formatMoney(saldoResponse.getSaldoSARTotal()));
                saldoViewModel.getSaldos().removeObservers(getViewLifecycleOwner());
            }
        });

    }
}