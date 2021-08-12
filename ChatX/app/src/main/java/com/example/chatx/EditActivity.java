package com.example.chatx;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditActivity extends AppCompatActivity {
    ImageButton saveButton;
    EditText reeditName,reeditBio;
    String textName, textBio;
    FirebaseAuth auth;
    FirebaseUser user;
    FirebaseDatabase db;
    DatabaseReference refs;
    DatabaseReference editRefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        Intent intent = getIntent();
        textName = intent.getStringExtra("name");
        textBio = intent.getStringExtra("bio");
        saveButton = findViewById(R.id.ButtonSave);
        reeditName = findViewById(R.id.reeditName);
        reeditBio = findViewById(R.id.reeditBio);
        reeditName.setText(textName);
        reeditBio.setText(textBio);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        db = FirebaseDatabase.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();
        refs = db.getReference("users");
        refs.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot children : snapshot.getChildren()){
                    if(user.getUid().equals(children.child("id").getValue())){
                        editRefs = children.getRef();
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void OnSaveEdit(View view){
        String textEditName, textEditBio;
        textEditName = reeditName.getText().toString();
        textEditBio = reeditBio.getText().toString();
        if(!textEditBio.equals("") || !textEditName.equals("")) {
            editRefs.child("name").setValue(textEditName);
            editRefs.child("bio").setValue(textEditBio);
            Toast.makeText(this,"Saved",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(EditActivity.this,SignInTaskActivity.class);
            startActivity(intent);
        }
        else {
            Toast.makeText(this,"Fill the fields",Toast.LENGTH_SHORT).show();
        }
    }
}