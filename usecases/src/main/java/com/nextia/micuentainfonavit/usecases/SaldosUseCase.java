package com.nextia.micuentainfonavit.usecases;
/**
 * class to interact between data module and app module about Saldos class
 */
import com.nextia.data.Database;
import com.nextia.domain.OnFinishRequestListener;
import com.nextia.domain.models.saldo.SaldoBody;
import com.nextia.domain.models.user.UserBody;
import com.nextia.domain.models.user.UserResponse;

public class SaldosUseCase {
    Database database = new Database();
    //To get the saldos from database
    public void getSaldos(SaldoBody saldo, OnFinishRequestListener listener){
      database.getSaldos(saldo,listener);
    }

}
