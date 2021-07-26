package com.example.projekt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.KeyguardManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.PowerManager;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projekt.models.Task;
import com.parse.ParseUser;

import org.parceler.Parcels;

import java.util.List;
import java.util.Locale;

public class ConcentrationActivity extends AppCompatActivity {
    public static final String CHANNEL_ID = "MainChannel";

    EditText etSetTime;
    TextView tvClock;
    Button btnCancel;
    Button btnStartTime;

    boolean isInBackground;
    boolean isScreenAwake;

    Task task;
    CountDownTimer countDownTimer;

    String time;
    long timeLeft;
    long endTime;

    //TODO isRunning boolean needs to be implemented for BackPressed & horizontal viewing(visibility)
    //TODO Send notification 5 seconds after pausing the app to come back to app. If after 10 seconds they aren't
    //TODO unpaused, then finish the current activity.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_concentration);

        etSetTime = findViewById(R.id.etStartTime);
        tvClock = findViewById(R.id.tvClock);
        btnCancel = findViewById(R.id.btnCancel);
        btnStartTime = findViewById(R.id.btnSetTime);

        task = Parcels.unwrap(getIntent().getParcelableExtra("CON"));

        btnStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String input = etSetTime.getText().toString();
                //TODO check input for empty


                long inputTime = Long.parseLong(input) * 60000;

                //TODO check input > 0

                timeLeft = inputTime;
                time = input;

                closeKeyboard();

                etSetTime.setText("");
                etSetTime.setVisibility(View.INVISIBLE);
                btnStartTime.setVisibility(View.INVISIBLE);
                //TODO set a TextView warning to not close the app. Set a reminder to get back to work!

                startTimer();

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO if isRunning else finish()
                AlertDialog dialog = new AlertDialog.Builder(ConcentrationActivity.this)
                        .setTitle("Are you sure you want to quit and lose your progress?")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        })

                        .setNegativeButton("NO", null)
                        .create();
                dialog.show();

            }
        });


    }


    //Starts the CountdownTimer, and determines what to do onFinish
    //Also gets the current ending time for the current time left.
    private void startTimer() {
        Log.i("hello", "START");
        endTime = System.currentTimeMillis() + timeLeft;

        countDownTimer = new CountDownTimer(timeLeft, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeft = millisUntilFinished;
                updateText();

            }

            @Override
            public void onFinish() {
                ParseUser user = ParseUser.getCurrentUser();
                task.addActivity(user.getUsername() + " worked for " + time + " minutes.");


                //Give notification that timer has finished
                NotificationCompat.Builder builder = new NotificationCompat.Builder(ConcentrationActivity.this, CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_launcher_foreground)
                        .setContentTitle("Projekt App")
                        .setContentText("Your Concentration Mode Timer has finished")
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);

                        //TODO what happens on notification click???

                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(ConcentrationActivity.this);

                // notificationId is a unique int for each notification that you must define
                notificationManager.notify(20, builder.build());

                finish();

            }
        }.start();


    }

    //Updates the text within the TextView given timeLeftInMillis
    private void updateText() {
        int hours = (int) (timeLeft / 1000) / 3600;
        int minutes = (int) ((timeLeft / 1000) % 3600) / 60;
        int seconds = (int) (timeLeft / 1000) % 60;

        String timeLeftText;
        if (hours > 0) {
            timeLeftText = String.format(Locale.getDefault(),
                    "%d:%02d:%02d", hours, minutes, seconds);
        } else {
            timeLeftText = String.format(Locale.getDefault(),
                    "%02d:%02d", minutes, seconds);
        }

        tvClock.setText(timeLeftText);


    }

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


    @Override
    public void onBackPressed() {

        //TODO add if(isRunning) else finish()
        AlertDialog dialog = new AlertDialog.Builder(ConcentrationActivity.this)
                .setTitle("Are you sure you want to quit and lose your progress?")
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })

                .setNegativeButton("NO", null)
                .create();
        dialog.show();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putLong("timeEnd", endTime);

    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        endTime = savedInstanceState.getLong("timeEnd");


        //Find time where we left off, and start timer
        timeLeft = endTime - System.currentTimeMillis();
        startTimer();
    }

    @Override
    protected void onStop() {
        //TODO instead of onStop() we want this called ONLY when app is in the background.
        super.onStop();

        ActivityManager.RunningAppProcessInfo myProcess = new ActivityManager.RunningAppProcessInfo();
        ActivityManager.getMyMemoryState(myProcess);
        isInBackground = myProcess.importance != ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND;

        PowerManager powerManager = (PowerManager) this.getSystemService(Context.POWER_SERVICE);
        isScreenAwake = (Build.VERSION.SDK_INT < 20? powerManager.isScreenOn():powerManager.isInteractive());

        if(isInBackground && isScreenAwake) {
            Log.d("hello","Your application is in background state");
            startService(new Intent(this, NotificationService.class));
        }


    }
}