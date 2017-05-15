package com.clubconnect.isayyuhh.clubconnect.objects;

/**
 * Created by joshuapena on 7/29/16.
 */


import com.clubconnect.isayyuhh.clubconnect.parse.PEvent;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

import com.clubconnect.isayyuhh.clubconnect.objects.Notification;
import com.clubconnect.isayyuhh.clubconnect.objects.Event;

public class ClubInfo {
    // club variables
    public int prevConnections;
    public int connected;
    public int marketSize;
    public boolean approved;
    public String  imageURL;
    public String time;
    public String summary;
    public String phone;
    public String location;
    public String day;
    public String title;
    public String email;
    public String channelName;
    public String clubReceiver;
    public String school;
    public List<String> subcategories;
    public List<String> categories;
    public List<ParseObject> interested;
    public ParseRelation<ParseObject> clubEvents;
    public List<Notification> sentNotifications;
    public List<Notification> clubReceivedNotifications;
    public List<Notification> receivedNotifications;

    // user variables
    public String clubName;

    public ClubInfo(ParseObject club) {
        try {
            JSONArray categoriesArray = club.getJSONArray("categories");
            this.categories = new ArrayList<>();
            if (categoriesArray != null) {
                for (int i = 0; i < categoriesArray.length(); i++) {
                    this.categories.add(categoriesArray.get(i).toString());
                }
            }

            JSONArray subcategoriesArray = club.getJSONArray("subcategories");
            this.subcategories = new ArrayList<>();
            if (subcategoriesArray != null) {
                for (int i = 0; i < subcategoriesArray.length(); i++) {
                    this.subcategories.add(subcategoriesArray.get(i).toString());
                }
            }

            JSONArray sentNotifications = club.getJSONArray("sentNotifications");
            this.sentNotifications = new ArrayList<>();
            if (sentNotifications != null) {
                for (int i = 0; i < sentNotifications.length(); i++) {
                    JSONArray message = sentNotifications.getJSONArray(i);
                    Notification tmp = new Notification(message.get(0).toString(),
                            message.get(1).toString());
                    this.sentNotifications.add(tmp);
                }
            }

            JSONArray receivedNotifications = club.getJSONArray("receivedNotifications");
            this.receivedNotifications = new ArrayList<>();
            if (receivedNotifications != null) {
                for (int i = 0; i < receivedNotifications.length(); i++) {
                    JSONArray message = receivedNotifications.getJSONArray(i);
                    Notification tmp = new Notification(message.get(0).toString(),
                            message.get(1).toString());
                    this.receivedNotifications.add(tmp);
                }
            }

            JSONArray clubReceivedNotifications = club.getJSONArray("clubReceivedNotifications");
            this.clubReceivedNotifications = new ArrayList<>();
            if (clubReceivedNotifications != null) {
                for (int i = 0; i < clubReceivedNotifications.length(); i++) {
                    JSONArray message = clubReceivedNotifications.getJSONArray(i);
                    Notification tmp = new Notification(message.get(0).toString(),
                            message.get(1).toString());
                    this.clubReceivedNotifications.add(tmp);
                }
            }

            ParseQuery<ParseObject> query = club.getRelation("Interested").getQuery();
            query.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    interested = objects;
                }
            });

            this.clubEvents = club.getRelation("ClubEvents");

            this.prevConnections = club.getInt("prevconnections");
            this.connected = club.getInt("connected");
            this.time = club.getJSONArray("times").get(0).toString();
            this.summary = club.getString("summary");
            this.phone = club.getString("phone");

            this.imageURL = club.getParseFile("clubCardImage").getUrl();
            this.location = club.getString("location");
            this.day = club.getJSONArray("days").get(0).toString();
            this.approved = club.getBoolean("approved");
            this.title = club.getString("title");
            this.email = club.getString("email");
            this.marketSize = club.getInt("marketSize");
            this.channelName = club.getString("channel_name");
            this.clubReceiver = club.getString("club_receiver");
            this.school = club.getString("school");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}