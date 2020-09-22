package com.nextia.micuentainfonavit.usecases;

import com.nextia.data.Database;
import com.nextia.domain.OnFinishRequestListener;
import com.nextia.domain.models.saldo.SaldoBody;
import com.nextia.domain.models.user.UserBody;

public class SaldosUseCase {
    Database database = new Database();
    public void getSaldos(SaldoBody saldo, OnFinishRequestListener listener){

      database.getSaldos(saldo,listener);

    }
}
