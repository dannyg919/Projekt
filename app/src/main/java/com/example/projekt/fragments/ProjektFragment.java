package com.example.projekt.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projekt.R;
import com.example.projekt.adapters.ProjektsAdapter;
import com.example.projekt.models.Projekt;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class ProjektFragment extends Fragment {
    protected List<Projekt> allProjekts;
    protected RecyclerView rvProjekts;
    protected ProjektsAdapter projektAdapter;

    public ProjektFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        return inflater.inflate(R.layout.fragment_projekt, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        rvProjekts = view.findViewById(R.id.rvProjekt);
        allProjekts = new ArrayList<>();
        projektAdapter = new ProjektsAdapter(getContext(), allProjekts);

        rvProjekts.setAdapter(projektAdapter);
        rvProjekts.setLayoutManager(new LinearLayoutManager(getContext()));

        queryProjekts();
    }

    private void queryProjekts() {
        ParseQuery<Projekt> query = ParseQuery.getQuery(Projekt.class);
        // show only projekts for this user
        ParseUser user = ParseUser.getCurrentUser();
        query.whereEqualTo(Projekt.KEY_OWNER, user);

        // order posts by creation date (newest first)
        query.addDescendingOrder("createdAt");
        // start an asynchronous call for posts
        query.findInBackground(new FindCallback<Projekt>() {
            @Override
            public void done(List<Projekt> projekts, ParseException e) {
                // check for errors
                if (e != null) {
                    return;
                }

                allProjekts.addAll(projekts);
                projektAdapter.notifyDataSetChanged();
            }
        });
    }

}
