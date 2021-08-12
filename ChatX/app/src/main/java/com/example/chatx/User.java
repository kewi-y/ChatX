package com.example.chatx;

public class User  {
    String id;
    String name;
    String bio;
    String avatarId;
    public User(String id,String name,String bio,String avatarId){
        this.id = id;
        this.name = name;
        this.bio = bio;
        this.avatarId = avatarId;
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
    public String getAvatarId() { return avatarId;}
}
