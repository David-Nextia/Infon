package com.nextia.micuentainfonavit.ui.home.welcome_cards;
/**
 * Adapter of the view of each card on welcome view
 */

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nextia.domain.models.welcome.WelcomeCard;
import com.nextia.micuentainfonavit.R;

import java.util.ArrayList;

public class CardAdapter extends RecyclerView.Adapter {
    ArrayList<WelcomeCard> cards= new ArrayList<>();
    @NonNull

    //creating view
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new SimpleViewHolder(view);
    }
    public void setData(ArrayList<WelcomeCard> cards){
        this.cards=cards;
        notifyDataSetChanged();
    }

    //filling view information
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((SimpleViewHolder) holder).onBind(cards.get(position));
    }

    //to get the total items that will show
    @Override
    public int getItemCount() {
        return cards.size();
    }

    //to get the type of view to show
    @Override
    public int getItemViewType(int position) {
        return R.layout.card_view_welcome;
    }

    //class that manages the binding part
    public class SimpleViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView description;
        ImageView imagen;
        public SimpleViewHolder(final View itemView) {
            super(itemView);
        }
        public void onBind(WelcomeCard card){
            title=itemView.findViewById(R.id.tv_title_description);
            description=itemView.findViewById(R.id.tv_description);
            imagen=itemView.findViewById(R.id.img_welcome);
            title.setText(card.getTitle());
            description.setText(card.getDescription());
            imagen.setImageResource(card.getImagen());

        }
    }
}
