package com.nextia.micuentainfonavit.ui.movements.views.pay_options.pay_market;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.nextia.micuentainfonavit.R;
import com.nextia.micuentainfonavit.Utils;

import java.util.ArrayList;

/**
 * Created by drm on 16/02/18.
 */

public class AdapterMarket extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private final int APPOINMENTS =0;
    private ArrayList<String> items;

    public AdapterMarket(Context context){
        this.items = Utils.getItems();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View headerView;
        switch (viewType){

            case APPOINMENTS:
                headerView=inflater.inflate(R.layout.row_markets,viewGroup,false);
                viewHolder=new AdapterMarket.ViewHolderItemMenu(headerView);
                break;

        }

        return viewHolder;
    }

    private class ViewHolderItemMenu extends RecyclerView.ViewHolder{

        TextView tx_market;


        private ViewHolderItemMenu(View itemView){
            super(itemView);
            tx_market=(TextView)itemView.findViewById(R.id.tx_market);
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        AdapterMarket.ViewHolderItemMenu aforeHolder;

        switch (holder.getItemViewType()){
            case APPOINMENTS:
                aforeHolder =(AdapterMarket.ViewHolderItemMenu) holder;
                configureViewHolderItemMenu(aforeHolder,position);
                break;
        }
    }

    private void configureViewHolderItemMenu(final AdapterMarket.ViewHolderItemMenu holder, final int position) {
        String appoinment = items.get(position);
        holder.tx_market.setText(String.valueOf(appoinment));

    }

    @Override
    public  int getItemViewType(int position) {

        return APPOINMENTS;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

}
