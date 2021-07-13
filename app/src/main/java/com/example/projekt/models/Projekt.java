package com.example.projekt.models;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("Projekt")

public class Projekt extends ParseObject {
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_NAME = "name";
    public static final String KEY_OWNER = "owner";

    public String getDescription(){
        return getString(KEY_DESCRIPTION);
    }
    public void setDescription(String description){
        put(KEY_DESCRIPTION,description);
    }

    public String getName(){
        return getString(KEY_NAME);
    }
    public void setName(String description){
        put(KEY_NAME,description);
    }

    public ParseUser getUser(){
        return getParseUser(KEY_OWNER);
    }
    public void setUser(ParseUser user){
        put(KEY_OWNER,user);
    }

}
