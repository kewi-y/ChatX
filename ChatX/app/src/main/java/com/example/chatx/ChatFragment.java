package com.example.chatx;

import android.app.SyncNotedAppOp;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ChatFragment extends Fragment  {
    FirebaseDatabase db;
    DatabaseReference refs;
    RecyclerView chatView;
    ChatAdapter chatAdapter;
    ArrayList<Message> messages = new ArrayList<>();
    ImageButton buttonSendMessage;
    AsyncListenMessages asyncListenMessages;
    User user;
    EditText editMessage;
    ChatAdapter.ClickCallback clickCallback;
    public ChatFragment(User user) {
        db = FirebaseDatabase.getInstance();
        this.user = user;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        refs = db.getReference("messages");
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        editMessage = view.findViewById(R.id.editMessageFragment);
        chatView = view.findViewById(R.id.chatViewFragment);
        buttonSendMessage = view.findViewById(R.id.sendMessageButton);
        buttonSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message;
                message = editMessage.getText().toString();
                refs.push().setValue(new Message(message, user.getName(), user.getId(), null));
            }
        });
        clickCallback = new ChatAdapter.ClickCallback() {
            @Override
            public void onClickMessageItem(Message message) {
                Intent intent = new Intent(getContext(),ProfileChekActivity.class);
                intent.putExtra("userId",user.getId());
                startActivity(intent);
            }
        };
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        asyncListenMessages = new AsyncListenMessages();
        asyncListenMessages.execute();
    }


    class AsyncListenMessages extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            refs = db.getReference("messages");
            refs.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Message message;
                    messages.clear();
                    for(DataSnapshot children : snapshot.getChildren()){
                        message = new Message((String) children.child("content").getValue(),(String) children.child("user_nickname").getValue(),(String) children.child("user_id").getValue(),(String) children.child("message_id").getValue());
                        messages.add(message);
                    }
                    chatAdapter = new ChatAdapter(messages,clickCallback);
                    chatView.setAdapter(chatAdapter);
                    chatView.smoothScrollToPosition(messages.size() - 1);
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            return null;
        }
    }
}