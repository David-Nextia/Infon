package com.nextia.micuentainfonavit.usecases;
/**
 * class to interact between data module and app module about User class
 */
import com.nextia.data.Database;
import com.nextia.domain.OnFinishRequestListener;
import com.nextia.domain.models.user.UserBody;
import com.nextia.domain.models.user.UserResponse;


public class UserUseCase {
    Database database = new Database();
    //To login on app and get user info from database
    public void doLogin(String username, String password, OnFinishRequestListener listener){
        UserBody user =  new UserBody(username,password);
        database.doLogin(user,listener);
    }

}
