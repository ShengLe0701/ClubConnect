package com.clubconnect.isayyuhh.clubconnect.objects;

/**
 * Created by joshuapena on 7/29/16.
 */

public class Notification {
    public String message;
    public String date;
    public Notification(String message, String date) {
        this.message = message;
        this.date = date;
    }

    public String toString() {
        return this.message;
    }
}