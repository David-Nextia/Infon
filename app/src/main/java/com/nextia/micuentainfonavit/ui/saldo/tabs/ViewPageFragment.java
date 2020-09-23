package com.nextia.micuentainfonavit.ui.saldo.tabs;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nextia.micuentainfonavit.R;


public class ViewPageFragment extends Fragment {
    public static final String ID = "id";
    public static final String TITLE = "title";
    public static final String PERIOD = "period";
    TextView txtSaldo, tx_title, tx_period;
    public ViewPageFragment() {}
    public static ViewPageFragment getIntance(String saldo, String title,String period){
        ViewPageFragment fragmentMySavings = new ViewPageFragment();

        Bundle bundle = new Bundle();
        bundle.putString(ID,saldo);
        bundle.putString(TITLE,title);
        bundle.putString(PERIOD,period);
        fragmentMySavings.setArguments(bundle);
        return fragmentMySavings;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root=inflater.inflate(R.layout.fragment_viewpager, container, false);
        txtSaldo = root.findViewById(R.id.txtMySaving);
        tx_title = root.findViewById(R.id.tx_title);
        tx_period=root.findViewById(R.id.tx_period);

        return root;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        tx_title.setText(bundle.getString(TITLE));
        tx_period.setText(String.valueOf(bundle.getString(PERIOD)));
        txtSaldo.setText(bundle.getString(ID));
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


}