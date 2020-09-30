package com.nextia.micuentainfonavit.ui.home;

import android.os.Bundle;
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

import com.nextia.domain.models.welcome.WelcomeCard;
import com.nextia.micuentainfonavit.R;

import java.util.ArrayList;

import ru.tinkoff.scrollingpagerindicator.ScrollingPagerIndicator;

public class HomeFragment extends Fragment {
    private RecyclerView recyclerView;
    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = root.findViewById(R.id.recyclerwelcome);
        ScrollingPagerIndicator recyclerIndicator = root.findViewById(R.id.indicatorrecycler);
        CardAdapter adapter= new CardAdapter();
        recyclerView.setAdapter(adapter);
        recyclerIndicator.attachToRecyclerView(recyclerView);
        homeViewModel.getCards().observe(getViewLifecycleOwner(), new Observer<ArrayList<WelcomeCard>>() {
            @Override
            public void onChanged(ArrayList<WelcomeCard> welcomeCards) {
                adapter.setData(welcomeCards);
            }
        });
        return root;
    }
}