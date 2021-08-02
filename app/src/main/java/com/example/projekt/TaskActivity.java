package com.example.projekt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.IntentCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projekt.adapters.CardsAdapter;
import com.example.projekt.adapters.TaskActivityAdapter;
import com.example.projekt.models.Activity;
import com.example.projekt.models.Card;
import com.example.projekt.models.Projekt;
import com.example.projekt.models.Task;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TaskActivity extends AppCompatActivity {
    TextView tvTaskName;
    RecyclerView rvTaskActivity;
    Button btnLogTime;
    Button btnConcentration;

    List<Activity> allActivity;
    Task task;


    TaskActivityAdapter activityAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        tvTaskName = findViewById(R.id.tvTaskName);
        btnLogTime = findViewById(R.id.btnLogTime);
        btnConcentration = findViewById(R.id.btnConcentration);
        rvTaskActivity = findViewById(R.id.rvTaskActivity);


        task = Parcels.unwrap(getIntent().getParcelableExtra("TASK"));

        tvTaskName.setText(task.getName());

        allActivity = new ArrayList<>();
        activityAdapter = new TaskActivityAdapter(this,allActivity, task);
        rvTaskActivity.setAdapter(activityAdapter);
        rvTaskActivity.setLayoutManager(new LinearLayoutManager(this));




        btnLogTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText timeEditText = new EditText(TaskActivity.this);
                timeEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
                timeEditText.setHint("Minutes");

                AlertDialog dialog = new AlertDialog.Builder(TaskActivity.this)
                        .setTitle("How long did you work for?")
                        .setView(timeEditText)

                        .setPositiveButton("Add Time", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {


                                ParseUser user = ParseUser.getCurrentUser();
                                String timeWorked = timeEditText.getText().toString();

                                saveActivity(timeWorked,user);




                            }
                        })

                        .setNegativeButton("Cancel", null)
                        .create();
                dialog.show();

            }
        });

        btnConcentration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(TaskActivity.this, ConcentrationActivity.class);
                i.putExtra("CON", Parcels.wrap(task));
                startActivity(i);

            }
        });

        queryActivity();

    }

    private void queryActivity() {
        ParseQuery<Activity> query = ParseQuery.getQuery(Activity.class);

        query.whereEqualTo(Activity.KEY_TASK, task);

        query.addDescendingOrder("createdAt");
        query.findInBackground(new FindCallback<Activity>() {
            @Override
            public void done(List<Activity> activities, ParseException e) {
                // check for errors
                if (e != null) {
                    return;
                }


                allActivity.addAll(activities);
                activityAdapter.notifyDataSetChanged();
            }
        });
    }

    private void saveActivity(String timeWorked, ParseUser user){
        Activity activity = new Activity();
        activity.setTask(task);
        activity.setUser(user);



        activity.setContent(user.getString("firstName") + " worked for " + timeWorked + " minutes.");

        activity.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {

                }

            }
        });
    }
}