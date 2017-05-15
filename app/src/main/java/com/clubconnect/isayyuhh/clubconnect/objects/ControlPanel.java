
package com.clubconnect.isayyuhh.clubconnect.objects;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ControlPanel {

    @SerializedName("clubSchools")
    @Expose
    public List<String> clubSchools = new ArrayList<String>();
    @SerializedName("otherConstants")
    @Expose
    public List<String> otherConstants = new ArrayList<String>();
    @SerializedName("studentSchools")
    @Expose
    public List<String> studentSchools = new ArrayList<String>();

    public ControlPanel withClubSchools(List<String> clubSchools) {
        this.clubSchools = clubSchools;
        return this;
    }

    public ControlPanel withOtherConstants(List<String> otherConstants) {
        this.otherConstants = otherConstants;
        return this;
    }

    public ControlPanel withStudentSchools(List<String> studentSchools) {
        this.studentSchools = studentSchools;
        return this;
    }
}
