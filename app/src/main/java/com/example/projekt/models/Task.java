package com.example.projekt.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("Task")
public class Task extends ParseObject {
    public static final String KEY_NAME = "name";
    public static final String KEY_CARD = "card";

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
}
