package com.example.projekt.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projekt.R;
import com.example.projekt.TaskActivity;
import com.example.projekt.models.Task;

import org.w3c.dom.Text;

import java.util.List;

public class TaskActivityAdapter extends RecyclerView.Adapter<TaskActivityAdapter.ViewHolder> {
    Context context;
    Task task;


    public TaskActivityAdapter(Context context,Task task){
        this.context = context;
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
        List<String> activity = task.getActivity();
        String currentActivity = activity.get(position);
        holder.tvActivity.setText(currentActivity);

    }

    @Override
    public int getItemCount() {
        return task.getActivity().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvActivity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvActivity = itemView.findViewById(R.id.tvActivity);
        }

    }
}
