
package com.clubconnect.isayyuhh.clubconnect.objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClubLink {

    @SerializedName("active")
    @Expose
    public Integer active;
    @SerializedName("clubID")
    @Expose
    public String clubID;
    @SerializedName("interest")
    @Expose
    public Integer interest;
    @SerializedName("people")
    @Expose
    public Integer people;

    public ClubLink withActive(Integer active) {
        this.active = active;
        return this;
    }

    public ClubLink withClubID(String clubID) {
        this.clubID = clubID;
        return this;
    }

    public ClubLink withInterest(Integer interest) {
        this.interest = interest;
        return this;
    }

    public ClubLink withPeople(Integer people) {
        this.people = people;
        return this;
    }

}
