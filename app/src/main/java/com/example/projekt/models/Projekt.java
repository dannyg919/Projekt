package com.example.projekt.models;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.List;

@ParseClassName("Projekt")

public class Projekt extends ParseObject {
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_NAME = "name";
    public static final String KEY_OWNER = "owner";
    public static final String KEY_MEMBERS = "membersList";

    public String getDescription() {
        return getString(KEY_DESCRIPTION);
    }

    public void setDescription(String description) {
        put(KEY_DESCRIPTION, description);
    }

    public String getName() {
        return getString(KEY_NAME);
    }

    public void setName(String name) {
        put(KEY_NAME, name);
    }

    public ParseUser getUser() {
        return getParseUser(KEY_OWNER);
    }

    public void setUser(ParseUser user) {
        put(KEY_OWNER, user);
    }

    public List<ParseUser> getMembers(){return getList(KEY_MEMBERS);}

    public void setMembers(List<ParseUser> members) { put(KEY_MEMBERS, members);}

}
