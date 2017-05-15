
package com.clubconnect.isayyuhh.clubconnect.objects;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Category {

    @SerializedName("school")
    @Expose
    public List<String> school = new ArrayList<String>();
    @SerializedName("subcategories")
    @Expose
    public List<String> subcategories = new ArrayList<String>();
    @SerializedName("title")
    @Expose
    public String title;

    public Category withSchool(List<String> school) {
        this.school = school;
        return this;
    }

    public Category withSubcategories(List<String> subcategories) {
        this.subcategories = subcategories;
        return this;
    }

    public Category withTitle(String title) {
        this.title = title;
        return this;
    }
}
