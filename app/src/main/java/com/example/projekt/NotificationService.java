package com.example.projekt;

import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
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
        initializeTimerTask();


        timer.schedule(timerTask, secondsAfter * 1000); //

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


                handler.post(new Runnable() {
                    public void run() {
                        /*
                        TaskStackBuilder stackBuilder = TaskStackBuilder.create(NotificationService.this);

                        stackBuilder.addNextIntent(newLauncherIntent(NotificationService.this));

                        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,
                                PendingIntent.FLAG_UPDATE_CURRENT);

                         */

                        NotificationCompat.Builder builder = new NotificationCompat.Builder(NotificationService.this, CHANNEL_ID)
                                .setSmallIcon(R.drawable.clipboard_logo)
                                .setContentTitle("Projekt App")
                                .setContentText("Return to the app or risk losing your progress!")
                                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                                //.setContentIntent(resultPendingIntent)
                                .setAutoCancel(true);


                        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(NotificationService.this);


                        notificationManager.notify(20, builder.build());


                    }
                });
            }
        };
    }
    /*
    public static Intent newLauncherIntent(final Context context) {
        final Intent intent = new Intent(context, ConcentrationActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        return intent;
    }

     */
}