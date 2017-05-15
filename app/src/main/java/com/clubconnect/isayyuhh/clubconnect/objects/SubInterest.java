package com.clubconnect.isayyuhh.clubconnect.objects;

import java.io.Serializable;

/**
 * Created by faviotorres on 2/14/16.
 */
public class SubInterest implements Serializable {
    public String name;
    public boolean added;

    public SubInterest() {}

    public SubInterest(String name) {
        this.name = name;
    }
}