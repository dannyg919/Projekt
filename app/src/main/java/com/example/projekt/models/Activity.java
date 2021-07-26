package com.example.projekt.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("Activity")
public class Activity extends ParseObject {
    public static final String KEY_CONTENT = "content";
    public static final String KEY_TASK = "task";
    public static final String KEY_USER = "user";

    public String getContent() {
        return getString(KEY_CONTENT);
    }

    public void setContent(String name) {
        put(KEY_CONTENT, name);
    }

    public ParseObject getTask() {
        return getParseObject(KEY_TASK);
    }

    public void setTask(ParseObject task) { put(KEY_TASK, task); }

    public ParseUser getUser(){ return getParseUser(KEY_USER);}

    public void setUser(ParseUser user) { put(KEY_USER,user); }

}
