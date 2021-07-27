package com.example.projekt.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.projekt.ProfilePictureActivity;
import com.example.projekt.R;
import com.parse.ParseFile;
import com.parse.ParseUser;

public class ProfileFragment extends Fragment {
    TextView tvUsername;
    ImageView ivProfilePicture;

    public ProfileFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        return inflater.inflate(R.layout.fragment_profile, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        tvUsername = view.findViewById(R.id.tvUsername);
        ivProfilePicture = view.findViewById(R.id.ivProfilePicture);

        ParseUser user = ParseUser.getCurrentUser();
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



    }


}
