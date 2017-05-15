package com.clubconnect.isayyuhh.clubconnect.objects;

import android.util.Log;

import com.clubconnect.isayyuhh.clubconnect.parse.PEvent;
import com.google.gson.Gson;
import com.parse.ParseClassName;
import com.parse.ParseObject;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

@ParseClassName("Event")
public class Event extends ParseObject {
    public List<String> subcategories;
    public String imageURL;
    public String day;
    public String email;
    public String date;
    public String startTime;
    public String endTime;
    public String phone;
    public String summary;
    public String title;
    public String school;
    public String location;
    public String id;
    public String fullDate;

    public Event() {}

    public Event(PEvent pEvent) {
        try {
            this.subcategories = new ArrayList<>();
            JSONArray subcategoriesArray = pEvent.getSubCategories();

            if (subcategoriesArray != null) {
                for (int i = 0; i < subcategoriesArray.length(); i++) {
                    this.subcategories.add(subcategoriesArray.get(i).toString());
                }
            }

            this.imageURL = pEvent.getImage().getUrl();
            this.day = pEvent.getDay();
            this.email = pEvent.getEmail();
            this.date = pEvent.getDate();
            this.startTime = pEvent.getStartTime();
            this.endTime = pEvent.getEndTime();
            this.phone = pEvent.getPhone();
            this.summary = pEvent.getSummary();
            this.title = pEvent.getTitle();
            this.school = pEvent.getSchool();
            this.location = pEvent.getLocation();
            this.id = pEvent.getObjectId();
            this.fullDate = this.day + ": " + this.date + ", " + this.startTime + "-" + this.endTime;
        } catch (JSONException je) {
            Log.e("ERROR", "Event Json Exception");
        }
    }

    public void set() {
        try {
            this.subcategories = new ArrayList<>();
            JSONArray subcategoriesArray = getJSONArray("subcategories");

            if (subcategoriesArray != null) {
                for (int i = 0; i < subcategoriesArray.length(); i++) {
                    this.subcategories.add(subcategoriesArray.get(i).toString());
                }
            }

            this.imageURL = getParseFile("clubCardImage").getUrl();
            this.day = getString("day");
            this.email = getString("email");
            this.date = getString("Date");
            this.startTime = getString("start_time");
            this.endTime = getString("end_time");
            this.phone = getString("phone");
            this.summary = getString("summary");
            this.title = getString("title");
            this.school = getString("school");
            this.location = getString("location");
            this.id = getObjectId();
            this.fullDate = this.day + ": " + this.date + ", " + this.startTime + "-" + this.endTime;
        } catch (JSONException je) {
            Log.e("ERROR", "Event Json Exception");
        }
    }
}