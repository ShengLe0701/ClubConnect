package com.clubconnect.isayyuhh.clubconnect.objects;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Club_new {

    @SerializedName("approved")
    @Expose
    public Boolean approved = false;
    @SerializedName("categories")
    @Expose
    public List<String> categories = new ArrayList<String>();
    @SerializedName("channelName")
    @Expose
    public String channelName;
    @SerializedName("clubReceivedNotifications")
    @Expose
    public List<List<String>> clubReceivedNotifications = new ArrayList<List<String>>();
    @SerializedName("clubReceiver")
    @Expose
    public String clubReceiver;
    @SerializedName("connected")
    @Expose
    public Integer connected;
    @SerializedName("days")
    @Expose
    public List<String> days = new ArrayList<String>();
    @SerializedName("email")
    @Expose
    public String email;
    @SerializedName("eventReceivers")
    @Expose
    public List<String> eventReceivers = new ArrayList<String>();
    @SerializedName("events")
    @Expose
    public List<String> events = new ArrayList<String>();
    @SerializedName("geoTagLocation")
    @Expose
    public String geoTagLocation;
    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("image")
    @Expose
    public String image;
    @SerializedName("interested")
    @Expose
    public List<String> interested = new ArrayList<String>();
    @SerializedName("location")
    @Expose
    public String location;
    @SerializedName("marketSize")
    @Expose
    public Integer marketSize;
    @SerializedName("phone")
    @Expose
    public String phone;
    @SerializedName("prevConnected")
    @Expose
    public Integer prevConnected;
    @SerializedName("receivedNotifications")
    @Expose
    public List<String> receivedNotifications = new ArrayList<String>();
    @SerializedName("school")
    @Expose
    public String school;
    @SerializedName("sentNotifications")
    @Expose
    public List<String> sentNotifications = new ArrayList<String>();
    @SerializedName("state")
    @Expose
    public String state;
    @SerializedName("subcategories")
    @Expose
    public List<String> subcategories = new ArrayList<String>();
    @SerializedName("summary")
    @Expose
    public String summary;
    @SerializedName("times")
    @Expose
    public List<String> times = new ArrayList<String>();
    @SerializedName("title")
    @Expose
    public String title;

    public Club_new Approved(Boolean approved) {
        this.approved = approved;
        return this;
    }

    public Club_new Categories(List<String> categories) {
        this.categories = categories;
        return this;
    }

    public Club_new ChannelName(String channelName) {
        this.channelName = channelName;
        return this;
    }

    public Club_new ClubReceivedNotifications(List<List<String>> clubReceivedNotifications) {
        this.clubReceivedNotifications = clubReceivedNotifications;
        return this;
    }

    public Club_new ClubReceiver(String clubReceiver) {
        this.clubReceiver = clubReceiver;
        return this;
    }

    public Club_new Connected(Integer connected) {
        this.connected = connected;
        return this;
    }

    public Club_new withDays(List<String> days) {
        this.days = days;
        return this;
    }

    public Club_new withEmail(String email) {
        this.email = email;
        return this;
    }

    public Club_new EventReceivers(List<String> eventReceivers) {
        this.eventReceivers = eventReceivers;
        return this;
    }

    public Club_new Events(List<String> events) {
        this.events = events;
        return this;
    }

    public Club_new GeoTagLocation(String geoTagLocation) {
        this.geoTagLocation = geoTagLocation;
        return this;
    }

    public Club_new Id(String id) {
        this.id = id;
        return this;
    }

    public Club_new Image(String image) {
        this.image = image;
        return this;
    }

    public Club_new Interested(List<String> interested) {
        this.interested = interested;
        return this;
    }

    public Club_new Location(String location) {
        this.location = location;
        return this;
    }

    public Club_new MarketSize(Integer marketSize) {
        this.marketSize = marketSize;
        return this;
    }

    public Club_new Phone(String phone) {
        this.phone = phone;
        return this;
    }

    public Club_new PrevConnected(Integer prevConnected) {
        this.prevConnected = prevConnected;
        return this;
    }

    public Club_new ReceivedNotifications(List<String> receivedNotifications) {
        this.receivedNotifications = receivedNotifications;
        return this;
    }

    public Club_new School(String school) {
        this.school = school;
        return this;
    }

    public Club_new SentNotifications(List<String> sentNotifications) {
        this.sentNotifications = sentNotifications;
        return this;
    }

    public Club_new State(String state) {
        this.state = state;
        return this;
    }

    public Club_new Subcategories(List<String> subcategories) {
        this.subcategories = subcategories;
        return this;
    }

    public Club_new Summary(String summary) {
        this.summary = summary;
        return this;
    }

    public Club_new Times(List<String> times) {
        this.times = times;
        return this;
    }

    public Club_new Title(String title) {
        this.title = title;
        return this;
    }

}
