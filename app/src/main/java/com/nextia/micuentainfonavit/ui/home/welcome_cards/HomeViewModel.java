package com.nextia.micuentainfonavit.ui.home.welcome_cards;
/**
 * View model off the Home fragment that creates the welcome cards data
 */

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.nextia.domain.models.welcome.WelcomeCard;
import com.nextia.micuentainfonavit.R;

import java.util.ArrayList;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<ArrayList<WelcomeCard>> mCards;

    //trigger methods on model created
    public HomeViewModel() {
        mCards= new MutableLiveData<>();
        setWelcomeCards();
    }

    //setting welcome cards on local way
    public void setWelcomeCards(){
        ArrayList<WelcomeCard> cards= new ArrayList<>();
        cards.add(new WelcomeCard(R.drawable.logo, "Mi Ahorro", "Utiliza tu ahorro en la Subcuenta de Vivienda para ampliar tu capacidad de compra."));
        cards.add(new WelcomeCard(R.drawable.logo, "Tasa de interés", "Mantienes una tasa anual fija sobre saldos insolutos."));
        cards.add(new WelcomeCard(R.drawable.logo, "Aportaciones patronales", "Cubren una parte proporcional del pago de la mensualidad de tu crédito"));
        cards.add(new WelcomeCard(R.drawable.logo, "Crédito conyugal", "La suma del monto de tu crédito más el de tu cónyuge, les ayudará a conseguir un mayor financiamiento. "));
        cards.add(new WelcomeCard(R.drawable.logo, "Unamos Créditos Infonavit", "La suma del monto de tu crédito más el de tu familiar o co-residente, les ayudará a conseguir un mayor financiamiento para adquirir una vivienda nueva o usada. (Disponible para dos participantes)"));
        mCards.setValue(cards);
    }

    //method to get cards form viewmodel
    public LiveData<ArrayList<WelcomeCard>> getCards() {
        return mCards;
    }
}