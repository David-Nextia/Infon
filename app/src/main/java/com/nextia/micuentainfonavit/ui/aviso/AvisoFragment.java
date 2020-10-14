package com.nextia.micuentainfonavit.ui.aviso;
/**
 * fragment of view aviso, suspensión y retención, it appears on the Main activity
 */

import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.nextia.domain.OnFinishRequestListener;
import com.nextia.domain.models.aviso_suspension.AvisosPDFResponse;
import com.nextia.domain.models.user.Credito;
import com.nextia.micuentainfonavit.LoginActivity;
import com.nextia.micuentainfonavit.MainActivity;
import com.nextia.micuentainfonavit.R;
import com.nextia.micuentainfonavit.Utils;
import com.nextia.micuentainfonavit.databinding.FragmentAvisoBinding;
import com.nextia.micuentainfonavit.foundations.DialogInfonavit;
import com.nextia.micuentainfonavit.ui.movements.views.movements.InnerMovementsFragment;
import com.nextia.micuentainfonavit.usecases.NoticeSuspensionCase;
import com.nextia.micuentainfonavit.usecases.UserUseCase;

import java.util.ArrayList;

public class AvisoFragment extends Fragment implements OnFinishRequestListener<AvisosPDFResponse> {
    NoticeSuspensionCase noticeSuspensionCase = new NoticeSuspensionCase();
    FragmentAvisoBinding binding;
    ArrayList<Credito> creditos;
    ArrayAdapter<String> arrayAdapter;
    ArrayList<String> Creditlist = new ArrayList<>();
    DialogInfonavit dialog;

    //To create view and instance methods
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_aviso, container, false);
        setSpinner();
        return binding.getRoot();
    }

    //function before initial view to show and stop skeleton
    @Override
    public void onStart() {
        super.onStart();
        Utils.showLoadingSkeleton(binding.rootView, R.layout.skeleton_aviso);
        new CountDownTimer(1500, 1000) {
            public void onFinish() {
                Utils.hideLoadingSkeleton();

            }

            public void onTick(long millisUntilFinished) {

            }
        }.start();
    }

    //fill spinner with credits from sharedpreferences and set methods
    public void setSpinner(){
        creditos = Utils.getSharedPreferencesUserData(getContext()).getCredito();
        Creditlist.clear();
        Creditlist.add("Seleccionar cuenta");
        for (int i = 0; i < creditos.size(); i++) {
            Creditlist.add("0000" + creditos.get(i).getNumeroCredito());
        }
        binding.spCredit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    dialog = new DialogInfonavit(getContext(), getString(R.string.title_error), getString(R.string.message_server_error), DialogInfonavit.ONE_BUTTON_DIALOG);
                    //servicio cualquiera forzado a dar error ya que el servicio inicial falla de por sí
                    //UserUseCase user = new UserUseCase();
                    //user.doLogin("", "", AvisoFragment.this);
                    String credit = parent.getSelectedItem().toString();
                    noticeSuspensionCase.getConsultPDFNotice(credit, Utils.getSharedPreferencesToken(getContext()), AvisoFragment.this);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, Creditlist);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spCredit.setAdapter(arrayAdapter);
    }

    //To manage on fail request
    @Override
    public void onFailureRequest(String message) {
        dialog.show();
    }

    @Override
    public void onTokenExpired() {
        DialogInfonavit alertdialog = new DialogInfonavit(getActivity(), "Aviso", getString(R.string.expired_Session), DialogInfonavit.ONE_BUTTON_DIALOG, new DialogInfonavit.OnButtonClickListener() {
            @Override
            public void onAcceptClickListener(Button button, AlertDialog dialog) {
                Intent i = new Intent(getActivity(), LoginActivity.class);
                dialog.dismiss();
                startActivity(i);
                getActivity().finish();
            }
        });
        alertdialog.show();
    }

    @Override
    public void onSuccesRequest(AvisosPDFResponse object, String token) {
        if(object.getStatusServicio().getCodigo().equals("02")){
            binding.suspensionUnsucess.setVisibility(View.VISIBLE);
        } else {
            binding.suspensionUnsucess.setVisibility(View.GONE);
        }
    }

}