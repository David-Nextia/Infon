package com.nextia.micuentainfonavit.ui.constancia;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.nextia.domain.models.credit_info.RespuestUm;
import com.nextia.micuentainfonavit.R;
import com.nextia.micuentainfonavit.Utils;
import com.nextia.micuentainfonavit.ui.movements.views.pay_options.pay_market.AdapterMarket;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;

public class ConstanciaAdapter extends  RecyclerView.Adapter {



    private final int APPOINMENTS =0;
    arrowListener listener;
    private List<RespuestUm> items= new ArrayList<>();
    //instance a new Adapter market with a list
    public void setData(List<RespuestUm> items){
        this.items=items;
        notifyDataSetChanged();
    }
    public void setListener(arrowListener listener)
    {
       this.listener= listener;
    }

    //creating viewholder
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        ViewHolderItemMenu viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View headerView;

                headerView=inflater.inflate(R.layout.constancia_item,viewGroup,false);
                viewHolder=new ViewHolderItemMenu(headerView);



        return viewHolder;
    }

    //custom view holder class
    private class ViewHolderItemMenu extends RecyclerView.ViewHolder{


        TextView anio,message;
        ImageView arrow;
        View view;

        private ViewHolderItemMenu(View itemView){
            super(itemView);
            anio=itemView.findViewById(R.id.year);
            message=itemView.findViewById(R.id.message);
            arrow=itemView.findViewById(R.id.arrow);
            view=itemView;

        }
        public void bindView(RespuestUm item){
            anio.setText(item.getEjercicioFiscal());
            if(item.getCodigoRespuesta().trim().equals("00"))
            {message.setText("Constancia de intereses disponible");
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.Onclick(item.getEjercicioFiscal());
                    }
                });

            }
           else{ message.setText(item.getDescripcionRespuesta());
           arrow.setVisibility(View.GONE);
           view.setOnClickListener(null);
           }

        }

    }

    //binding view on view holder
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        ( (ViewHolderItemMenu)holder).bindView(items.get(position));

    }



    //gettin item number on recyclerview
    @Override
    public int getItemCount() {
        return items.size();
    }
 public interface arrowListener{
      public void  Onclick(String Year);
 }
}
