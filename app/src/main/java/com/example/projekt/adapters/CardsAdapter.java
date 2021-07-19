package com.example.projekt.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projekt.AddProjektActivity;
import com.example.projekt.R;
import com.example.projekt.models.Card;
import com.example.projekt.models.Projekt;
import com.example.projekt.models.Task;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.SaveCallback;


import java.util.ArrayList;
import java.util.List;

public class CardsAdapter extends RecyclerView.Adapter<CardsAdapter.ViewHolder> {
    private Context context;
    private List<Card> cards;
    private Projekt projekt;

    public CardsAdapter(Context context, List<Card> cards, Projekt projekt) {
        this.context = context;
        this.cards = cards;
        this.projekt = projekt;
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
                    //Create Dialog box to create new card
                    final EditText cardEditText = new EditText(context);
                    AlertDialog dialog = new AlertDialog.Builder(context)
                            .setTitle("Add a new card")
                            .setView(cardEditText)

                            .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    Card card = new Card();
                                    card.setName(String.valueOf(cardEditText.getText()));
                                    card.setProjekt(projekt);

                                    card.saveInBackground(new SaveCallback() {
                                        @Override
                                        public void done(ParseException e) {
                                            if (e != null) {
                                                Toast.makeText(context, "Error while saving!", Toast.LENGTH_SHORT).show();
                                            }


                                        }
                                    });
                                    cards.add(card);
                                    notifyItemInserted(cards.size()-1);
                                    

                                }
                            })

                            .setNegativeButton("Cancel", null)
                            .create();
                    dialog.show();
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

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvCardName;
        private TextView tvNewCard;
        private RecyclerView rvTasks;

        private List<Task> allTasks;
        private TasksAdapter tasksAdapter;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCardName = itemView.findViewById(R.id.tvCardName);
            tvNewCard = itemView.findViewById(R.id.tvNewCard);
            rvTasks = itemView.findViewById(R.id.rvTasks);

        }

        public void bind(Card card) {
            tvCardName.setText(card.getName());

            allTasks = new ArrayList<>();
            tasksAdapter = new TasksAdapter(context, allTasks, card);
            rvTasks.setAdapter(tasksAdapter);
            rvTasks.setLayoutManager(new GridLayoutManager(context, 2));

            queryTasks(card);


        }

        private void queryTasks(Card card) {
            ParseQuery<Task> query = ParseQuery.getQuery(Task.class);

            query.whereEqualTo(Task.KEY_CARD, card);

            // order posts by creation date (newest first)
            query.addAscendingOrder("createdAt");
            // start an asynchronous call for posts
            query.findInBackground(new FindCallback<Task>() {
                @Override
                public void done(List<Task> tasks, ParseException e) {
                    // check for errors
                    if (e != null) {
                        return;
                    }


                    allTasks.addAll(tasks);
                    tasksAdapter.notifyDataSetChanged();
                }
            });

        }


    }

}
