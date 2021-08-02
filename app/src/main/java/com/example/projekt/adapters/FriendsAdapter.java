package com.example.projekt.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.projekt.R;
import com.example.projekt.models.Activity;
import com.example.projekt.models.Task;
import com.parse.ParseUser;

import java.util.List;

public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.ViewHolder>{

    Context context;
    List<ParseUser> friends;

    public FriendsAdapter(Context context, List<ParseUser> friends){
        this.context = context;
        this.friends = friends;
    }

    @NonNull
    @Override
    public FriendsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;

        view = LayoutInflater.from(context).inflate(R.layout.item_friend, parent, false);

        return new FriendsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendsAdapter.ViewHolder holder, int position) {

        ParseUser friend = friends.get(position);
        holder.bind(friend);

    }

    @Override
    public int getItemCount() {
        return friends.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvFriendName;
        private TextView tvFriendUsername;
        private ImageView ivFriendPicture;

        public ViewHolder(@NonNull View itemView){
            super(itemView);

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



        }
    }
}
