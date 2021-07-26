package com.example.projekt;

import android.app.Application;


import android.content.Context;


import com.example.projekt.models.Card;
import com.example.projekt.models.Projekt;
import com.example.projekt.models.Task;
import com.parse.Parse;
import com.parse.ParseObject;

public class ProjektApplication extends Application {

    private static Context appContext;
    public static boolean wasInBackground;

    public void onCreate() {
        super.onCreate();

        ParseObject.registerSubclass(Projekt.class);
        ParseObject.registerSubclass(Card.class);
        ParseObject.registerSubclass(Task.class);

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("omymch9acwgh4ZeW9X3T34ATaRPRY40EyCqA6PKc")
                .clientKey("6jQmL0nCtIyxM6CtQ3Hz5DIsy9pgAzAMNfMd87mA")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }

}
