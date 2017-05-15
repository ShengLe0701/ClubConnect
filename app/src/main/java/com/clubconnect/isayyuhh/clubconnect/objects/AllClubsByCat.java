
package com.clubconnect.isayyuhh.clubconnect.objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AllClubsByCat {

    @SerializedName("\"Another One\"")
    @Expose
    public AnotherOne anotherOne;
    @SerializedName("\"STEM\"")
    @Expose
    public STEM sTEM;

    public AllClubsByCat withAnotherOne(AnotherOne anotherOne) {
        this.anotherOne = anotherOne;
        return this;
    }

    public AllClubsByCat withSTEM(STEM sTEM) {
        this.sTEM = sTEM;
        return this;
    }
}
