package com.example.projekt;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApplication extends Application {

    public void onCreate() {
        super.onCreate();

        //ParseObject.registerSubclass(Projekt.class);

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("omymch9acwgh4ZeW9X3T34ATaRPRY40EyCqA6PKc")
                .clientKey("6jQmL0nCtIyxM6CtQ3Hz5DIsy9pgAzAMNfMd87mA")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }

}
