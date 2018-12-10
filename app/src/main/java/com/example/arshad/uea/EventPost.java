package com.example.arshad.uea;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class EventPost extends EventPostId {

    public String user_id, name, date, time, venue, desc,image_url;
    public Date timestamp;

    public EventPost() {}


    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public EventPost(String user_id, String name, String date, String time, String venue, String desc, String image_url, Date timestamp) {
        this.user_id = user_id;
        this.name = name;
        this.date = date;
        this.time = time;
        this.venue = venue;
        this.desc = desc;
        this.image_url = image_url;
        this.timestamp = timestamp;
    }







}