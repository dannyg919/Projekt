package com.example.projekt.models;

import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@ParseClassName("Task")
public class Task extends ParseObject {
    public static final String KEY_NAME = "name";
    public static final String KEY_CARD = "card";
    public static final String KEY_ACTIVITY = "activity";

    public String getName() {
        return getString(KEY_NAME);
    }

    public void setName(String name) {
        put(KEY_NAME, name);
    }

    public ParseObject getCard() {
        return getParseObject(KEY_CARD);
    }

    public void setCard(ParseObject card) {
        put(KEY_CARD, card);
    }

    public List<String> getActivity() {return getList(KEY_ACTIVITY); }

    public void setActivity(List<String> activity) {
        put(KEY_ACTIVITY,activity);
    }

    public void addActivity(String activity){
        add(KEY_ACTIVITY,activity);
        saveInBackground();

    }

}
