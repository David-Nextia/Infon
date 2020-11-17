package com.nextia.micuentainfonavit.ui.movements.views.pay_options.pay_market;
/**
 * adapter of every market on market fragment
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.nextia.micuentainfonavit.R;
import com.nextia.micuentainfonavit.Utils;

import java.util.ArrayList;

public class AdapterMarket extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private final int APPOINMENTS =0;
    private ArrayList<String> items;
    //instance a new Adapter market with a list
    public AdapterMarket(Context context){
        this.items = Utils.getItems();
    }

    //creating viewholder
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

    //custom view holder class
    private class ViewHolderItemMenu extends RecyclerView.ViewHolder{

        TextView tx_market;


        private ViewHolderItemMenu(View itemView){
            super(itemView);
            tx_market=(TextView)itemView.findViewById(R.id.tx_market);
        }

    }

    //binding view on view holder
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

    //method to fill view with data on viewholder
    private void configureViewHolderItemMenu(final AdapterMarket.ViewHolderItemMenu holder, final int position) {
        String appoinment = items.get(position);
        holder.tx_market.setText(String.valueOf(appoinment));

    }

    //gettin viewtype method
    @Override
    public  int getItemViewType(int position) {

        return APPOINMENTS;
    }

    //gettin item number on recyclerview
    @Override
    public int getItemCount() {
        return items.size();
    }

}
