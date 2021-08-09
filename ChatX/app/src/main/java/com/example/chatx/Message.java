package com.example.chatx;

import java.util.Random;

public class Message {
    private String content;
    private String user_nickname;
    private String message_id;
    private Random random;

    public Message(String content,String user_nickname,String message_id){
        random = new Random();
        this.content = content;
        this.user_nickname = user_nickname;
        if(message_id == null) {
            this.message_id = String.valueOf(random.nextInt());
        }
        else{
            this.message_id = message_id;
        }
    }


    public String getContent() {
        return content;
    }

    public String getMessage_id() {
        return message_id;
    }

    public String getUser_nickname() {
        return user_nickname;
    }
}
