package com.nextia.micuentainfonavit.usecases;

import com.nextia.data.Database;
import com.nextia.domain.OnFinishRequestListener;
import com.nextia.domain.models.saldo.SaldoBody;
import com.nextia.domain.models.user.UserBody;

public class SaldosUseCase {
    Database database = new Database();
    public void getSaldos(OnFinishRequestListener listener){
        SaldoBody saldo =  new SaldoBody();
      database.getSaldos(saldo,listener);

    }
}
