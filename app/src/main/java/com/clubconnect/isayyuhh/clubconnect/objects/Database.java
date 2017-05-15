
package com.clubconnect.isayyuhh.clubconnect.objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Database {

    @SerializedName("global")
    @Expose
    public Global global;
    @SerializedName("schools")
    @Expose
    public Schools schools;
    @SerializedName("users")
    @Expose
    public Users users;

    public Database withGlobal(Global global) {
        this.global = global;
        return this;
    }

    public Database withSchools(Schools schools) {
        this.schools = schools;
        return this;
    }

    public Database withUsers(Users users) {
        this.users = users;
        return this;
    }

}
