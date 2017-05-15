
package com.clubconnect.isayyuhh.clubconnect.objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class School {

    @SerializedName("clubs")
    @Expose
    public Clubs clubs;
    @SerializedName("events")
    @Expose
    public Events events;

    public School withClubs(Clubs clubs) {
        this.clubs = clubs;
        return this;
    }

    public School withEvents(Events events) {
        this.events = events;
        return this;
    }
}
