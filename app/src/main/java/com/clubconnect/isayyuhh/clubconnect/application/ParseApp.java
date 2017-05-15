package com.clubconnect.isayyuhh.clubconnect.application;

import android.app.Application;

import com.clubconnect.isayyuhh.clubconnect.objects.Club;
import com.clubconnect.isayyuhh.clubconnect.objects.Event;
import com.clubconnect.isayyuhh.clubconnect.parse.PCategory;
import com.clubconnect.isayyuhh.clubconnect.parse.PEvent;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseUser;

public class ParseApp extends Application{

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(this,
                "FNFWvQeyNksZyP1Kpml0W1JLBUT9Zhdu78K2vUOa",
                "b6NhSIwD82D2ORyqbKSOwYXSNtCMsiM9L8ztcFqR");

        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();

        defaultACL.setPublicReadAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);

        ParseObject.registerSubclass(PCategory.class);
        ParseObject.registerSubclass(PEvent.class);
        ParseObject.registerSubclass(Event.class);
        ParseObject.registerSubclass(Club.class);
    }
}
