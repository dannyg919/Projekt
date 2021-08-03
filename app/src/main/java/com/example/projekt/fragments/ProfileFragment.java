package com.example.projekt.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.projekt.ProfilePictureActivity;
import com.example.projekt.R;
import com.example.projekt.TaskActivity;
import com.example.projekt.adapters.FriendsAdapter;
import com.example.projekt.adapters.ProjektsAdapter;
import com.example.projekt.models.Card;
import com.example.projekt.models.Projekt;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment {
    TextView tvName;
    TextView tvUsername;
    ImageView ivProfilePicture;
    Button btnFriend;

    protected List<ParseUser> allFriends;
    protected RecyclerView rvFriends;
    protected FriendsAdapter friendsAdapter;

    List<String> friendsList;

    public ProfileFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        return inflater.inflate(R.layout.fragment_profile, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        tvName = view.findViewById(R.id.tvName);
        tvUsername = view.findViewById(R.id.tvUsername);
        ivProfilePicture = view.findViewById(R.id.ivProfilePicture);
        btnFriend = view.findViewById(R.id.btnFriend);
        rvFriends = view.findViewById(R.id.rvFriends);

        allFriends = new ArrayList<>();
        friendsAdapter = new FriendsAdapter(getContext(), allFriends);

        rvFriends.setAdapter(friendsAdapter);
        rvFriends.setLayoutManager(new LinearLayoutManager(getContext()));

        queryFriends();

        ParseUser user = ParseUser.getCurrentUser();
        tvName.setText(user.getString("firstName") + " " + user.getString("lastName"));
        tvUsername.setText(user.getUsername());


        Glide.with(getContext())
                .load(user.getParseFile("profilePicture").getUrl())
                .circleCrop()
                .into(ivProfilePicture);

        ivProfilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), ProfilePictureActivity.class);
                startActivity(i);
            }
        });

        btnFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText userEditText = new EditText(getContext());
                userEditText.setHint("Enter Friend's Username");

                AlertDialog dialog = new AlertDialog.Builder(getContext())
                        .setTitle("Enter your friend's username below")
                        .setView(userEditText)

                        .setPositiveButton("Add Friend", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                addFriend(userEditText.getText().toString());
                            }
                        })

                        .setNegativeButton("Cancel", null)
                        .create();
                dialog.show();

            }
        });


    }


    private void queryFriends() {

        ParseQuery<ParseUser> query = ParseUser.getQuery();

        ParseUser currentUser = ParseUser.getCurrentUser();

        List<String> list =  new ArrayList<>();

        if(currentUser.getList("friendsList") != null){
            list = currentUser.getList("friendsList");
        }

        query.whereContainedIn("objectId", list);


        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> friends, ParseException e) {
                // check for errors
                if (e != null) {
                    return;
                }

                allFriends.addAll(friends);
                friendsAdapter.notifyDataSetChanged();

            }
        });


    }


    private void addFriend(String username) {
        ParseQuery<ParseUser> query = ParseUser.getQuery();

        query.whereEqualTo("username", username);


        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> list, ParseException e) {
                // check for errors
                if (e != null) {
                    return;
                }

                if (list.isEmpty()) {
                    Toast.makeText(getContext(), "User does not exist", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Friend Added!", Toast.LENGTH_SHORT).show();
                    //ADD FRIEND TO FRIENDS LIST

                    friendsList = ParseUser.getCurrentUser().getList("friendsList");
                    if (friendsList != null) {
                        friendsList.add(list.get(0).getObjectId());
                        ParseUser.getCurrentUser().put("friendsList", friendsList);
                        ParseUser.getCurrentUser().saveInBackground();

                    } else {
                        List<String> singleFriend = new ArrayList<>();
                        singleFriend.add(list.get(0).getObjectId());

                        ParseUser.getCurrentUser().put("friendsList", singleFriend);
                        ParseUser.getCurrentUser().saveInBackground();

                    }


                }


            }
        });


    }


}
