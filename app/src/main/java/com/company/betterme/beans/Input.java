package com.company.betterme.beans;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Input extends RealmObject {

    public Input(String what, long added, long when, boolean completed) {
        this.what = what;
        this.added = added;
        this.when = when;
        this.completed = completed;
    }

    //the goal itself
    private String what;

    @PrimaryKey
    //the time it is added
    private long added;

    //the time it is due
    private long when;

    //if the goal is completed or not
    private boolean completed;


    //default constructor
    public Input() {
    }


    //getters and setters

    public String getWhat() {
        return what;
    }

    public void setWhat(String what) {
        this.what = what;
    }

    public long getAdded() {
        return added;
    }

    public void setAdded(long added) {
        this.added = added;
    }

    public long getWhen() {
        return when;
    }

    public void setWhen(long when) {
        this.when = when;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
