package com.nextia.micuentainfonavit.ui.movements.views.movements;

import androidx.lifecycle.ViewModel;

import com.nextia.domain.OnFinishRequestListener;
import com.nextia.micuentainfonavit.usecases.CreditUseCase;

public class InnerMovementsViewModel extends ViewModel {
      CreditUseCase credit= new CreditUseCase();
      public void getHistoric(String credito, OnFinishRequestListener listener){
          credit.getInfoCreditHistoric(credito,listener);
      }
}