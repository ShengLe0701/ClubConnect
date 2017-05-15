
package com.clubconnect.isayyuhh.clubconnect.objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Global {

    @SerializedName("categories")
    @Expose
    public Categories categories;
    @SerializedName("controlPanel")
    @Expose
    public ControlPanel controlPanel;

    public Global withCategories(Categories categories) {
        this.categories = categories;
        return this;
    }

    public Global withControlPanel(ControlPanel controlPanel) {
        this.controlPanel = controlPanel;
        return this;
    }
}
