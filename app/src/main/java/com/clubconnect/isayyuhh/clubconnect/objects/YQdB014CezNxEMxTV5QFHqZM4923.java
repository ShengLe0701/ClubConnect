
package com.clubconnect.isayyuhh.clubconnect.objects;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class YQdB014CezNxEMxTV5QFHqZM4923 {

    @SerializedName("categories")
    @Expose
    public List<String> categories = new ArrayList<String>();
    @SerializedName("clubId")
    @Expose
    public String clubId;
    @SerializedName("email")
    @Expose
    public String email;
    @SerializedName("forYouClubs")
    @Expose
    public ForYouClubs forYouClubs;
    @SerializedName("forYouEvents")
    @Expose
    public ForYouEvents forYouEvents;
    @SerializedName("isClub")
    @Expose
    public Boolean isClub;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("phone")
    @Expose
    public Integer phone;
    @SerializedName("savedClubs")
    @Expose
    public List<List<String>> savedClubs = new ArrayList<List<String>>();
    @SerializedName("savedEvents")
    @Expose
    public List<List<String>> savedEvents = new ArrayList<List<String>>();
    @SerializedName("school")
    @Expose
    public String school;
    @SerializedName("subcategories")
    @Expose
    public List<String> subcategories = new ArrayList<String>();
    @SerializedName("uid")
    @Expose
    public String uid;
    @SerializedName("userName")
    @Expose
    public String userName;

    public YQdB014CezNxEMxTV5QFHqZM4923 withCategories(List<String> categories) {
        this.categories = categories;
        return this;
    }

    public YQdB014CezNxEMxTV5QFHqZM4923 withClubId(String clubId) {
        this.clubId = clubId;
        return this;
    }

    public YQdB014CezNxEMxTV5QFHqZM4923 withEmail(String email) {
        this.email = email;
        return this;
    }

    public YQdB014CezNxEMxTV5QFHqZM4923 withForYouClubs(ForYouClubs forYouClubs) {
        this.forYouClubs = forYouClubs;
        return this;
    }

    public YQdB014CezNxEMxTV5QFHqZM4923 withForYouEvents(ForYouEvents forYouEvents) {
        this.forYouEvents = forYouEvents;
        return this;
    }

    public YQdB014CezNxEMxTV5QFHqZM4923 withIsClub(Boolean isClub) {
        this.isClub = isClub;
        return this;
    }

    public YQdB014CezNxEMxTV5QFHqZM4923 withName(String name) {
        this.name = name;
        return this;
    }

    public YQdB014CezNxEMxTV5QFHqZM4923 withPhone(Integer phone) {
        this.phone = phone;
        return this;
    }

    public YQdB014CezNxEMxTV5QFHqZM4923 withSavedClubs(List<List<String>> savedClubs) {
        this.savedClubs = savedClubs;
        return this;
    }

    public YQdB014CezNxEMxTV5QFHqZM4923 withSavedEvents(List<List<String>> savedEvents) {
        this.savedEvents = savedEvents;
        return this;
    }

    public YQdB014CezNxEMxTV5QFHqZM4923 withSchool(String school) {
        this.school = school;
        return this;
    }

    public YQdB014CezNxEMxTV5QFHqZM4923 withSubcategories(List<String> subcategories) {
        this.subcategories = subcategories;
        return this;
    }

    public YQdB014CezNxEMxTV5QFHqZM4923 withUid(String uid) {
        this.uid = uid;
        return this;
    }

    public YQdB014CezNxEMxTV5QFHqZM4923 withUserName(String userName) {
        this.userName = userName;
        return this;
    }
}
