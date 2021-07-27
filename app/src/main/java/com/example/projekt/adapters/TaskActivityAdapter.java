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
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

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
            String userID = activity.getUser().getObjectId();


            ParseQuery<ParseUser> query = ParseQuery.getQuery("_User");
            query.getInBackground(userID, new GetCallback<ParseUser>() {
                public void done(ParseUser user, ParseException e) {
                    if (e != null) {
                        //error
                    }

                    Glide.with(context)

                            .load(user.getParseFile("profilePicture").getUrl())
                            .circleCrop()
                            .into(ivActivityPicture);

                }
            });


            tvActivity.setText(activity.getContent());
            ;


        }
    }
}
