package com.clubconnect.isayyuhh.clubconnect.callbacks;

import com.clubconnect.isayyuhh.clubconnect.objects.Events;
import com.clubconnect.isayyuhh.clubconnect.objects.Notification;
import com.parse.ParseObject;
import com.parse.ParseRelation;

import java.util.List;

/**
 * Created by joshuapena on 7/29/16.
 */

public interface CallbackAirplane {
    // for events fragment
    ParseRelation<ParseObject> getEventArr();

    // for message fragment
    List<Notification> getNotificationArr();

    // for analytics fragment
    // for connections
    int getConnected();
    // for potential
    int getMarketSize();
    // for people page
    List<ParseObject> getInterested();

    String getImageURL();
    String getDate();
    String getTime();
    String getClubTitle();
    String getLocation();
    String getStatus();
}
