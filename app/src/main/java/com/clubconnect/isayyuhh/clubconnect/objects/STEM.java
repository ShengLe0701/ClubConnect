
package com.clubconnect.isayyuhh.clubconnect.objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class STEM {

    @SerializedName("0")
    @Expose
    public String _0;
    @SerializedName("bySubCat")
    @Expose
    public BySubCat_ bySubCat;

    public STEM with0(String _0) {
        this._0 = _0;
        return this;
    }

    public STEM withBySubCat(BySubCat_ bySubCat) {
        this.bySubCat = bySubCat;
        return this;
    }
}
