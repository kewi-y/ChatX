package com.example.chatx;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatHolder> {
    private ArrayList<Message> messages;
    interface ClickCallback{
        public void onClickMessageItem(Message message);
    }
    ClickCallback clickCallback;
    public ChatAdapter(ArrayList<Message> messages, ClickCallback clickCallback){
        this.clickCallback = clickCallback;
        this.messages = messages;
    }
    @NonNull
    @Override
    public ChatHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.message_item,parent,false);
        return new ChatHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatAdapter.ChatHolder holder, int position) {
        String messageString,authorString;
        messageString = messages.get(position).getContent();
        authorString = messages.get(position).getUser_nickname();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message message = new Message(messages.get(position).getContent(),messages.get(position).getUser_nickname(),messages.get(position).getUser_id(),messages.get(position).getMessage_id());
                clickCallback.onClickMessageItem(message);
            }
        });
        holder.setData(messageString,authorString);
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    class ChatHolder extends RecyclerView.ViewHolder {
        TextView authorView;
        TextView contentView;
        public ChatHolder(@NonNull View itemView) {
            super(itemView);
            authorView = itemView.findViewById(R.id.AuthorView);
            contentView = itemView.findViewById(R.id.ContentView);
        }
        public void setData(String message,String author){
            authorView.setText(author);
            contentView.setText(message);
        }
    }
}
