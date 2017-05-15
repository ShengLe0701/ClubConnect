package com.clubconnect.isayyuhh.clubconnect.objects;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

@ParseClassName("Club")
public class Club extends ParseObject{

    public ParseQuery<ParseObject> events;
    public ParseRelation<ParseUser> users;
    public List<String> categories;
    public List<String> subcategories;
    public String day;
    public String email;
    public String title;
    public String location;
    public String imageURL;
    public String id;
    public String time;
    public String summary;
    public String date;
    public String phone;
    public String school;
    public int matching;

    public Club() {}

    public void set() {
        try {
            JSONArray categoriesArray = this.getJSONArray("categories");
            JSONArray subcategoriesArray = this.getJSONArray("subcategories");

            this.categories = new ArrayList<>();
            if (categoriesArray != null) {
                for (int i = 0; i < categoriesArray.length(); i++) {
                    this.categories.add(categoriesArray.get(i).toString());
                }
            }

            this.subcategories = new ArrayList<>();
            if (subcategoriesArray != null) {
                for (int i = 0; i < subcategoriesArray.length(); i++) {
                    this.subcategories.add(subcategoriesArray.get(i).toString());
                }
            }

            this.phone = this.getString("phone");
            this.school = this.getString("school");
            this.day = this.getJSONArray("days").get(0).toString();
            this.time = this.getJSONArray("times").get(0).toString();
            this.email = this.getString("email");
            this.title = this.getString("title");
            this.location = this.getString("location");
            this.id = this.getObjectId();
            this.imageURL = this.getParseFile("clubCardImage").getUrl();
            this.summary = this.getString("summary");
            this.date = this.day + " @ " + this.time;
            this.events = this.getRelation("ClubEvents").getQuery();
            this.users = this.getRelation("Interested");
            this.matching = 0;
        } catch (Exception je) {
            je.printStackTrace();
        }
    }

    public void setMatching(List<String> userSubcategories) {
        for (String subcategory : this.subcategories) {
            for (String userSubcategory : userSubcategories) {
                if (subcategory.equals(userSubcategory)) this.matching++;
            }
        }
    }
}