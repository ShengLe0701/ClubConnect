package com.clubconnect.isayyuhh.clubconnect.objects;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by isayyuhh on 4/22/16.
 */
public class Events {
    protected List<Event> events;

    public Events() {
        this.events = new ArrayList<>();
    }

    public Events(List<Event> events) {
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