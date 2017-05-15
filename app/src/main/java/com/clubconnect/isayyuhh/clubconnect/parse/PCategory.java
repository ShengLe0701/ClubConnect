package com.clubconnect.isayyuhh.clubconnect.parse;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import org.json.JSONArray;

@ParseClassName("Interest")
public class PCategory extends ParseObject {

    public String getTitleC(){
        return getString("title");
    }

    public JSONArray getArrC(){
        return getJSONArray("subcategories");
    }
}
