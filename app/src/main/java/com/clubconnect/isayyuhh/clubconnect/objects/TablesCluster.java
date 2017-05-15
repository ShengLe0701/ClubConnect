
package com.clubconnect.isayyuhh.clubconnect.objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TablesCluster {

    @SerializedName("col")
    @Expose
    public Integer col;
    @SerializedName("p1Lat")
    @Expose
    public Double p1Lat;
    @SerializedName("p1Long")
    @Expose
    public Double p1Long;
    @SerializedName("p2Lat")
    @Expose
    public Double p2Lat;
    @SerializedName("p2Long")
    @Expose
    public Double p2Long;
    @SerializedName("rows")
    @Expose
    public Integer rows;

    public TablesCluster withCol(Integer col) {
        this.col = col;
        return this;
    }

    public TablesCluster withP1Lat(Double p1Lat) {
        this.p1Lat = p1Lat;
        return this;
    }

    public TablesCluster withP1Long(Double p1Long) {
        this.p1Long = p1Long;
        return this;
    }

    public TablesCluster withP2Lat(Double p2Lat) {
        this.p2Lat = p2Lat;
        return this;
    }

    public TablesCluster withP2Long(Double p2Long) {
        this.p2Long = p2Long;
        return this;
    }

    public TablesCluster withRows(Integer rows) {
        this.rows = rows;
        return this;
    }
}
