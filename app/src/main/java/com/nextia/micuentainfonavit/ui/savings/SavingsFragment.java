package com.nextia.micuentainfonavit.ui.savings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.nextia.domain.models.saldo.SaldoResponse;
import com.nextia.micuentainfonavit.R;
import com.nextia.micuentainfonavit.Utils;
import com.nextia.micuentainfonavit.databinding.FragmentSavingsBinding;
import com.nextia.micuentainfonavit.foundations.DialogInfonavit;
import com.nextia.micuentainfonavit.ui.savings.tabs.ViewPageFragment;
import com.nextia.micuentainfonavit.ui.savings.tabs.ViewPagerAdapter;

public class SavingsFragment extends Fragment {

    FragmentSavingsBinding binding;

    private SavingsViewModel savingsViewModel;
    ViewPagerAdapter adapterViewpage;

    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_savings, container, false);
        adapterViewpage = new ViewPagerAdapter(getChildFragmentManager());

    /*  this.getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);*/
        savingsViewModel= new ViewModelProvider(getActivity()).get(SavingsViewModel.class);
        if(savingsViewModel.getSaldos().getValue()==null)
        {

            Utils.showLoadingSkeleton(binding.rootView, R.layout.skeleton_savings);
            savingsViewModel.getSaldo(this.getContext());

        }else{
            binding.setSaldo(savingsViewModel.getSaldos().getValue());
            //Utils.hideLoadingSkeleton();
        }
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        savingsViewModel.getSaldos().observe(getViewLifecycleOwner(), new Observer<SaldoResponse>() {
            @Override
            public void onChanged(SaldoResponse saldoResponse) {
                if(saldoResponse != null && validateField(saldoResponse)) {
                    //getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    if(adapterViewpage.getCount() == 0) {
                        adapterViewpage.addFragment(ViewPageFragment.getIntance(Utils.formatMoney(saldoResponse.getSaldoAnterior()), " Fondo de ahorro", "De 1972 al 28/feb/1992"), "Año 1972");
                        adapterViewpage.addFragment(ViewPageFragment.getIntance(Utils.formatMoney(saldoResponse.getSaldoSAR92()), "Subcuenta de vivienda SAR", "Del 1/mar/1992 al 30/jun/1997"), "Año 1992");
                        adapterViewpage.addFragment(ViewPageFragment.getIntance(Utils.formatMoney(saldoResponse.getSaldoSAR97()), " Subcuenta de vivienda", "Del 1/jul/1997 a la actualidad"), "Año 1997");
                        adapterViewpage.notifyDataSetChanged();
                    }
                    binding.viewpager.setAdapter(adapterViewpage);
                    binding.tabs.setupWithViewPager(binding.viewpager);
                    binding.setSaldo(saldoResponse);
                    Utils.hideLoadingSkeleton();
                }else{
                    //Error
                   dialogError();
                }
            }
        });
    }
    private boolean validateField(SaldoResponse saldoResponse) {
        if (saldoResponse.getAportacion() == null
                || saldoResponse.getNombreEmpresa() == null
                || saldoResponse.getSaldoSARTotal() == null
                || saldoResponse.getFechaPago() == null
                || saldoResponse.getSaldoAnterior() == null
                || saldoResponse.getSaldoSAR92() == null
                || saldoResponse.getSaldoSAR97() == null) {
            return false;
        }
        return true;
    }
    private void dialogError(){
        DialogInfonavit alertdialog = new DialogInfonavit(getContext(), getString(R.string.title_error),getString(R.string.message_server_error), DialogInfonavit.ONE_BUTTON_DIALOG, new DialogInfonavit.OnButtonClickListener() {
            @Override
            public void onAcceptClickListener(Button button, AlertDialog dialog) {
                dialog.dismiss();
                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                navController.popBackStack(R.id.nav_home, false);
            }
        });
        alertdialog.show();
    }
}