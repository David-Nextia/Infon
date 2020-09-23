package com.nextia.micuentainfonavit.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.nextia.domain.models.welcome.WelcomeCard;
import com.nextia.micuentainfonavit.R;

import java.util.ArrayList;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private MutableLiveData<ArrayList<WelcomeCard>> mCards;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mCards= new MutableLiveData<>();
        mText.setValue("fragmento home");
        setWelcomeCards();
    }
    public void setWelcomeCards(){
        ArrayList<WelcomeCard> cards= new ArrayList<>();
        cards.add(new WelcomeCard(R.drawable.logo, "Mi Ahorro", "Utiliza tu ahorro en la Subcuenta de Vivienda para ampliar tu capacidad de compra."));
        cards.add(new WelcomeCard(R.drawable.logo, "Tasa de interes", "Mantienes una tasa anual fija sobre saldos insolutos."));
        cards.add(new WelcomeCard(R.drawable.logo, "Aportaciones patronales", "Cubren una parte proporcional del pago de la mensualidad de tu crédito"));
        cards.add(new WelcomeCard(R.drawable.logo, "Crédito conyugal", "La suma del monto de tu crédito más el de tu cónyuge, les ayudará a conseguir un mayor financiamiento. "));
        cards.add(new WelcomeCard(R.drawable.logo, "Unamos Créditos Infonavit", "La suma del monto de tu crédito más el de tu familiar o corresidente, les ayudará a conseguir un mayor financiamiento para adquirir vivienda nueva o usada. (Disponible para dos participantes)"));
        mCards.setValue(cards);
    }
    public LiveData<String> getText() {
        return mText;
    }
    public LiveData<ArrayList<WelcomeCard>> getCards() {
        return mCards;
    }
}