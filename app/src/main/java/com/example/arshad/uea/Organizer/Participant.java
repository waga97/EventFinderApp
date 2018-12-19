package com.example.arshad.uea.Organizer;

public class Participant {

    public String name;
    public String userId;


    public Participant (){


    }

    public Participant(String name,String userId) {
        this.name = name;
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}