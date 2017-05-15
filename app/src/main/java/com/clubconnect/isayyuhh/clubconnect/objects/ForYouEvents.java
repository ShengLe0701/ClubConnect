
package com.clubconnect.isayyuhh.clubconnect.objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ForYouEvents {

    @SerializedName("eventLink")
    @Expose
    public EventLink eventLink;

    public ForYouEvents withEventLink(EventLink eventLink) {
        this.eventLink = eventLink;
        return this;
    }

}
