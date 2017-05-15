
package com.clubconnect.isayyuhh.clubconnect.objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EventLink {

    @SerializedName("active")
    @Expose
    public Integer active;
    @SerializedName("eventID")
    @Expose
    public String eventID;
    @SerializedName("interests")
    @Expose
    public Integer interests;
    @SerializedName("people")
    @Expose
    public Integer people;

    public EventLink withActive(Integer active) {
        this.active = active;
        return this;
    }

    public EventLink withEventID(String eventID) {
        this.eventID = eventID;
        return this;
    }

    public EventLink withInterests(Integer interests) {
        this.interests = interests;
        return this;
    }

    public EventLink withPeople(Integer people) {
        this.people = people;
        return this;
    }
}
