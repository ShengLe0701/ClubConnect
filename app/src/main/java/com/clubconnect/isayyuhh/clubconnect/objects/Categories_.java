
package com.clubconnect.isayyuhh.clubconnect.objects;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Categories_ {

    @SerializedName("categories")
    @Expose
    public List<String> categories = new ArrayList<String>();

    public Categories_ withCategories(List<String> categories) {
        this.categories = categories;
        return this;
    }
}
