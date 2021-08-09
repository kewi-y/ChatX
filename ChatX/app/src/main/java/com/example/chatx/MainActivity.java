package com.example.chatx;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    FirebaseDatabase db;
    DatabaseReference refs;
    EditText messageEdit;
    ArrayList<Message>messages = new ArrayList<>();
    ChatAdapter chatAdapter;
    RecyclerView chatView;
    UpdateMessages updateMessages = new UpdateMessages();
    User user;
    Intent intent;
    boolean hasBeenUpdated = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intent = getIntent();
        user = new User(intent.getStringExtra("id"),intent.getStringExtra("name"),intent.getStringExtra("bio"));
        db = FirebaseDatabase.getInstance();
        refs = db.getReference("messages");
        messageEdit = findViewById(R.id.editMessage);
        chatView = findViewById(R.id.chatView);
        chatAdapter = new ChatAdapter(messages);
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateMessages.execute();
    }
    public void onSendMessage(View view){
        refs.push().setValue(new Message(messageEdit.getText().toString(),user.name,null));
    }
    class UpdateMessages extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            refs = db.getReference("messages");
            refs.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    try {
                        Thread.sleep(200);
                        hasBeenUpdated = false;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if(messages.size() != snapshot.getChildrenCount()){
                        UpdateMessagesList();
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
        }
    }
    public void UpdateMessagesList(){
        if(hasBeenUpdated == false) {
            messages.clear();
            refs.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Message message;
                    long count = snapshot.getChildrenCount();
                    Log.d("count State ",String.valueOf(count));
                    for (DataSnapshot children : snapshot.getChildren()) {
                        message = new Message((String) children.child("content").getValue(), (String) children.child("user_nickname").getValue(), (String) children.child("message_id").getValue());
                        messages.add(message);

                    }
                    chatAdapter = new ChatAdapter(messages);
                    chatView.setAdapter(chatAdapter);
                    chatView.smoothScrollToPosition(messages.size() - 1);
                    hasBeenUpdated = true;
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }
    }
}