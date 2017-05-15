
package com.clubconnect.isayyuhh.clubconnect.objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Events_new {

    @SerializedName("events")
    @Expose
    protected List<Event> events;

    public Events_new() {
        this.events = new ArrayList<>();
    }

    public Events_new(List<Event> events) {
        this.events = events;
    }

    public void add(Event event) {
        this.events.add(event);
    }

    public void delete(Event event) {
        for (Event current : this.events) {
            if (current.id.equals(event.id)) {
                this.events.remove(current);
                break;
            }
        }
    }

    public void clear() {
        this.events = new ArrayList<>();
    }

    public List<Event> list() {
        return Collections.unmodifiableList(this.events);
    }
}
