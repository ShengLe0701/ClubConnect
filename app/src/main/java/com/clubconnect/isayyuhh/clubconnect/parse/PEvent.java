package com.clubconnect.isayyuhh.clubconnect.parse;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;

import org.json.JSONArray;

@ParseClassName("Event2")
public class PEvent extends ParseObject {

    public JSONArray getSubCategories(){
        return getJSONArray("subcategories");
    }

    public ParseFile getImage(){
        return getParseFile("clubCardImage");
    }

    public String getDay(){
        return getString("day");
    }

    public String getEmail(){
        return getString("email");
    }

    public String getDate(){
        return getString("Date");
    }

    public String getStartTime(){
        return getString("start_time");
    }

    public String getEndTime(){
        return getString("end_time");
    }

    public String getPhone(){
        return getString("phone");
    }

    public String getSummary(){
        return getString("summary");
    }

    public String getTitle(){
        return getString("title");
    }

    public String getSchool() { return getString("school"); }

    public String getLocation(){
        return getString("location");
    }

}
