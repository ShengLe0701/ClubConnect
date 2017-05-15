
package com.clubconnect.isayyuhh.clubconnect.objects;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MapData {

    @SerializedName("allClubsByCat")
    @Expose
    public AllClubsByCat allClubsByCat;
    @SerializedName("tablesClusters")
    @Expose
    public List<TablesCluster> tablesClusters = new ArrayList<TablesCluster>();

    public MapData withAllClubsByCat(AllClubsByCat allClubsByCat) {
        this.allClubsByCat = allClubsByCat;
        return this;
    }

    public MapData withTablesClusters(List<TablesCluster> tablesClusters) {
        this.tablesClusters = tablesClusters;
        return this;
    }
}
