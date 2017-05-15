
package com.clubconnect.isayyuhh.clubconnect.objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ForYouClubs {

    @SerializedName("clubLink")
    @Expose
    public ClubLink clubLink;

    public ForYouClubs withClubLink(ClubLink clubLink) {
        this.clubLink = clubLink;
        return this;
    }
}
