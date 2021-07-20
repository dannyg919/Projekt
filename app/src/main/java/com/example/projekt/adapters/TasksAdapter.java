package com.example.projekt.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projekt.R;
import com.example.projekt.TaskActivity;
import com.example.projekt.models.Card;
import com.example.projekt.models.Task;
import com.parse.ParseException;
import com.parse.SaveCallback;

import org.parceler.Parcels;

import java.util.List;

public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.ViewHolder> {
    private Context context;
    private List<com.example.projekt.models.Task> tasks;
    private Card card;

    public TasksAdapter(Context context, List<com.example.projekt.models.Task> tasks, Card card) {
        this.context = context;
        this.tasks = tasks;
        this.card = card;
    }

    @Override
    public int getItemViewType(int position) {
        return (position == tasks.size()) ? R.layout.item_add_task : R.layout.item_task;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;

        if (viewType == R.layout.item_task) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);

        }
        //If last in list we want to add this button.
        else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_add_task, parent, false);
        }

        return new TasksAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TasksAdapter.ViewHolder holder, int position) {

        if (position == tasks.size()) {
            holder.tvAddTask.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Create Dialog box to create new card
                    final EditText taskEditText = new EditText(context);
                    AlertDialog dialog = new AlertDialog.Builder(context)
                            .setTitle("Add a new task")
                            .setView(taskEditText)

                            .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    com.example.projekt.models.Task task = new Task();
                                    task.setName(String.valueOf(taskEditText.getText()));
                                    task.setCard(card);

                                    task.saveInBackground(new SaveCallback() {
                                        @Override
                                        public void done(ParseException e) {
                                            if (e != null) {
                                                Toast.makeText(context, "Error while saving!", Toast.LENGTH_SHORT).show();
                                            }

                                        }
                                    });
                                    tasks.add(task);
                                    notifyItemInserted(tasks.size()-1);
                                }
                            })

                            .setNegativeButton("Cancel", null)
                            .create();
                    dialog.show();
                }


            });
        } else {

            com.example.projekt.models.Task task = tasks.get(position);
            holder.bind(task);
        }

    }

    @Override
    public int getItemCount() {
        return tasks.size() + 1;
    }

    public void clear() {
        tasks.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<com.example.projekt.models.Task> list) {

        tasks.addAll(list);
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTaskName;
        private TextView tvAddTask;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTaskName = itemView.findViewById(R.id.tvTaskName);
            tvAddTask = itemView.findViewById(R.id.tvAddTask);
        }

        public void bind(com.example.projekt.models.Task task) {
            tvTaskName.setText(task.getName());

            tvTaskName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context, TaskActivity.class);
                    i.putExtra("TASK", Parcels.wrap(task));
                    context.startActivity(i);

                }
            });

        }

    }
}
