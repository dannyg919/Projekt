package com.example.projekt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.example.projekt.adapters.FriendsAdapter;
import com.example.projekt.adapters.MembersAdapter;
import com.example.projekt.models.Projekt;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

public class AddMemberActivity extends AppCompatActivity {
    Projekt projekt;
    List<ParseUser> allFriends;
    MembersAdapter membersAdapter;
    RecyclerView rvFriends;
    TextView tvProjektName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_member);

        tvProjektName = findViewById(R.id.tvProjektName);

        projekt = Parcels.unwrap(getIntent().getParcelableExtra("projekt"));

        tvProjektName.setText(projekt.getName());

        rvFriends = findViewById(R.id.rvFriends);

        allFriends = new ArrayList<>();
        membersAdapter = new MembersAdapter(this, projekt, allFriends);

        rvFriends.setAdapter(membersAdapter);
        rvFriends.setLayoutManager(new LinearLayoutManager(this));

        queryFriends();

    }

    private void queryFriends() {
        ParseQuery<ParseUser> query = ParseUser.getQuery();

        ParseUser currentUser = ParseUser.getCurrentUser();

        query.whereContainedIn("objectId", currentUser.getList("friendsList"));


        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> friends, ParseException e) {
                // check for errors
                if (e != null) {
                    return;
                }

                allFriends.addAll(friends);
                membersAdapter.notifyDataSetChanged();

            }
        });


    }
}