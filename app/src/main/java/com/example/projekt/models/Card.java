package com.example.projekt.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("Card")
public class Card extends ParseObject {

    public static final String KEY_NAME = "name";
    public static final String KEY_PROJEKT = "projekt";

    public String getName() {
        return getString(KEY_NAME);
    }

    public void setName(String name) {
        put(KEY_NAME, name);
    }

    public ParseObject getProjekt() {
        return getParseObject(KEY_PROJEKT);
    }

    public void setProjekt(ParseObject projekt) {
        put(KEY_PROJEKT, projekt);
    }


}

