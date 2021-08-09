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
    public ChatAdapter(ArrayList<Message> messages){
        this.messages = messages;
    }
    public void addMessage(Message message){
        messages.add(message);
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
        String message,author;
        message = messages.get(position).getContent();
        author = messages.get(position).getUser_nickname();
        holder.setData(message,author);
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
