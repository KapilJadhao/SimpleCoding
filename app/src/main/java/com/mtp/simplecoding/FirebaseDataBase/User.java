package com.mtp.simplecoding.FirebaseDataBase;

import com.google.firebase.database.IgnoreExtraProperties;



@IgnoreExtraProperties
public class User {

    public String name;
    public String email;
    public String mobile;

    // Default constructor required for calls to
    // DataSnapshot.getValue(User.class)
    public User() {
    }

    public User(String name, String email,String mobile) {
        this.name = name;
        this.email = email;
        this.mobile = mobile;

    }
}
