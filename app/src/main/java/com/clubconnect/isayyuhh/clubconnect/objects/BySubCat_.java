
package com.clubconnect.isayyuhh.clubconnect.objects;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BySubCat_ {

    @SerializedName("\"subCatName\"")
    @Expose
    public List<String> subCatName = new ArrayList<String>();

    public BySubCat_ withSubCatName(List<String> subCatName) {
        this.subCatName = subCatName;
        return this;
    }
}
