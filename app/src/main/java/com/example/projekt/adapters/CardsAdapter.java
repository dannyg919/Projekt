package com.example.projekt.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projekt.AddProjektActivity;
import com.example.projekt.R;
import com.example.projekt.models.Card;


import java.util.List;

public class CardsAdapter extends RecyclerView.Adapter<CardsAdapter.ViewHolder> {
    private Context context;
    private List<Card> cards;

    public CardsAdapter(Context context, List<Card> cards) {
        this.context = context;
        this.cards = cards;
    }

    @Override
    public int getItemViewType(int position) {
        return (position == cards.size()) ? R.layout.item_add_card : R.layout.item_card;
    }

    @NonNull
    @Override
    public CardsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;

        if (viewType == R.layout.item_card) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card, parent, false);

        }
        //If last in list we want to add this button.
        else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_add_card, parent, false);
        }


        return new CardsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardsAdapter.ViewHolder holder, int position) {

        if (position == cards.size()) {
            holder.tvNewCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, "New card added!", Toast.LENGTH_SHORT).show();

                }
            });
        } else {

            Card card = cards.get(position);
            holder.bind(card);
        }

    }

    @Override
    public int getItemCount() {
        return cards.size() + 1;
    }

    public void clear() {
        cards.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Card> list) {

        cards.addAll(list);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvCardName;
        private TextView tvNewCard;
        private RecyclerView rvTasks;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCardName = itemView.findViewById(R.id.tvCardName);
            tvNewCard = itemView.findViewById(R.id.tvNewCard);
            //rvTasks = itemView.findViewById(R.id.rvTasks);

        }

        public void bind(Card card) {
            tvCardName.setText(card.getName());

        }
    }

}
