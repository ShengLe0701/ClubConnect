
package com.clubconnect.isayyuhh.clubconnect.objects;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Event_new {

    @SerializedName("categories")
    @Expose
    public List<String> categories = new ArrayList<String>();
    @SerializedName("channelName")
    @Expose
    public String channelName;
    @SerializedName("connected")
    @Expose
    public Integer connected;
    @SerializedName("email")
    @Expose
    public String email;
    @SerializedName("endDayAndTime")
    @Expose
    public List<String> endDayAndTime = new ArrayList<String>();
    @SerializedName("eventReceiver")
    @Expose
    public String eventReceiver;
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
    @SerializedName("receivedNotifications")
    @Expose
    public List<List<String>> receivedNotifications = new ArrayList<List<String>>();
    @SerializedName("school")
    @Expose
    public String school;
    @SerializedName("sentNotifications")
    @Expose
    public List<List<String>> sentNotifications = new ArrayList<List<String>>();
    @SerializedName("startDayAndTime")
    @Expose
    public List<String> startDayAndTime = new ArrayList<String>();
    @SerializedName("state")
    @Expose
    public String state;
    @SerializedName("subcategories")
    @Expose
    public List<String> subcategories = new ArrayList<String>();
    @SerializedName("summary")
    @Expose
    public String summary;
    @SerializedName("title")
    @Expose
    public String title;

    public Event_new() {}

    public Event_new Categories(List<String> categories) {
        this.categories = categories;
        return this;
    }

    public Event_new ChannelName(String channelName) {
        this.channelName = channelName;
        return this;
    }

    public Event_new Connected(Integer connected) {
        this.connected = connected;
        return this;
    }

    public Event_new Email(String email) {
        this.email = email;
        return this;
    }

    public Event_new EndDayAndTime(List<String> endDayAndTime) {
        this.endDayAndTime = endDayAndTime;
        return this;
    }

    public Event_new EventReceiver(String eventReceiver) {
        this.eventReceiver = eventReceiver;
        return this;
    }

    public Event_new Id(String id) {
        this.id = id;
        return this;
    }

    public Event_new Image(String image) {
        this.image = image;
        return this;
    }

    public Event_new Interested(List<String> interested) {
        this.interested = interested;
        return this;
    }

    public Event_new Location(String location) {
        this.location = location;
        return this;
    }

    public Event_new MarketSize(Integer marketSize) {
        this.marketSize = marketSize;
        return this;
    }

    public Event_new Phone(String phone) {
        this.phone = phone;
        return this;
    }

    public Event_new ReceivedNotifications(List<List<String>> receivedNotifications) {
        this.receivedNotifications = receivedNotifications;
        return this;
    }

    public Event_new School(String school) {
        this.school = school;
        return this;
    }

    public Event_new SentNotifications(List<List<String>> sentNotifications) {
        this.sentNotifications = sentNotifications;
        return this;
    }

    public Event_new StartDayAndTime(List<String> startDayAndTime) {
        this.startDayAndTime = startDayAndTime;
        return this;
    }

    public Event_new State(String state) {
        this.state = state;
        return this;
    }

    public Event_new Subcategories(List<String> subcategories) {
        this.subcategories = subcategories;
        return this;
    }

    public Event_new Summary(String summary) {
        this.summary = summary;
        return this;
    }

    public Event_new Title(String title) {
        this.title = title;
        return this;
    }
}
