package com.example.projekt.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.projekt.R;
import com.example.projekt.models.Projekt;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class MembersAdapter extends RecyclerView.Adapter<MembersAdapter.ViewHolder> {

    Context context;
    List<ParseUser> friends;
    Projekt projekt;
    List<ParseUser> membersList;

    public MembersAdapter(Context context, Projekt projekt, List<ParseUser> friends) {
        this.context = context;
        this.projekt = projekt;
        this.friends = friends;
    }

    @NonNull
    @Override
    public MembersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;

        view = LayoutInflater.from(context).inflate(R.layout.item_friend, parent, false);

        return new MembersAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MembersAdapter.ViewHolder holder, int position) {

        ParseUser friend = friends.get(position);
        holder.bind(friend);

    }

    @Override
    public int getItemCount() {
        return friends.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout rlFriends;
        private TextView tvFriendName;
        private TextView tvFriendUsername;
        private ImageView ivFriendPicture;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rlFriends = itemView.findViewById(R.id.rlFriends);
            tvFriendName = itemView.findViewById(R.id.tvFriendName);
            tvFriendUsername = itemView.findViewById(R.id.tvFriendUsername);
            ivFriendPicture = itemView.findViewById(R.id.ivFriendPicture);

        }

        public void bind(ParseUser friend) {
            tvFriendName.setText(friend.getString("firstName") + " " + friend.getString("lastName"));
            tvFriendUsername.setText(friend.getUsername());

            Glide.with(context)
                    .load(friend.getParseFile("profilePicture").getUrl())
                    .circleCrop()
                    .into(ivFriendPicture);

            rlFriends.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addTeamMember(friend);

                }
            });


        }

        private void addTeamMember(ParseUser friend) {
            ParseQuery<Projekt> query = ParseQuery.getQuery("Projekt");

            query.getInBackground(projekt.getObjectId(), new GetCallback<Projekt>() {
                @Override
                public void done(Projekt object, ParseException e) {
                    membersList = object.getMembers();




                    if (membersList != null) {

                        if (membersList.contains(friend)) {
                            Toast.makeText(context, "Team Member Already Added!",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(context, "Team Member Added!",Toast.LENGTH_SHORT).show();
                            membersList.add(friend);
                            object.setMembers(membersList);
                            object.saveInBackground();
                        }


                    } else {
                        Toast.makeText(context, "Team Member Added!",Toast.LENGTH_SHORT).show();
                        membersList = new ArrayList<ParseUser>();
                        membersList.add(friend);
                        object.setMembers(membersList);
                        object.saveInBackground();

                    }

                }
            });


        }
    }
}

