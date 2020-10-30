package com.nextia.micuentainfonavit.ui.home.new_welcome;
/**
 * class of first view of new users
 */

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nextia.domain.models.user.UserResponse;
import com.nextia.micuentainfonavit.R;
import com.nextia.micuentainfonavit.Utils;

public class NewWelcomeFragment extends Fragment {

    private View rootView;
    TextView name;
    UserResponse user;
    String nameString;
    //creating view
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_new_welcome, container, false);
        rootView = root.findViewById(R.id.rootView);
        name=root.findViewById(R.id.tv_titulo_bienvenido);
        user=Utils.getSharedPreferencesUserData(getContext());
        nameString=user.getNombre();

        if(user.getNombre().equals("")){
            name.setText("¡Bienvenido!");        }

        else{
            char[] charArray = nameString.toCharArray();
            boolean foundSpace = true;
            for(int i = 0; i < charArray.length; i++) {

                // if the array element is a letter
                if(Character.isLetter(charArray[i])) {
                    charArray[i] = Character.toLowerCase(charArray[i]);
                    // check space is present before the letter
                    if(foundSpace) {
                        // change the letter into uppercase
                        charArray[i] = Character.toUpperCase(charArray[i]);
                        foundSpace = false;
                    }
                }

                else {
                    // if the new character is not character
                    foundSpace = true;
                }
            }



            //nameString=nameString.substring(0,1).toUpperCase() + nameString.substring(1).toLowerCase();
            nameString = String.valueOf(charArray);
            if(user.getScurp().charAt(10)=='M')
            {name.setText("¡Bienvenida\n"+nameString+"!");}
            else{
                name.setText("¡Bienvenido\n"+nameString+"!");
            }

        }
        return root;
    }
   //starting skeleton view and stopping it after a while
    @Override
    public void onStart() {
        super.onStart();
        Utils.showLoadingSkeleton(rootView, R.layout.skeleton_new_welcome);
        new CountDownTimer(1500, 1000) {
            public void onFinish() {
                Utils.hideLoadingSkeleton();
            }

            public void onTick(long millisUntilFinished) {

            }
        }.start();
    }
}