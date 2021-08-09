package com.example.chatx;

public class User  {
    String id;
    String name;
    String bio;
    public User(String id,String name,String bio){
        this.id = id;
        this.name = name;
        this.bio = bio;
    }

    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getBio(){
        return bio;
    }
}
