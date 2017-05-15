
package com.clubconnect.isayyuhh.clubconnect.objects;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BySubCat {

    @SerializedName("\"subCatName\"")
    @Expose
    public List<String> subCatName = new ArrayList<String>();

    public BySubCat withSubCatName(List<String> subCatName) {
        this.subCatName = subCatName;
        return this;
    }
}
