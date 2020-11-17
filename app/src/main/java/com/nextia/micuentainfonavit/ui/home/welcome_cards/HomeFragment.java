package com.nextia.micuentainfonavit.ui.home.welcome_cards;
/**
 * class of view home, showing welcome cards
 */

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.nextia.domain.models.welcome.WelcomeCard;
import com.nextia.micuentainfonavit.R;
import com.nextia.micuentainfonavit.Utils;

import java.util.ArrayList;

import ru.tinkoff.scrollingpagerindicator.ScrollingPagerIndicator;

public class HomeFragment extends Fragment {
    private RecyclerView recyclerView;
    private HomeViewModel homeViewModel;
    private View rootView;
    CardAdapter adapter;
    ScrollingPagerIndicator recyclerIndicator;



    //creating and instancing view
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        //finding views
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = root.findViewById(R.id.recyclerwelcome);
        rootView = root.findViewById(R.id.rootView);
        recyclerIndicator = root.findViewById(R.id.indicatorrecycler);

        setRecyclerView();
        setLiveData();
        return root;
    }

    //function before initial view to show and stop skeleton
    @Override
    public void onStart() {
        super.onStart();
        Utils.showLoadingSkeleton(rootView, R.layout.skeleton_home);
        new CountDownTimer(1500, 1000) {
            public void onFinish() {
                Utils.hideLoadingSkeleton();
            }

            public void onTick(long millisUntilFinished) {

            }
        }.start();
    }

    //To create recycler view, its adapter and bound them
    public void setRecyclerView() {
        adapter = new CardAdapter();
        recyclerView.setAdapter(adapter);
        recyclerIndicator.attachToRecyclerView(recyclerView);
        int halfCard = (int) (getResources().getDimension(R.dimen.WelcomeCardWidth))/2;
        int halfScreenWidth=(Utils.getScreenWidth(getActivity()))/2;
        int padding= halfScreenWidth-halfCard;
        recyclerView.setPadding(padding,0,padding,0);

    }

    //To set wich data will be observed and trigger methods
    public void setLiveData() {
        homeViewModel.getCards().observe(getViewLifecycleOwner(), new Observer<ArrayList<WelcomeCard>>() {
            @Override
            public void onChanged(ArrayList<WelcomeCard> welcomeCards) {
                adapter.setData(welcomeCards);

            }
        });
    }

}