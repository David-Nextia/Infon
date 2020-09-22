package com.nextia.data;

import com.nextia.domain.login.UserResponse;

public interface OnLoginFinished {
    void OnError(String error);

    //void OnSuccess(Context c, MCIUser mciUser);   /// regresar login
    // void OnSuccess(Context c, LoginDetailResponse mciUser);
    void OnSuccess(UserResponse mciUserResponse);
}
