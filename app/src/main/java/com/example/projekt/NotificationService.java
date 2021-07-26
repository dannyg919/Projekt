package com.example.projekt;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.Timer;
import java.util.TimerTask;

public class NotificationService extends Service {
    public static final String CHANNEL_ID = "MainChannel";

    Timer timer;
    TimerTask timerTask;
    int secondsAfter = 5;


    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        startTimer();

        return START_STICKY;
    }


    @Override
    public void onCreate() {
    }

    @Override
    public void onDestroy() {
        stoptimertask();
        super.onDestroy();
    }

    // use a handler to be able to run TimerTask
    final Handler handler = new Handler();


    public void startTimer() {
        //set a new Timer
        timer = new Timer();

        //initialize the TimerTask's job
        initializeTimerTask();

        //schedule the timer, after the first 5000ms the TimerTask will run every 10000ms
        timer.schedule(timerTask, secondsAfter*1000); //

    }

    public void stoptimertask() {
        //stop the timer, if it's not already null
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    public void initializeTimerTask() {

        timerTask = new TimerTask() {
            public void run() {

                //use a handler to run a toast that shows the current timestamp
                handler.post(new Runnable() {
                    public void run() {

                        NotificationCompat.Builder builder = new NotificationCompat.Builder(NotificationService.this, CHANNEL_ID)
                                .setSmallIcon(R.drawable.ic_launcher_foreground)
                                .setContentTitle("Projekt App")
                                .setContentText("Return to the app or risk losing your progress!")
                                .setPriority(NotificationCompat.PRIORITY_DEFAULT);


                        //TODO what happens on notification click???

                        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(NotificationService.this);

                        // notificationId is a unique int for each notification that you must define
                        notificationManager.notify(20, builder.build());



                    }
                });
            }
        };
    }
}