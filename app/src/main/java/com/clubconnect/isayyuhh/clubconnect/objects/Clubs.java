package com.clubconnect.isayyuhh.clubconnect.objects;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by isayyuhh on 4/22/16.
 */
public class Clubs {
    protected List<Club> clubs;

    public Clubs() {
        this.clubs = new ArrayList<>();
    }

    public Clubs(List<Club> clubs) {
        this.clubs = clubs;
    }

    public void add(Club club) {
        this.clubs.add(club);
    }

    public void delete(Club club) {
        for (Club current : this.clubs) {
            if (current.id.equals(club.id)) {
                this.clubs.remove(current);
                break;
            }
        }
    }

    public void clear() {
        this.clubs = new ArrayList<>();
    }

    public List<Club> list() {
        return Collections.unmodifiableList(this.clubs);
    }
}