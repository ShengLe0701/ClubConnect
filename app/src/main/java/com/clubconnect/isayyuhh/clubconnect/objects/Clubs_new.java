
package com.clubconnect.isayyuhh.clubconnect.objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Clubs_new {

    @SerializedName("KuszBuewXefcMnVH6Z0OJKjsYO72")
    @Expose
    public KuszBuewXefcMnVH6Z0OJKjsYO72 kuszBuewXefcMnVH6Z0OJKjsYO72;
    @SerializedName("club")
    @Expose
    public Club club;
    @SerializedName("clubs")
    @Expose
    protected List<Club> clubs;

    public Clubs_new() {
        this.clubs = new ArrayList<>();
    }

    public Clubs_new(List<Club> clubs) {
        this.clubs = clubs;
    }

    public Clubs_new withKuszBuewXefcMnVH6Z0OJKjsYO72(KuszBuewXefcMnVH6Z0OJKjsYO72 kuszBuewXefcMnVH6Z0OJKjsYO72) {
        this.kuszBuewXefcMnVH6Z0OJKjsYO72 = kuszBuewXefcMnVH6Z0OJKjsYO72;
        return this;
    }

    public Clubs_new withClub(Club club) {
        this.club = club;
        return this;
    }
}
