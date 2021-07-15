package com.example.projekt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.projekt.adapters.CardsAdapter;
import com.example.projekt.adapters.ProjektsAdapter;
import com.example.projekt.models.Card;
import com.example.projekt.models.Projekt;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

public class ProjektActivity extends AppCompatActivity {
    TextView tvProjektName;
    RecyclerView rvCards;
    List<Card> allCards;
    CardsAdapter cardsAdapter;

    Projekt projekt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projekt);

        projekt = Parcels.unwrap(getIntent().getParcelableExtra("DETAILS"));

        tvProjektName = findViewById(R.id.tvProjektName);
        rvCards = findViewById(R.id.rvCards);

        tvProjektName.setText(projekt.getName());

        allCards = new ArrayList<>();
        cardsAdapter = new CardsAdapter(this, allCards, projekt);
        rvCards.setAdapter(cardsAdapter);
        rvCards.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        SnapHelper helper = new LinearSnapHelper();
        helper.attachToRecyclerView(rvCards);

        queryCards();

    }


    private void queryCards() {
        ParseQuery<Card> query = ParseQuery.getQuery(Card.class);
        // show only projekts for this user

        query.whereEqualTo(Card.KEY_PROJEKT, projekt);

        // order posts by creation date (newest first)
        query.addAscendingOrder("createdAt");
        // start an asynchronous call for posts
        query.findInBackground(new FindCallback<Card>() {
            @Override
            public void done(List<Card> cards, ParseException e) {
                // check for errors
                if (e != null) {
                    return;
                }


                allCards.addAll(cards);
                cardsAdapter.notifyDataSetChanged();
            }
        });
    }


}