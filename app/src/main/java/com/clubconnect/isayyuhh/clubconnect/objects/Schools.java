
package com.clubconnect.isayyuhh.clubconnect.objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Schools {

    @SerializedName("school")
    @Expose
    public School school;

    public Schools withSchool(School school) {
        this.school = school;
        return this;
    }
}
