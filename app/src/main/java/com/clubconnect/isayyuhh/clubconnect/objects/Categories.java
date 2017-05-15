
package com.clubconnect.isayyuhh.clubconnect.objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Categories {

    @SerializedName("category")
    @Expose
    public Category category;
    @SerializedName("clubsByCat")
    @Expose
    public ClubsByCat clubsByCat;
    @SerializedName("mapData")
    @Expose
    public MapData mapData;

    public Categories withCategory(Category category) {
        this.category = category;
        return this;
    }

    public Categories withClubsByCat(ClubsByCat clubsByCat) {
        this.clubsByCat = clubsByCat;
        return this;
    }

    public Categories withMapData(MapData mapData) {
        this.mapData = mapData;
        return this;
    }
}
