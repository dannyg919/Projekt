package com.example.projekt.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.projekt.R;
import com.example.projekt.TaskActivity;
import com.example.projekt.models.Activity;
import com.example.projekt.models.Card;
import com.example.projekt.models.Projekt;
import com.example.projekt.models.Task;
import com.parse.ParseObject;
import com.parse.ParseUser;

import org.w3c.dom.Text;

import java.util.List;

public class TaskActivityAdapter extends RecyclerView.Adapter<TaskActivityAdapter.ViewHolder> {
    Context context;
    List<Activity> activities;
    Task task;


    public TaskActivityAdapter(Context context, List<Activity> activities, Task task) {
        this.context = context;
        this.activities = activities;
        this.task = task;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;

        view = LayoutInflater.from(context).inflate(R.layout.item_activity, parent, false);

        return new TaskActivityAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskActivityAdapter.ViewHolder holder, int position) {

        Activity activity = activities.get(position);
        holder.bind(activity);

    }

    @Override
    public int getItemCount() {
        return activities.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvActivity;
        private ImageView ivActivityPicture;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvActivity = itemView.findViewById(R.id.tvActivity);
            ivActivityPicture = itemView.findViewById(R.id.ivActivityPicture);


        }

        public void bind(Activity activity) {
            ParseUser user = activity.getParseUser("user");

            user.fetchInBackground();

            tvActivity.setText(activity.getContent());

            Glide.with(context)
                    //TODO change this to use user.getProfilePicture etc.
                    .load(R.drawable.default_profile_pic)
                    .circleCrop()
                    .into(ivActivityPicture);



        }
    }
}
