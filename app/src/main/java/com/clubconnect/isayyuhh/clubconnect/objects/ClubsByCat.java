
package com.clubconnect.isayyuhh.clubconnect.objects;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClubsByCat {

    @SerializedName("STEM")
    @Expose
    public List<String> sTEM = new ArrayList<String>();

    public ClubsByCat withSTEM(List<String> sTEM) {
        this.sTEM = sTEM;
        return this;
    }

}
