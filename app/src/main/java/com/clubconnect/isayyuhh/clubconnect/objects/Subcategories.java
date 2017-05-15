
package com.clubconnect.isayyuhh.clubconnect.objects;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Subcategories {

    @SerializedName("subcategories")
    @Expose
    public List<String> subcategories = new ArrayList<String>();

    public Subcategories withSubcategories(List<String> subcategories) {
        this.subcategories = subcategories;
        return this;
    }
}
