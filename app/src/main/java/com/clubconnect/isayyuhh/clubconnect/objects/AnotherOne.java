
package com.clubconnect.isayyuhh.clubconnect.objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AnotherOne {

    @SerializedName("0")
    @Expose
    public String _0;
    @SerializedName("bySubCat")
    @Expose
    public BySubCat bySubCat;

    public AnotherOne with0(String _0) {
        this._0 = _0;
        return this;
    }

    public AnotherOne withBySubCat(BySubCat bySubCat) {
        this.bySubCat = bySubCat;
        return this;
    }
}
