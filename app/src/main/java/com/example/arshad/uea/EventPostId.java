package com.example.arshad.uea;

import android.support.annotation.NonNull;

import com.google.firebase.firestore.Exclude;

public class EventPostId {

    @Exclude
    public String EventPostId;

    public <T extends EventPostId> T withId(@NonNull final String id) {
        this.EventPostId = id;
        return (T) this;
    }

}